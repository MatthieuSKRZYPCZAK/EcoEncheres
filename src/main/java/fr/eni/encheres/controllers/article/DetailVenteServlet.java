package fr.eni.encheres.controllers.article;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitsManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.ArticleException;

/**
 * Servlet implementation class DetailVenteServlet
 */
@WebServlet("/article")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class DetailVenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {

			if (session.getAttribute("isConnected") == null) {
				throw new Error("vous devez avoir un compte ou vous identifer pour accéder à ce contenu");
			}
			if (request.getParameter("id") != null) {

				ArticleManager articleManager = new ArticleManager();
				RetraitsManager retraitManager = new RetraitsManager();
				EnchereManager enchereManager = new EnchereManager();
				Article article = articleManager.getById(Integer.parseInt(request.getParameter("id")));

				if (article == null) {
					throw new Error("L'article n'existe pas");
				}
				if (session != null) {
					Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("isConnected");
					if (utilisateurSession != null
							&& utilisateurSession.getNoUtilisateur() == article.getUtilisateur().getNoUtilisateur()) {
						request.setAttribute("moi", true);
						request.setAttribute("utilisateur", utilisateurSession);

					} else if (utilisateurSession != null) {
						request.setAttribute("utilisateur", utilisateurSession);

					} else {
						response.sendRedirect("login");
						return;
					}
				}
				Encheres enchere = enchereManager.enchereExist(article.getNoArticle());
				if (enchere != null) {
					request.setAttribute("enchere", enchere);
				}
				CategorieManager categorieManager = new CategorieManager();
				List<Categorie> listCategorie = categorieManager.selectAllCategorie();
				request.setAttribute("listCategorie", listCategorie);
				Retraits retrait = retraitManager.getByNoArticle(article.getNoArticle());
				request.setAttribute("retrait", retrait);
				request.setAttribute("article", article);
				request.getRequestDispatcher("/WEB-INF/jsp/article.jsp").forward(request, response);

			} else {
				throw new Error("La page demandée n'existe pas");
			}
		} catch (NumberFormatException e) {
			session.setAttribute("erreur", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Error e) {
			session.setAttribute("erreur", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// Date et heure de la création de la vente pour le fileName
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmm");
		String dateFormatee = dateFormat.format(date);

		// Génère un nombre aléatoire qui servira pour le nom de l'image
		Random random = new Random();
		int nombreAleatoire = random.nextInt(3999) + 1;
		String nombreAleatoireStr = String.valueOf(nombreAleatoire);

		// Je récupère les informations saisie dans le formulaire
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		String dateDebutEncheresStr = request.getParameter("dateDebutEncheres");
		String dateFinEncheresStr = request.getParameter("dateFinEncheres");
		String prixInitialStr = request.getParameter("prixInitial");
		Categorie categorie = new Categorie(Integer.parseInt(request.getParameter("listCategorie")), null);
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String id = request.getParameter("noArticle");

		// Je vérifie si l'utilisateur est actif
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("isConnected");

		try {

			if (!utilisateur.isActif()) {
				throw new ArticleException("Votre compte est désactivé, vous ne pouvez pas vendre d'article");
			}

			// ************************//
			// ** GESTION DE L'IMAGE **//
			// ************************//
			// Chemin enregistré dans le context.xml
			String relativePath = getServletContext().getInitParameter("uploadPath");

			Part part = request.getPart("image");

			String filePath = null;
			String fileName = getFileName(part);

			// sépare l'extention du fichier
			String[] fn = fileName.split("(\\.)");
			fileName = fn[0];
			String ext = fn[(fn.length - 1)];
			if (!ext.isEmpty()) {
				// Génère le nom du fichier (Nombre aléatoire + nom de l'article + pseudo
				// utilisateur + date)
				fileName = nombreAleatoireStr + "-" + nomArticle.trim().toLowerCase() + "-" + utilisateur.getPseudo()
						+ "-" + dateFormatee + "." + ext;
				if (fileName != null && fileName.length() > 0) {
					filePath = relativePath + File.separator + fileName;
					part.write(filePath);
				}
			}
			if (fileName.isEmpty()) {
				fileName = "default-picture.png";
			}

			String image = fileName;
			// Création de l'article
			int prixInitial = Integer.parseInt(prixInitialStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

			Date dateDebutEncheres = (Date) sdf.parse(dateDebutEncheresStr);
			Date dateFinEncheres = (Date) sdf.parse(dateFinEncheresStr);
			int newId = Integer.parseInt(id);
			Article article = new Article(newId, nomArticle, description, dateDebutEncheres, dateFinEncheres,
					prixInitial, utilisateur, categorie, image);
			ArticleManager articleManager = new ArticleManager();
			Article articleUpdate = articleManager.update(article);
			Retraits retrait = new Retraits(articleUpdate, rue, codePostal, ville);
			RetraitsManager retraitsManager = new RetraitsManager();
			retraitsManager.update(retrait);

			response.sendRedirect(request.getContextPath() + "/article?id=" + id);

		} catch (ArticleException e) {
			System.out.println("erreur post vente" + e);
			request.setAttribute("erreur", e.getMessage());
			CategorieManager categorieManager = new CategorieManager();
			List<Categorie> listCategorie = categorieManager.selectAllCategorie();
			request.setAttribute("listCategorie", listCategorie);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebutEncheres", dateDebutEncheresStr);
			request.setAttribute("dateFinEncheres", dateFinEncheresStr);
			request.setAttribute("prixInitial", prixInitialStr);
			request.setAttribute("rue", utilisateur.getRue());
			request.setAttribute("codePostal", utilisateur.getCodePostal());
			request.setAttribute("ville", utilisateur.getVille());

			response.sendRedirect(request.getContextPath() + "/article?id=" + id);

		} catch (Exception e) {
			System.out.println("erreur post vente" + e);
			session.setAttribute("erreur", "une erreur est survenue");
			response.sendRedirect("accueil");
		}

	}

	/*
	 * Récupération du nom du fichier dans la requête.
	 */
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
		}
		return "Default.file";
	}

}
