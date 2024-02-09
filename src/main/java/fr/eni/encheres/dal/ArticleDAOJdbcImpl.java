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
import fr.eni.encheres.bo.Utilisateur;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	// INSERT
	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie, image) VALUES(?,?,?,?,?,?,?,?);";

	// READ
	private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS;";
	private static final String SELECT_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=?;";
	private static final String SELECT_ALL_START = "SELECT *FROM ARTICLES_VENDUS WHERE etat_vente = 'en cours';";
	private static final String SELECT_ALL_START_CAT = "SELECT *FROM ARTICLES_VENDUS WHERE etat_vente = 'en cours' AND no_categorie=?;";
	private static final String SELECT_ALL_WITH_ENCHERES = "SELECT ARTICLES_VENDUS.*, ENCHERES.*, CATEGORIES.*, ENCHERES.no_utilisateur AS acheteur, ARTICLES_VENDUS.no_utilisateur AS vendeur FROM ARTICLES_VENDUS LEFT JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article LEFT JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie;";
	private static final String SELECT_ALL_BY_USER_ID = "SELECT ARTICLES_VENDUS.*, ENCHERES.no_utilisateur AS acheteur, ENCHERES.montant_enchere AS montantEnchere, ENCHERES.date_enchere FROM ARTICLES_VENDUS LEFT JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article WHERE ARTICLES_VENDUS.no_utilisateur = ?;";

	// UPDATE
	private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, no_utilisateur=?, no_categorie=?, image=? WHERE no_article=?;";
	private static final String UPDATE_ETAT = "UPDATE ARTICLES_VENDUS SET etat_vente = ? WHERE no_article=?;";
	private static final String UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article=?;";

	// DELETE
	private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article=?;";

	/**
	 * Construit un objet Article à partir d'un ResultSet.
	 *
	 * @param rs Le ResultSet à partir duquel construire l'objet Article.
	 * @return L'objet Article construit à partir du ResultSet.
	 * @throws SQLException Si une erreur SQL survient lors de la construction de l'objet Article.
	 */
	private Article articleBuilder(ResultSet rs) throws SQLException {
		Article articleCourant;
		articleCourant = new Article();
		articleCourant.setNoArticle(rs.getInt("no_article"));
		articleCourant.setNomArticle(rs.getString("nom_article"));
		articleCourant.setDescription(rs.getString("description"));
		Timestamp debut = rs.getTimestamp("date_debut_encheres");
		Date dateDebut = new Date(debut.getTime());
		Timestamp fin = rs.getTimestamp("date_fin_encheres");
		Date dateFin = new Date(fin.getTime());
		articleCourant.setDateDebutEncheres(dateDebut);
		articleCourant.setDateFinEncheres(dateFin);
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
	 * Récupère un Article à partir de son identifiant.
	 *
	 * @param id L'identifiant de l'Article à récupérer.
	 * @return L'Article correspondant à l'identifiant spécifié, ou null si aucun Article n'est trouvé.
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
	 * Crée un nouvel Article dans la base de données.
	 *
	 * @param article L'Article à créer dans la base de données.
	 * @return L'Article créé, ou null si la création a échoué.
	 */
	@Override
	public Article create(Article article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
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

	/**
	 * Récupère tous les articles de la base de données.
	 *
	 * @return Une liste contenant tous les articles.
	 */
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

	/**
	 * Récupère tous les articles en vente aux enchères de la base de données.
	 *
	 * @return Une liste contenant tous les articles en vente aux enchères.
	 */
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

	/**
	 * Met à jour l'état de vente d'un article dans la base de données.
	 *
	 * @param etat     Le nouvel état de vente de l'article.
	 * @param noArticle Le numéro de l'article à mettre à jour.
	 */
	@Override
	public void articleUpdateEtat(String etat, int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ETAT);
			pstmt.setString(1, etat);
			pstmt.setInt(2, noArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour le prix de vente d'un article dans la base de données.
	 *
	 * @param montantEnchere Le nouveau prix de vente de l'article.
	 * @param noArticle      Le numéro de l'article à mettre à jour.
	 */
	@Override
	public void updatePrixVente(int montantEnchere, int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_PRIX_VENTE);
			pstmt.setInt(1, montantEnchere);
			pstmt.setInt(2, noArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère tous les articles de la base de données avec les dates de début et de fin formattées.
	 *
	 * @return Une liste contenant tous les articles avec les dates formattées.
	 */
	@Override
	public List<Article> getAllForDate() {
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article articleCourant;
				articleCourant = new Article();
				articleCourant.setNoArticle(rs.getInt("no_article"));
				articleCourant.setNomArticle(rs.getString("nom_article"));
				articleCourant.setDescription(rs.getString("description"));
				Timestamp debut = rs.getTimestamp("date_debut_encheres");
				Date dateDebut = new Date(debut.getTime());
				Timestamp fin = rs.getTimestamp("date_fin_encheres");
				Date dateFin = new Date(fin.getTime());
				articleCourant.setDateDebutEncheres(dateDebut);
				articleCourant.setDateFinEncheres(dateFin);
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
				listeArticles.add(articleCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeArticles;
	}

	/**
	 * Met à jour les informations d'un article dans la base de données.
	 *
	 * @param article L'article avec les nouvelles informations.
	 * @return L'article mis à jour.
	 */
	@Override
	public Article update(Article article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setTimestamp(3, new Timestamp(article.getDateDebutEncheres().getTime()));
			pstmt.setTimestamp(4, new Timestamp(article.getDateFinEncheres().getTime()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setInt(6, article.getUtilisateur().getNoUtilisateur());
			pstmt.setInt(7, article.getCategorie().getNoCategorie());
			pstmt.setString(8, article.getImage());
			pstmt.setInt(9, article.getNoArticle());
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				Article updateArticle = getById(article.getNoArticle());
				return updateArticle;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Supprime un article de la base de données.
	 *
	 * @param noArticle Le numéro de l'article à supprimer.
	 */
	@Override
	public void detele(int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, noArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère tous les articles d'une catégorie en cours de vente aux enchères.
	 *
	 * @param noCategorie Le numéro de la catégorie des articles à récupérer.
	 * @return Une liste contenant tous les articles de la catégorie en cours de vente aux enchères.
	 */
	@Override
	public List<Article> getAllArticleByCategorieEnCours(int noCategorie) {
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_START_CAT);
			pstmt.setInt(1, noCategorie);
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

	/**
	 * Récupère tous les articles de la base de données avec les informations sur les enchères associées.
	 *
	 * @return Une liste contenant tous les articles avec les informations sur les enchères associées.
	 */
	@Override
	public List<Article> getAllArticlesWithEncheres() {
		List<Article> list = new ArrayList<Article>();
		try (Connection cnx = ConnectionProvider.getConnection();) {
			PreparedStatement ps = cnx.prepareStatement(SELECT_ALL_WITH_ENCHERES);
			ResultSet rs = ps.executeQuery();
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
				int montant_enchere = rs.getInt("montant_enchere");
				int idAcheteur = rs.getInt("acheteur");
				int idVendeur = rs.getInt("vendeur");
				String image = (rs.getString("image"));
				Utilisateur acheteur = new Utilisateur();
				UtilisateurDAOJdbcImpl aDAOJdbc = new UtilisateurDAOJdbcImpl();
				acheteur = aDAOJdbc.selectById(idAcheteur);
				Utilisateur vendeur = new Utilisateur();
				UtilisateurDAOJdbcImpl vDAOJdbc = new UtilisateurDAOJdbcImpl();
				vendeur = vDAOJdbc.selectById(idVendeur);

				Article art = new Article(no_article, nom_article, dateDebut, dateFin, prix_initial, montant_enchere,
						etat_vente, vendeur, cat, image, acheteur);
				list.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Récupère tous les articles d'un utilisateur donné.
	 *
	 * @param id L'identifiant de l'utilisateur.
	 * @return Une liste contenant tous les articles de l'utilisateur.
	 */
	@Override
	public List<Article> getAllByUserId(int id) {
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
				int idAcheteur = rs.getInt("acheteur");
				String image = (rs.getString("image"));
				Utilisateur acheteur = new Utilisateur();
				UtilisateurDAOJdbcImpl aDAOJdbc = new UtilisateurDAOJdbcImpl();
				acheteur = aDAOJdbc.selectById(idAcheteur);
				Utilisateur vendeur = new Utilisateur();
				UtilisateurDAOJdbcImpl vDAOJdbc = new UtilisateurDAOJdbcImpl();
				vendeur = vDAOJdbc.selectById(id);
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
