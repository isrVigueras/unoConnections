package com.tikal.cacao.springController.viewObjects.v33;

import com.tikal.cacao.sat.cfd33.Comprobante;

public class ComprobanteVO {

	private String email;
	
	private Comprobante comprobante;
	
	private String comentarios;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
