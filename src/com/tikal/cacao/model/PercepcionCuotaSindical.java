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
public class PercepcionCuotaSindical extends Percepcion {
	
	private double porcentajeCuota;
	
	
	/**
	 * @param tipo
	 * @param clave
	 * @param descripcion
	 */
	public PercepcionCuotaSindical(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	public double getPorcentajeCuota() {
		return porcentajeCuota;
	}
	
	public void setPorcentajeCuota(double porcentaje) {
		this.porcentajeCuota = porcentaje;
	}
}
