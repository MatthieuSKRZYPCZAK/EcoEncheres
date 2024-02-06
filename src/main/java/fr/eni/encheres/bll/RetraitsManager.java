package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.RetraitsDAO;

public class RetraitsManager {
	private RetraitsDAO retraitsDAO;
	
	public RetraitsManager() {
		this.retraitsDAO=DAOFactory.getRetraitsDAO();
	}

	public void create(Retraits retrait) {
		this.retraitsDAO.create(retrait);
		
	}

	public Retraits getByNoArticle(int noArticle) {
		return this.retraitsDAO.getByNoArticle(noArticle);
	}

}
