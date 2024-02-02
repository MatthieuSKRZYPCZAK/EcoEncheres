package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;


public class CategorieDAOJdbcImpl implements CategorieDAO{
	
	//READ
	private static final String SELECT_ALL="SELECT * FROM CATEGORIES;";
	private static final String SELECT_CATEGORIE="SELECT COUNT(*) FROM CATEGORIES WHERE LOWER(libelle) = LOWER(?)";

	
	//INSERT
	private static final String INSERT="INSERT INTO CATEGORIES (libelle) VALUES(?);";
	
	//UPDATE
	private static final String UPDATE="UPDATE CATEGORIES SET libelle=? WHERE no_categorie = ?;";
	
	//DELETE
	private static final String DELETE="DELETE FROM CATEGORIES WHERE no_categorie=?;";

	@Override
	public void insert(String categorie) {


		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setString(1, categorie);
			pstmt.executeUpdate();


		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public void delete(int id) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
	
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(int id, String libelle) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, libelle);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Categorie> allCategories() {
		List<Categorie> listeCategories= new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				int noCategorie = rs.getInt("no_categorie");
				String libelle = rs.getString("libelle");
				Categorie categorieCourant=new Categorie(noCategorie, libelle);
				listeCategories.add(categorieCourant);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listeCategories;
	}



	@Override
	public boolean existe(String libelle) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
