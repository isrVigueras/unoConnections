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
public class ProcesadorSueldos extends ProcesadorPercepcion {
	
	/**
	 * @see com.tikal.cacao.sat.calculos.percepciones.ProcesadorPercepcion#ejecutar()
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double cantidad = percepcion.getCantidad();
		percepcion.setImporteGravado(cantidad);
		this.setSueldo(cantidad);
		this.setIngresoGravable(this.getIngresoGravable() + this.getSueldo());
		this.setIngresoCotizable(this.getIngresoCotizable() + this.getSueldo());
		this.setTotalAPagar(this.getTotalAPagar() + this.getSueldo());
		//pago.setSalarioDiario(calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo));

	}

}
