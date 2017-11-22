package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.RiesgoPuesto;
import com.tikal.cacao.sat.calculos.PrestacionesDeLey;

/**
 * Esta clase se usa para realizar los c&aacute;lculos de las cuotas de seguridad social
 * que forma parte de las deducciones para el c&aacute;lculo de la N&oacute;mina
 * 
 * @author Tikal
 */
class IMSS {
	
	static double tope3SMG = 80.04 * 3;//SALARIO_MINIMO * 3;
	
	//CUOTAS PATRONALES
	
	static double cuotaInvalidezVidaPatron = 0.0175;
	
	static double cuotaRetiroPatron = 0.02;
	
	static double cuotaCesantiaVejezPatron = 0.0315;
	
	static double cuotaPrestacionesEnDineroPatron = 0.07;
	
	static double cuotaGastosMedicosPatron = 0.0105;
	
	static double cuotaFijaPorTrabajador = 0.204;
	
	static double cuotaAdicionalSobreExcedente = 0.011;
	
	static double cuotaGuarderiasYPrestacionesSociales = 0.01;
	
//	static final double PORCENTAJE_CUOTA_PATRON = cuotaGastosMedicosPatron + cuotaPrestacionesEnDineroPatron +
//			cuotaInvalidezVidaPatron + cuotaGuarderiasYPrestacionesSociales;
	
	
	//CUOTAS OBRERAS
	
	static double cuotaInvalidezVida = 0.00625;
	
	static double cuotaCesantiaVejez = 0.01125; // INVESTIGAR LA FORMA EN QUE SE REALIZA EL PAGO BIMESTRAL
	
	static double cuotaPrestacionesEnDinero = 0.00250; // en caso de ausencias menores a 8 días en un mes se cubre este seguro
	
	static double cuotaGastosMedicos = 0.00375; // en caso de ausencias menores a 8 días en un mes se cubre este seguro
	
	static double porcentajeSobreExcedente = 0.0040;
	
	static final double PORCENTAJE_RETENCION_MENSUAL = cuotaInvalidezVida + cuotaPrestacionesEnDinero + cuotaGastosMedicos;
	
	static final double LIM_PREMIO_PUNTUALIDAD_Y_ASISTENCIA = 0.1;
	/**
	 * Regresa la cuota por concepto de {@code TipoDeduccion.SEGURIDAD_SOCIAL} de acuerdo al 
	 * <tt>sbc</tt> y al <tt>periodo</tt> especificados
	 * @param sbc Salario Base de Cotizaci&oacute;n para calcular la cuota al IMSS
	 * @param periodo periodo de pago que se utiliza para calcular los d&iacute;as cotizados
	 * @param diasAusentismo d&iacute;as que se ausento el trabajador
	 * @param diasIncapacidad d&iacute;as que se ausento el trabajador por una incapacidad expedida por el IMSS
	 * @return la cuota por concepto de {@code TipoDeduccion.SEGURIDAD_SOCIAL}
	 */
    static double calcularCuota(double sbc, PeriodosDePago periodo, int diasAusentismo, int diasIncapacidad) {
    	int diasCotizados = calcularDiasCotizados(periodo)- diasAusentismo - diasIncapacidad;
	    double sbcDiasCotizados = sbc * diasCotizados;
	    double sbcDiasAusentismo = sbc * diasAusentismo;
	    double sbcDiasIncapacidad = sbc * diasIncapacidad;
	    double retDiasAusentismo = 0.0, retDiasIncapacidad = 0.0;
	    double retInicImss = sbcDiasCotizados * (PORCENTAJE_RETENCION_MENSUAL + cuotaCesantiaVejez);
	    if (diasAusentismo > 0 && diasAusentismo < 8) {
	    	retDiasAusentismo = sbcDiasAusentismo * (cuotaPrestacionesEnDinero + cuotaGastosMedicos);
	    	retInicImss += retDiasAusentismo;
	    } else if (diasAusentismo > 8)
	    	; //
	    if (diasIncapacidad > 0) {
	    	retDiasIncapacidad = sbcDiasIncapacidad * cuotaCesantiaVejez;
	    	retInicImss += retDiasIncapacidad;
	    }
	    	
	    double retTotalImss = 0.0;
	    if (sbc > tope3SMG) {
	    	double sbcExcedente = (sbc - tope3SMG) * diasCotizados;
	    	double retExcedente = sbcExcedente * porcentajeSobreExcedente;
	    	retTotalImss = retInicImss + retExcedente;
	    } else {
	    	retTotalImss = retInicImss;
	    }
	    return retTotalImss;
    }
    
