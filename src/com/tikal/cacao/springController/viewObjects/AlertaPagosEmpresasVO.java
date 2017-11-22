package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Empresa;

/**
 * @author Tikal
 *
 */
public class AlertaPagosEmpresasVO {

	private Empresa empresa;
	
	private String alerta;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	
	
}
