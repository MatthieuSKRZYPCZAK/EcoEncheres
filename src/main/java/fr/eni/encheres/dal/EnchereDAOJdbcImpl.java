package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	// INSERT
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?,?,?,?);";

	// READ
	private static final String SELECT_ID_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article=?;";
	private static final String SELECT_ALL = "SELECT * FROM ENCHERES;";

	// UPDATE
	private static final String PAID = "UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?;";
	private static final String REFUND = "UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?;";
	private static final String UPDATE = "UPDATE ENCHERES SET no_utilisateur=?, date_enchere=?, montant_enchere=? WHERE no_article=?;";

	@Override
	public Encheres enchereExist(int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return enchereBuilder(rs);
			}
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Encheres enchereBuilder(ResultSet rs) throws SQLException {
		Encheres encheresCourant;
		encheresCourant = new Encheres();
		encheresCourant.setDateEnchere(rs.getDate("date_enchere"));
		encheresCourant.setMontantEnchere(rs.getInt("montant_enchere"));
		Utilisateur utilisateur = new Utilisateur();
		UtilisateurDAOJdbcImpl uDAOJdbc = new UtilisateurDAOJdbcImpl();
		utilisateur = uDAOJdbc.selectById(rs.getInt("no_utilisateur"));
		Article article = new Article();
		ArticleDAOJdbcImpl aDAOJdbc = new ArticleDAOJdbcImpl();
		article = aDAOJdbc.getById(rs.getInt("no_article"));
		encheresCourant.setArticle(article);
		encheresCourant.setUtilisateur(utilisateur);

		return encheresCourant;
	}

	@Override
	public void placeBid(int noUtilisateur, int noArticle, int montantDeLEnchere) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setInt(1, noUtilisateur);
			pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(3, montantDeLEnchere);
			pstmt.setInt(4, noArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void placeNewBid(int noUtilisateur, int noArticle, int montantDeLEnchere) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setInt(1, noUtilisateur);
			pstmt.setInt(2, noArticle);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(4, montantDeLEnchere);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void refundPreviousBidder(int credit, int bidder) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(REFUND);
			pstmt.setInt(1, credit);
			pstmt.setInt(2, bidder);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paidBid(int utilisateur, int montantDeLEnchere) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(PAID);
			pstmt.setInt(1, montantDeLEnchere);
			pstmt.setInt(2, utilisateur);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Encheres> getAll() {
		List<Encheres> listeEncheres = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			Encheres enchereCourant = new Encheres();
			while (rs.next()) {
				enchereCourant = enchereBuilder(rs);
				listeEncheres.add(enchereCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeEncheres;
	}

}
