package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tikal.cacao.dao.ComplementoRenglonDAO;
import com.tikal.cacao.reporte.ComplementoRenglon;

public class ComplementoRenglonDAOImp implements ComplementoRenglonDAO{

	@Override
	public void guardar(ComplementoRenglon r) {
		ofy().save().entity(r).now();

	}

	@Override
	public void guardar(List<ComplementoRenglon> lista) {
		ofy().save().entities(lista).now();

	}

	@Override
	public ComplementoRenglon consultar(String uuid) {
		return ofy().load().type(ComplementoRenglon.class).id(uuid).now();
	}

	@Override
	public List<ComplementoRenglon> consultar(String rfcEmisor, String serie, Date fechaI, Date fechaF) {
		List<ComplementoRenglon> renglones = null;
		if (serie != null) {
			renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor).filter("serie", serie)
					.filter("fechaCertificacion >=", fechaI).filter("fechaCertificacion <=", fechaF).list();
		} else {
			renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor)
					.filter("fechaCertificacion >=", fechaI).filter("fechaCertificacion <=", fechaF).list();
		}
		return renglones;
	}

	@Override
	public List<ComplementoRenglon> consultar(String rfcEmisor, String rfcReceptor, String serie, Date fechaI,
			Date fechaF) {
		List<ComplementoRenglon> renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("serie", serie).filter("rfcRec", rfcReceptor).filter("fecha >=", fechaI)
				.filter("fecha <=", fechaF).list();
		return renglones;
	}

	@Override
	public List<ComplementoRenglon> consultarPag(String rfcEmisor, int page) {
		List<ComplementoRenglon> renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor)
				.order("-fechaCertificacion").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}

	@Override
	public List<ComplementoRenglon> consultarPag(String rfcEmisor, String serie, int page) {
		List<ComplementoRenglon> renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("serie", serie).order("- fechaCertificacion").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}
	
	@Override
	public int pags(String rfcEmisor) {
		return ((ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor",rfcEmisor).count()-1)/25)+1;
	}

	@Override
	public int pags(String rfc, String serie) {
		return ((ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfc).filter("serie", serie).count() - 1)
				/ 25) + 1;
	}

	@Override
	public List<ComplementoRenglon> consultarPagRec(String rfcEmisor, String receptor, int page) {
		List<ComplementoRenglon> renglones = ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("rfcReceptor", receptor).order("- fechaCertificacion").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}

	@Override
	public int pagsRec(String rfc, String receptor) {
		return ((ofy().load().type(ComplementoRenglon.class).filter("rfcEmisor", rfc).filter("rfcReceptor", receptor)
				.count() - 1) / 25) + 1;
	}

	@Override
	public void eliminar(String uuid) {
		ofy().delete().type(ComplementoRenglon.class).id(uuid).now();
	}

	@Override
	public List<ComplementoRenglon> consultarids(String[] ids) {
		Map<String, ComplementoRenglon> mapa = ofy().load().type(ComplementoRenglon.class).ids(ids);
		List<ComplementoRenglon> lista = new ArrayList<ComplementoRenglon>();
		Iterator it = mapa.entrySet().iterator();

	    for (Entry<String, ComplementoRenglon> e: mapa.entrySet()) {
	        lista.add(e.getValue());
	    }

		return lista;

	}

	@Override
	public void eliminar(List<ComplementoRenglon> lista) {
		ofy().delete().entities(lista).now();
	}


}
