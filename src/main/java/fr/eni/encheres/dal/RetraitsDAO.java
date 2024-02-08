package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retraits;

public interface RetraitsDAO {

	void create(Retraits retrait);

	Retraits getByNoArticle(int noArticle);

	void update(Retraits retrait);

}
