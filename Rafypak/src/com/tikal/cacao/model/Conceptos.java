package com.tikal.cacao.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Conceptos {
	@Id
	private String rfc;
	
	private List<Concepto> conceptos;

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public List<Concepto> getConceptos() {
		if(this.conceptos==null){
			this.conceptos= new ArrayList<Concepto>();
		}
		return conceptos;
	}

	public void setConceptos(List<Concepto> conceptos) {
		this.conceptos = conceptos;
	}
	
	public void addConcepto(Concepto c){
		if(this.conceptos==null){
			this.conceptos= new ArrayList<Concepto>();
		}
		this.conceptos.add(c);
	}
}
