package fr.eni.encheres.controllers.enchere;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.UtilisateurException;

/**
 * Servlet implementation class MesVentesServlet
 */
@WebServlet("/mesVentes")
public class MesVentesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			if (request.getParameter("id") == null) {
				throw new UtilisateurException("L'utilisateur n'existe pas.");
			}
			if (session == null) {
				response.sendRedirect("login");
			}
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur = utilisateurManager.getById(Integer.parseInt(request.getParameter("id")));
			Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
			if (utilisateurSession.getNoUtilisateur() != utilisateur.getNoUtilisateur()) {
				throw new UtilisateurException("Ce contenu ne vous concerne pas.");
			}
			System.out.println(request.getParameter("id"));
			ArticleManager articleManager = new ArticleManager();
			List<Article> listEncheres = articleManager.getByUserId(Integer.parseInt(request.getParameter("id")));
			for(Article article : listEncheres) {
				System.out.println(article.getNomArticle());
			}

			request.setAttribute("utilisateur", utilisateur);
			request.setAttribute("listeEncheres", listEncheres);
			request.getRequestDispatcher("/WEB-INF/jsp/ventes-user.jsp").forward(request, response);

		} catch (UtilisateurException e) {
			session.setAttribute("erreur", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}

}
