/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionAusentismo;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionAusentismo extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		DeduccionAusentismo dAusentismo = (DeduccionAusentismo) deduccion;
		this.setDiasAusentismo(dAusentismo.getDiasAusente());
		double descuento = CalculadoraNomina.calcularDescuentoPorAusencia(this.getSueldo(),
				dAusentismo.getDiasAusente(), dAusentismo.isTrabajaSeisDias(), periodo);
		this.setTotalAPagar(this.getTotalAPagar() - CalculadoraNomina.aplicarDeduccion(dAusentismo, descuento));
	}

}
