/**
 * 
 */
package com.tikal.cacao.tarifas.subsidioEmpleo;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tikal
 *
 */
@Entity
public abstract class TarifaSubsidio {
	@Id
	String id;
	
	double limiteInferior1;
	
	@Index
	double limiteInferior2;
	
	double limiteSuperior;
	
	double cuotaFija;
	
	/**
	 * Por ciento para aplicarse sobre el excedente del limite inferior 1
	 */
	double porcentajeDelImpuesto;
	
	double subsidioParaElEmpleo;
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
