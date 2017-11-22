/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class Direccion {
	
	/**
	 * 
	 */
	private String calle;
	
	/**
	 * 
	 */
	private String numExterior;
	
	/**
	 * 
	 */
	private String numInterior;
	
	/**
	 * 
	 */
	private String codigoPostal;
	
	/**
	 * 
	 */
	private String colonia;
	
	/**
	 * 
	 */
	private String localidad;
	

	/**
	 * 
	 */
	private String municipio;
	
	/**
	 * 
	 */
	//private Estado estado;
	private String estado;
	
	/**
	 * 
	 */
	public Direccion() {};

	/**
	 * @param calle
	 * @param numExterior
	 * @param numInterior
	 * @param codigoPostal
	 * @param colonia
	 * @param localidad
	 * @param estado
	 */
//	public Direccion(String calle, String numExterior, String numInterior, String codigoPostal, String colonia,
//			String localidad, Estado estado) {
//		this.calle = calle;
//		this.numExterior = numExterior;
//		this.numInterior = numInterior;
//		this.codigoPostal = codigoPostal;
//		this.colonia = colonia;
//		this.localidad = localidad;
//		this.estado = estado;
//	}
	

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle
	 * @param numExterior
	 * @param numInterior
	 * @param codigoPostal
	 * @param colonia
	 * @param localidad
	 * @param estado
	 */
	public Direccion(String calle, String numExterior, String numInterior, String codigoPostal, String colonia,
			String localidad, String estado,String municipio) {
		super();
		this.calle = calle;
		this.numExterior = numExterior;
		this.numInterior = numInterior;
		this.codigoPostal = codigoPostal;
		this.colonia = colonia;
		this.localidad = localidad;
		this.estado = estado;
		this.municipio=municipio;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the numExterior
	 */
	public String getNumExterior() {
		return numExterior;
	}

	/**
	 * @param numExterior the numExterior to set
	 */
	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	/**
	 * @return the numInterior
	 */
	public String getNumInterior() {
		return numInterior;
	}

	/**
	 * @param numInterior the numInterior to set
	 */
	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}

	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	/**
	 * @return the localidad
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		//return estado.toString();
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
//	public void setEstado(String estado) {
//		if (estado.equals("Michoacán") | estado.equals("Yucatán")) 
//			estado = estado.replace('á', 'a');
//		else if (estado.equals("Ciudad de México") | estado.equals("Estado de México") | estado.equals("Querétaro"))
//			estado = estado.replace('é', 'e');
//		else if (estado.equals("San Luis Potosí"))
//			estado = estado.replace('í', 'i');
//		else if (estado.equals("Nuevo León"))
//			estado = estado.replace('ó', 'o');
//		this.estado = Estado.valueOf(estado.toUpperCase().replaceAll(" ", "_"));
//		
//	}
	
	
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
}