package com.tikal.cacao.model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Perfil {
	
	@Id Long id; 
	@Index String tipo;
	private boolean[] permisos;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean[] getPermisos() {
		return permisos;
	}
	public void setPermisos(boolean[] permisos) {
		this.permisos = permisos;
	}
}
