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

/**
 * Servlet implementation class ActifServlet
 */
@WebServlet("/actif")
public class ActifServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.getById(Integer.parseInt(id));
		HttpSession session = request.getSession();
		if(session != null) {
			Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
			if(utilisateurSession.isAdministrateur()) {
				if(utilisateur.isActif()) {
					utilisateurManager.desactive(utilisateur.getNoUtilisateur());
				} else {
					utilisateurManager.active(utilisateur.getNoUtilisateur());
				}
			}
		}
		response.sendRedirect("utilisateurs");
	}

}
