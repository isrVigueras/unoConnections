package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.model.Imagen;

@Repository
public class ImagenDAOImp implements ImagenDAO{

	@Override
	public void addImagen(String rfc, String url) {
		Imagen i = new Imagen();
		i.setImage(url);
		i.setRfc(rfc);
		ofy().save().entity(i);
	}
	
	@Override
	public Imagen get(String rfc) {
		return ofy().load().type(Imagen.class).id(rfc).now();
	}

}
