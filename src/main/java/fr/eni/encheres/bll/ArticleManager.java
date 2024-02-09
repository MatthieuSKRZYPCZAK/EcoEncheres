package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.ArticleException;

public class ArticleManager {
	private ArticleDAO articleDAO;

	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}

	public Article getById(int id) {
		return this.articleDAO.getById(id);

	}

	public Article create(Article article) throws ArticleException {
		Date dateActuelle = new Date();
		if (article.getNomArticle().isEmpty() || article.getNomArticle() == null) {
			throw new ArticleException("Veuillez donner un nom à votre article.");
		}
		if (article.getDescription().isEmpty() || article.getDescription() == null) {
			throw new ArticleException("Veuillez donner une description.");
		}
		if (article.getPrixInitial() > 50000 || article.getPrixInitial() < 0) {
			throw new ArticleException("Le prix doit être supérieur à 0 et inférieur à 50 000.");
		}
		if (!article.getUtilisateur().isActif()) {
			throw new ArticleException("Vous n'avez pas l'autorisation de vendre");
		}
		if (article.getCategorie() == null) {
			throw new ArticleException("Vous n'avez selectionné aucune categorie");
		}
		if (article.getDateDebutEncheres().before(dateActuelle)) {
			throw new ArticleException(
					"Vous ne pouvez pas fixer une date de début d'enchère antérieure à la date actuelle");
		}
		if (article.getDateDebutEncheres().after(article.getDateFinEncheres())) {
			throw new ArticleException(
					"Vous ne pouvez pas fixer une date de fin d'enchère antérieure à la date de début d'enchère.");
		}
		return this.articleDAO.create(article);
	}

	/**
	 * méthode qui permet de mettre à jour l'état de vente des articles
	 */
	public void majEtatVenteAll() {
		List<Article> listeArticles = new ArrayList<>();
		listeArticles = getAllForDate();
		String cree = "créé";
		String enCours = "en cours";
		String termine = "terminé";

		for (Article article : listeArticles) {
			Date nowDate = new Date();
			if (article.getEtatVente().contains(enCours)) {
				if (article.getDateFinEncheres().before(nowDate)) {
					articleUpdateEtat(termine, article.getNoArticle());
					EnchereManager enchereManager = new EnchereManager();
					Encheres encheres = enchereManager.enchereExist(article.getNoArticle());
					if (encheres != null) {
						articleUpdatePrixVente(encheres.getMontantEnchere(), article.getNoArticle());
					}
				}
			}
			if (article.getEtatVente().contains(cree)) {
				if (article.getDateDebutEncheres().before(nowDate)) {
					articleUpdateEtat(enCours, article.getNoArticle());
				}
			}
		}
	}

	private void articleUpdatePrixVente(int montantEnchere, int noArticle) {
		this.articleDAO.updatePrixVente(montantEnchere, noArticle);

	}

	public void articleUpdateEtat(String etat, int noArticle) {
		this.articleDAO.articleUpdateEtat(etat, noArticle);
	}

	public List<Article> getAll() {
		return this.articleDAO.getAll();
	}

	public List<Article> getAllForDate() {
		return this.articleDAO.getAllForDate();
	}

	public List<Article> getAllArticleEnchere() {
		return this.articleDAO.getAllArticleEnchere();
	}

	public Article update(Article article) throws ArticleException {
		Date dateActuelle = new Date();
		if (article.getNomArticle().isEmpty() || article.getNomArticle() == null) {
			throw new ArticleException("Veuillez donner un nom à votre article.");
		}
		if (article.getDescription().isEmpty() || article.getDescription() == null) {
			throw new ArticleException("Veuillez donner une description.");
		}
		if (article.getPrixInitial() > 50000 || article.getPrixInitial() < 0) {
			throw new ArticleException("Le prix doit être supérieur à 0 et inférieur à 50 000.");
		}
		if (!article.getUtilisateur().isActif()) {
			throw new ArticleException("Vous n'avez pas l'autorisation de vendre");
		}
		if (article.getCategorie() == null) {
			throw new ArticleException("Vous n'avez selectionné aucune categorie");
		}
		if (article.getDateDebutEncheres().before(dateActuelle)) {
			throw new ArticleException(
					"Vous ne pouvez pas fixer une date de début d'enchère antérieure à la date actuelle");
		}
		if (article.getDateDebutEncheres().after(article.getDateFinEncheres())) {
			throw new ArticleException(
					"Vous ne pouvez pas fixer une date de fin d'enchère antérieure à la date de début d'enchère.");
		}
		return this.articleDAO.update(article);
	}

	public void delete(int noArticle) {
		this.articleDAO.detele(noArticle);
		
	}

	public List<Article> getAllArticleByCategorieEnCours(int noCategorie) {
		return this.articleDAO.getAllArticleByCategorieEnCours(noCategorie);
	}

	public List<Article> getAllWithEncheres() {
		return this.articleDAO.getAllArticlesWithEncheres();
	}

	public List<Article> getByUserId(int id) {
		return this.articleDAO.getAllByUserId(id);
	}

}
