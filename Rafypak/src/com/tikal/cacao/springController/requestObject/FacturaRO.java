package com.tikal.cacao.springController.requestObject;

import com.tikal.cacao.sat.cfd.Comprobante;

/**
 * @author Tikal
 *
 */
public class FacturaRO {

	private Comprobante comprobante;
	
	private String uuid;

	/**
	 * @return the comprobante
	 */
	public Comprobante getComprobante() {
		return comprobante;
	}

	/**
	 * @param comprobante the comprobante to set
	 */
	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
