/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
@Repository
public class EmpresasDAOImpl implements EmpresasDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.EmpresasDAO#crear(com.tikal.cacao.model.Empresa)
	 */
	public EmpresasDAOImpl() {

	}

	@Override
	public void crear(Empresa e) {
		ofy().save().entity(e);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.EmpresasDAO#actualizar(com.tikal.cacao.model.Empresa)
	 */
	@Override
	public void actualizar(Empresa e) {
		Empresa old = this.consultar(e.getRFC());
		if (old != null) {
			old.setDireccion(e.getDireccion());
			old.setNombre(e.getNombre());
			old.setRazonSocial(e.getRazonSocial());
			old.setEmails(e.getEmails());
			ofy().save().entity(old);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.EmpresasDAO#consultar(java.lang.String)
	 */
	@Override
	public Empresa consultar(String rfc) {
		return ofy().load().type(Empresa.class).id(rfc).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.EmpresasDAO#eliminar(com.tikal.cacao.model.Empresa)
	 */
	@Override
	public void eliminar(Empresa e) {
		e.setActivo(false);
		actualizar(e);
	}

	@Override
	public List<Empresa> consultarTodos() {
		return ofy().load().type(Empresa.class).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.EmpresasDAO#aplicarUnRegimen(com.tikal.cacao.model.
	 * Regimen, com.tikal.cacao.model.Empresa)
	 */
	@Override
	public void aplicarUnRegimen(Regimen r, Empresa e) {
		if (r.getId() == null) {
			ofy().save().entities(r).now();
		}
		e.addRegimen(Ref.create(r));
		actualizar(e);
		
	}

	@Override
	public String[] consultarEmails(String rfc) {
		Empresa empresa = this.consultar(rfc);
		return empresa.getEmails();
	}


}
