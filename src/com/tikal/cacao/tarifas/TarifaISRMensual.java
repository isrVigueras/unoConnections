/**
 * 
 */
package com.tikal.cacao.tarifas;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Ismael
 *
 */
@Entity
public class TarifaISRMensual {
	
	@Id
	long index;

	double limiteInferior1;
	
	double limiteInferior2;
	
	double limiteSuperior;
	
	double cuotaFija;
	
	/**
	 * Por ciento para aplicarse sobre el excedente del limite inferior 1
	 */
	double porcentajeDelImpuesto;
	
	double subsidioParaElEmpleo;

	/**
	 * @return the index
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(long index) {
		this.index = index;
	}

	/**
	 * @return the limiteInferior1
	 */
	public double getLimiteInferior1() {
		return limiteInferior1;
	}

	/**
	 * @param limiteInferior1 the limiteInferior1 to set
	 */
	public void setLimiteInferior1(double limiteInferior1) {
		this.limiteInferior1 = limiteInferior1;
	}

	/**
	 * @return the limiteInferior2
	 */
	public double getLimiteInferior2() {
		return limiteInferior2;
	}

	/**
	 * @param limiteInferior2 the limiteInferior2 to set
	 */
	public void setLimiteInferior2(double limiteInferior2) {
		this.limiteInferior2 = limiteInferior2;
	}

	/**
	 * @return the limiteSuperior
	 */
	public double getLimiteSuperior() {
		return limiteSuperior;
	}

	/**
	 * @param limiteSuperior the limiteSuperior to set
	 */
	public void setLimiteSuperior(double limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	/**
	 * @return the cuotaFija
	 */
	public double getCuotaFija() {
		return cuotaFija;
	}

	/**
	 * @param cuotaFija the cuotaFija to set
	 */
	public void setCuotaFija(double cuotaFija) {
		this.cuotaFija = cuotaFija;
	}

	/**
	 * @return the porcentajeDelImpuesto
	 */
	public double getPorcentajeDelImpuesto() {
		return porcentajeDelImpuesto;
	}

	/**
	 * @param porcentajeDelImpuesto the porcentajeDelImpuesto to set
	 */
	public void setPorcentajeDelImpuesto(double porcentajeDelImpuesto) {
		this.porcentajeDelImpuesto = porcentajeDelImpuesto;
	}

	/**
	 * @return the subsidioParaElEmpleo
	 */
	public double getSubsidioParaElEmpleo() {
		return subsidioParaElEmpleo;
	}

	/**
	 * @param subsidioParaElEmpleo the subsidioParaElEmpleo to set
	 */
	public void setSubsidioParaElEmpleo(double subsidioParaElEmpleo) {
		this.subsidioParaElEmpleo = subsidioParaElEmpleo;
	}
	
	
}
