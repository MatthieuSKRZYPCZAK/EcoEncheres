package fr.eni.encheres.controllers.categorie;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.CategorieException;


/**
 * Servlet implementation class CategorieServlet
 */
@WebServlet("/categorie")
public class CategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CategorieManager categorieManager = new CategorieManager();
		List<Categorie> listeCategories = categorieManager.allCategories();

		request.setAttribute("listeCategories", listeCategories);
		request.getRequestDispatcher("/WEB-INF/jsp/categorie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			//Vérifie si l'utilisateur est connecté et si il est administrateur
			HttpSession session = request.getSession();
			if(session != null) {
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if(utilisateurSession.isAdministrateur()) {
					System.out.println("connecté en admin sur categorie lors de la création");
					CategorieManager categorieManager = new CategorieManager();
					String libelle = request.getParameter("categorie");
					if(categorieManager.existe(libelle)) {
						throw new CategorieException("Ce libellé existe déjà");
					} else {
						if(libelle != null && !"".equals(libelle)) {
							System.out.println("je vais créer");
							categorieManager.create(libelle);
							request.setAttribute("successMessage", "Libellé créé avec succès");
						}
					}
				}
			}
			doGet(request, response);
		} catch(CategorieException e) {	
			request.setAttribute("erreur", e.getMessage());
			doGet(request, response);
		} catch(Exception e) {
			request.setAttribute("erreur", e.getMessage());
			doGet(request, response);
		}
	}
}
