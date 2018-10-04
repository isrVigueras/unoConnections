/**
 * 
 */
package com.tikal.cacao.model.orm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo")
public class Tipo {

	private String id;
	
	private String nombre;
	
//	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
//	private List<Division> listaD;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "id_t")
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

//	public List<Division> getListaD() {
//		return listaD;
//	}
//
//	public void setListaD(List<Division> listaD) {
//		this.listaD = listaD;
//	}
	
	
}
