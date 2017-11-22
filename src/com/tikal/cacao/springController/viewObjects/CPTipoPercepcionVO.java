/**
 * 
 */
package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.CatalogosContabilidadPropia;
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;
import com.tikal.cacao.model.EntTipoPercepcion;

/**
 * @author Tikal
 *
 */
public class CPTipoPercepcionVO {

	private ContabilidadPropiaTipoPercepcion cpTipoPercepcion;
	
	private String idTipoSAT;

	public ContabilidadPropiaTipoPercepcion getCpTipoPercepcion() {
		return cpTipoPercepcion;
	}

	public void setCpTipoPercepcion(ContabilidadPropiaTipoPercepcion cpTipoPercepcion) {
		this.cpTipoPercepcion = cpTipoPercepcion;
	}

	public String getIdTipoSAT() {
		return idTipoSAT;
	}

	public void setIdTipoSAT(String idTipoSAT) {
		this.idTipoSAT = idTipoSAT;
	}
	
	
}
