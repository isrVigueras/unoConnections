package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.ListaDeClavesDeClasesDAO;
import com.tikal.cacao.model.ListaDeClasesDeProdServ;

@Repository
public class ListaDeClavesDeClasesDAOImpl implements ListaDeClavesDeClasesDAO {

	@Override
	public void guardar(ListaDeClasesDeProdServ lista) {
		ofy().save().entity(lista).now();

	}

	@Override
	public ListaDeClasesDeProdServ obtener(String rfc) {
		
		return ofy().load().type(ListaDeClasesDeProdServ.class).id(rfc).now();
	}

}
