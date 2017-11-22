/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfTrue;

/**
 * @author Tikal
 *
 */
@Entity
public class Empleado {
	// agregar campo Puesto de Trabajao
	
	/**
	 * 
	 */
	@Id
	private Long numEmpleado; // key
	
	/**
	 * 
	 */
	private String RFC;
	
	/**
	 * 
	 */
	private NombrePersona nombre;
	
	/**
	 * 
	 */
	private String curp;
	
	/**
	 * 
	 */
	private String fechaDeContratacion;
	
	/**
	 * 
	 */
	private String puesto;
	
	/**
	 * 
	 */
	private String departamento;
	
	/**
	 * 
	 */
	private String contrato;
	
	/**
	 * 
	 */
	private Direccion direccion;
	
	/**
	 * 
	 */
	private String telefono;
	
	/**
	 * 
	 */
	private String email;
	
	/**
	 * 
	 */
	private String numSeguroSocial;
	
	private String nacionalidad;
		
	private String estadoCivil;
	
	private String claveBanco;
	
	//private Bancos nombreBanco;
	private String nombreBanco;
	
	private String clabe;
	
	/**
	 * 
	 */
	//@Index(IfTrue.class)
	private boolean activo = true;
	
	/**
	 * @return the numEmpleado
	 */
	public Long getNumEmpleado() {
		return numEmpleado;
	}

	/**
	 * @param numEmpleado the numEmpleado to set
	 */
	public void setNumEmpleado(Long numEmpleado) {
		this.numEmpleado = numEmpleado;
	}

	
	
	/**
	 * @return the rFC
	 */
	public String getRFC() {
		return RFC;
	}

	/**
	 * @param rFC the rFC to set
	 */
	public void setRFC(String rFC) {
		RFC = rFC;
	}

	/**
	 * @return the nombre
	 */
	public NombrePersona getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(NombrePersona nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}
	

	/**
	 * @return the fechaDeContratacion
	 */
	public String getFechaDeContratacion() {
		return fechaDeContratacion;
	}

	/**
	 * @param fechaDeContratacion the fechaDeContratacion to set
	 */
	public void setFechaDeContratacion(String fechaDeContratacion) {
		this.fechaDeContratacion = fechaDeContratacion;
	}

	
	
	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return puesto;
	}

	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the contrato
	 */
	public String getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the direccion
	 */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the numSeguroSocial
	 */
	public String getNumSeguroSocial() {
		return numSeguroSocial;
	}

	/**
	 * @param numSeguroSocial the numSeguroSocial to set
	 */
	public void setNumSeguroSocial(String numSeguroSocial) {
		this.numSeguroSocial = numSeguroSocial;
	}

	
	
	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public String getClaveBanco() {
		return claveBanco;
	}

	public void setClaveBanco(String claveBanco) {
		this.claveBanco = claveBanco;
	}

//	public Bancos getNombreBanco() {
//		return nombreBanco;
//	}
//
//	public void setNombreBanco(Bancos claveBanco) {
//		this.nombreBanco = claveBanco;
//	}
	
	public String getNombreBanco() {
		return nombreBanco;
	}
	
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	/**
	 * @return the estaActivo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param estaActivo the estaActivo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}
