/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import com.tikal.cacao.model.ModalidadDescuento;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 *
 */
public abstract class Infonavit {
	
	static final double PORCENTAJE_APORTACION = 0.05;
	
	static final int DIAS_BIMESTRE = 61;
	
	static final int MESES_DE_BIMESTRE = 2;
	
	/**
	 * 
	 * @param sba
	 * @param periodo
	 * @return
	 */
	public static double calcularAportaciones(double sba, PeriodosDePago periodo, int diasPagados) {
		int diasCotizados = IMSS.calcularDiasCotizados(periodo);
		if (diasPagados != diasCotizados)
			diasCotizados = diasPagados;
		double aportacion = sba * diasCotizados * PORCENTAJE_APORTACION;
		return Util.redondear(aportacion);
	}
	
	/**
	 * 
	 * @param sba
	 * @param periodo
	 * @param modDescuento
	 * @param valorCredito
	 * @return
	 */
	static double calcularPagoCredito(double sba, PeriodosDePago periodo, ModalidadDescuento modDescuento, double valorCredito) {
		int diasNomina = IMSS.calcularDiasCotizados(periodo);
		double retInfonavit = 0.0;
		switch(modDescuento) {
		case CUOTA_FIJA:
			retInfonavit = valorCredito * MESES_DE_BIMESTRE / DIAS_BIMESTRE * diasNomina;
			break;
		case PORCENTAJE:
			retInfonavit = sba * valorCredito * diasNomina;
			break;
		case VECES_SMGV:
			retInfonavit = 80.04 /*SALARIO_MINIMO*/* valorCredito * MESES_DE_BIMESTRE / DIAS_BIMESTRE * diasNomina;
			break;
		case VECES_UMA:
			break;
		}
		return retInfonavit;
	}
}
