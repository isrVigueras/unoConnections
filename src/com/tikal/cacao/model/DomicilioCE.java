package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class DomicilioCE extends mx.gob.sat.comercioexterior11.ComercioExterior.Emisor.Domicilio{

	@Id
	private Long id;
	@Index
	private String rfc;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
}
