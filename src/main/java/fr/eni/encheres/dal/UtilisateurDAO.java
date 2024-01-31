package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
		
	public Utilisateur selectById(int id);
	public void deleteById(int no_utilisateur);
	public Utilisateur login(String email);

}
