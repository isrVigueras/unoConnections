package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Regimen;

public class RegimenVO extends Regimen {

	private String tipo;
	public RegimenVO() {

	}

	public RegimenVO(Regimen r) {
		this.setActivo(r.isActivo());
		this.setDeducciones(r.getDeducciones());
		this.setId(r.getId());
		this.setIdEmpleados(r.getIdEmpleados());
		this.setNombre(r.getNombre());
		this.setPercepciones(r.getPercepciones());
		this.setTipo(r.getTipoRegimen());
		this.setFormaPago(r.getFormaPago());
		this.setDiasDePago(r.getDiasDePago());
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Regimen getReg(){
		Regimen r= new Regimen();
		r.setActivo(this.isActivo());
		r.setDeducciones(this.getDeducciones());
		r.setId(this.getId());
		r.setIdEmpleados(this.getIdEmpleados());
		r.setNombre(this.getNombre());
		r.setPercepciones(this.getPercepciones());
		r.setTipoRegimen(this.tipo);
		r.setFormaPago(this.getFormaPago());
		r.setDiasDePago(this.getDiasDePago());
		return r;
	}

}