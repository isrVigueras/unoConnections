package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class ContadorEmpleados {

	@Id
	private Long id;
	
	private long cuenta;

	public long getId() {
		return id;
	}
	
	/**
	 * @return the cuenta
	 */
	public long getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(long cuenta) {
		this.cuenta = cuenta;
	}
	
	
}
