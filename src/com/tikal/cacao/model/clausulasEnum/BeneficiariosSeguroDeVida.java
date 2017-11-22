/**
 * 
 */
package com.tikal.cacao.model.clausulasEnum;

import com.google.gson.annotations.SerializedName;

/**
 * &Eacute;ste enum sirve para especificar a la(s) persona(s) beneficiaria(s) de la 
 * <i>Prima de Seguro de vida</i> del(la) asegurado(a)
 * @author Tikal
 *
 */
public enum BeneficiariosSeguroDeVida {

	@SerializedName("Cónyuge")
	CONYUGE,
	
	@SerializedName("Concubina o Concubino")
	CONCUBINA_O_CONCUBINO,
	
	@SerializedName("Padres")
	PADRES,
	
	@SerializedName("Hijos")
	HIJOS,
	
	@SerializedName("Abuelos")
	ABUELOS,
	
	@SerializedName("Nietos")
	NIETOS,
	
	@SerializedName("Otros")
	OTROS
}
