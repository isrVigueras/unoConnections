/**
 * 
 */
package com.tikal.cacao.model.clausulasEnum;

import com.google.gson.annotations.SerializedName;

/**
 * &Eacute;ste enum sirve para especificar los riesgos amparados por las p&oacute;lizas 
 * de <i>Seguro de vida</i>.
 * @author Tikal
 *
 */
public enum RiesgosAmparadosPorSeguroDeVida {

	@SerializedName("Fallecimiento")
	FALLECIMIENTO,
	
	@SerializedName("Invalidez")
	INVALIDEZ,
	
	@SerializedName("Pérdidas orgánicas")
	PERDIDAS_ORGANICAS,
	
	@SerializedName("Incapacidad del asegurado para realizar un trabajo personal")
	INCAPACIDAD
}
