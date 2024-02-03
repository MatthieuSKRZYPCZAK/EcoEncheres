package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	//READ
		private static final String SELECT_ALL="SELECT * FROM ARTICLES_VENDUS;";
		private static final String SELECT_ID="SELECT * FROM ARTICLES_VENDUS WHERE no_article=?;";
		
	
		
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
			try {
				Utilisateur utilisateur = UtilisateurDAOJdbcImpl.utilisateurBuilder(rs);
				articleCourant.setUtilisateur(utilisateur);
				Categorie categorie = CategorieDAOJdbcImpl.categorieBuilder(rs);
				articleCourant.setCategorie(categorie);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return articleCourant;
		}
		
		
	@Override
	public Article getById(int id) {
		if (id == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return articleBuilder(rs);
			}
			return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
