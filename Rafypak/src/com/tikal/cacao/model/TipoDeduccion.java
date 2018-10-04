/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum TipoDeduccion {
	@SerializedName("Seguridad social")
	SEGURIDAD_SOCIAL("Seguridad social"),
	
	@SerializedName("ISR")
	ISR("ISR"),
	
	@SerializedName("Aportaciones a retiro, cesantía en edad avanzada y vejez")
	APORTACIONES_A_RETIRO_CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ("Aportaciones a retiro cesantia en edad avanzada y vejez"),
	
	@SerializedName("Otros")
	OTROS("Otros"),
	
	@SerializedName("Aportaciones a Fondo de vivienda")
	APORTACIONES_A_FONDO_DE_VIVIENDA("Aportaciones a Fondo de vivienda"),
	
	@SerializedName("Descuento por incapacidad")
	DESCUENTO_POR_INCAPACIDAD("Descuento por incapacidad"),
	
	@SerializedName("Pension alimenticia")
	PENSION_ALIMENTICIA("Pension alimenticia"),
	
	@SerializedName("Renta")
	RENTA("Renta"),
	
	@SerializedName("Préstamos a provenientes del Fondo Nacional de la Vivienda para los Trabajadores")
	PRESTAMOS_PROVENIENTES_DEL_FONDO_NACIONAL_DE_LA_VIVIENDA_PARA_LOS_TRABAJADORES("Prestamos provenientes del Fondo Nacional de la Vivienda para los Trabajadores"),
	
	@SerializedName("Pago por crédito vivienda")
	PAGO_POR_CREDITO_DE_VIVIENDA("Pago por credito de vivienda"),
	
	@SerializedName("Pago de abonos INFONACOT")
	PAGO_DE_ABONOS_INFONACOT("Pago de abonos INFONACOT"),
	
	@SerializedName("Anticipo de salarios")
	ANTICIPO_DE_SALARIOS("Anticipo de salarios"),
	
	@SerializedName("Pagos hechos con exceso al trabajador")
	PAGOS_HECHOS_CON_EXCESO_AL_TRABAJADOR("Pagos hechos con exceso al trabajador"),
	
	@SerializedName("Errores")
	ERRORES("Errores"),
	
	@SerializedName("Pérdidas")
	PERDIDAS("Perdidas"),
	
	@SerializedName("Averías")
	AVERIAS("Averias"),
	
	@SerializedName("Aquisición de artículos producidos por la empresa o establecimiento")
	ADQUISICION_DE_ARTICULOS_PRODUCIDOS_POR_LA_EMPRESA_O_ESTABLECIMIENTO("Adquisicion de articulos producidos por la empresa o establecimiento"),
	
	@SerializedName("Cuotas para la constitución y fomento de sociedades cooperativas y de cajas de ahorro")
	CUOTAS_PARA_LA_CONSTITUCION_Y_FOMENTO_DE_SOCIEDADES_COOPERATIVAS_Y_DE_CAJAS_DE_AHORRO("Cuotas para la constitucion y fomento de sociedades cooperativas y de cajas de ahorro"),
	
	@SerializedName("Cuotas sindicales")
	CUOTAS_SINDICALES("Cuotas sindicales"),
	
	@SerializedName("Ausencia (Ausentismo)")
	AUSENCIA("Ausencia"),
	
	@SerializedName("Cuotas obrero patronales")
	CUOTAS_OBRERO_PATRONALES("Cuotas obrero patronales");
	
	
	private String brandname;

	/**
	 * @param brandname
	 */
	private TipoDeduccion(String brandname) {
		this.brandname = brandname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return brandname;
	}

	
	
}