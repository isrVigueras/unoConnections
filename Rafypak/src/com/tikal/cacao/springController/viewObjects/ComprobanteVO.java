package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.sat.cfd.Comprobante;

import mx.gob.sat.implocal.ImpuestosLocales;

public class ComprobanteVO {
	
	private String email;
	
	private Comprobante comprobante;
	
	private String comentarios;
	
	private ImpuestosLocales impuestosLocales;

	/**
	 * @return the emails
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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

	public ImpuestosLocales getImpuestosLocales() {
		return impuestosLocales;
	}

	public void setImpuestosLocales(ImpuestosLocales impuestosLocales) {
		this.impuestosLocales = impuestosLocales;
	}
	
	
	
}