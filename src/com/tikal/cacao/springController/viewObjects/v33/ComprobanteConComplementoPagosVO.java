package com.tikal.cacao.springController.viewObjects.v33;

import com.tikal.cacao.model.Serial;

import mx.gob.sat.pagos.Pagos;

public class ComprobanteConComplementoPagosVO {

	private String uuid;
	
	private Serial serie;
	
	private Pagos complementoPagos;
	
	private String email;


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

	
}
