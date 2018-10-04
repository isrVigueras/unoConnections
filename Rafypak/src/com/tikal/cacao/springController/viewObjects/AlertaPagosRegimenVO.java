package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
public class AlertaPagosRegimenVO {
	
	private Regimen regimen;
	
	private String alerta;

	public Regimen getRegimen() {
		return regimen;
	}

	public void setRegimen(Regimen regimen) {
		this.regimen = regimen;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	
}
