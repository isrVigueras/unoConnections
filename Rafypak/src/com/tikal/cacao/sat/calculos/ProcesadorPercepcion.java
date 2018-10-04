/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionPrevisionSocial;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;

/**
 * @author Tikal
 *
 */
public abstract class ProcesadorPercepcion {
	
	@Autowired
	private MapaRecursos recursos;
	
	
	public void setSueldo(double sueldo) {
		this.recursos.setSueldo(sueldo);
	}
	
	public double getSueldo() {
		return this.recursos.getSueldo();
	}
	
	public void setTotalAPagar(double totalAPagar) {
		this.recursos.setTotalAPagar(totalAPagar);;
	}
	
	public double getTotalAPagar() {
		return this.recursos.getTotalAPagar();
	}
	
	public void setIngresoGravable(double ingresoGravable) {
		this.recursos.setIngresoGravable(ingresoGravable);;
	}
	
	public double getIngresoGravable() {
		return this.recursos.getIngresoGravable();
	}
	
	public void setIngresoCotizable(double ingresoCotizable) {
		this.recursos.setIngresoCotizable(ingresoCotizable);;
	}
	
	public double getIngresoCotizable() {
		return this.recursos.getIngresoCotizable();
	}
	
	public void setSBC(double sbc) {
		this.recursos.setSBC(sbc);
	}
	
	public double getSBC() {
		return this.recursos.getSBC();
	}
	
	public void setUltimoSBC(double sbc) {
		this.recursos.setUltimoSBC(sbc);
	}
	
	public double getUltimoSBC() {
		return this.recursos.getUltimoSBC();
	}
	
	public void setMontoPremioASBC(double montoPremioASBC) {
		this.recursos.setMontoPremioASBC(montoPremioASBC);
	}
	
	public double getMontoPremioASBC() {
		return this.recursos.getMontoPremioASBC();
	}
	
	public double getTiempoExtraParaSBC() {
		return this.recursos.getTiempoExtraParaSBC();
	}
	
	public void setTiempoExtraParaSBC(double tiempoExtraParaSBC) {
		this.recursos.setTiempoExtraParaSBC(tiempoExtraParaSBC);
	}
	
	public TarifaSubsidio getTarifa() {
		return this.recursos.getTarifa();
	}
	
	public void setTarifa(TarifaSubsidio tarifa) {
		this.recursos.setTarifa(tarifa);
	}
	
	public boolean isAsegurado() {
		return this.recursos.isAsegurado();
	}
	
	public void setAsegurado(boolean esAsegurado) {
		this.recursos.setAsegurado(esAsegurado);
	}
	
	public boolean isTrabajaDomingos() {
		return this.recursos.isTrabajaDomingos();
	}
	
	public void setTrabajaDomingos(boolean trabajaDomingos) {
		this.recursos.setTrabajaDomingos(trabajaDomingos);
	}
	
	public void setMontoPrevisionSocial(double mPrevisionSocial) {
		this.recursos.setPrevisionSocial(mPrevisionSocial);;
	}
	
	public double getMontoPrevisionSocial() {
		return this.recursos.getPrevisionSocial();
	}
	
	public int getDiasAusentismo() {
		return this.recursos.getDiasAusentismo();
	}
	
	public void setDiasAusentismo(int diasAusentismo) {
		this.recursos.setDiasAusentismo(diasAusentismo);
	}
	
	public int getDiasIncapacidad() {
		return this.recursos.getDiasIncapacidad();
	}

	public void setDiasIncapacidad(int diasIncapacidad) {
		this.recursos.setDiasIncapacidad(diasIncapacidad);
	}
	
	/**
	 * Este m&eacute;todo reinicia a <strong>0.0</strong> los valores de los recursos 
	 * que comparten los objetos {@code ProcesadorPercepcion} y {@code ProcesadorDeduccion}
	 * 
	 */
	public void reiniciarRecursos() {
		setIngresoCotizable(0.0);
		setIngresoGravable(0.0);
		setMontoPremioASBC(0.0);
		setTiempoExtraParaSBC(0.0);
		setUltimoSBC(0.0);
		setSBC(0.0);
		setSueldo(0.0);
		setTotalAPagar(0.0);
		setMontoPrevisionSocial(0.0);
		setAsegurado(false);
		setTrabajaDomingos(false);
		setDiasAusentismo(0);
	}
	
	protected void ejecutarProcesadorPremios(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double premio = percepcion.getCantidad();
		percepcion.setImporteGravado(premio);
		if (this.getUltimoSBC() == 0.0) {
			this.setSBC(IMSS.calcularSBC(this.getSueldo(), fechaContratacion, periodo));  
		} else {
			this.setSBC(this.getUltimoSBC());
		}
		this.setMontoPremioASBC(this.getMontoPremioASBC() + CalculadoraNomina.integrarPremioASBC(premio, this.getSBC(), periodo));
		this.setIngresoGravable(this.getIngresoGravable() + premio);
		this.setTotalAPagar(this.getTotalAPagar() + premio);	
	}
	
	/**
	 * &Eacute:ste m&eacute;todo realiza las operaciones para procesar un objeto de tipo {@code PercepcionPrevisionSocial}
	 * Si el estado del objeto indica que si es una percepci&oacute;n de previsi&oacute;n social, entonces
	 * el monto de esta percepci&oacute;n estar&aacute; exento del pago del Impuesto Sobre la Renta (ISR), en caso contrario
	 * no exentar&aacute para ISR.
	 * @param pPrevisionSocial la percepci&oacute;n que puede ser tratada como previsi&oacute;n social
	 */
	protected void ejecutarPrevisionSocial(PercepcionPrevisionSocial pPrevisionSocial) {
//		double cantidad = pPrevisionSocial.getCantidad();

//		if (pPrevisionSocial.isPrevisionSocial()) {
//			this.setMontoPrevisionSocial(this.getMontoPrevisionSocial() + cantidad);
//		} else {
//			this.setIngresoGravable(this.getIngresoGravable() + cantidad);
//		    this.setTotalAPagar(this.getTotalAPagar() + cantidad);		
//      }		
		
	}
	
	protected abstract void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion);

}
