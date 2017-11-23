package com.tikal.cacao.dao;

import com.tikal.cacao.model.ListaDeClasesDeProdServ;

public interface ListaDeClavesDeClasesDAO {

	public void guardar(ListaDeClasesDeProdServ lista);
	
	public ListaDeClasesDeProdServ obtener(String rfc);
}
