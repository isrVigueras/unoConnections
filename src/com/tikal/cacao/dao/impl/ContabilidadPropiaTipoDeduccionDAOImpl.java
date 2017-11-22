/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;

/**
 * @author Tikal
 *
 */
@Repository
public class ContabilidadPropiaTipoDeduccionDAOImpl extends CatalogosDAOImpl<ContabilidadPropiaTipoDeduccion, Long, String> {

	@Override
	public List<ContabilidadPropiaTipoDeduccion> consultarTodos(Class<ContabilidadPropiaTipoDeduccion> type) {
		List<ContabilidadPropiaTipoDeduccion> lista = ofy().load().type(type).list();
		Collections.sort(lista);
		return lista;
	}

	@Override
	public List<ContabilidadPropiaTipoDeduccion> consultarTodosPorIndice(Class<ContabilidadPropiaTipoDeduccion> type,
			String index) {
		List<ContabilidadPropiaTipoDeduccion> lista = ofy().load().type(type).filter("rfcEmpresa", index).list();
		Collections.sort(lista);
		return lista;
	}

}
