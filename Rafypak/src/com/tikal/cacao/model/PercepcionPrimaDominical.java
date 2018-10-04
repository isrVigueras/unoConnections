/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Subclass;

/**
 * @author Tikal
 *
 */
@Subclass
public class PercepcionPrimaDominical extends Percepcion {

	private int domingosLaborados;

	public PercepcionPrimaDominical(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	public int getDomingosLaborados() {
		return domingosLaborados;
	}

	public void setDomingosLaborados(int domingosLaborados) {
		this.domingosLaborados = domingosLaborados;
	}
	
	
}
