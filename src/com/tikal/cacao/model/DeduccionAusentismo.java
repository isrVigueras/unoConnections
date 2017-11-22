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
public class DeduccionAusentismo extends Deduccion {

	private int diasAusente;

	/** Campo para especificar si el empleado trabaja 6 d&iacute;as a la semana */
	private boolean trabajaSeisDias;
	
	public DeduccionAusentismo() {
		super();
	}

	public DeduccionAusentismo(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the diasAusente
	 */
	public int getDiasAusente() {
		return diasAusente;
	}

	/**
	 * @param diasAusente the diasAusente to set
	 */
	public void setDiasAusente(int diasAusente) {
		this.diasAusente = diasAusente;
	}

	/**
	 * @return the trabajaSeisDias
	 */
	public boolean isTrabajaSeisDias() {
		return trabajaSeisDias;
	}

	/**
	 * @param trabajaSeisDias the trabajaSeisDias to set
	 */
	public void setTrabajaSeisDias(boolean trabajaSeisDias) {
		this.trabajaSeisDias = trabajaSeisDias;
	}
	
	
	
	
}
