package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Serial {

	@Id
	private String id;
	
	@Index
	private String rfc;
	
	@Index 
	private String serie;
	
	private long folio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public long getFolio() {
		return folio;
	}

	public void setFolio(long folio) {
		this.folio = folio;
	}
	
	public void incrementa(){
		this.folio++;
	}
}
