package fr.eni.encheres.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class AccueilServlet
 */
@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		if (utilisateur != null) {
			utilisateur = utilisateurManager.getById(utilisateur.getNoUtilisateur());
			session.setAttribute("isConnected", utilisateur);
		}

		CategorieManager categorieManager = new CategorieManager();
		ArticleManager articleManager = new ArticleManager();
		articleManager.majEtatVenteAll();

		List<Article> listArticle = articleManager.getAllArticleEnchere();
		// Je récupère uniquement les categories présentes dans la liste articles :
		Set<Integer> categorieId = new HashSet<>();
		for (Article article : listArticle) {
			Categorie categorie = article.getCategorie();
			if (categorie != null) {
				int noCategorie = categorie.getNoCategorie();
				categorieId.add(noCategorie);
			}
		}

		List<Categorie> categories = new ArrayList<>();
		for (int noCategorie : categorieId) {
			Categorie categorie = (Categorie) categorieManager.getById(noCategorie);
			if (categorie != null) {
				categories.add(categorie);
			}
		}
		String rechercheStr = request.getParameter("recherche");
		String categorieStr = request.getParameter("listCategorie");
		if (categorieStr != null && rechercheStr != null) {
			Categorie categorieSelected = categorieManager.getById(Integer.parseInt(categorieStr));
			request.setAttribute("selectedCategorie", categorieSelected);
			request.setAttribute("rechercheTape", rechercheStr);

		} else {
			if (categorieStr != null) {

			} else if (rechercheStr != null) {

			}
		}

		EnchereManager enchereManager = new EnchereManager();
		List<Encheres> listEncheres = enchereManager.getAll();

		request.setAttribute("encheres", listEncheres);
		request.setAttribute("listCategorie", categories);
		request.setAttribute("listArticle", listArticle);
		request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
