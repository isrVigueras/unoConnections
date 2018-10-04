package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Pago;

public class PagoVO extends Pago{

	String Nombre;
	String rfc;
	String nss;
	String fechaIngreso;
	String curp;
	String puesto;
	String departamento;
	String claveBanco;
	
	long idPago;
	
	
	public PagoVO(){}
	
	public PagoVO(Pago p, Empleado e){
		this.setPago(p);
		this.setIdEmpleado(p.getIdEmpleado());
		
		String apMaterno = e.getNombre().getApellidoMaterno();
    	apMaterno = apMaterno != null ? apMaterno : "";
    	String strNombre = e.getNombre().getNombresDePila().concat(" ").concat(e.getNombre().getApellidoPaterno()).
    			concat(" ").concat(apMaterno);
		this.setNombre(strNombre);
    	
		this.setRfc(e.getRFC());
		this.setNss(e.getNumSeguroSocial());
		
		this.setFechaIngreso(e.getFechaDeContratacion());
		this.setCurp(e.getCurp());
		
		this.setPuesto(e.getPuesto());
		this.setDepartamento(e.getDepartamento());
		
		this.setClaveBanco(e.getClaveBanco());
	}
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}
	
	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	
	public String getClaveBanco() {
		return claveBanco;
	}

	public void setClaveBanco(String claveBanco) {
		this.claveBanco = claveBanco;
	}

	public long getIdPago() {
		return idPago;
	}

	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}

	public Pago getPago() {
		
		Pago p = new Pago();
		p.setCantidadAPagar(this.getCantidadAPagar());
		p.setMontoPrevisionSocial(this.getMontoPrevisionSocial());
		p.setDeducciones(this.getDeducciones());
		p.setDiasPagados(this.getDiasPagados());
		p.setFechaDePago(this.getFechaDePago());
		p.setFechaDePagoEsquema(this.getFechaDePagoEsquema());
		p.setFormaPago(this.getFormaPago());
		p.setIdEmpleado(this.getIdEmpleado());
		p.setPercepciones(this.getPercepciones());
		p.setSalarioDiario(this.getSalarioDiario());
		p.setSalarioDiarioIntegrado(this.getSalarioDiarioIntegrado());
		p.setRazonSocial(this.getRazonSocial());
		p.setRfcEmpresa(this.getRfcEmpresa());
		p.setCpLugarExpedicion(this.getCpLugarExpedicion());
		p.setTipoRegimen(this.getTipoRegimen());
		p.setIdRegimen(this.getIdRegimen());
		p.setTrabajadorAsegurado(this.isTrabajadorAsegurado());
		p.setCadenaComprobante(this.getCadenaComprobante());
		return p;
	}
	
	public void setPago(Pago p) {
		Long id = p.getId();
		if ( id != null) {
			this.setIdPago(id);
		}
		this.setCantidadAPagar(p.getCantidadAPagar());
		this.setDeducciones(p.getDeducciones());
		this.setDiasPagados(p.getDiasPagados());
		this.setFechaDePago(p.getFechaDePago());
		this.setFechaDePagoEsquema(p.getFechaDePagoEsquema());
		this.setFormaPago(p.getFormaPago());
		this.setPercepciones(p.getPercepciones());
		this.setSalarioDiario(p.getSalarioDiario());
		this.setSalarioDiarioIntegrado(p.getSalarioDiarioIntegrado());
		
		this.setRazonSocial(p.getRazonSocial());
		this.setRfcEmpresa(p.getRfcEmpresa());
		this.setRegistroPatronal(p.getRegistroPatronal());
		this.setCpLugarExpedicion(p.getCpLugarExpedicion());
		this.setTipoRegimen(p.getTipoRegimen());
		this.setIdRegimen(p.getIdRegimen());
		this.setTrabajadorAsegurado(p.isTrabajadorAsegurado());
		this.setCadenaComprobante(p.getCadenaComprobante());
	}
	
}
