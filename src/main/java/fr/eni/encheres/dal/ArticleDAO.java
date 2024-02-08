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


	

}
