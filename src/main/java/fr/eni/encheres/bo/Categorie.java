package fr.eni.encheres.bo;


public class Categorie {
	private int noCategorie;
	private String libelle;
	
	
	public Categorie() {
		super();
	}

	
	public Categorie(int no_categorie, String libelle) {
		super();
		this.noCategorie = no_categorie;
		this.libelle = libelle;
	}
	
	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int no_categorie) {
		this.noCategorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + "]";
	}
	

}
