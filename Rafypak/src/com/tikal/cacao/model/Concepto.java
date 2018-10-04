package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class Concepto {

	private String noIdentificacion;
	
	private String unidad;
	
	private String descripcion;
	
	private float valorUnitario;
	
	private String descripcionSAT;
	
	private String claveProdServ;
	
	private String claveUnidad;
	
	private String unidadAduana;
	
	private String descripcionUnidadSAT;

	public String getNoIdentificacion() {
		return noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getDescripcionSAT() {
		return descripcionSAT;
	}

	public void setDescripcionSAT(String descripcionSAT) {
		this.descripcionSAT = descripcionSAT;
	}

	public String getClaveProdServ() {
		return claveProdServ;
	}

	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}

	public String getClaveUnidad() {
		return claveUnidad;
	}

	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	public String getDescripcionUnidadSAT() {
		return descripcionUnidadSAT;
	}

	public void setDescripcionUnidadSAT(String descripcionUnidadSAT) {
		this.descripcionUnidadSAT = descripcionUnidadSAT;
	}

	public String getUnidadAduana() {
		return unidadAduana;
	}

	public void setUnidadAduana(String unidadAduana) {
		this.unidadAduana = unidadAduana;
	}
	
	
}
