package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.RetraitsDAO;

public class RetraitsManager {
	private RetraitsDAO retraitsDAO;
	
	public RetraitsManager() {
		this.retraitsDAO=DAOFactory.getRetraitsDAO();
	}

}
