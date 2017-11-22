/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorPercepcionSeguroGastosMedicosMayores extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		// TODO Espeficar si esta percepción es gravable conforme a la LISR
		double cantidad = percepcion.getCantidad();
		percepcion.setImporteExento(cantidad);
		this.setTotalAPagar(this.getTotalAPagar() + cantidad);
	}

}
