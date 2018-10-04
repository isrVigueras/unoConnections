package com.tikal.cacao.dao.sql;

import java.util.List;

public interface HibernateDAO<T> {
	
	void guardar(T entity);
	
	T consultarPorId(String id);
	
	List<T> consultaComboBox(String parameter);
	
	List<T> consultarTodos();
	
	

}
