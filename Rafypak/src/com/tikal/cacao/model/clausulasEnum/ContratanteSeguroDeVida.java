/**
 * 
 */
package com.tikal.cacao.model.clausulasEnum;

import com.google.gson.annotations.SerializedName;

/**
 * &Eacute;ste enum sirve para especificar a la persona que contrata un
 * <i>Seguro de vida</i> con el fin de definir si un ingreso por motivo de ésta percepci&oacute;n 
 * esta exento del pago del Impuesto Sobre la Renta y, en su caso, si la misma es
 * deducible para el contribuyente.
 * @author Tikal
 *
 */
public enum ContratanteSeguroDeVida {

	@SerializedName("Empleador")
	EMPLEADOR,
	
	@SerializedName("Trabajador")
	TRABAJADOR,
	
	@SerializedName("Otro")
	OTRO
}
