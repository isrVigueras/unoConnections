package com.tikal.cacao.dao.sql;

import java.util.List;

import com.tikal.cacao.model.orm.UnidadDeMedida;

public interface UnidadDeMedidaDAO {
	
	void guardar(UnidadDeMedida unidadM);
	
	List<UnidadDeMedida> consultarPorNombre(String nombre);

}
