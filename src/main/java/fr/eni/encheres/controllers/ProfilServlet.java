package fr.eni.encheres.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.UpdateException;



/**
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (request.getParameter("id") != null) {
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur = utilisateurManager.getById(Integer.parseInt(id));
			HttpSession session = request.getSession();
			if(session != null) {
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if(utilisateurSession != null && utilisateurSession.getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
					request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
					return;
				}
			}
		}

		response.sendRedirect(request.getContextPath()+"/accueil");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Je récupère les informations saisie dans le formulaire
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");
		int id = utilisateur.getNoUtilisateur();
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville").toUpperCase();
		String motDePasse = request.getParameter("motDePasse");
		String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
		String confirmationMotDePasse = request.getParameter("confirmationMotDePasse");
		
		
		if(utilisateur != null) {
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			
			try {
				Utilisateur updateUtilisateur = new Utilisateur();
				updateUtilisateur = utilisateurManager.updateUtilisateur(id, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, nouveauMotDePasse, confirmationMotDePasse);

				session.setAttribute("isConnected", updateUtilisateur);
				request.setAttribute("successMessage", "la modification a été validé avec succes");
				System.out.println("utilisateur modifié");
				

			} catch (UpdateException e) {
				request.setAttribute("erreur", e);
				
			}
		}



	}

}
