package com.tikal.cacao.dao;

import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Receptor;

/**
 * @author Tikal
 *
 */
public interface EmisorDAO {

	void crear(Emisor emisor);
	
	Emisor consultar(String rfc);
	
	public void addReceptor(String rfc, Receptor r);
	
	public void updateReceptor(String rfc, Receptor r, int indice);
	
	public void deleteReceptor(String rfc,int indice);
	
}
