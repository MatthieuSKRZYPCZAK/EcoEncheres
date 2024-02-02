package fr.eni.encheres.controllers.categorie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Utilisateur;


/**
 * Servlet implementation class SupprimerCategorieServlet
 */
@WebServlet("/supprimerCategorie")
public class SupprimerCategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			if(session != null) {
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if(utilisateurSession.isAdministrateur()) {
					CategorieManager categorieManager = new CategorieManager();
					categorieManager.delete(Integer.parseInt(request.getParameter("id")));
					request.setAttribute("erreur", "Vous venez de supprimer un libellé");
				}
			}
			request.getRequestDispatcher("/categorie").forward(request, response);
			
		} catch(Exception e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/categorie").forward(request, response);
		}
		
	}
}