    static double calcularCuotasPatronales(double sbc, double primaRiesgo, PeriodosDePago periodo, int diasLaborados) {
    	double porcentajeCuota = 0.0;
    	int diasCotizados = calcularDiasCotizados(periodo);
    	if (diasLaborados < diasCotizados) {
    		if (diasLaborados >= diasCotizados + 7) {
    			porcentajeCuota = cuotaGastosMedicosPatron + cuotaPrestacionesEnDineroPatron;
    		} else {
    			porcentajeCuota = cuotaGastosMedicosPatron + cuotaPrestacionesEnDineroPatron +
    					cuotaInvalidezVidaPatron + cuotaGuarderiasYPrestacionesSociales;
    		}
    		diasCotizados = diasLaborados;
    	}
    	double cuotaImssPatronal = sbc * diasCotizados * porcentajeCuota;
    	if (sbc > tope3SMG) {
    		double sbcExcedente = (sbc - tope3SMG) * diasCotizados;
    		double cuotaExcedente = sbcExcedente * cuotaAdicionalSobreExcedente;
    		cuotaImssPatronal += cuotaExcedente;
    	}
    	cuotaImssPatronal += primaRiesgo * CalculadoraNomina.UMA;
    	return cuotaImssPatronal;
    }
    
    static double calcularCuotasPatronalesRCV(double sbc, int diasLaborados, PeriodosDePago periodo) {
    	//TODO agregar variable que especifique que la diferencia entre los diasCotizados y los diasLaborados
    	//se debe a incapacidades médicas amparadas por el IMSS
    	int diasCotizados = calcularDiasCotizados(periodo);
    	double cuotaRCV = 0.0;
    	if (diasLaborados < diasCotizados) {
    		
    	} else {
    		cuotaRCV = diasCotizados * sbc * (cuotaRetiroPatron + cuotaCesantiaVejezPatron);
    	}
    	return cuotaRCV;
    	
    }
    
    /**
     * &Eacute;ste m&eacute;todo calcula la cuota que debe cubrir el patr&oacute;n
     * mensualmente por concepto del Seguro de Riesgo de Trabajo de un empleado
     * con el salario base de cotizaci&oacute;n especificado.
     * @param sbc el Salario Base de Cotizaci&oacute;n del empleado
     * @param riesgoPuesto representa la clase de prima media correspondiente
     *  al puesto del empleado
     *   
     * @return la cuota por el Seguro de Riesgo de Trabajo
     */
    static double calcularCuotaContraRiesgoDeTrabajo(double sbc, RiesgoPuesto riesgoPuesto) {
    	return sbc * riesgoPuesto.getPorcentaje();
    }
    
    static double calcularAportacionesRCV(double sbc, PeriodosDePago periodo) {
    	int diasCotizados = calcularDiasCotizados(periodo);
	    double sbcDiasCotizados = sbc * diasCotizados;
	    double aportacionesRCV = sbcDiasCotizados * cuotaCesantiaVejez;
	    return aportacionesRCV;
    }
	  
    static double calcularSBC(double sueldo, Date fechaIngreso, PeriodosDePago periodo) {
		int diasDeVacaciones = CalculadoraNomina.calcularDiasDeVacaciones(fechaIngreso);
		double salarioDiario = CalculadoraNomina.calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo);
		double primaVacacionalDiaria = salarioDiario * diasDeVacaciones * PrestacionesDeLey.PORCENTAJE_PRIMA_VACACIONAL / 365/*DIAS_DEL_EJERCICIO*/;
		double aguinaldoDiario = salarioDiario * PrestacionesDeLey.DIAS_DE_AGUINALDO / 365/*DIAS_DEL_EJERCICIO*/;
		double sbc = salarioDiario + primaVacacionalDiaria + aguinaldoDiario;
		return sbc;
    }
    
    static double calcularSBCConPrimaDominical(double sueldo, Date fechaIngreso, PeriodosDePago periodo) {
    	double sbc = calcularSBC(sueldo, fechaIngreso, periodo);
    	double primaDominicalDiaria = CalculadoraNomina.NUM_DOMINGOS_AL_ANIO * CalculadoraNomina.PORCENTAJE_PRIMA_DOMINICAL / 365;
    	return sbc + primaDominicalDiaria;
    }
    
    static double calcularSBCVariable(double percepcionesVariables) {
    	return percepcionesVariables / 61;
    }
    
    static int calcularDiasCotizados(PeriodosDePago periodo) {
    	int diasCotizados;
    	switch(periodo){
	    	case MENSUAL:
	    		diasCotizados = 30;
	    		break;
	    	case QUINCENAL:
	    		diasCotizados = 15;
	    		break;
	    	case DECENAL:
	    		diasCotizados = 10;
	    		break;
	    	case SEMANAL:
	    		diasCotizados = 7;
	    		break;
	    	default:
	    		diasCotizados = 30;
    	}
    	return diasCotizados;
    }
    
    /**
     * &Eacute;ste m&eacute;todo calcula la cantidad que a solicitud entrega el IMSS
     * por concepto de <em>Ayuda para gastos de funeral</em>
     * @return la cantidad por Ayuda para gastos de funeral
     */
    static double calcularAyudaGastosFuneral() {
    	return CalculadoraNomina.SALARIO_MINIMO * 60;
    }
}
