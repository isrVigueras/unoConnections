/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class NombrePersona {

	/**
	 * 
	 */
	private String nombresDePila;
	
	/**
	 * 
	 */
	private String apellidoPaterno;
	
	/**
	 * 
	 */
	private String apellidoMaterno;
	
	/**
	 * 
	 */
	public NombrePersona() {}
	
	/**
	 * 
	 * @param nombre
	 * @param apPaterno
	 * @param apMaterno
	 */
	public NombrePersona(String nombre, String apPaterno, String apMaterno) {
		this.nombresDePila = nombre;
		this.apellidoPaterno = apPaterno;
		this.apellidoMaterno = apMaterno;
	}

	/**
	 * @return the nombresDePila
	 */
	public String getNombresDePila() {
		return nombresDePila;
	}

	/**
	 * @param nombresDePila the nombresDePila to set
	 */
	public void setNombresDePila(String nombresDePila) {
		this.nombresDePila = nombresDePila;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	@Override
	public String toString(){
		return this.nombresDePila+" "+this.apellidoPaterno+" "+this.apellidoMaterno;
	}
}
