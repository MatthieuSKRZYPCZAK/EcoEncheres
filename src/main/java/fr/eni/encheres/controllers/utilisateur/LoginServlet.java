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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String lastLogin = null;
		boolean seSouvenir = false;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("lastLogin".equals(cookie.getName())) {
					lastLogin = cookie.getValue();
					seSouvenir = true;
				}
			}
		}

		request.setAttribute("lastLogin", lastLogin);
		request.setAttribute("seSouvenir", seSouvenir);
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// Je récupère les informations saisie dans le formulaire
		String pseudo = request.getParameter("pseudo").toLowerCase();
		String seSouvenir = request.getParameter("seSouvenir");
		if ("on".equals(seSouvenir)) {
			Cookie cookie = new Cookie("lastLogin", request.getParameter("pseudo"));
			cookie.setMaxAge(8 * 24 * 60 * 60);
			response.addCookie(cookie);
			request.setAttribute("seSouvenir", true);
		} else {
			request.setAttribute("seSouvenir", false);
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("lastLogin".equals(cookie.getName())) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						request.setAttribute("seSouvenir", true);
						break;
					}
				}
			}
		}

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.login(pseudo);
		if (utilisateur != null) {
			if (BCrypt.checkpw(request.getParameter("password"), utilisateur.getMotDePasse())) {
				HttpSession ses;
				ses = request.getSession();
				ses.setAttribute("isConnected", utilisateur);
				response.sendRedirect(request.getContextPath() + "/accueil");
			} else {
				request.setAttribute("lastLogin", request.getParameter("pseudo"));
				request.setAttribute("erreur", "identifiant/password incorrect");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		} else {
			System.out.println("utilisateur non trouvé");
			request.setAttribute("erreur", "identifiant/password incorrect");
			request.setAttribute("lastLogin", request.getParameter("pseudo"));
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}

}