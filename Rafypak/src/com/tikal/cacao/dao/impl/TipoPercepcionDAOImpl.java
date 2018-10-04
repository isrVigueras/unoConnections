/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.model.EntTipoPercepcion;

/**
 * @author Tikal
 *
 */
@Repository
public class TipoPercepcionDAOImpl extends CatalogosDAOImpl<EntTipoPercepcion, Long, String> {

	@Override
	public List<EntTipoPercepcion> consultarTodos(Class<EntTipoPercepcion> type) {
		List<EntTipoPercepcion> lista = ofy().load().type(type).list();
		Collections.sort(lista);
		return lista;
	}
}
