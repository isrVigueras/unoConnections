package com.tikal.cacao.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ListaDeClasesDeProdServ {
	

	@Id
	private String idRFC;
	
	private List<String> clavesClases;


	public String getIdRFC() {
		return idRFC;
	}

	public void setIdRFC(String idRFC) {
		this.idRFC = idRFC;
	}

	public List<String> getListaClavesClases() {
		if (clavesClases == null) {
			this.clavesClases = new ArrayList<>();
		}
		return clavesClases;
	}
	
	
}
