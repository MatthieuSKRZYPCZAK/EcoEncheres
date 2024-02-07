package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Encheres;

public interface EnchereDAO {

	Encheres enchereExist(int noArticle);

	void placeBid(int noUtilisateur, int noArticle, int montantDeLEnchere);

	void placeNewBid(int noUtilisateur, int noArticle, int montantDeLEnchere);

	void refundPreviousBidder(int credit, int bidder);

	void paidBid(int utilisateur, int montantDeLEnchere);

	List<Encheres> getAll();

}
