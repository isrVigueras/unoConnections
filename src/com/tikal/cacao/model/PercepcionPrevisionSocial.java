/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class PercepcionPrevisionSocial extends Percepcion {

	private boolean esPrevisionSocial;
	
	public PercepcionPrevisionSocial() {}

	public PercepcionPrevisionSocial(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the esPrevisionSocial
	 */
	public boolean isEsPrevisionSocial() {
		return esPrevisionSocial;
	}

	/**
	 * @param esPrevisionSocial the esPrevisionSocial to set
	 */
	public void setEsPrevisionSocial(boolean esPrevisionSocial) {
		this.esPrevisionSocial = esPrevisionSocial;
	}
	
	

}
