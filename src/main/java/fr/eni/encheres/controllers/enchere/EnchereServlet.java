package fr.eni.encheres.controllers.enchere;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitsManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.ArticleException;

/**
 * Servlet implementation class enchereServlet
 */
@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		try {

			if (session.getAttribute("isConnected") == null) {
				throw new ArticleException("vous devez avoir un compte ou vous identifer pour accéder à ce contenu");
			}

			ArticleManager articleManager = new ArticleManager();
			RetraitsManager retraitManager = new RetraitsManager();
			EnchereManager enchereManager = new EnchereManager();
			Article article = articleManager.getById(Integer.parseInt(request.getParameter("id")));

			if (article == null) {
				throw new ArticleException("L'article n'existe pas");
			}
			if (session != null) {
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if (utilisateurSession != null
						&& utilisateurSession.getNoUtilisateur() == article.getUtilisateur().getNoUtilisateur()) {
					request.setAttribute("moi", true);
					request.setAttribute("utilisateur", utilisateurSession);

				} else if (utilisateurSession != null) {
					request.setAttribute("utilisateur", utilisateurSession);

				} else {
					response.sendRedirect("login");
					return;
				}
			}
			Encheres enchere = enchereManager.enchereExist(article.getNoArticle());
			if (enchere != null) {
				request.setAttribute("enchere", enchere);
			}
			Retraits retrait = retraitManager.getByNoArticle(article.getNoArticle());
			request.setAttribute("retrait", retrait);
			request.setAttribute("article", article);
			request.getRequestDispatcher("/WEB-INF/jsp/article.jsp").forward(request, response);

		} catch (ArticleException e) {
			session.setAttribute("erreur", e);
			response.sendRedirect(request.getContextPath() + "/article?id=" + id);
		} catch (NumberFormatException e) {
			session.setAttribute("erreur", e);
			response.sendRedirect(request.getContextPath() + "/article?id=" + id);
		} catch (Error e) {
			session.setAttribute("erreur", e);
			response.sendRedirect(request.getContextPath() + "/article?id=" + id);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {

			Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");
			if (session.getAttribute("isConnected") == null) {
				System.out.println("test sessions");
				throw new ArticleException(
						"vous devez avoir un compte ou vous identifer afin d'enchérir sur un article");
			}
			int noArticle = Integer.parseInt(request.getParameter("noArticle"));
			ArticleManager articleManager = new ArticleManager();
			articleManager.majEtatVenteAll();
			EnchereManager enchereManager = new EnchereManager();
			Article article = articleManager.getById(noArticle);
			int montantDeLEnchere = Integer.parseInt(request.getParameter("prixEnchere"));
			enchereManager.enchere(utilisateur, article, montantDeLEnchere);
			Encheres updateEnchere = enchereManager.enchereExist(article.getNoArticle());
			utilisateur.setCredit(utilisateur.getCredit() - montantDeLEnchere);
			request.setAttribute("article", article);
			session.setAttribute("isConnected", utilisateur);
			request.setAttribute("enchere", updateEnchere);

			response.sendRedirect("article?id=" + request.getParameter("noArticle"));

		} catch (Exception e) {
			session.setAttribute("erreur", e);
			response.sendRedirect(request.getContextPath() + "/article?id=" + request.getParameter("noArticle"));
		}

	}

}
