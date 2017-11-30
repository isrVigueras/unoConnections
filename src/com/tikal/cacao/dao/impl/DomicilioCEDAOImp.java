package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.cacao.dao.DomicilioCEDAO;
import com.tikal.cacao.model.DomicilioCE;

public class DomicilioCEDAOImp implements DomicilioCEDAO {

	@Override
	public void guardar(DomicilioCE d) {
		DomicilioCE dom= this.get(d.getRfc());
		if(dom!=null){
			d.setId(dom.getId());
		}
		ofy().save().entity(d);
	}

	@Override
	public DomicilioCE get(String rfc) {
		List<DomicilioCE> lista = ofy().load().type(DomicilioCE.class).filter("rfc", rfc).list();
		if (lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}

}
