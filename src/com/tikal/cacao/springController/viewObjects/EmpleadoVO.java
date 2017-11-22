package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Bancos;
import com.tikal.cacao.model.Empleado;

public class EmpleadoVO {
	private Empleado empleado;
	private String idEmpresa;
	private String nombreRegimen;
	private String claveBanco;
	private String nombreBanco;
	
	public Empleado getEmpleado() {
		if (nombreBanco != null && !nombreBanco.equals("")) {
			empleado.setNombreBanco(nombreBanco);
			empleado.setClaveBanco(claveBanco);
//			if (claveBanco.equals("623")) {
//				//empleado.setNombreBanco(Bancos.SKANDIA);
//				empleado.setNombreBanco(nombreBanco);
//				empleado.setClaveBanco(claveBanco);
//			} else {
//				empleado.setNombreBanco(Bancos.valueOf(nombreBanco.replace(" ", "_").
//						replace("&", "y").replace("-", "_")));
//				empleado.setClaveBanco(empleado.getNombreBanco().getClave());
//			} 
		}
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombreRegimen() {
		return nombreRegimen;
	}
	public void setNombreRegimen(String nombreRegimen) {
		this.nombreRegimen = nombreRegimen;
	}
	
	

}
