/**
 * 
 */
package com.tikal.cacao.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tikal
 *
 */
@Entity
public class Regimen { 
	
	/**
	 * 
	 */
	@Id
	private Long id; // key
	
	/**
	 * 
	 */
	@Index
	private String nombre;

	/**
	 * 
	 */
	private List<Percepcion> percepciones;
	
	/**
	 * 
	 */
	private List<Deduccion> deducciones;
	
	/**
	 * 
	 */
	private List<Long> idEmpleados;
	
	/**
	 * 
	 */
	private boolean activo = true;
	
	/**
	 * 
	 */
	private RegimenContratacion tipoRegimen;
	
	/**
	 * 
	 */
	private String formaPago;
	
	/**
	 * 
	 */
	private int[] diasDePago;
	
	/** */
	private RiesgoPuesto riesgoPuesto;
	
	
	public Regimen(){
		this.idEmpleados= new ArrayList<Long>();
	}
	
	
	

	/**
	 * @param id
	 * @param nombre
	 * @param percepciones
	 * @param deducciones
	 * @param idEmpleados
	 * @param activo
	 * @param tipoRegimen
	 * @param formaPago
	 * @param diasDePago
	 * @param sueldo
	 */
	public Regimen(Long id, String nombre, List<Percepcion> percepciones, List<Deduccion> deducciones,
			List<Long> idEmpleados, boolean activo, String formaPago, int[] diasDePago,
			double sueldo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.percepciones = percepciones;
		this.deducciones = deducciones;
		this.idEmpleados = idEmpleados;
		this.activo = activo;
		this.formaPago = formaPago;
		this.diasDePago = diasDePago;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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
	 * @return the idEmpleados
	 */
	public List<Long> getIdEmpleados() {
		return idEmpleados;
	}

	/**
	 * @param idEmpleados the idEmpleados to set
	 */
	public void setIdEmpleados(List<Long> idEmpleados) {
		this.idEmpleados = idEmpleados;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the tipoRegimen
	 */
	public String getTipoRegimen() {
		if(this.tipoRegimen==null){
			return "";
		}
		return tipoRegimen.toString();
	}
	
	/**
	 * @return la clave que va en el recibo de pago en el campo de R&eacute;gimen
	 */
	public String getRegimenContratacion() {
		return this.tipoRegimen.getClave();
	}

	/**
	 * @param tipoRegimen the tipoRegimen to set
	 */
	public void setTipoRegimen(String tipoRegimen) {
		this.tipoRegimen = RegimenContratacion.valueOf(
				tipoRegimen.toUpperCase().replaceAll(" ","_").replaceFirst("ó", "o"));
	}

	public void setTipoRegimenContratacion(RegimenContratacion regContratacion) {
		this.tipoRegimen = regContratacion;
	}
	
	
	/**
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
	 * @return the diasDePago
	 */
	public int[] getDiasDePago() {
		return diasDePago;
	}


	/**
	 * @param diasDePago the diasDePago to set
	 */
	public void setDiasDePago(int[] diasDePago) {
		this.diasDePago = diasDePago;
	}

	/**
	 * @return the riesgoPuesto
	 */
	public RiesgoPuesto getRiesgoPuesto() {
		return riesgoPuesto;
	}

	/**
	 * @param riesgoPuesto the riesgoPuesto to set
	 */
	public void setRiesgoPuesto(RiesgoPuesto riesgoPuesto) {
		this.riesgoPuesto = riesgoPuesto;
	}

	
	public void addEmpleado(Long id){
		this.idEmpleados.add(id);
	}
	
	
}
