package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum PeriodosDePago {
	@SerializedName("Bimestral")
	BIMESTRAL, 
	
	@SerializedName("Mensual")
	MENSUAL, 
	
	@SerializedName("Quincenal")
	QUINCENAL, 
	
	@SerializedName("Decenal")
	DECENAL, 
	
	@SerializedName("Semanal")
	SEMANAL
}
