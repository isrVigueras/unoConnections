/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
@Component
public class ProcesadorDeduccionSeguridadSocial extends ProcesadorDeduccion {
	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		/*Para integrar el tiempo extraordinario al SBC, se debe sumar el tiempo
		 *extraordinario trabajado en un bimestre*/
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
		this.setTotalAPagar(this.getTotalAPagar()
				- CalculadoraNomina.aplicarDeduccion(deduccion, IMSS.calcularCuota(this.getSBC(), periodo, this.getDiasAusentismo(), this.getDiasIncapacidad())));
	
	}

}
