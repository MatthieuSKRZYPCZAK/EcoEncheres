package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurManager() {
		this.utilisateurDAO=DAOFactory.getUtilisateurDAO();
	}
	
	public Utilisateur getById(int id) {
		return this.utilisateurDAO.selectById(id);
	}

	public void supprimerUtilisateur(int no_utilisateur) {
		this.utilisateurDAO.deleteById(no_utilisateur);
		
	}


}
