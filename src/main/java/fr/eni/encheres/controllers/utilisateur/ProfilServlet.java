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
import fr.eni.encheres.exception.UpdateException;
import fr.eni.encheres.exception.UtilisateurException;

/**
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			HttpSession session = request.getSession();
			if (request.getParameter("id") != null) {
				UtilisateurManager utilisateurManager = new UtilisateurManager();
				Utilisateur utilisateur = utilisateurManager.getById(Integer.parseInt(request.getParameter("id")));
				if (utilisateur == null) {
					throw new UtilisateurException("L'utilisateur n'existe pas");
				}
				if (session != null) {
					Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
					if (utilisateurSession != null
							&& utilisateurSession.getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
						request.setAttribute("moi", true);
						request.setAttribute("utilisateur", utilisateur);
						request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
						return;
					} else {
						request.setAttribute("utilisateur", utilisateur);
						request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
					}
				} else {
					response.sendRedirect("login");
				}
			} else {
				throw new UtilisateurException("La page demandé n'existe pas");
			}
		} catch (UtilisateurException e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
		} catch (Error e) {
			request.setAttribute("erreur", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// Je récupère les informations saisie dans le formulaire
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");
		if (utilisateur != null) {
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
			UtilisateurManager utilisateurManager = new UtilisateurManager();

			try {
				Utilisateur updateUtilisateur = new Utilisateur();
				updateUtilisateur = utilisateurManager.updateUtilisateur(id, pseudo, nom, prenom, email, telephone, rue,
						codePostal, ville, motDePasse, nouveauMotDePasse, confirmationMotDePasse);
				session.setAttribute("isConnected", updateUtilisateur);
				session.setAttribute("successMessage", "la modification a été validé avec succes");
				System.out.println("utilisateur modifié");
				response.sendRedirect(request.getContextPath() + "/profil?id=" + id);
			} catch (UpdateException e) {
				session.setAttribute("erreur", e);
				System.out.println("id :" + id);
				System.out.println("erreur catch profilservlet post");
				response.sendRedirect(request.getContextPath() + "/profil?id=" + id);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}
}
