package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retraits;

public class RetraitsDAOJdbcImpl implements RetraitsDAO {

	// INSERT
	private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES(?,?,?,?);";
	private static final String SELECT_BY_NO_ARTICLE = "SELECT * FROM RETRAITS WHERE no_article=?;";
	
	// UPDATE
	private static final String UPDATE = "UPDATE RETRAITS SET rue=?, code_postal=?, ville=? WHERE no_article = ?;";

	@Override
	public void create(Retraits retrait) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setInt(1, retrait.getArticle().getNoArticle());
			pstmt.setString(2, retrait.getRue());
			pstmt.setString(3, retrait.getCodePostal());
			pstmt.setString(4, retrait.getVille());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	static Retraits retraitsBuilder(ResultSet rs) throws SQLException {
		Retraits retraitsCourant;
		retraitsCourant = new Retraits();
		retraitsCourant.setCodePostal(rs.getString("code_postal"));
		retraitsCourant.setRue(rs.getString("rue"));
		retraitsCourant.setVille(rs.getString("ville"));
		Article article = new Article();
		ArticleDAOJdbcImpl aDAOJdbc = new ArticleDAOJdbcImpl();
		article = aDAOJdbc.getById(rs.getInt("no_article"));
		retraitsCourant.setArticle(article);
		return retraitsCourant;
	}

	@Override
	public Retraits getByNoArticle(int noArticle) {
		if (noArticle == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return retraitsBuilder(rs);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Retraits retrait) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, retrait.getRue());
			pstmt.setString(2, retrait.getCodePostal());
			pstmt.setString(3, retrait.getVille());
			pstmt.setInt(4, retrait.getArticle().getNoArticle());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
