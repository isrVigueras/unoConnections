/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
@Component
public class ProcesadorAguinaldo extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.percepciones.ProcesadorPercepcion#ejecutar()
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double aguinaldoBruto = CalculadoraNomina.calcularAguinaldo(this.getSueldo(),
				PrestacionesDeLey.DIAS_DE_AGUINALDO, periodo);
		if (aguinaldoBruto >= CalculadoraNomina.AGUINALDO_EXENTO) {
			percepcion.setImporteGravado(aguinaldoBruto - CalculadoraNomina.AGUINALDO_EXENTO);
			percepcion.setImporteExento(CalculadoraNomina.AGUINALDO_EXENTO);
		} else {
			percepcion.setImporteExento(aguinaldoBruto);
		}
		this.setIngresoGravable(this.getIngresoGravable() + aguinaldoBruto - CalculadoraNomina.AGUINALDO_EXENTO);
		percepcion.setCantidad(aguinaldoBruto);
		this.setTotalAPagar(this.getTotalAPagar() + percepcion.getCantidad());

	}

}
