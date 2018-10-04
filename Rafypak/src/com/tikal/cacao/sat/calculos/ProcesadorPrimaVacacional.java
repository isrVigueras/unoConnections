package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorPrimaVacacional extends ProcesadorPercepcion {

	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double primaVacacional = CalculadoraNomina.calcularPrimaVacacional(this.getSueldo(), fechaContratacion);
		
		if (primaVacacional > CalculadoraNomina.PRIMA_VACACIONAL_EXENTA) {
			double primaVacacionalGravada = primaVacacional - CalculadoraNomina.PRIMA_VACACIONAL_EXENTA;
			this.setIngresoGravable(this.getIngresoGravable() + primaVacacionalGravada);
			percepcion.setImporteExento(CalculadoraNomina.PRIMA_VACACIONAL_EXENTA);
			percepcion.setImporteGravado(primaVacacionalGravada);
		} else {
			percepcion.setImporteExento(primaVacacional);
		}
		this.setTotalAPagar(this.getTotalAPagar() + primaVacacional);
	    percepcion.setCantidad(primaVacacional);		
	}

}
