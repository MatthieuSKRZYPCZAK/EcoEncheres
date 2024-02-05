package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface CategorieDAO {

	void create(String categorie);

	void delete(int id);

	void update(int id, String libelle);

	List<Categorie> allCategories();

	boolean existe(String libelle);

	List<Categorie> selectAll();

	Categorie getById(int id);

}
