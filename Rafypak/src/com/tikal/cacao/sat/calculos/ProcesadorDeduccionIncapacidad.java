/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionIncapacidad;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
@Component
public class ProcesadorDeduccionIncapacidad extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		DeduccionIncapacidad dIncapacidad = (DeduccionIncapacidad) deduccion;
		this.setDiasIncapacidad(dIncapacidad.getDiasIncapacidad());
		double descuentoIncapacidad = CalculadoraNomina.calcularDescuentoPorIncapacidad(this.getSueldo(),
				dIncapacidad.getDiasIncapacidad(), periodo, dIncapacidad.getIncapacidad());
		this.setTotalAPagar(
				this.getTotalAPagar() - CalculadoraNomina.aplicarDeduccion(dIncapacidad, descuentoIncapacidad));
	}

}
