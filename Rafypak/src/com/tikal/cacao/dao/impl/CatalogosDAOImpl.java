/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.List;

import com.tikal.cacao.dao.CatalogosDAO;

/**
 * @author Tikal
 *
 */
public abstract class CatalogosDAOImpl<T , S, C> implements CatalogosDAO<T, S, C> {
	
	@Override
	public void crear(T t) {
		ofy().save().entity(t).now();
		
	}
	
	@Override
	public void crear(List<T> listT) {
		ofy().save().entities(listT).now();
		
	}
	
	@Override
	public T consultar(S id, Class<T> type) {
		String strId = null;
		Long longId = null;
		if (id instanceof String) {
			strId = (String) id;
			return ofy().load().type(type).id(strId).now();
		} else if (id instanceof Long){
			longId = (Long) id;
			return ofy().load().type(type).id(longId).now();
		} else {
			return null;
		}
	}
	
	@Override
	public T consultarPorIndice(C clave, Class<T> type) {
		return ofy().load().type(type).filter("clave", clave).first().now();
	}
	
	
	@Override
	public void eliminar(Class<T> type, List<S> ids) {
		ofy().delete().type(type).ids(ids);
		// verificar éste método para eliminar las deducciones viejas y guardar las nuevas
	}
	
	@Override
	public List<T> consultarTodosPorIndice(Class<T> type, String index) {
		return ofy().load().type(type).filter("clave", index).list();
	}

}
