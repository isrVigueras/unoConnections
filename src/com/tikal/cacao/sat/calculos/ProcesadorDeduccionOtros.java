/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.exception.DescuentoSalarioException;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionOtros extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		double descuento = deduccion.getDescuento();
		if (descuento >= this.getSueldo()) {
			throw new DescuentoSalarioException("La cantidad a descontar no puede ser mayor que el sueldo del empleado");
		}
		this.setTotalAPagar(this.getTotalAPagar() - descuento);

	}

}
