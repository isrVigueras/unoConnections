package com.tikal.cacao.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class Emisor {
	@Id
	private String rfc;
		
	private List<Receptor> receptores;
	
	public Emisor() {}
	
	public Emisor(String rfc) {
		this.rfc = rfc;
	}
	
	public String getRfc() {
		return rfc;
	}
	
	public List<Receptor> getReceptores() {
		if (receptores == null) {
			receptores = new ArrayList<>();
		}
		return receptores;
	}
	
	public void addReceptor(Receptor r){
		if (receptores == null) {
			receptores = new ArrayList<>();
		}
		receptores.add(r);
	}

}
