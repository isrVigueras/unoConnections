/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Subclass;

/**
 * @author Tikal
 *
 */
@Subclass
public class PercepcionSubsidiosIncapacidad extends Percepcion {

	private TipoIncapacidad tipoIncapacidad;
	
	/**
	 * Cantidad de d&iacute;as que dur&oacute; la incapacidad
	 */
	private Integer diasIncapacidad;


	/**
	 * @param tipo
	 * @param clave
	 * @param descripcion
	 */
	public PercepcionSubsidiosIncapacidad(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}

	/**
	 * @return the tipoIncapacidad
	 */
	public TipoIncapacidad getTipoIncapacidad() {
		return tipoIncapacidad;
	}

	/**
	 * @param tipoIncapacidad the tipoIncapacidad to set
	 */
	public void setTipoIncapacidad(TipoIncapacidad tipoIncapacidad) {
		this.tipoIncapacidad = tipoIncapacidad;
	}

	public Integer getDiasIncapacidad() {
		return diasIncapacidad;
	}

	public void setDiasIncapacidad(Integer diasIncapacidad) {
		this.diasIncapacidad = diasIncapacidad;
	}
	
	
	
	
}
