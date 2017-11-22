/**
 * 
 */
package com.tikal.cacao.model;

import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 *
 */
public class Deduccion {
	
	public Deduccion() {}
	
	
	
//	public Deduccion(String clave, TipoDeduccion descripcion, double descuento) {
//		this.clave = clave;
//		this.descripcion = descripcion;
//		this.descuento = descuento;
//	}
	
	public Deduccion(String tipo, String clave, String descripcion) {
		this.tipo = tipo;
		this.clave = clave;
		this.descripcion = descripcion;
	}



	/**
	 * Atributo requerido para expresar la Clave agrupadora bajo la cual
	 * se clasifica la decucci&oacute;n
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
	private double descuento;
	
//	/**
//	 * Atributo que se utiliza para especificar el tipo de incapacidad, siempre y cuando,
//	 * el atributo {@code descripcion} es igual a {@link TipoDeduccion}.<strong>DESCUENTO_POR_INCAPACIDAD</strong>
//	 */
//	private TipoIncapacidad incapacidad;
//	
//	/**
//	 * Cantidad de d&iacute;as que dur&oacute; la incapacidad
//	 */
//	private Integer diasIncapacidad;
	
	//private double porcentaje;
	
	
	
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		if (descripcion == null) {
			return "";
		}
		
		return descripcion.toString();
	}
	
	/**
	 * @return
	 */
	public String getTipoEnum() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the descuento
	 */
	public double getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento
	 *            the descuento to set
	 */
	public void setDescuento(double descuento) {
		this.descuento = Util.redondear(descuento);
	}

//	/**
//	 * @return the incapacidad
//	 */
//	public TipoIncapacidad getIncapacidad() {
//		return incapacidad;
//	}
//
//	/**
//	 * @param incapacidad the incapacidad to set
//	 */
//	public void setIncapacidad(TipoIncapacidad incapacidad) {
//		this.incapacidad = incapacidad;
//	}
//
//	/**
//	 * @return the diasIncapacidad
//	 */
//	public int getDiasIncapacidad() {
//		return diasIncapacidad;
//	}
//
//	/**
//	 * @param diasIncapacidad the diasIncapacidad to set
//	 */
//	public void setDiasIncapacidad(int diasIncapacidad) {
//		this.diasIncapacidad = diasIncapacidad;
//	}

}
