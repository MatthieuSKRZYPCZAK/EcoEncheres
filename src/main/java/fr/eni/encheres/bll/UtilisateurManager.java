package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
private UtilisateurDAO utilisateurDAO;
	

	/**
	 * Constructeur de la classe UtilisateurManager. Initialise l'objet en utilisant
	 * le DAOFactory pour obtenir un UtilisateurDAO.
	 */
	public UtilisateurManager() {
		this.utilisateurDAO=DAOFactory.getUtilisateurDAO();
	}
	
	/**
	 * Récupère un utilisateur en fonction de son identifiant.
	 * @param id L'identifiant de l'utilisateur à récupérer.
	 * @return L'objet Utilisateur correspondant à l'identifiant, ou null s'il existe pas.
	 */
	public Utilisateur getById(int id) {
		return this.utilisateurDAO.selectById(id);
	}
	
	/**
	 * Supprime un utilisateur en fonction de son numéro d'utilisateur(id)
	 * @param no_utilisateur Le numéro de l'utilisateur (id) à supprimer.
	 */
	public void supprimerUtilisateur(int no_utilisateur) {
		this.utilisateurDAO.deleteById(no_utilisateur);
		
	}
	
	/**
	 * Permet à un utilisateur de se connecter en utilisant son adresse e-mail.
	 * @param email L'adresse e-mail de l'utilisateur pour la connexion.
	 * @return Retourne l'objet Utilisateur correspondant s'il existe, sinon null.
	 */
	public Utilisateur login(String email) {
		return this.utilisateurDAO.login(email);
	}


}
