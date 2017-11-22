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
public class PercepcionHorasExtra extends Percepcion {
	
	public PercepcionHorasExtra() {}
	
//	public PercepcionHorasExtra(String clave, TipoPercepcion descripcion) {
//		super(clave, descripcion);
//	}
	
	public PercepcionHorasExtra(String clave, String descripcion) {
		super(clave, descripcion);
	}
	
	public PercepcionHorasExtra(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}
	/**
	 * Cantidad de horas extra laboradas por semana
	 */
	private int[] horasExtra;
	
	private int horasDobles;
	
	private int horasTriples;
	
	private double montoHorasTriples;
	
	/**
	 * @return the horasExtra
	 */
	public int[] getHorasExtra() {
		return horasExtra;
	}

	/**
	 * @param horasExtra the horasExtra to set
	 */
	public void setHorasExtra(int[] horasExtra) {
		this.horasExtra = horasExtra;
	}

	/**
	 * @return the horasDobles
	 */
	public int getHorasDobles() {
		return horasDobles;
	}

	/**
	 * @param horasDobles the horasDobles to set
	 */
	public void setHorasDobles(int horasDobles) {
		this.horasDobles = horasDobles;
	}

	/**
	 * @return the horasTriples
	 */
	public int getHorasTriples() {
		return horasTriples;
	}

	/**
	 * @param horasTriples the horasTriples to set
	 */
	public void setHorasTriples(int horasTriples) {
		this.horasTriples = horasTriples;
	}

	/**
	 * @return the montoHorasTriples
	 */
	public double getMontoHorasTriples() {
		return montoHorasTriples;
	}

	/**
	 * @param montoHorasTriples the montoHorasTriples to set
	 */
	public void setMontoHorasTriples(double montoHorasTriples) {
		this.montoHorasTriples = montoHorasTriples;
	}
	
	
	
}
