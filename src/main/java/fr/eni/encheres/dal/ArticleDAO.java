package fr.eni.encheres.dal;


import fr.eni.encheres.bo.Article;


public interface ArticleDAO {

	Article getById(int id);

	Article create(Article article);
	

}
