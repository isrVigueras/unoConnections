/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.List;

/**
 * @author Tikal
 *
 */
public interface CatalogosDAO<T,S,C> {
	
	void crear(T t);
	
	void crear(List<T> listT);
	
	T consultar(S id, Class<T> type);
	
	T consultarPorIndice(C clave, Class<T> type);
	
	List<T> consultarTodos(Class<T> type);
	
	List<T> consultarTodosPorIndice(Class<T> type, String index);
	
	// eliminar catalogos de un mismo subtipo
	void eliminar(Class<T> type, List<S> ids);
	

}
