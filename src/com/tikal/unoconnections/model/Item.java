package com.tikal.unoconnections.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Item {

	private String modelo;
	private String marca;
	private String fechaFabricacion;
	private String sintoma;
	private String cliente;
	private String ticket;
	private Date fechaRecepcion;
	private String serial;
	
	@Id
	private String idInterno; //Este atributo se va a cargar en automatico por el DataStore (como el auto increment)
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getFechaFabricacion() {
		return fechaFabricacion;
	}
	public void setFechaFabricacion(String fechaFabricacion) {
		this.fechaFabricacion = fechaFabricacion;
	}
	public String getSintoma() {
		return sintoma;
	}
	public void setSintoma(String sintoma) {
		this.sintoma = sintoma;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getIdInterno() {
		return idInterno;
	}
	public void setIdInterno(String idInterno) {
		this.idInterno = idInterno;
	}
	
	
	
}
