/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionPrimaSeguroDeVida;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.clausulasEnum.RiesgosAmparadosPorSeguroDeVida;

/**
 * @author Tikal
 *
 */
public class ProcesadorPercepcionPrimaSeguroDeVida extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		PercepcionPrimaSeguroDeVida pPrima = (PercepcionPrimaSeguroDeVida) percepcion;
		double cantidad = percepcion.getCantidad();
		switch (pPrima.getContratante()) {
			case TRABAJADOR:
				pPrima.setImporteExento(cantidad);
				this.setTotalAPagar(this.getTotalAPagar() + cantidad);
				break;
			case EMPLEADOR:
			case OTRO:
				switchRiesgoYBeneficiarios(pPrima);
				break;
		}

	}
	
	private void switchRiesgoYBeneficiarios(PercepcionPrimaSeguroDeVida p) {
		double cantidad = p.getCantidad();
		switch (p.getRiesgosAmparados()) {
			case FALLECIMIENTO:
				switch (p.getBeneficiarios()) {
					case CONYUGE:
					case CONCUBINA_O_CONCUBINO:
					case HIJOS:
					case PADRES:
					case NIETOS:
					case ABUELOS:
						p.setImporteExento(cantidad);
						this.setTotalAPagar(this.getTotalAPagar() + cantidad);
						break;
					case OTROS:
						p.setImporteGravado(cantidad);
						this.setIngresoGravable(this.getIngresoGravable() + cantidad);
						this.setTotalAPagar(this.getTotalAPagar() + cantidad);
						break;
					default:
						break;
				}
				break;
			case INVALIDEZ:
			case PERDIDAS_ORGANICAS:
			case INCAPACIDAD:
				p.setImporteExento(cantidad);
				this.setTotalAPagar(this.getTotalAPagar() + cantidad);
				break;
		}
		
	}

}
