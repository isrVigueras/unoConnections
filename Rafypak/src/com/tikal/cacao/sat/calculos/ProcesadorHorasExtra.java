/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorHorasExtra extends ProcesadorPercepcion {

	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		PercepcionHorasExtra pHorasExtra = (PercepcionHorasExtra) percepcion;
		
		double salario = CalculadoraNomina.calcularCuotaDiariaSobreSalarioFijo(this.getSueldo(), periodo);
		double horasExtras[] = CalculadoraNomina.calcularPagoDeHorasExtras(salario, pHorasExtra.getHorasExtra());
		
		pHorasExtra.setImporteExento(horasExtras[0]);
		pHorasExtra.setImporteGravado(horasExtras[1]);
		pHorasExtra.setMontoHorasTriples(horasExtras[2]);
		pHorasExtra.setHorasDobles((int)horasExtras[3]);
		pHorasExtra.setHorasTriples((int)horasExtras[4]);
		
		this.setIngresoGravable(this.getIngresoGravable() + horasExtras[1]);
		this.setIngresoCotizable(this.getIngresoCotizable() + horasExtras[2]);
		this.setTotalAPagar(this.getTotalAPagar() + horasExtras[0] + horasExtras[1]);
		
		this.setTiempoExtraParaSBC(this.getTiempoExtraParaSBC() + horasExtras[2]);
		pHorasExtra.setCantidad(horasExtras[0] + horasExtras[1]);		
	}

}
