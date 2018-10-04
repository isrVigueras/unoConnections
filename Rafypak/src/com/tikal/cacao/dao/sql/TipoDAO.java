package com.tikal.cacao.dao.sql;

import java.util.List;

import com.tikal.cacao.model.orm.Tipo;

public interface TipoDAO {

	public void guardar(Tipo t);
	
	public List<Tipo> consultarTodos();
	
	public Tipo consultarPorId(String id);
}
