package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	/**
	 * Récupère un article par son identifiant.
	 *
	 * @param id L'identifiant de l'article à récupérer.
	 * @return L'article correspondant à l'identifiant spécifié.
	 */
	Article getById(int id);

	/**
	 * Crée un nouvel article dans la base de données.
	 *
	 * @param article L'article à créer.
	 * @return L'article créé.
	 */
	Article create(Article article);

	/**
	 * Récupère tous les articles de la base de données.
	 *
	 * @return Une liste contenant tous les articles.
	 */
	List<Article> getAll();

	/**
	 * Récupère tous les articles actuellement en enchères.
	 *
	 * @return Une liste contenant tous les articles actuellement en enchères.
	 */
	List<Article> getAllArticleEnchere();

	/**
	 * Met à jour l'état de vente d'un article dans la base de données.
	 *
	 * @param etat     Le nouvel état de vente de l'article.
	 * @param noArticle L'identifiant de l'article à mettre à jour.
	 */
	void articleUpdateEtat(String etat, int noArticle);

	/**
	 * Met à jour le prix de vente d'un article dans la base de données.
	 *
	 * @param montantEnchere Le nouveau montant de l'enchère.
	 * @param noArticle      L'identifiant de l'article à mettre à jour.
	 */
	void updatePrixVente(int montantEnchere, int noArticle);

	/**
	 * Récupère tous les articles pour lesquels la date d'enchères est valide.
	 *
	 * @return Une liste contenant tous les articles pour lesquels la date d'enchères est valide.
	 */
	List<Article> getAllForDate();

	/**
	 * Met à jour un article dans la base de données.
	 *
	 * @param article L'article à mettre à jour.
	 * @return L'article mis à jour.
	 */
	Article update(Article article);

	/**
	 * Supprime un article de la base de données.
	 *
	 * @param noArticle L'identifiant de l'article à supprimer.
	 */
	void detele(int noArticle);

	/**
	 * Récupère tous les articles d'une catégorie actuellement en enchères.
	 *
	 * @param noCategorie L'identifiant de la catégorie.
	 * @return Une liste contenant tous les articles d'une catégorie actuellement en enchères.
	 */
	List<Article> getAllArticleByCategorieEnCours(int noCategorie);

	/**
	 * Récupère tous les articles avec des enchères associées.
	 *
	 * @return Une liste contenant tous les articles avec des enchères associées.
	 */
	List<Article> getAllArticlesWithEncheres();

	/**
	 * Récupère tous les articles associés à un utilisateur.
	 *
	 * @param id L'identifiant de l'utilisateur.
	 * @return Une liste contenant tous les articles associés à un utilisateur.
	 */
	List<Article> getAllByUserId(int id);

}
