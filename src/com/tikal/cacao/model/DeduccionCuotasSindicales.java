/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class DeduccionCuotasSindicales extends Deduccion {

	private double porcentajeCuota;

	public DeduccionCuotasSindicales() {
		super();
	}

	public DeduccionCuotasSindicales(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the porcentajeCuota
	 */
	public double getPorcentajeCuota() {
		return porcentajeCuota;
	}

	/**
	 * @param porcentajeCuota the porcentajeCuota to set
	 */
	public void setPorcentajeCuota(double porcentajeCuota) {
		this.porcentajeCuota = porcentajeCuota;
	}
	
	
	
}
