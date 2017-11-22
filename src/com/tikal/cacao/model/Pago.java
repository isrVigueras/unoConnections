package com.tikal.cacao.model;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 * Este objeto representa el resultado de calcular la nómina para un empleado. A este objeto
 * se le aplicar&aacute; la operaci&oacute;n ajustes para calcular el pago real del empleado 
 */
@Entity
public class Pago {
	
	@Id
	private Long id;
	
	private double cantidadAPagar;
	
	// campo que indica el monto total de las prestaciones de previsión social
	// que recibe el trabajador
	private double montoPrevisionSocial;
	
	@Index
	private Date fechaDePago;//TODO agregar campo stringComprobante
	
	@Index
	private Date fechaDePagoEsquema;
	
	private String formaPago;
	
	private String diasPagados;
	
	private List<Percepcion> percepciones;
	
	private List<Deduccion> deducciones;
	
	@Index
	private Long idEmpleado;
	
	private String rfcEmpresa;
	
	private String razonSocial;
	
	private String registroPatronal;
	
	private String cpLugarExpedicion;
	
	@Index
	private long idRegimen;
	
	/** Clave del Tipo de R&eacute;gimen de Contrataci&oacute;n del empleado
	 * al que pertenece este pago*/
	private String tipoRegimen;
	
	private double salarioDiario;
	
	private double salarioDiarioIntegrado;
	
	/* campo que especifica si el trabajador de este pago esta asegurado por
	   el IMSS*/
	private boolean trabajadorAsegurado;
	
	private String cadenaComprobante;

	/**
	 * @return the cantidadAPagar
	 */
	public double getCantidadAPagar() {
		return cantidadAPagar;
	}

	
	
	/**
	 * @return the fechaDePagoEsquema
	 */
	public Date getFechaDePagoEsquema() {
		return fechaDePagoEsquema;
	}



	/**
	 * @param fechaDePagoEsquema the fechaDePagoEsquema to set
	 */
	public void setFechaDePagoEsquema(Date fechaDePagoEsquema) {
		this.fechaDePagoEsquema = fechaDePagoEsquema;
	}



	/**
	 * @param cantidadAPagar the cantidadAPagar to set
	 */
	public void setCantidadAPagar(double cantidadAPagar) {
		this.cantidadAPagar = Util.redondear(cantidadAPagar);
	}

	/**
	 * @return the fechaDePago
	 */
	public Date getFechaDePago() {
		return fechaDePago;
	}

	/**
	 * @param fechaDePago the fechaDePago to set
	 */
	public void setFechaDePago(Date fechaDePago) {
		this.fechaDePago = fechaDePago;
	}
	

	/**
	 * &Eacute;ste m&eacute;todo regresa la periodicidad del pago. Esta puede ser:
	 * <em>Semanal</em>, <em>Decenal</em>, <em>Quincenal</em> o  <em>Semanal</em>
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the diasPagados
	 */
	public String getDiasPagados() {
		int longitudStr = diasPagados.length();
		if (longitudStr == 2 || longitudStr == 1)
			diasPagados = diasPagados.concat(".001");
		return diasPagados;
	}

	/**
	 * @param diasPagados the diasPagados to set
	 */
	public void setDiasPagados(String diasPagados) {
		this.diasPagados = diasPagados;
	}

	/**
	 * @return the percepciones
	 */
	public List<Percepcion> getPercepciones() {
		return percepciones;
	}

	/**
	 * @param percepciones the percepciones to set
	 */
	public void setPercepciones(List<Percepcion> percepciones) {
		this.percepciones = percepciones;
	}

	/**
	 * @return the deducciones
	 */
	public List<Deduccion> getDeducciones() {
		return deducciones;
	}

	/**
	 * @param deducciones the deducciones to set
	 */
	public void setDeducciones(List<Deduccion> deducciones) {
		this.deducciones = deducciones;
	}

	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getCpLugarExpedicion() {
		return cpLugarExpedicion;
	}

	public void setCpLugarExpedicion(String cpLugarExpedicion) {
		this.cpLugarExpedicion = cpLugarExpedicion;
	}

	/**
	 * @return the registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * @param registroPatronal the registroPatronal to set
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * @return the idRegimen
	 */
	public long getIdRegimen() {
		return idRegimen;
	}

	/**
	 * @param idRegimen the idRegimen to set
	 */
	public void setIdRegimen(long idRegimen) {
		this.idRegimen = idRegimen;
	}

	/**
	 * @return la Clave del Tipo de R&eacute;gimen de Contrataci&oacute;n del empleado
	 * al que pertenece este pago
	 */
	public String getTipoRegimen() {
		return tipoRegimen;
	}

	/**
	 * @param tipoRegimen the tipoRegimen to set
	 */
	public void setTipoRegimen(String tipoRegimen) {
		this.tipoRegimen = tipoRegimen;
	}

	/**
	 * @return the salarioDiario
	 */
	public double getSalarioDiario() {
		return salarioDiario;
	}

	/**
	 * @param salarioDiario the salarioDiario to set
	 */
	public void setSalarioDiario(double salarioDiario) {
		this.salarioDiario = Util.redondear(salarioDiario);
	}

	/**
	 * @return the salarioDiarioIntegrado
	 */
	public double getSalarioDiarioIntegrado() {
		return salarioDiarioIntegrado;
	}

	/**
	 * @param salarioDiarioIntegrado the salarioDiarioIntegrado to set
	 */
	public void setSalarioDiarioIntegrado(double salarioDiarioIntegrado) {
		this.salarioDiarioIntegrado = Util.redondear(salarioDiarioIntegrado);
	}

	/**
	 * @return the cadenaComprobante
	 */
	public String getCadenaComprobante() {
		return cadenaComprobante;
	}



	/**
	 * @param cadenaComprobante the cadenaComprobante to set
	 */
	public void setCadenaComprobante(String cadenaComprobante) {
		this.cadenaComprobante = cadenaComprobante;
	}



	/**
	 * @return the trabajadorAsegurado
	 */
	public boolean isTrabajadorAsegurado() {
		return trabajadorAsegurado;
	}

	/**
	 * @param trabajadorAsegurado the trabajadorAsegurado to set
	 */
	public void setTrabajadorAsegurado(boolean trabajadorAsegurado) {
		this.trabajadorAsegurado = trabajadorAsegurado;
	}

	/**
	 * @return the montoPrevisionSocial
	 */
	public double getMontoPrevisionSocial() {
		return montoPrevisionSocial;
	}

	/**
	 * @param montoPrevisionSocial the montoPrevisionSocial to set
	 */
	public void setMontoPrevisionSocial(double montoPrevisionSocial) {
		this.montoPrevisionSocial = montoPrevisionSocial;
	}
	
	
	
	
	
}
