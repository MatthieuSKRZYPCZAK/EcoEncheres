package fr.eni.encheres.bll;



import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;


public class ArticleManager {
	private ArticleDAO articleDAO;
	
	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}

	public Article getById(int id) {
		return this.articleDAO.getById(id);

	}

	public void create(Article article) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
