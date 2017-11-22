/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.model.TipoRegimenContratacion;

/**
 * @author Tikal
 *
 */
@Repository
public class TipoRegimenContratacionDAOImpl extends CatalogosDAOImpl<TipoRegimenContratacion, Long, String> {

	@Override
	public List<TipoRegimenContratacion> consultarTodos(Class<TipoRegimenContratacion> type) {
		List<TipoRegimenContratacion> lista = ofy().load().type(type).list();
		Collections.sort(lista);
		return lista;
	}

}
