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
public class ProcesadorPercepcionComisiones extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double cantidadComisiones = percepcion.getCantidad();
		percepcion.setImporteGravado(cantidadComisiones);
		this.setTotalAPagar(this.getTotalAPagar() + cantidadComisiones);
		//TODO agregar la cantidad de esta percepción para calculo de SBC 
	}

}
