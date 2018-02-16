package com.tikal.cacao.dao.impl;

import java.util.List;

import com.tikal.cacao.dao.PagosFacturaVttDAO;
import com.tikal.cacao.model.PagosFacturaVTT;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class PagosFacturaVttDAOImp implements PagosFacturaVttDAO{

	@Override
	public List<PagosFacturaVTT> consultarPorUuidRelacionado(String uuidRelacionado) {
		
		return null;
	}

	@Override
	public PagosFacturaVTT consultarUUID(String uuid) {
		return ofy().load().type(PagosFacturaVTT.class).id(uuid).now();
	}

	@Override
	public List<PagosFacturaVTT> cosultarPorCliente(String rfcCliente) {
		return null;
	}

}
