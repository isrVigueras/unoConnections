/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;

/**
 * @author Tikal
 *
 */
@Repository
public class ContabilidadPropiaTipoPercepcionDAOImpl extends CatalogosDAOImpl<ContabilidadPropiaTipoPercepcion, Long, String> {

	
	@Override
	public List<ContabilidadPropiaTipoPercepcion> consultarTodos(Class<ContabilidadPropiaTipoPercepcion> type) {
		List<ContabilidadPropiaTipoPercepcion> lista = ofy().load().type(type).list();
		Collections.sort(lista);
		return lista;
	}

	@Override
	public List<ContabilidadPropiaTipoPercepcion> consultarTodosPorIndice(Class<ContabilidadPropiaTipoPercepcion> type,
			String index) {
		List<ContabilidadPropiaTipoPercepcion> lista = ofy().load().type(type).filter("rfcEmpresa", index).list();
		Collections.sort(lista);
		return lista;
	}

}
