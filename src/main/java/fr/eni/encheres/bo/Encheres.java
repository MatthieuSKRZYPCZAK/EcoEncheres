package fr.eni.encheres.bo;

import java.sql.Date;

public class Encheres {
	private Date dateEnchere;
	private int montantEnchere;
	private Utilisateur utilisateur;
	private Article article;

	public Encheres() {
		super();
	}

	public Encheres(Date dateEnchere, int montantEnchere, Utilisateur utilisateur, Article article) {
		super();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.utilisateur = utilisateur;
		this.article = article;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(java.util.Date dateEnchereOfficiel) {
		this.dateEnchere = (Date) dateEnchereOfficiel;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
