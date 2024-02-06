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
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.ArticleException;

/**
 * Servlet implementation class enchereServlet
 */
@WebServlet("/enchere")
public class enchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			HttpSession session = request.getSession();
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
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur updateUser = utilisateurManager.getById(utilisateur.getNoUtilisateur());
			Encheres updateEnchere = enchereManager.enchereExist(article.getNoArticle());
			session.setAttribute("article", article);
			session.setAttribute("isConnected", updateUser);
			session.setAttribute("enchere", updateEnchere);
			session.setAttribute("successMessage", "la modification a été validé avec succes");

			response.sendRedirect(request.getContextPath() + "/article?id=" + noArticle);

		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
		}

	}

}
