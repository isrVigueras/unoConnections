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
public class ProcesadorPercepcionCuotasIMSSPagadasPatron extends ProcesadorPercepcion {

	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		boolean b1 = this.getUltimoSBC() == 0.0;
		boolean b2 = this.getSBC() == 0.0;
		if (b1 && b2) {
			this.setSBC(IMSS.calcularSBC(this.getSueldo(), fechaContratacion, periodo)
					+ this.getMontoPremioASBC());
		} else if (b2) {
			this.setSBC(this.getUltimoSBC());
		}
		double cuotaPagadaPorPatron = IMSS.calcularCuota(this.getSBC(), periodo, this.getDiasAusentismo(), this.getDiasIncapacidad());
		this.setTotalAPagar(this.getTotalAPagar() + cuotaPagadaPorPatron);
		percepcion.setCantidad(cuotaPagadaPorPatron);
		percepcion.setImporteExento(cuotaPagadaPorPatron);
	}

}
