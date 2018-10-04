package com.tikal.cacao.dao;

import com.tikal.cacao.model.Imagen;

public interface ImagenDAO {
	
	public void addImagen(String rfc, String url);
	
	public Imagen get(String rfc);

	
}
