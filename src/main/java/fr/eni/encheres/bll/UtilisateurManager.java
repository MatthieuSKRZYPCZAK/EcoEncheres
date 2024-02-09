package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.RegisterException;
import fr.eni.encheres.exception.UpdateException;
import fr.eni.encheres.services.BCrypt;

public class UtilisateurManager {
	private UtilisateurDAO utilisateurDAO;

	/**
	 * Constructeur de la classe UtilisateurManager. Initialise l'objet en utilisant
	 * le DAOFactory pour obtenir un UtilisateurDAO.
	 */
	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	/**
	 * Récupère un utilisateur en fonction de son identifiant.
	 * 
	 * @param id L'identifiant de l'utilisateur à récupérer.
	 * @return L'objet Utilisateur correspondant à l'identifiant, ou null s'il
	 *         existe pas.
	 */
	public Utilisateur getById(int id) {
		return this.utilisateurDAO.selectById(id);
	}

	/**
	 * Supprime un utilisateur en fonction de son numéro d'utilisateur(id)
	 * 
	 * @param no_utilisateur Le numéro de l'utilisateur (id) à supprimer.
	 */
	public void supprimerUtilisateur(int no_utilisateur) {
		this.utilisateurDAO.deleteById(no_utilisateur);

	}

	/**
	 * Permet à un utilisateur de se connecter en utilisant son adresse e-mail.
	 * 
	 * @param email L'adresse e-mail de l'utilisateur pour la connexion.
	 * @return Retourne l'objet Utilisateur correspondant s'il existe, sinon null.
	 */
	public Utilisateur login(String pseudo) {
		return this.utilisateurDAO.login(pseudo);
	}

	/**
	 * Vérifie si le mail existe en base de donnée
	 * 
	 * @param email à vérifier
	 * @return {@code true} si le mail existe déjà, sinon {@code false}.
	 */
	public boolean emailExists(String email) {
		return this.utilisateurDAO.emailExists(email);
	}

	/**
	 * Vérifie si le pseudo existe en base de donnée
	 * 
	 * @param pseudo à vérifier
	 * @return {@code true} si le pseudo existe déjà, sinon {@code false}.
	 */
	public boolean pseudoExists(String pseudo) {
		return this.utilisateurDAO.pseudoExists(pseudo);
	}

