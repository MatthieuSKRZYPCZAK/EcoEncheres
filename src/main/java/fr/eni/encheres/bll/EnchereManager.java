package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.ArticleException;

public class EnchereManager {
	private EnchereDAO enchereDAO;

	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}

	public Encheres enchereExist(int noArticle) {
		return this.enchereDAO.enchereExist(noArticle);
	}

	public void enchere(Utilisateur utilisateur, Article article, int montantDeLEnchere) throws ArticleException {
		if (montantDeLEnchere > utilisateur.getCredit()) {
			throw new ArticleException("Vous n'avez pas assez de crédit pour payer cette somme");
		}
		if (!article.getEtatVente().contains("en cours")) {
			throw new ArticleException(
					"l'article n'est actuellement pas disponible à la vente ou a été retiré de la vente.");
		}
		if (article.getUtilisateur().getNoUtilisateur() == (utilisateur.getNoUtilisateur())) {
			throw new ArticleException("Vous ne pouvez pas enchérir sur votre propre article");
		}
		int prix = 0;

		Encheres encheres = enchereExist(article.getNoArticle());
		if (encheres != null) {
			if (encheres.getMontantEnchere() > utilisateur.getCredit()) {
				throw new ArticleException("Vous n'avez pas assez de crédit.");
			}
			prix = encheres.getMontantEnchere();
			if (prix > utilisateur.getCredit()) {
				throw new ArticleException("Vous n'avez pas assez de crédit.");
			}
			Utilisateur lastBidder = encheres.getUtilisateur();
			refundPreviousBidder((encheres.getMontantEnchere() + lastBidder.getCredit()),
					lastBidder.getNoUtilisateur());
			placeBid(utilisateur.getNoUtilisateur(), article.getNoArticle(), montantDeLEnchere);
		} else {
			prix = article.getPrixInitial();
			if (prix > utilisateur.getCredit()) {
				throw new ArticleException("Vous n'avez pas assez de crédit.");
			}
			placeNewBid(utilisateur.getNoUtilisateur(), article.getNoArticle(), montantDeLEnchere);
		}

		paidBid(utilisateur.getNoUtilisateur(), (utilisateur.getCredit() - montantDeLEnchere));

	}

	private void paidBid(int utilisateur, int montantDeLEnchere) {
		this.enchereDAO.paidBid(utilisateur, montantDeLEnchere);

	}

	private void placeNewBid(int noUtilisateur, int noArticle, int montantDeLEnchere) {
		this.enchereDAO.placeNewBid(noUtilisateur, noArticle, montantDeLEnchere);

	}

	private void placeBid(int noUtilisateur, int noArticle, int montantDeLEnchere) {
		this.enchereDAO.placeBid(noUtilisateur, noArticle, montantDeLEnchere);

	}

	public void refundPreviousBidder(int credit, int bidder) {
		this.enchereDAO.refundPreviousBidder(credit, bidder);
	}

	public List<Encheres> getAll() {
		return this.enchereDAO.getAll();
	}

	public List<Article> getEncheresByUserId(int id) {
		return this.enchereDAO.getEncheresByUserId(id);
	}

}
