package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	// INSERT
	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie, image) VALUES(?,?,?,?,?,?,?,?);";

	// READ
	private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS;";
	private static final String SELECT_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=?;";
	private static final String SELECT_ALL_START = "SELECT *FROM ARTICLES_VENDUS WHERE etat_vente = 'en cours';";
	
	//UPDATE
	private static final String UPDATE_ETAT = "UPDATE ARTICLES_VENDUS SET etat_vente = ? WHERE no_article=?;";

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Article articleBuilder(ResultSet rs) throws SQLException {
		Article articleCourant;
		articleCourant = new Article();
		articleCourant.setNoArticle(rs.getInt("no_article"));
		articleCourant.setNomArticle(rs.getString("nom_article"));
		articleCourant.setDescription(rs.getString("description"));
		articleCourant.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
		articleCourant.setDateFinEncheres(rs.getDate("date_fin_encheres"));
		articleCourant.setPrixInitial(rs.getInt("prix_initial"));
		articleCourant.setPrixVente(rs.getInt("prix_vente"));
		articleCourant.setEtatVente(rs.getString("etat_vente"));
		articleCourant.setImage(rs.getString("image"));
		Utilisateur utilisateur = new Utilisateur();
		UtilisateurDAOJdbcImpl uDAOJdbc = new UtilisateurDAOJdbcImpl();
		utilisateur = uDAOJdbc.selectById(rs.getInt("no_utilisateur"));
		Categorie categorie = new Categorie();
		CategorieDAOJdbcImpl cDAOJdbc = new CategorieDAOJdbcImpl();
		categorie = cDAOJdbc.getById(rs.getInt("no_categorie"));
		articleCourant.setCategorie(categorie);
		articleCourant.setUtilisateur(utilisateur);


		return articleCourant;
	}

	/**
	 * 
	 */
	@Override
	public Article getById(int id) {
		if (id == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return articleBuilder(rs);
			}
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Article create(Article article) {
		try (Connection cnx = ConnectionProvider.getConnection())

		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setTimestamp(3, new Timestamp(article.getDateDebutEncheres().getTime()));
			pstmt.setTimestamp(4, new Timestamp(article.getDateFinEncheres().getTime()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setInt(6, article.getUtilisateur().getNoUtilisateur());
			pstmt.setInt(7, article.getCategorie().getNoCategorie());
			pstmt.setString(8, article.getImage());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				article = getById(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return article;
	}

	@Override
	public List<Article> getAll() {
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilder(rs);
				listeArticles.add(articleCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeArticles;
	}

	@Override
	public List<Article> getAllArticleEnchere() {
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_START);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilder(rs);
				listeArticles.add(articleCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeArticles;
	}

	@Override
	public void articleUpdateEtat(String etat, int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ETAT);
			pstmt.setString(1, etat);
			pstmt.setInt(2, noArticle);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
