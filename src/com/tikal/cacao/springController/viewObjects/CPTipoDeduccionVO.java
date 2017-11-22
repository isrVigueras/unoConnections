/**
 * 
 */
package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;

/**
 * @author Tikal
 *
 */
public class CPTipoDeduccionVO {
	
	private ContabilidadPropiaTipoDeduccion cpTipoDeduccion;
	
	private String idTipoSAT;

	
	public ContabilidadPropiaTipoDeduccion getCpTipoDeduccion() {
		return cpTipoDeduccion;
	}

	public void setCpTipoDeduccion(ContabilidadPropiaTipoDeduccion cpTipoDeduccion) {
		this.cpTipoDeduccion = cpTipoDeduccion;
	}

	public String getIdTipoSAT() {
		return idTipoSAT;
	}

	public void setIdTipoSAT(String idTipoSAT) {
		this.idTipoSAT = idTipoSAT;
	}
	
	
}
