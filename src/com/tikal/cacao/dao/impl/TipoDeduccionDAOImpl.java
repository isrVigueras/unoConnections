/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.model.EntTipoDeduccion;

/**
 * @author Tikal
 *
 */
@Repository
public class TipoDeduccionDAOImpl extends CatalogosDAOImpl<EntTipoDeduccion, Long, String> {

	@Override
	public List<EntTipoDeduccion> consultarTodos(Class<EntTipoDeduccion> type) {
		List<EntTipoDeduccion> lista = ofy().load().type(type).list();
		Collections.sort(lista);
		return lista;
	}
}
