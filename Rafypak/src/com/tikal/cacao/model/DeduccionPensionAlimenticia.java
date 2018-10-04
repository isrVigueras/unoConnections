/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class DeduccionPensionAlimenticia extends Deduccion {

	private double porcentajePension;
	
	public DeduccionPensionAlimenticia() {
		super();
	}

	public DeduccionPensionAlimenticia(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the porcentajePension
	 */
	public double getPorcentajePension() {
		return porcentajePension;
	}

	/**
	 * @param porcentajePension the porcentajePension to set
	 */
	public void setPorcentajePension(double porcentajePension) {
		this.porcentajePension = porcentajePension;
	}

	
	
}
