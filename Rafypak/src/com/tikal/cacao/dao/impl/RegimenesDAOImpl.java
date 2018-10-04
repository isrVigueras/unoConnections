/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Ref;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
@Repository
public class RegimenesDAOImpl implements RegimenesDAO {

	public RegimenesDAOImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.RegimenesDAO#crear(com.tikal.cacao.model.Regimen)
	 */
	@Override
	public void crear(Regimen regimen) {
		ofy().save().entity(regimen).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.RegimenesDAO#actualizar(com.tikal.cacao.model.
	 * Regimen)
	 */
	@Override
	public void actualizar(Regimen regimen) {
		ofy().save().entity(regimen).now();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.RegimenesDAO#consultar(long)
	 */
	@Override
	public Regimen consultar(long id) {
		return ofy().load().type(Regimen.class).id(id).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.RegimenesDAO#eliminar(com.tikal.cacao.model.Regimen)
	 */
	@Override
	public void eliminar(Regimen regimen) {
		regimen.setActivo(false);
		actualizar(regimen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.RegimenesDAO#consultaPorEmpresa(com.tikal.cacao.model
	 * .Empresa)
	 */
	@Override
	public List<Regimen> consultaPorEmpresa(Empresa empresa) {
		Regimen regimenRecuperado;

		return empresa.getRegimenes();
	}

	@Override
	public Regimen consultar(String nombre) {
		List<Regimen> lista = ofy().load().type(Regimen.class).filter("nombre", nombre).list();
		if (lista.size() == 0) {
			return null;
		} else {
			return lista.get(0);
		}
	}

}
