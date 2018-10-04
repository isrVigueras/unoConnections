/**
 *
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum TipoIncapacidad {
	@SerializedName("Riesgo de Trabajo")
	RIESGO_DE_TRABAJO,
	
	@SerializedName("Enfermedad en general")
	ENFERMEDAD_EN_GENERAL,
	
	@SerializedName("Maternidad")
	MATERNIDAD
}
