package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.cacao.dao.ConceptosDAO;
import com.tikal.cacao.model.Concepto;
import com.tikal.cacao.model.Conceptos;

public class ConceptosDAOImp implements ConceptosDAO{

	@Override
	public void add(String rfc, Concepto c) {
		Conceptos cs= ofy().load().type(Conceptos.class).id(rfc).now();
		if(cs==null){
			cs= new Conceptos();
			cs.setRfc(rfc);
		}
		cs.addConcepto(c);
		ofy().save().entity(cs).now();
	}

	@Override
	public Conceptos consultar(String rfc) {
		Conceptos cs= ofy().load().type(Conceptos.class).id(rfc).now();
		if(cs==null){
			cs= new Conceptos();
			cs.setRfc(rfc);
		}
		return cs;
	}

	@Override
	public void eliminar(String rfc, int ind) {
		Conceptos cs=this.consultar(rfc);
		List<Concepto> l= cs.getConceptos();
		l.remove(ind);
		cs.setConceptos(l);
		ofy().save().entity(cs).now();
	}

	@Override
	public void actualizar(String rfc, int ind, Concepto c) {
		Conceptos cs=this.consultar(rfc);
		List<Concepto> l= cs.getConceptos();
		l.set(ind, c);
		cs.setConceptos(l);
		ofy().save().entity(cs).now();
	}

	@Override
	public void add(String rfc, List<Concepto> lista) {
		Conceptos cs= this.consultar(rfc);
		List<Concepto> l= cs.getConceptos();
		l.addAll(lista);
		cs.setConceptos(l);
		ofy().save().entity(cs).now();
		
	}

	@Override
	public void alv(String rfc) {
		Conceptos cs= this.consultar(rfc);
		ofy().delete().entity(cs).now();
	}
	
}
