package fr.eni.encheres.controllers;

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
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//Je récupère les informations saisie dans le formulaire
		String email = request.getParameter("email");
		Cookie mail;
		mail= new Cookie("lastLogin", email);
		mail.setMaxAge(8*24*60*60);
		response.addCookie(mail);
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.login(email);
		if (utilisateur != null) {
			if(BCrypt.checkpw(request.getParameter("password"), utilisateur.getMot_de_passe())) {
				HttpSession ses;
				ses = request.getSession();
				ses.setAttribute("userConnected", utilisateur);
				response.sendRedirect(request.getContextPath()+"/accueil");
			} else {
				request.setAttribute("lastLoginEmail", email);
				request.setAttribute("erreur", "email/password incorrect");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}

			
		} else {
			request.setAttribute("erreur", "email/password incorrect");
			request.setAttribute("lastLoginEmail", email);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}

}
