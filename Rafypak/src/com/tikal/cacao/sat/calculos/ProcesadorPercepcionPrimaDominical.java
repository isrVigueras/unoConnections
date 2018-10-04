/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionPrimaDominical;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorPercepcionPrimaDominical extends ProcesadorPercepcion {

	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		this.setTrabajaDomingos(true);
		PercepcionPrimaDominical pPrimaDominical = (PercepcionPrimaDominical) percepcion;
		double[] primaDominical = CalculadoraNomina.calcularPrimaDominical(this.getSueldo(), periodo, pPrimaDominical.getDomingosLaborados());
		pPrimaDominical.setImporteExento(primaDominical[0]);
		pPrimaDominical.setImporteGravado(primaDominical[1]);
		this.setIngresoGravable(this.getIngresoGravable() + primaDominical[1]);
		this.setTotalAPagar(this.getTotalAPagar() + primaDominical[0] + primaDominical[1]);
	}

}
