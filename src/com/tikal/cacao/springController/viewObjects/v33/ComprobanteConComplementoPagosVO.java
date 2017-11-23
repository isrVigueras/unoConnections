package com.tikal.cacao.springController.viewObjects.v33;

import com.tikal.cacao.sat.cfd33.Comprobante;

import mx.gob.sat.pagos.Pagos;

public class ComprobanteConComplementoPagosVO {

	private Comprobante cfdiParaPago;
	
	private Pagos complementoPagos;
	
	private String email;

	public Comprobante getComprobante() {
		return cfdiParaPago;
	}

	public void setComprobante(Comprobante comprobante) {
		this.cfdiParaPago = comprobante;
	}

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
	
	
}
