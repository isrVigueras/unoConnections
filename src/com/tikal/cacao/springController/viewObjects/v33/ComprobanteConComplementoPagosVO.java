package com.tikal.cacao.springController.viewObjects.v33;

import com.tikal.cacao.model.Serial;
import com.tikal.cacao.sat.cfd33.Comprobante;

import mx.gob.sat.pagos.Pagos;

public class ComprobanteConComplementoPagosVO {

	private String uuid;
	
	private Serial serie;
	
	private Pagos complementoPagos;
	
	private String email;

	private Comprobante cfdi;

	public Pagos getComplementoPagos() {
		return complementoPagos;
	}

	public void setComplementoPagos(Pagos complementoPagos) {
		this.complementoPagos = complementoPagos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Serial getSerie() {
		return serie;
	}

	public void setSerie(Serial serie) {
		this.serie = serie;
	}

	public Comprobante getCfdi() {
		return cfdi;
	}

	public void setCfdi(Comprobante cfdi) {
		this.cfdi = cfdi;
	}

	
}
