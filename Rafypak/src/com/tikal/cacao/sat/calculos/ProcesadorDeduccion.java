/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;

/**
 * @author Tikal
 *
 */
public abstract class ProcesadorDeduccion {
	
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
		this.setIngresoCotizable(ingresoCotizable);;
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
	
	public TarifaSubsidio getTarifa() {
		return this.recursos.getTarifa();
	}
	
	public void setTarifa(TarifaSubsidio tarifa) {
		this.recursos.setTarifa(tarifa);
	}
	
	public boolean isTrabajaDomingos() {
		return this.recursos.isTrabajaDomingos();
	}
	
	public void setTrabajaDomingos(boolean trabajaDomingos) {
		this.recursos.setTrabajaDomingos(trabajaDomingos);
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
	
	public void especificarSBC(PeriodosDePago periodo, Date fechaContratacion) {
		double sbcOrdinario = 0.0;
		boolean b1 = this.getUltimoSBC() == 0.0;
		boolean b2 = this.getSBC() == 0.0;
		if (this.isTrabajaDomingos()) {
			sbcOrdinario = IMSS.calcularSBCConPrimaDominical(this.getSueldo(), fechaContratacion, periodo);
		} else {
			sbcOrdinario = IMSS.calcularSBC(this.getSueldo(), fechaContratacion, periodo);
		}
		
		if (b1 && b2) {
			this.setSBC(sbcOrdinario);
		} else if (b2 && !b1) {
			if (this.getUltimoSBC() != sbcOrdinario ) {
				this.setSBC(getUltimoSBC()); //TODO implementar modificación de SBC cada bimestre
			} else {
				this.setSBC(sbcOrdinario);
			}
		}
	}
	
	abstract void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion);

}
