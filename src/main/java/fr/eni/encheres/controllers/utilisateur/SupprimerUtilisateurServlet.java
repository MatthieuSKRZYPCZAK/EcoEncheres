package fr.eni.encheres.controllers.utilisateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.UtilisateurException;
import fr.eni.encheres.services.BCrypt;



/**
 * Servlet implementation class SupprimerUtilisateurServlet
 */
@WebServlet("/supprimer")
public class SupprimerUtilisateurServlet extends HttpServlet {
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
					UtilisateurManager utilisateurManager = new UtilisateurManager();
					Utilisateur utilisateur = new Utilisateur();
					utilisateur = utilisateurManager.getById(Integer.parseInt(request.getParameter("id")));
					if(utilisateur.isAdministrateur()) {
						request.setAttribute("erreur", "Vous ne pouvez pas supprimer un compte administrateur");
						request.getRequestDispatcher("/utilisateurs").forward(request, response);
						
					} else {
						utilisateurManager.supprimer(Integer.parseInt(request.getParameter("id")));
						request.setAttribute("successMessage", "Vous avez bien supprimé l'utilisateur "+ utilisateur.getPseudo() + " " + utilisateur.getPrenom()+ " " + utilisateur.getNom() + " " + utilisateur.getEmail());
						request.getRequestDispatcher("/utilisateurs").forward(request, response);
//						response.sendRedirect("utilisateurs");
//						return;
					}
				} else {
					throw new Exception("Vous devez être administrateur pour faire celà");
				}
			}
		} catch (UtilisateurException e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/accueil").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/accueil").forward(request, response);
		}		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			String id = request.getParameter("id");
			//Vérifie si l'utilisateur est connecté
			HttpSession session = request.getSession();
			if(session != null) {
				Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
				if(utilisateurSession.getNoUtilisateur() == Integer.parseInt(request.getParameter("id"))){
					System.out.println("je suis le bonne utilisateur id = " +id);
					if(BCrypt.checkpw(request.getParameter("motDePasse"), utilisateurSession.getMotDePasse())) {
						System.out.println("Et mon passe est correcte");
						UtilisateurManager utilisateurManager = new UtilisateurManager();
						utilisateurManager.supprimer(utilisateurSession.getNoUtilisateur());
						session.invalidate();
						response.sendRedirect(request.getContextPath() + "/login");
						return;
					} else {
						System.out.println("mon passe est incorrecte et l'id est : " + id);
						request.getSession().setAttribute("erreur", "mauvais mot de passe");
						response.sendRedirect(request.getContextPath() + "/profil?id=" + id);
						return;
					}
				} else {
					throw new UtilisateurException("Vous n'êtes pas autorisé à supprimer ce compte");
				}
				
			} else {
				throw new UtilisateurException("Vous devez être connecté");
			}
			
			} catch(UtilisateurException e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/accueil").forward(request, response);
			} catch(Exception e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("login");
		}
	}


}
