/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum RiesgoPuesto {
	@SerializedName("Clase I")
	CLASE_I("1", 0.54355),
	
	@SerializedName("Clase II")
	CLASE_II("2", 1.13065),
	
	@SerializedName("Clase III")
	CLASE_III("3", 2.59840),
	
	@SerializedName("Clase IV")
	CLASE_IV("4", 4.653225),
	
	@SerializedName("Clase V")
	CLASE_V("5", 7.58875);
	
	private String clave;
	
	private double porcentaje;
	
	private RiesgoPuesto(String clave, double porcentaje) {
		this.clave = clave;
		this.porcentaje = porcentaje;
	}

	public String getClave() {
		return clave;
	}

	public double getPorcentaje() {
		return porcentaje;
	}
	
	
	
	

}
