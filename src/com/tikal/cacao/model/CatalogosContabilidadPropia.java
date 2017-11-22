/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tikal
 *
 */
@Entity
public abstract class CatalogosContabilidadPropia<T extends Catalogos> implements Comparable<CatalogosContabilidadPropia<T>> {

	@Id
	private Long id;
	
	@Index
	private String clave;
	
	private String descripcion;
	
	private Key<T> llaveAgrupadora; 
	
	@Index
	private String rfcEmpresa;
	
	
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

	/**
	 * @return the llaveAgrupadora
	 */
	public Key<T> getLlaveAgrupadora() {
		return llaveAgrupadora;
	}

	/**
	 * @param llaveAgrupadora the llaveAgrupadora to set
	 */
	public void setLlaveAgrupadora(Key<T> llaveAgrupadora) {
		this.llaveAgrupadora = llaveAgrupadora;
	}

	/**
	 * @return the rfcEmpresa
	 */
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	/**
	 * @param rfcEmpresa the rfcEmpresa to set
	 */
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	
	@Override
	public int compareTo(CatalogosContabilidadPropia<T> o) {
		return this.getClave().compareTo(o.getClave());
	}
	
	
}
