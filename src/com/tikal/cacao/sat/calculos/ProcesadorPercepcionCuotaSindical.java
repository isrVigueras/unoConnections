/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionCuotaSindical;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * &Eacute;sta clase implementa el procedimiento (comportamiento) para sumar la cantidad por
 * concepto de <i>Cuotas Sindicales Pagadas por el Patr&oacute;n</i> al pago de n&oacute;mina 
 * de los trabajadores. 
 * @author Tikal
 *
 */
public class ProcesadorPercepcionCuotaSindical extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		PercepcionCuotaSindical pCuotaSindical= (PercepcionCuotaSindical) percepcion;
		double cuotaSindical = CalculadoraNomina.calcularCuotaSindical(this.getSueldo(), pCuotaSindical.getPorcentajeCuota());
		pCuotaSindical.setCantidad(cuotaSindical);
		pCuotaSindical.setImporteExento(cuotaSindical);
		this.setTotalAPagar(this.getTotalAPagar() + pCuotaSindical.getCantidad());
	}

}
