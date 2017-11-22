/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class IndicadorNomina {
	
	@Id
	private String nombre;
	
	private double valor;
	
	public IndicadorNomina(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param nombre
	 * @param valor
	 */
	public IndicadorNomina(String nombre, double valor) {
		this.nombre = nombre;
		this.valor = valor;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
}
