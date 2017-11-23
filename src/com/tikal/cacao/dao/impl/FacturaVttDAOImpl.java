package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.tikal.cacao.dao.FacturaVttDAO;
import com.tikal.cacao.dao.PagosFacturaVttDAO;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.PagosFacturaVTT;

public class FacturaVttDAOImpl implements FacturaVttDAO {

	@Override
	public void guardar(FacturaVTT f) {
		ofy().save().entity(f).now();
	}

	@Override
	public FacturaVTT consultar(String uuid) {
		return ofy().load().type(FacturaVTT.class).id(uuid).now();
	}

	@Override
	public List<FacturaVTT> consutarTodas(String rfcEmisor) {
		List<FacturaVTT> facturas = ofy().load().type(FacturaVTT.class).filter("rfcEmisor", rfcEmisor).order("-fechaCertificacion").list();
		if (facturas == null)
			return new ArrayList<>();
		else
			return facturas;
	}

	@Override
	public boolean eliminar(FacturaVTT f) {
		if (f.getEstatus().equals(Estatus.GENERADO)) {
			ofy().delete().entity(f).now();
			return true;
		}
		return false;
	}

	@Override
	public List<PagosFacturaVTT> consultarPorUuidRelacionado(String uuidRelacionado) {
		return ofy().load().type(PagosFacturaVTT.class).filter("uuidCFDIRelacionado", uuidRelacionado).list();
	}

	@Override
	public List<PagosFacturaVTT> cosultarPorCliente(String rfcCliente) {
		//TODO IMPLEMENTAR ÍNDICE
		return null;
		//return ofy().load().type(PagosFacturaVTT.class).filter("", rfcCliente).list();
	}

}
