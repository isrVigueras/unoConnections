/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tikal
 *
 */
@Entity
public abstract class Catalogos implements Comparable<Catalogos> {
	
	@Id
	private Long id;
	
	@Index
	private String clave;
	
	private String descripcion;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public int compareTo(Catalogos o) {
		return this.getClave().compareTo(o.getClave());
	}

}
