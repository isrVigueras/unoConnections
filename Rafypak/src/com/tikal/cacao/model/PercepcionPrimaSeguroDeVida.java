/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Subclass;
import com.tikal.cacao.model.clausulasEnum.BeneficiariosSeguroDeVida;
import com.tikal.cacao.model.clausulasEnum.ContratanteSeguroDeVida;
import com.tikal.cacao.model.clausulasEnum.RiesgosAmparadosPorSeguroDeVida;

/**
 * @author Tikal
 *
 */
@Subclass
public class PercepcionPrimaSeguroDeVida extends Percepcion {

	private BeneficiariosSeguroDeVida beneficiarios;
	
	private ContratanteSeguroDeVida contratante;
	
	private RiesgosAmparadosPorSeguroDeVida riesgosAmparados;

	public PercepcionPrimaSeguroDeVida() {}
	
	

	/**
	 * @param tipo
	 * @param clave
	 * @param descripcion
	 */
	public PercepcionPrimaSeguroDeVida(String tipo, String clave, String descripcion) {
		super(tipo, clave, descripcion);
	}


	/**
	 * @return the beneficiarios
	 */
	public BeneficiariosSeguroDeVida getBeneficiarios() {
		return beneficiarios;
	}

	/**
	 * @param beneficiarios the beneficiarios to set
	 */
	public void setBeneficiarios(BeneficiariosSeguroDeVida beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	/**
	 * @return the contratante
	 */
	public ContratanteSeguroDeVida getContratante() {
		return contratante;
	}

	/**
	 * @param contratante the contratante to set
	 */
	public void setContratante(ContratanteSeguroDeVida contratante) {
		this.contratante = contratante;
	}

	/**
	 * @return the riesgosAmparados
	 */
	public RiesgosAmparadosPorSeguroDeVida getRiesgosAmparados() {
		return riesgosAmparados;
	}

	/**
	 * @param riesgosAmparados the riesgosAmparados to set
	 */
	public void setRiesgosAmparados(RiesgosAmparadosPorSeguroDeVida riesgosAmparados) {
		this.riesgosAmparados = riesgosAmparados;
	}
	
	
}
