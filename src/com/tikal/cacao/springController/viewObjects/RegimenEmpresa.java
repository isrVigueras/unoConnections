package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Regimen;

public class RegimenEmpresa {
	private String empresa;
	private Regimen regimen;
	private String tipo;
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public Regimen getRegimen() {
		return regimen;
	}
	public void setRegimen(Regimen regimen) {
		this.regimen = regimen;
	}
	
	
}
