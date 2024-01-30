package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	
	//READ
	private static final String SELECT_ID="SELECT * FROM UTILISATEURS WHERE no_utilisateur=?;";
	
	//DELETE
	private static final String DELETE="DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";




	/**
	 * Récupère un utilisateur de la base de données en utilisant son identifiant (no_utilisateur).
	 * @param id (no_utilisateur) Identifiant de l'utilisateur à rechercher
	 * @return Retourne un objtet Utilisateur correspondant à l'id fourni
	 * @throws SQLException
	 */
	@Override
	public Utilisateur selectById(int id) {
		if (id == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return utilisateurBuilder(rs);
			}
			rs.close();
			cnx.close();
			return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Supprime un utilisateur en utilisant son identifiant (no_utilisateur)
	 * @param id (no_utilisateur) Identifiant de l'utilisateur à supprimer
	 * @throws SQLException
	 */
	@Override
	public void deleteById(int no_utilisateur) {
		if(no_utilisateur == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, no_utilisateur);
			pstmt.executeUpdate();
			cnx.close();
			System.out.println("[UtilisateurDAOJdbcImpl:deleteById] L'utilisateur id : " + no_utilisateur + "a été supprimé");
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Construit et retourne un Objet Utilisateur à partir des données d'un ResultSet
	 * @param rs ResultSet contenant les données d'un utilisateur spécifique
	 * @return Retourne un Objet Utilisateur avec les données extraites du ResultSet
	 * @throws SQLException
	 */
	private Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
		Utilisateur utilisateurCourant;
		utilisateurCourant = new Utilisateur();
		utilisateurCourant.setPseudo(rs.getString("pseudo"));
		utilisateurCourant.setNom(rs.getString("nom"));
		utilisateurCourant.setPrenom(rs.getString("prenom"));
		utilisateurCourant.setEmail(rs.getString("email"));
		utilisateurCourant.setTelephone(rs.getString("telephone"));
		utilisateurCourant.setRue(rs.getString("rue"));
		utilisateurCourant.setCode_postal(rs.getString("code_postal"));
		utilisateurCourant.setVille(rs.getString("ville"));
		utilisateurCourant.setMot_de_passe(rs.getString("mot_de_passe"));
		utilisateurCourant.setCredit(rs.getInt("credit"));
		utilisateurCourant.setAdministrateur(rs.getBoolean("administrateur"));
		utilisateurCourant.setActif(rs.getBoolean("actif"));
		
		return utilisateurCourant;
	}


	

}
