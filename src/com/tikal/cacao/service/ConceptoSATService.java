package com.tikal.cacao.service;

import java.util.List;

import com.tikal.cacao.model.orm.ProductoOServicio;

public interface ConceptoSATService {

	public void generarConceptos(String rfc, Object[][] listaPS);
	
	public List<ProductoOServicio> cargarProdServ(String rfc);
	
}
