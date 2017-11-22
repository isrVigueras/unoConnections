package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Receptor;

/**
 * 
 * @author Tikal
 *
 */
@Repository
public class EmisorDAOImpl implements EmisorDAO {

	@Override
	public void crear(Emisor emisor) {
		ofy().save().entity(emisor).now();
	}

	@Override
	public Emisor consultar(String rfc) {
		return ofy().load().type(Emisor.class).id(rfc).now();
	}

	@Override
	public void addReceptor(String rfc, Receptor r) {
		Emisor e= this.consultar(rfc);
		if(e==null){
			e= new Emisor(rfc);
		}
		e.addReceptor(r);
		ofy().save().entity(e).now();
	}

	@Override
	public void updateReceptor(String rfc, Receptor r, int indice) {
		Emisor e= this.consultar(rfc);
		List<Receptor> lista = e.getReceptores();
		lista.set(indice, r);
		ofy().save().entity(e).now();
	}

	@Override
	public void deleteReceptor(String rfc, int indice) {
		Emisor e= this.consultar(rfc);
		List<Receptor> lista = e.getReceptores();
		lista.remove(indice);
		ofy().save().entity(e).now();		
	}

}
