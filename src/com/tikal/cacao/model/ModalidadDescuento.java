/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum ModalidadDescuento {
	@SerializedName("Porcentaje")
	PORCENTAJE,
	
	@SerializedName("Cuota Fija")
	CUOTA_FIJA,
	
	@SerializedName("Veces SMGV")
	VECES_SMGV,
	
	@SerializedName("Veces UMA")
	VECES_UMA
}
