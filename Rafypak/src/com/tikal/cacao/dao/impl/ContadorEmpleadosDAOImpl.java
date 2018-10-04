/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.ContadorEmpleadosDAO;
import com.tikal.cacao.model.ContadorEmpleados;

/**
 * @author Tikal
 *
 */
@Repository
public class ContadorEmpleadosDAOImpl implements ContadorEmpleadosDAO {

	@Override
	public ContadorEmpleados consultar() {
		return ofy().load().type(ContadorEmpleados.class).first().now();
	}

	@Override
	public void incrementar(ContadorEmpleados contador) {
		contador.setCuenta(contador.getCuenta() + 1);
		ofy().save().entity(contador).now();

	}

}
