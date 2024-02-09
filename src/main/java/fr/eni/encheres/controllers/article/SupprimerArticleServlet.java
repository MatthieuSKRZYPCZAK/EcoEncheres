package fr.eni.encheres.controllers.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class SupprimerArticleServlet
 */
@WebServlet("/supprimerVente")
public class SupprimerArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();

		try {
			if (request.getParameter("id") == null) {
				throw new Error("La page demandée n'existe pas");
			}
			if (session == null) {
				throw new Error("Vous devez vous identifier");
			}
			Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
			ArticleManager articleManager = new ArticleManager();
			Article article = articleManager.getById(Integer.parseInt(request.getParameter("id")));

			if (utilisateurSession.getNoUtilisateur() == article.getUtilisateur().getNoUtilisateur()) {
				articleManager.delete(article.getNoArticle());
			} else if (utilisateurSession.isAdministrateur()) {
				articleManager.delete(article.getNoArticle());
			}

			request.setAttribute("successMessage", "Enchère annulée avec succès.");
			request.getRequestDispatcher("/accueil").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/accueil").forward(request, response);
		}
	}

}
