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

	/**
	 * Initialise un nouveau gestionnaire d'articles avec un DAO d'articles.
	 */
	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}

	/**
	 * Récupère un article par son identifiant.
	 *
	 * @param id L'identifiant de l'article à récupérer.
	 * @return L'article correspondant à l'identifiant spécifié.
	 */
	public Article getById(int id) {
		return this.articleDAO.getById(id);

	}
	
	/**
	 * Crée un nouvel article dans la base de données.
	 *
	 * @param article L'article à créer.
	 * @return L'article créé.
	 * @throws ArticleException Si une erreur survient lors de la création de l'article.
	 */
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
	 * Met à jour l'état de vente de tous les articles en fonction de leur date d'enchères.
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

	/**
	 * Met à jour le prix de vente d'un article dans la base de données.
	 *
	 * @param montantEnchere Le nouveau montant de l'enchère.
	 * @param noArticle      L'identifiant de l'article à mettre à jour.
	 */
	private void articleUpdatePrixVente(int montantEnchere, int noArticle) {
		this.articleDAO.updatePrixVente(montantEnchere, noArticle);

	}

	/**
	 * Met à jour l'état de vente d'un article dans la base de données.
	 *
	 * @param etat     Le nouvel état de vente de l'article.
	 * @param noArticle L'identifiant de l'article à mettre à jour.
	 */
	public void articleUpdateEtat(String etat, int noArticle) {
		this.articleDAO.articleUpdateEtat(etat, noArticle);
	}

	/**
	 * Récupère tous les articles de la base de données.
	 *
	 * @return Une liste contenant tous les articles.
	 */
	public List<Article> getAll() {
		return this.articleDAO.getAll();
	}

	/**
	 * Récupère tous les articles pour lesquels la date d'enchères est valide.
	 *
	 * @return Une liste contenant tous les articles pour lesquels la date d'enchères est valide.
	 */
	public List<Article> getAllForDate() {
		return this.articleDAO.getAllForDate();
	}

	/**
	 * Récupère tous les articles actuellement en enchères.
	 *
	 * @return Une liste contenant tous les articles actuellement en enchères.
	 */
	public List<Article> getAllArticleEnchere() {
		return this.articleDAO.getAllArticleEnchere();
	}

	/**
	 * Met à jour un article dans la base de données.
	 *
	 * @param article L'article à mettre à jour.
	 * @return L'article mis à jour.
	 * @throws ArticleException Si une erreur survient lors de la mise à jour de l'article.
	 */
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

	/**
	 * Supprime un article de la base de données.
	 *
	 * @param noArticle L'identifiant de l'article à supprimer.
	 */
	public void delete(int noArticle) {
		this.articleDAO.detele(noArticle);

	}

	/**
	 * Récupère tous les articles d'une catégorie actuellement en enchères.
	 *
	 * @param noCategorie L'identifiant de la catégorie.
	 * @return Une liste contenant tous les articles d'une catégorie actuellement en enchères.
	 */
	public List<Article> getAllArticleByCategorieEnCours(int noCategorie) {
		return this.articleDAO.getAllArticleByCategorieEnCours(noCategorie);
	}

	/**
	 * Récupère tous les articles avec des enchères associées.
	 *
	 * @return Une liste contenant tous les articles avec des enchères associées.
	 */
	public List<Article> getAllWithEncheres() {
		return this.articleDAO.getAllArticlesWithEncheres();
	}

	/**
	 * Récupère tous les articles associés à un utilisateur.
	 *
	 * @param id L'identifiant de l'utilisateur.
	 * @return Une liste contenant tous les articles associés à un utilisateur.
	 */
	public List<Article> getByUserId(int id) {
		return this.articleDAO.getAllByUserId(id);
	}

}
