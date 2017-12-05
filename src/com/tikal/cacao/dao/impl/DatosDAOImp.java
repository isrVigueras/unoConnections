package com.tikal.cacao.dao.impl;

import java.util.List;

import com.tikal.cacao.dao.DatosDAO;
import com.tikal.unoconnections.tralix.Datos;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class DatosDAOImp implements DatosDAO{

	@Override
	public void guardar(Datos d) {
		ofy().save().entity(d).now();
	}

	@Override
	public void guardar(List<Datos> lista) {
		ofy().save().entities(lista).now();
	}

	@Override
	public List<Datos> getByRFC(String rfc) {
		List<Datos> lista= ofy().load().type(Datos.class).filter("rfcEmisor",rfc).list();
		return lista;
	}

	@Override
	public List<Datos> todos() {
		List<Datos> lista= ofy().load().type(Datos.class).filter("pausada",false).list();
		return lista;
	}

	@Override
	public void elimiar(Datos d) {
		ofy().delete().entity(d).now();
	}

	@Override
	public void elimiar(List<Datos> lista) {
		ofy().delete().entities(lista);
	}

	@Override
	public int indice() {
		return 0;
	}

}