	/**
	 * Crée un nouvel utilisateur en vérifiant et validant les informations fournies
	 * 
	 * @param pseudo                 Le pseudo de l'utilisateur
	 * @param nom                    Le nom de l'utilisateur
	 * @param prenom                 Le prénom de l'utilisateur
	 * @param email                  L'adresse e-mail de l'utilisateur
	 * @param telephone              Le numéro de téléphone de l'utilisateur
	 * @param rue                    L'adresse de l'utilisateur
	 * @param codePostal             Le code postal de l'utilisateur
	 * @param ville                  La ville de l'utilisateur
	 * @param motDePasse             Le mot de passe de l'utilisateur
	 * @param confirmationMotDePasse La confirmation du mot de passe de
	 *                               l'utilisateur
	 * @return Retourne l'utilisateur si il satisfait toutes les conditions sinon
	 *         lance une exception
	 * @throws RegisterException Si une ou plusieurs conditions de validations
	 *                           échouent, cette exception est lancée avec un
	 *                           message d'erreur détaillé
	 */
	public Utilisateur createUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, String confirmationMotDePasse)
			throws RegisterException {
		// Vérifie si les champs sont vides ou null
		if (pseudo.isEmpty() || pseudo == null && nom.isEmpty() || nom == null && prenom.isEmpty()
				|| prenom == null && email.isEmpty() || email == null && telephone.isEmpty()
				|| telephone == null && rue.isEmpty() || rue == null && codePostal.isEmpty()
				|| codePostal == null && ville.isEmpty() || ville == null && motDePasse.isEmpty()
				|| motDePasse == null && confirmationMotDePasse.isEmpty() || confirmationMotDePasse == null) {
			throw new RegisterException("Il y a des champs qui n'ont pas été renseignés.");
		}

		// Vérification si l'email et le pseudo existent, si le mot de passe et le mot
		// de passe confirmé sont identiques,
		// si le pseudo et le mot de passe respectent les conditions.
		boolean emailExists = emailExists(email.toLowerCase());
		boolean pseudoExists = pseudoExists(pseudo.toLowerCase());

		if (!motDePasse.equals(confirmationMotDePasse)
				|| !motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$")
				|| emailExists || pseudoExists || telephone.length() != 10 || !telephone.matches("^[0-9]{10}$")
				|| codePostal.length() != 5 || !codePostal.matches("^[0-9]{5}")
				|| !pseudo.matches("^[a-zA-Z0-9]{3,30}$")) {
			if (!motDePasse.equals(confirmationMotDePasse)) {
				throw new RegisterException("La confirmation du mot de passe ne correspond pas.");
			}
			if (!motDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$")) {
				throw new RegisterException(
						"Le mot de passe doit contenir au moins 8 caractères, dont au moins une majuscule, une minuscule, un chiffre et un caractère spécial.");
			}
			if (emailExists) {
				throw new RegisterException(" L'adresse email est déjà utilisée.");
			}
			if (pseudoExists) {
				throw new RegisterException("Le pseudo est déjà pris.");
			}
			if (!pseudo.matches("^[a-zA-Z0-9]{3,30}$")) {
				throw new RegisterException(
						"Le pseudo doit contenir entre 3 et 30 caractères alphanumériques (lettres majuscule, lettre minuscules ou chiffres).");
			}
			if (telephone.length() != 10 || !telephone.matches("^[0-9]{10}$")) {
				throw new RegisterException("Le numéro de téléphone doit contenir 10 chiffres.");
			}
			if (codePostal.length() != 5 || !codePostal.matches("^[0-9]{5}")) {
				throw new RegisterException("Le code postal doit contenir 5 chiffres consécutifs.");
			}

		}
		// Crypte le mot de passe de l'utilisateur avec BCrypt
		String passwordHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt(12));

		System.out.println("Utilisateur manager - pseudo : " + pseudo.toLowerCase() + " nom : " + nom.toUpperCase()
				+ " prenom : " + prenom.toLowerCase() + " email : " + email.toLowerCase() + " telephone : " + telephone
				+ " rue : " + rue.toLowerCase() + " code postal : " + codePostal + " ville : " + ville.toUpperCase()
				+ " password : " + passwordHash);

		// création de l'utilisateur et retourne l'utilisateur crée
		Utilisateur utilisateur = new Utilisateur(pseudo.toLowerCase(), nom.toUpperCase(), prenom.toLowerCase(),
				email.toLowerCase(), telephone, rue.toLowerCase(), codePostal, ville.toUpperCase(), passwordHash);
		utilisateur = utilisateurDAO.insert(utilisateur);

		return utilisateur;
	}

	/**
	 * 
	 * @param id
	 * @param pseudo
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param telephone
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param motDePasse
	 * @param nouveauMotDePasse
	 * @param confirmationMotDePasse
	 * @return
	 * @throws UpdateException
	 */
	public Utilisateur updateUtilisateur(int id, String pseudo, String nom, String prenom, String email,
			String telephone, String rue, String codePostal, String ville, String motDePasse, String nouveauMotDePasse,
			String confirmationMotDePasse) throws UpdateException {
		Utilisateur utilisateurSession = getById(id);
		Utilisateur updateUtilisateur = new Utilisateur();

		// Vérifie si les champs sont vides ou null
		if (pseudo.isEmpty() || pseudo == null && nom.isEmpty() || nom == null && prenom.isEmpty()
				|| prenom == null && email.isEmpty() || email == null && telephone.isEmpty()
				|| telephone == null && rue.isEmpty() || rue == null && codePostal.isEmpty()
				|| codePostal == null && ville.isEmpty() || ville == null) {
			throw new UpdateException("Il y a des champs qui n'ont pas été renseignés.");
		}

		boolean emailExists = emailExists(email.toLowerCase());
		boolean pseudoExists = pseudoExists(pseudo.toLowerCase());

		if (!BCrypt.checkpw(motDePasse, utilisateurSession.getMotDePasse())) {
			throw new UpdateException("Mot de passe incorrect");
		}

		if (emailExists) {
			if ((!utilisateurSession.getEmail().toLowerCase().equals(email.toLowerCase()))) {
				throw new UpdateException("L'adresse email est déjà utilisée.");
			}
		}

		if (pseudoExists) {
			if ((!utilisateurSession.getPseudo().toLowerCase().equals(pseudo.toLowerCase()))) {
				throw new UpdateException(" Le pseudo est déjà pris.");
			}
		}

		if (!pseudo.matches("^[a-zA-Z0-9]{3,30}$")) {
			throw new UpdateException(
					"Le pseudo doit contenir entre 3 et 30 caractères alphanumériques (lettres majuscule, lettre minuscules ou chiffres).");
		}
		if (telephone.length() != 10 || !telephone.matches("^[0-9]{10}$")) {
			throw new UpdateException("Le numéro de téléphone doit contenir 10 chiffres.");
		}
		if (codePostal.length() != 5 || !codePostal.matches("^[0-9]{5}")) {
			throw new UpdateException("Le code postal doit contenir 5 chiffres consécutifs.");
		}

		if (!nouveauMotDePasse.isEmpty()) {
			if (!nouveauMotDePasse.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$")) {
				throw new UpdateException(
						"Le mot de passe doit contenir au moins 8 caractères, dont au moins une majuscule, une minuscule, un chiffre et un caractère spécial.");
			}
			if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
				throw new UpdateException(
						"Le nouveau mot de passe ne correspond pas avec le mot de passe de confirmation.");
			}
		}

//				if(errorMessage.length() > 0) {
//					throw new UpdateException(errorMessage.toString());
//				}

		if (!nouveauMotDePasse.isEmpty()) {
			String passwordHash = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt(12));
			updateUtilisateur.setMotDePasse(passwordHash);
		} else {
			updateUtilisateur.setMotDePasse(utilisateurSession.getMotDePasse());
		}
		updateUtilisateur.setPseudo(pseudo.toLowerCase());
		updateUtilisateur.setNom(nom.toUpperCase());
		updateUtilisateur.setPrenom(prenom.toLowerCase());
		updateUtilisateur.setEmail(email.toLowerCase());
		updateUtilisateur.setCodePostal(codePostal);
		updateUtilisateur.setRue(rue.toLowerCase());
		updateUtilisateur.setNoUtilisateur(id);
		updateUtilisateur.setVille(ville.toUpperCase());
		updateUtilisateur.setTelephone(telephone);

		updateUtilisateur = utilisateurDAO.updateUtilisateur(updateUtilisateur);
		return updateUtilisateur;
	}

	/**
	 * Récupère la liste de tous les utilisateurs
	 * 
	 * @return Retourne la liste des utilisateurs
	 */
	public List<Utilisateur> selectionnerTousLesUtilisateurs() {
		return this.utilisateurDAO.selectAllUsers();
	}

	public void supprimer(int no_utilisateur) {
		this.utilisateurDAO.delete(no_utilisateur);

	}

	public void desactive(int id) {
		this.utilisateurDAO.disable(id);

	}

	public void active(int id) {
		this.utilisateurDAO.enable(id);

	}
}
