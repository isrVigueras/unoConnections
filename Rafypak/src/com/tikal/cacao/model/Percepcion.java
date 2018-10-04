/**
 * 
 */
package com.tikal.cacao.model;

import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 *
 */
public class Percepcion {
	
	public Percepcion(){}
	
	public Percepcion(String clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}
	
	public Percepcion(String clave, String descripcion, double cantidad) {
		this.clave = clave;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	public Percepcion(String tipo, String clave, String descripcion) {
		this.tipo = tipo;
		this.clave = clave;
		this.descripcion = descripcion;
	}
	/**
	 * Atributo requerido para expresar la Clave agrupadora bajo la cual
	 * se clasifica la percepci&oacute;n
	 */
	private String tipo;
	/**
	 * 
	 */
	private String clave;
	
	/**
	 * 
	 */
	private String descripcion;
	
	/**
	 * 
	 */
	private double cantidad;
	
	private double importeGravado;
	
	private double importeExento;
	
	/**
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @return the tipo
	 */
	public String getDescripcion() {
		if(descripcion==null){
			return "";
		}
		return descripcion.toString();
	}
	
	/**
	 * @return
	 */
//	public EntTipoPercepcion getTipoEnum() {
//		return descripcion;
//	}
	

	/**
	 * @param descripcion the tipo to set
	 */
//	public void setDescripcion(String descripcion) {
//		this.descripcion = TipoPercepcion.valueOf(descripcion.toUpperCase().replaceAll(" ", "_"));
//	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cantidad
	 */
	public double getCantidad() {	
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = Util.redondear(cantidad);
	}

	/**
	 * @return the importeGravado
	 */
	public double getImporteGravado() {
		return importeGravado;
	}

	/**
	 * @param importeGravado the importeGravado to set
	 */
	public void setImporteGravado(double importeGravado) {
		this.importeGravado = Util.redondear(importeGravado);
	}

	/**
	 * @return the importeExento
	 */
	public double getImporteExento() {
		return importeExento;
	}

	/**
	 * @param importeExento the importeExento to set
	 */
	public void setImporteExento(double importeExento) {
		this.importeExento = Util.redondear(importeExento);
	}

	
	
}
