package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

public class CategorieManager {
	private CategorieDAO categorieDAO;

	public List<Categorie> selectAllCategorie() {
		return this.categorieDAO.selectAll();
	}

	public CategorieManager() {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}

	public void delete(int id) {
		this.categorieDAO.delete(id);
	}

	public void update(int id, String libelle) {
		this.categorieDAO.update(id, libelle);
	}

	public List<Categorie> allCategories() {
		return this.categorieDAO.allCategories();
	}

	public void create(String categorie) {
		this.categorieDAO.create(categorie);
	}

	public boolean existe(String libelle) {
		return this.categorieDAO.existe(libelle);

	}

	public Categorie getById(int id) {
		return this.categorieDAO.getById(id);
	}

}
