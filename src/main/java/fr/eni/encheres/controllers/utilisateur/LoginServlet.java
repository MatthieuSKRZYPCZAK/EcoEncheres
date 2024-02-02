package fr.eni.encheres.controllers.utilisateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.services.BCrypt;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//Je récupère les informations saisie dans le formulaire
		String pseudo = request.getParameter("pseudo").toLowerCase();
		if(request.getParameter("pseudo") == null) {
			System.out.println("pseudo null");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
		if(request.getParameter("password") == null) {
			System.out.println("password null");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
//		Boolean seSouvenir = false;
//		String seSouvenirParam = request.getParameter("seSouvenir");
//		if(seSouvenirParam != null && "on".equals(seSouvenirParam)) {
//			seSouvenir = true;
//		}
//		
		//Création du cookie pour le pseudo
		Cookie cookiePseudo = new Cookie("lastLogin",pseudo);
		cookiePseudo.setMaxAge(8*24*60*60);
		response.addCookie(cookiePseudo);
//		
//		//Création du cooke pour seSouvenir
//		Cookie cookieSeSouvenir = new Cookie("seSouvenir", seSouvenir.toString());
//		cookieSeSouvenir.setMaxAge(8*24*60*60);
//		response.addCookie(cookieSeSouvenir);
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.login(pseudo);
		if (utilisateur != null) {
			if(BCrypt.checkpw(request.getParameter("password"), utilisateur.getMotDePasse())) {
				HttpSession ses;
				ses = request.getSession();
				ses.setAttribute("isConnected", utilisateur);
				response.sendRedirect(request.getContextPath()+"/accueil");
			} else {
				request.setAttribute("lastLogin", pseudo);
				request.setAttribute("erreur", "identifiant/password incorrect");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}			
		} else {
			System.out.println("utilisateur non trouvé");
			request.setAttribute("erreur", "identifiant/password incorrect");
			request.setAttribute("lastLogin", pseudo);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}

}