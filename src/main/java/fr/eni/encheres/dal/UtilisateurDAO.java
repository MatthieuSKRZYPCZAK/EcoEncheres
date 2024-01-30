package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
		
	Utilisateur selectById(int id);

	void deleteById(int no_utilisateur);

}
