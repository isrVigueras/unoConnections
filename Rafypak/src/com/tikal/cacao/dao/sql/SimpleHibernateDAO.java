package com.tikal.cacao.dao.sql;

import java.util.List;

import com.tikal.cacao.model.orm.EntidadCatalogo;

public interface SimpleHibernateDAO<T extends EntidadCatalogo> {

	void guardar(T entity);
	
	T consultar(String id);
	
	List<T> consultarTodos();
}
