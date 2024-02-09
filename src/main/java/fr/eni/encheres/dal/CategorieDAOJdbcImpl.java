package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;

public class CategorieDAOJdbcImpl implements CategorieDAO {

	// READ
	private static final String SELECT_ALL = "SELECT * FROM CATEGORIES;";
	private static final String SELECT_CATEGORIE = "SELECT COUNT(*) FROM CATEGORIES WHERE LOWER(libelle) = LOWER(?)";
	private static final String SELECT_ID = "SELECT * FROM CATEGORIES WHERE no_categorie=?;";

	// INSERT
	private static final String INSERT = "INSERT INTO CATEGORIES (libelle) VALUES(?);";

	// UPDATE
	private static final String UPDATE = "UPDATE CATEGORIES SET libelle=? WHERE no_categorie = ?;";

	// DELETE
	private static final String DELETE = "DELETE FROM CATEGORIES WHERE no_categorie=?;";

	/**
	 * Crée une nouvelle catégorie avec le libellé spécifié.
	 * 
	 * @param categorie Le libellé de la nouvelle catégorie à créer.
	 */
	@Override
	public void create(String categorie) {

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setString(1, categorie);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime la catégorie avec l'identifiant spécifié.
	 * 
	 * @param id L'identifiant de la catégorie (no_categorie) à supprimer.
	 */
	@Override
	public void delete(int id) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Met à jour le libellé de la catégorie avec l'identifiant spécifié.
	 * 
	 * @param id      L'identifiant de la catégorie à mettre à jour.
	 * @param libelle Le nouveau libellé de la catégorie.
	 */
	@Override
	public void update(int id, String libelle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, libelle);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Récupère toutes les catégories présentes dans la base de données.
	 * 
	 * @return Une liste contenant toutes les catégories présentes dans la base de
	 *         données.
	 */
	@Override
	public List<Categorie> allCategories() {
		List<Categorie> listeCategories = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int noCategorie = rs.getInt("no_categorie");
				String libelle = rs.getString("libelle");
				Categorie categorieCourant = new Categorie(noCategorie, libelle);
				listeCategories.add(categorieCourant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeCategories;
	}

	/**
	 * Vérifie si une catégorie avec le libellé spécifié existe déjà dans la base de
	 * données.
	 * 
	 * @param libelle Le libellé de la catégorie à vérifier.
	 * @return Vrai si la catégorie existe déjà, sinon faux.
	 */
	@Override
	public boolean existe(String libelle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Récupère toutes les catégories présentes dans la base de données
	 * 
	 * @return Une liste contenant toutes les catégories présentes dans la base de
	 *         données.
	 */
	@Override
	public List<Categorie> selectAll() {
		List<Categorie> listCategorie = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int noCategorie = rs.getInt("no_categorie");
				String libelle = rs.getString("libelle");
				Categorie categorie = new Categorie(noCategorie, libelle);
				listCategorie.add(categorie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCategorie;
	}

	/**
	 * Construit une catégorie à partir d'un ResultSet.
	 * 
	 * @param rs Le ResultSet à partir duquel construire la catégorie.
	 * @return La catégorie construite à partir du ResultSet.
	 * @throws SQLException
	 */
	static Categorie categorieBuilder(ResultSet rs) throws SQLException {
		Categorie categorieCourant;
		categorieCourant = new Categorie();
		categorieCourant.setNoCategorie(rs.getInt("no_categorie"));
		categorieCourant.setLibelle(rs.getString("libelle"));

		return categorieCourant;
	}

	/**
	 * Récupère une catégorie à partir de son identifiant.
	 * 
	 * @param id L'identifiant de la catégorie à récupérer.
	 * @return La catégorie correspondant à l'identifiant spécifié, ou null si
	 *         aucune catégorie n'est trouvée.
	 */
	@Override
	public Categorie getById(int id) {
		if (id == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return categorieBuilder(rs);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
