package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.Concepto;
import com.tikal.cacao.model.Conceptos;

public interface ConceptosDAO {
	public void add(String rfc,Concepto c);
	public void add(String rfc,List<Concepto> lista);
	public Conceptos consultar(String rfc);
	public void eliminar(String rfc,int ind);
	public void alv(String rfc);
	public void actualizar(String rfc, int ind, Concepto c);
}
