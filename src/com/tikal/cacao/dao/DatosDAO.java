package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.unoconnections.tralix.Datos;

public interface DatosDAO {

	public void guardar(Datos d);
	
	public void guardar(List<Datos> lista);
	
	public List<Datos> getByRFC(String rfc);
	
	public List<Datos> todos();
	
	public void elimiar(Datos d);
	
	public void elimiar(List<Datos> lista);
	
	public int indice();
}
