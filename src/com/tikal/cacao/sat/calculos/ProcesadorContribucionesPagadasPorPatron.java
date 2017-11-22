package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;

public class ProcesadorContribucionesPagadasPorPatron extends ProcesadorPercepcion {

	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		/* de ejecutarse este caso, el ingresoGravable debe ser el mismo
//		   que el ingresoGravable que se utiliza para calcular el ISR
 * 		   Implementar la interface Comparator<T> para ordenar las Percepciones
 * 		   y colocar la Percepción correspondiente a este procesador en la última
 *         posición
//		*/
		double monto = CalculadoraNomina.calcularISR(this.getIngresoGravable(), this.getTarifa(), false);
		percepcion.setCantidad(monto);
		percepcion.setImporteExento(monto);
        this.setTotalAPagar(this.getTotalAPagar() + monto);   //totalAPagar += percepcion.getCantidad();
		
	}

}
