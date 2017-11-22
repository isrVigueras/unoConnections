/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class DeduccionIncapacidad extends Deduccion {

	/**
	 * Atributo que se utiliza para especificar el tipo de incapacidad
	 */
	private TipoIncapacidad incapacidad;
	
	/**
	 * Cantidad de d&iacute;as que dur&oacute; la incapacidad
	 */
	private Integer diasIncapacidad;

	
	
	public DeduccionIncapacidad() {
		super();
	}

	public DeduccionIncapacidad(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the incapacidad
	 */
	public TipoIncapacidad getIncapacidad() {
		return incapacidad;
	}

	/**
	 * @param incapacidad the incapacidad to set
	 */
	public void setIncapacidad(TipoIncapacidad incapacidad) {
		this.incapacidad = incapacidad;
	}

	/**
	 * @return the diasIncapacidad
	 */
	public Integer getDiasIncapacidad() {
		return diasIncapacidad;
	}

	/**
	 * @param diasIncapacidad the diasIncapacidad to set
	 */
	public void setDiasIncapacidad(Integer diasIncapacidad) {
		this.diasIncapacidad = diasIncapacidad;
	}
	
	
}
