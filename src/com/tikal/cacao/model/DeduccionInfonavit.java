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
public class DeduccionInfonavit extends Deduccion {
	
	public DeduccionInfonavit() {}
	
	public DeduccionInfonavit(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}
	
	private ModalidadDescuento modalidad;
	
	private double valorDeCredito;

	/**
	 * @return the modalidad
	 */
	public ModalidadDescuento getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(ModalidadDescuento modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * @return the valorDeCredito
	 */
	public double getValorDeCredito() {
		return valorDeCredito;
	}

	/**
	 * @param valorDeCredito the valorDeCredito to set
	 */
	public void setValorDeCredito(double valorDeCredito) {
		this.valorDeCredito = valorDeCredito;
	}
	
	
}
