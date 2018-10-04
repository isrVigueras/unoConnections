package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.sat.cfd.Comprobante;

import mx.gob.sat.implocal.ImpuestosLocales;

/**
 * @author Tikal
 *
 */
public class ComprobanteConComentarioVO {
	
	private Comprobante comprobante;
	
	private String comentarios;
	
	private ImpuestosLocales impuestosLocales;

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
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the impuestosLocales
	 */
	public ImpuestosLocales getImpuestosLocales() {
		return impuestosLocales;
	}

	/**
	 * @param impuestosLocales the impuestosLocales to set
	 */
	public void setImpuestosLocales(ImpuestosLocales impuestosLocales) {
		this.impuestosLocales = impuestosLocales;
	}

}
