package fr.eni.encheres.controllers.retrait;

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

import fr.eni.encheres.exception.RetraitException;

/**
 * Servlet implementation class retraitServlet
 */
@WebServlet("/retrait")
public class retraitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("isConnected") == null) {
				throw new RetraitException("vous devez avoir un compte ou vous identifer pour accéder à ce contenu");
			}
			if (request.getParameter("id") != null) {
				ArticleManager articleManager = new ArticleManager();
				Article article = articleManager.getById(Integer.parseInt(request.getParameter("id")));
				
				if(article == null) {
					throw new RetraitException("L'article n'existe pas");
				}
				if(!article.getEtatVente().contains("terminé")) {
					throw new RetraitException("L'enchère n'est pas encore terminé ou l'article a déjà été retiré.");
				}
				EnchereManager enchereManager = new EnchereManager();
				Encheres enchere = enchereManager.enchereExist(article.getNoArticle());
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if((utilisateurSession.getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()) && article.getEtatVente().contains("terminé")) {
					articleManager.articleUpdateEtat("retiré", article.getNoArticle());
					
				} else {
					throw new RetraitException("Vous ne pouvez pas retirer un article que vous n'avez pas gagné.");
				}
				article.setEtatVente("retiré");
				RetraitsManager retraitManager = new RetraitsManager();
				Retraits retrait = retraitManager.getByNoArticle(article.getNoArticle());
				request.setAttribute("retrait", retrait);
				request.setAttribute("article", article);
				request.getRequestDispatcher("/article?id="+article.getNoArticle()).forward(request, response);
				
				
			} else {
				throw new RetraitException("La page demandé n'existe pas");
			}
		}catch(RetraitException e) {
			session.setAttribute("erreur", e);
			response.sendRedirect("accueil");
			
		}
	}


}
