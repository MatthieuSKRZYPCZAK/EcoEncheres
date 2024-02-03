package fr.eni.encheres.controllers.article;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.ArticleException;

/**
 * Servlet implementation class VendreArticleServlet
 */
@WebServlet("/vendre")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100)  // 100 MB
public class VendreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("isConnected");
		CategorieManager categorieManager = new CategorieManager();
		List<Categorie> listCategorie = categorieManager.selectAllCategorie();
		request.setAttribute("listCategorie", listCategorie);
		request.setAttribute("rue", utilisateur.getRue());
		request.setAttribute("codePostal", utilisateur.getCodePostal());
		request.setAttribute("ville", utilisateur.getVille());
		request.getRequestDispatcher("/WEB-INF/jsp/vendre.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			
			//Je vérifie si l'utilisateur est actif
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");
			if(!utilisateur.isActif()) {
				throw new ArticleException("Votre compte est désactivé, vous ne pouvez pas vendre d'article");
			}
			
			//Je récupère les informations saisie dans le formulaire
			String nomArticle = request.getParameter("nomArticle");
			String description = request.getParameter("description");
			String dateDebutEncheres = request.getParameter("dateDebutEncheres");
			String dateFinEncheres = request.getParameter("dateFinEncheres");
			String prixInitial = request.getParameter("prixInitial");
			Categorie categorie = new Categorie(Integer.parseInt(request.getParameter("listCategorie")), null);
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			// penser à récupérer l'image.
			
			System.out.println(nomArticle);
			System.out.println(description);
			System.out.println(dateDebutEncheres);
			System.out.println(dateFinEncheres);
			System.out.println(prixInitial);
			System.out.println(rue);
			System.out.println(codePostal);
			System.out.println(ville);
			System.out.println(categorie.getNoCategorie());
			
			response.sendRedirect("accueil");
			
		} catch(ArticleException e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
			
		} catch(Exception e) {
			
		}
		
	}

}
