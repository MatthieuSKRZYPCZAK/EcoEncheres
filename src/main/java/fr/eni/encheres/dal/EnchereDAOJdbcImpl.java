package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	// INSERT
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?,?,?,?);";

	// READ
	private static final String SELECT_ID_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article=?;";
	private static final String SELECT_ALL = "SELECT * FROM ENCHERES;";
	private static final String SELECT_ALL_BY_USER_ID = "SELECT ARTICLES_VENDUS.*, ENCHERES.no_utilisateur AS acheteur, ENCHERES.montant_enchere AS montantEnchere FROM ENCHERES LEFT JOIN ARTICLES_VENDUS ON ARTICLES_VENDUS.no_article = ENCHERES.no_article WHERE ENCHERES.no_utilisateur = ?";

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

	public Encheres enchereBuilder(ResultSet rs) throws SQLException {
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

	@Override
	public List<Article> getEncheresByUserId(int id) {
		List<Article> list = new ArrayList<Article>();
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_BY_USER_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int categorie = rs.getInt("no_categorie");
				Categorie cat = new Categorie();
				CategorieDAOJdbcImpl cDAOJdbc = new CategorieDAOJdbcImpl();
				cat = cDAOJdbc.getById(categorie);
				int no_article = rs.getInt("no_article");
				String nom_article = rs.getString("nom_article");
				int prix_initial = rs.getInt("prix_initial");
				Timestamp debut = rs.getTimestamp("date_debut_encheres");
				Date dateDebut = new Date(debut.getTime());
				Timestamp fin = rs.getTimestamp("date_fin_encheres");
				Date dateFin = new Date(fin.getTime());
				String etat_vente = rs.getString("etat_vente");
				int montant_enchere = rs.getInt("montantEnchere");
				int idVendeur = rs.getInt("no_utilisateur");
				String image = (rs.getString("image"));
				Utilisateur acheteur = new Utilisateur();
				UtilisateurDAOJdbcImpl aDAOJdbc = new UtilisateurDAOJdbcImpl();
				Utilisateur vendeur = new Utilisateur();
				UtilisateurDAOJdbcImpl vDAOJdbc = new UtilisateurDAOJdbcImpl();
				vendeur = aDAOJdbc.selectById(idVendeur);
				acheteur = vDAOJdbc.selectById(id);
				Article art = new Article(no_article, nom_article, dateDebut, dateFin, prix_initial, montant_enchere,
						etat_vente, vendeur, cat, image, acheteur);
				list.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
