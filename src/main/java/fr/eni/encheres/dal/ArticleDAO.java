package fr.eni.encheres.dal;


import java.util.List;

import fr.eni.encheres.bo.Article;


public interface ArticleDAO {

	Article getById(int id);

	Article create(Article article);

	List<Article> getAll();

	List<Article> getAllArticleEnchere();

	void articleUpdateEtat(String etat, int noArticle);

	void updatePrixVente(int montantEnchere, int noArticle);

	List<Article> getAllForDate();

	Article update(Article article);

	void detele(int noArticle);

	List<Article> getAllArticleByCategorieEnCours(int noCategorie);

	List<Article> getAllArticlesWithEncheres();

	List<Article> getAllByUserId(int id);

	

}
