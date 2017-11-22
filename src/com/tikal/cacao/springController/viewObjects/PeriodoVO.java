/**
 * 
 */
package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class PeriodoVO {

	private String rfcEmpresa;
	
	private PeriodosDePago periodo;

	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	public PeriodosDePago getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodosDePago periodo) {
		this.periodo = periodo;
	}
	
	
}
