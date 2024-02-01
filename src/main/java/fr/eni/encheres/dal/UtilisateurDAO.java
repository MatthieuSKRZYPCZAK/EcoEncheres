package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
		
	public Utilisateur selectById(int id);
	public void deleteById(int no_utilisateur);
	public Utilisateur login(String pseudo);
	public boolean emailExists(String email);
	public boolean pseudoExists(String email);
	public Utilisateur insert(Utilisateur utilisateur);
	public Utilisateur updateUtilisateur(Utilisateur utilisateur);
	public List<Utilisateur> selectAllUsers();
	public void delete(int no_utilisateur);
	public void disable(int id);
	public void enable(int id);

}
