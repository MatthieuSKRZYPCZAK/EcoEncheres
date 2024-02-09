package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	//INSERT
	private static final String INSERT="INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?);";
	
	
	//UPDATE
	private static final String UPDATE="UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?;";
	private static final String ENABLE="UPDATE UTILISATEURS SET actif=1 WHERE no_utilisateur=?;";
	private static final String DISABLE="UPDATE UTILISATEURS SET actif=0 WHERE no_utilisateur=?;";
	
	
	//READ
	private static final String SELECT_ALL="SELECT * FROM UTILISATEURS ORDER BY no_utilisateur desc;";
	private static final String SELECT_ID="SELECT * FROM UTILISATEURS WHERE no_utilisateur=?;";
	private static final String SELECT_EMAIL="SELECT * FROM UTILISATEURS WHERE email=?;";
	private static final String SELECT_PSEUDO="SELECT * FROM UTILISATEURS WHERE pseudo=?;";
	
	//DELETE
	private static final String DELETE="DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";
	


	/**
	 * Récupère un utilisateur de la base de données en utilisant son identifiant (no_utilisateur).
	 * @param id (no_utilisateur) Identifiant de l'utilisateur à rechercher
	 * @return Retourne un objet Utilisateur correspondant à l'id fourni.
	 * @throws SQLException
	 */
	@Override
	public Utilisateur selectById(int id) {
		if (id == 0) {
			return null;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return utilisateurBuilder(rs);
			}
			return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Méthode permet à un utilisateur de se connecter en utilisant son adresse e-mail.
	 * @param email L'adresse e-mail de l'utilisateur pour la connexion.
	 * @return Retourne un objet Utilisateur correspondant s'il existe, sinon null.
	 * @throws SQLException
	 */
	@Override
	public Utilisateur login(String pseudo) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return utilisateurBuilder(rs);
			}
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
	public void deleteById(int noUtilisateur) {
		if(noUtilisateur == 0) {
			System.out.println("Pas d'id, prévoir l'erreur (exception)");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, noUtilisateur);
			pstmt.executeUpdate();
			System.out.println("[UtilisateurDAOJdbcImpl:deleteById] L'utilisateur id : " + noUtilisateur + "a été supprimé");
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
	static Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
		Utilisateur utilisateurCourant;
		utilisateurCourant = new Utilisateur();
		utilisateurCourant.setNoUtilisateur(rs.getInt("no_utilisateur"));
		utilisateurCourant.setPseudo(rs.getString("pseudo"));
		utilisateurCourant.setNom(rs.getString("nom"));
		utilisateurCourant.setPrenom(rs.getString("prenom"));
		utilisateurCourant.setEmail(rs.getString("email"));
		utilisateurCourant.setTelephone(rs.getString("telephone"));
		utilisateurCourant.setRue(rs.getString("rue"));
		utilisateurCourant.setCodePostal(rs.getString("code_postal"));
		utilisateurCourant.setVille(rs.getString("ville"));
		utilisateurCourant.setMotDePasse(rs.getString("mot_de_passe"));
		utilisateurCourant.setCredit(rs.getInt("credit"));
		utilisateurCourant.setAdministrateur(rs.getBoolean("administrateur"));
		utilisateurCourant.setActif(rs.getBoolean("actif"));
		
		return utilisateurCourant;
	}

	/**
	 * Vérifie si l'email existe déjà dans la base de données.
	 * @param email de l'adresse email à vérifier.
	 * @return {@code true} si l'email existe déjà, {@code false} sinon.
	 * @throws SQLException
	 */
	@Override
	public boolean emailExists(String email) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_EMAIL);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Vérifie si le pseudo existe déjà dans la base de données.
	 * @param pseudo Le pseudo à vérifier
	 * @return {@code true} si le pseudo existe déjà, {@code false} sinon.
	 * @throws SQLException
	 */
	@Override
	public boolean pseudoExists(String pseudo) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Insère un nouvel utilisateur dans la base de données
	 * @param utilisateur L'utilisateur à insérer dans la base de données
	 * @return L'utilisateur avec ses données mises à jour, y compris son ID
	 * @throws SQLException
	 */
	@Override
	public Utilisateur insert(Utilisateur utilisateur) {


		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();


			if(rs.next())
			{
				int id = rs.getInt(1);
				utilisateur = selectById(id);
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return utilisateur;
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getNoUtilisateur());
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated > 0) {
				Utilisateur updateUser = selectById(utilisateur.getNoUtilisateur());
				return updateUser;
			}else {
				return null;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Récupère et renvoie la liste de tous les utilisateurs depuis la base de donnée
	 * @return Retourne une liste de tous les utilisateurs présents dans la base de donnée
	 */
	@Override
	public List<Utilisateur> selectAllUsers() {
		List<Utilisateur> listeUtilisateurs= new ArrayList<Utilisateur>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			Utilisateur utilisateurCourant=new Utilisateur();
			while(rs.next())
			{
				if(rs.getInt("no_utilisateur")!=utilisateurCourant.getNoUtilisateur())
				{
					utilisateurCourant = utilisateurBuilder(rs);
					listeUtilisateurs.add(utilisateurCourant);
				}

			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listeUtilisateurs;
	}

	@Override
	public void delete(int no_utilisateur) {
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE);
			pstmt.setInt(1, no_utilisateur);
			pstmt.executeUpdate();
	
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void disable(int id) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DISABLE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
	
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void enable(int id) {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(ENABLE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
	
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

}
