/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tikal.cacao.dao.TarifasDAO;
import com.tikal.cacao.dao.impl.TarifasDAOImpl;
import com.tikal.cacao.exception.DomingosLaboradosException;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.TipoIncapacidad;
import com.tikal.cacao.sat.calculos.IMSS;
import com.tikal.cacao.sat.calculos.PrestacionesDeLey;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 *
 */
public abstract class CalculadoraNomina {
	
	static double SALARIO_MINIMO = 80.04;
	
	static double UMA = 75.49;
	
	static final int DIAS_DEL_EJERCICIO = 365;
	
	static double AGUINALDO_EXENTO = SALARIO_MINIMO * 30;
	
	static double PRIMA_VACACIONAL_EXENTA = SALARIO_MINIMO * 15;
	
	static double LIM_MONTO_EXENTO_HORAS_EXTRAS = SALARIO_MINIMO * 5;
	
	static final int JORNADA_DIURNA_MAX = 8;
	
	static int MAX_HORAS_EXTRAS_DOBLES = 9;
	
	static final double FACTOR_RLISR = 30.4;
	
	static final double PORCENTAJE_PRIMA_DOMINICAL = 0.25;
	
	static final int NUM_DOMINGOS_AL_ANIO = 52;
	
    static final int DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_INTEGRO = 84;
	
	static final int DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_MITAD = 144; // 84 + 60
	
	static final double LIM_PORCENTAJE_ABONO_INFONACOT = 0.20;
	
	static final double LIM_PORCENTAJE_DEUDAS_CON_PATRON = 0.30;
	
	static final double LIM_PORCENTAJE_DESCUENTO_RENTA = 0.15;
	
	static final double LIM_PORCENTAJE_CUOTAS_SC_CAJA_AHORRO = 0.30;
	
	static final double FACTOR_EXENCION_PAGOS_POR_SEPARACION = 90 * SALARIO_MINIMO;
	
	static TarifasDAO tarifasDAO = new TarifasDAOImpl();
	
	/**
	 * Procedimiento para el c&aacute;lculo del ISR de conformidad con el art&iacute;culo 96 de la LISR
	 * 
	 * @param ingresosGravados es la base para el c&aacute;lculo del impuesto
	 * @param tarifa la tarifa a aplicar para el c&aacute;lculo
	 * @param esRLISR indica si este procedimiento se usa como parte del procedimiento del RLISR.
	 *  si su valor es <tt>true</tt> no se toma en cuenta el Subsidio para el Empleo en el c&aacute;lculo,
	 *  lo opuesto sucede si su valor es <tt>false</tt> 
	 * @return resultado del calculo. Si el resultado > 0 es la cantidad de impuesto a retener 
	 *  pero si el resultado < 0 es la cantidad de subsidio al empleo pagado al contribuyente
	 *
	 */
	public static double calcularISR(double ingresosGravados, TarifaSubsidio tarifa, boolean esRLISR) {
		double resultado = 0.0;
		if (esRLISR) {
			resultado = (ingresosGravados - tarifa.getLimiteInferior1()) * (tarifa.getPorcentajeDelImpuesto() / 100)
					+ tarifa.getCuotaFija();
		} else {
			resultado = (ingresosGravados - tarifa.getLimiteInferior1()) * (tarifa.getPorcentajeDelImpuesto() / 100)
					+ tarifa.getCuotaFija() - tarifa.getSubsidioParaElEmpleo();
		}
		return resultado;
	}
	
	// Nota: Preguntar si es un requerimiento saber cuánto se retiene de ISR al aguinaldo
	//       o solo se calcular el ISR de los ingresos ordinarios junto con el aguinaldo
	/**
	 * Regresa el ISR a cargo del aguinaldo aplicando el procedimiento de la LISR
	 * @param aguinaldoBruto monto que recibe el trabajador por concepto de la gratificaci&oacuten anual
	 * @param ingresoOrdinario monto mensual que recibe normalmente el trabajador 
	 * @param tarifa la tarifa mensual a aplicar sobre el total de ingresos (ingresos ordinarios + aguinaldo)
	 * @param tarifaOrdinaria la tarifa mensual a aplicar sobre los ingresos ordinarios
	 * @return impuesto a cargo del aguinaldo
	 */
	public static double calcularISRAguinaldo(double aguinaldoBruto, double ingresoOrdinario, TarifaSubsidio tarifa, TarifaSubsidio tarifaOrdinaria) {
		double aguinaldoGravado = aguinaldoBruto - AGUINALDO_EXENTO;
		if (aguinaldoGravado < 0) {
			return 0.0; // *Cuando los 30 SMG excedan al aguinaldo a pagar, no se paga impuesto por concepto de aguinaldo
		} else {
			double totalIngresosGravados = aguinaldoGravado + ingresoOrdinario;
			return calcularISR(totalIngresosGravados, tarifa, false) - calcularISR(ingresoOrdinario, tarifaOrdinaria, false);
		}
	}
	
	/**
	 * Regresa el impuesto calculado conforme al procedimiento del RLISR
	 * @param aguinaldoBruto monto que recibe el trabajador por concepto de la gratificaci&oacuten anual
	 * @param ingresoOrdinario monto que recibe el trabajador de forma regular en el mes de que se trate
	 * @param tarifa la tarifa mensual a aplicar para el c&aacute;lculo
	 * @param tarifaOrdinaria a tarifa mensual a aplicar sobre los ingresos ordinarios
	 * @return impuesto a cargo del aguinaldo
	 */
	public static double calcularISRAguinaldoReglamento(double aguinaldoBruto, double ingresoOrdinario, TarifaSubsidio tarifa, TarifaSubsidio tarifaOrdinaria) {
		double aguinaldoGravado = aguinaldoBruto - AGUINALDO_EXENTO;
		double aguinaldoPromedioMensual = aguinaldoGravado / DIAS_DEL_EJERCICIO * FACTOR_RLISR;
		double baseGravable = aguinaldoPromedioMensual + ingresoOrdinario;
		double impuestoBaseGravable = calcularISR(baseGravable, tarifa, true);
		double impuestoIngresoOrdinario = calcularISR(ingresoOrdinario, tarifaOrdinaria, true);
		double isrAguinaldoPromedioMensual = impuestoBaseGravable - impuestoIngresoOrdinario;
		double tasa = isrAguinaldoPromedioMensual / aguinaldoPromedioMensual;
		double isrAguinaldoReglamento = aguinaldoGravado * tasa;
		return isrAguinaldoReglamento;
	}

	
	/**
	 * Regresa la cantidad de aguinaldo bruto que se le debe pagar a un trabajador de acuerdo a su sueldo y
	 * a los d&iacute;as de aguinaldo que otorga la empresa donde labora. Este m&eacute;todo <strong>NO</strong> considera
	 * los ajustes a los d&iacute;as efectivamente laborados por el trabajador
	 * 
	 * @param sueldo monto ordinario que recibe el trabajador cada perido de pago
	 * @param diasDePago d&iacute;as de aguinaldo que otorga la empresa
	 * @param periodo periodo de pago del trabajador (<tt>PeriodosDePago.MENSUAL</tt>, <tt>PeriodosDePago.QUINCENAL</tt>
	 * <tt>PeriodosDePago.DECENAL</tt>, <tt>PeriodosDePago.SEMANAL</tt>)
	 * @return cantidad de aguinaldo bruto a pagar al trabajador
	 */
	public static double calcularAguinaldo(double sueldo, int diasDePago, PeriodosDePago periodo) {
		double aguinaldoBruto = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo)
				* calcularDiasDeAguinaldoAPagar(DIAS_DEL_EJERCICIO, diasDePago);
		return aguinaldoBruto;
	}
	
	
	/**
	 * Regresa la cantidad de aguinaldo bruto que se le debe pagar a un trabajador de acuerdo a su sueldo y
	 * a los d&iacute;as de aguinaldo que otorga la empresa donde labora. Este m&eacute;todo <strong>SI</strong> considera
	 * los ajustes a los d&iacute;as efectivamente laborados por el trabajador
	 *
	 * @param sueldo monto ordinario que recibe el trabajador cada perido de pago
	 * @param diasDePago d&iacute;as de aguinaldo que otorga la empresa
	 * @param periodo periodo de pago del trabajador (<tt>PeriodosDePago.MENSUAL</tt>, 
	 *  <tt>PeriodosDePago.QUINCENAL</tt>, <tt>PeriodosDePago.DECENAL</tt>, <tt>PeriodosDePago.SEMANAL</tt>)
	 * @param faltas cantidad de d&iacute;as que se ausent&oacute; el trabajador debido Faltas injustificadas
	 * @param incEnfGral cantidad de d&iacute;as que se ausent&oacute; el trabajador debido a Incapacidad por
	 *  enfermedad general
	 * @param diasFechaIngreso d&iacute;as transcurridos del inicio del ejercicio al d&iacute;a anterior
	 *  a la fecha de ingreso del trabajador
	 * 
	 * @return cantidad de aguinaldo bruto a pagar al trabajador
	 */
	public static double calcularAguinaldo(double sueldo, int diasDePago, PeriodosDePago periodo, int faltas, int incEnfGral, int diasFechaIngreso) {
		int diasParaElPagoDeAguinaldo = calcularDiasParaElPagoDelAguinaldo(faltas, incEnfGral, diasFechaIngreso);
		double aguinaldoBruto = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo)
				* calcularDiasDeAguinaldoAPagar(diasParaElPagoDeAguinaldo, diasDePago);
		return aguinaldoBruto;
	}

	public static double calcularPTU(double monto ) {
		//TODO
		return 0.0;
	}
	
	/**
	 * Regresa un arreglo cuyos elementos son: <em>el monto exento, el monto gravable, el monto por horas triples,
	 * el n&uacute;mero de horas extras pagadas al doble y el n&uacute;mero de horas extras pagadas al triple</em>
	 * del pago de las horas extras que labor&oacute; un trabajador durante el periodo de pago.
	 * 
	 * @param salario monto diario que recibe el trabajador
	 * @param arrHorasExtras arreglo con la cantidad de horas extras laboradas en el periodo de pago
	 * @return un arreglo con  elementos que son: el monto exento (<strong>arreglo[0]</strong>), el 
	 *  monto gravado (<strong>arreglo[1]</strong>) del pago de horas extras, el monto de por las horas extras pagadas
	 *  al triple (<strong>arreglo[2]</strong>), el n&uacute;mero de horas extras pagadas al doble (<strong>arreglo[3]</strong>),
	 *  el n&uacute;mero de horas extras pagadas al triple (<strong>arreglo[4]</strong>)
	 */
	//TODO agregar en la vista un campo que indique si parte de las horas extra se pagan igual
	public static double[] calcularPagoDeHorasExtras(double salario, int arrHorasExtras[]) {
		double cuotaPorHora = salario / JORNADA_DIURNA_MAX;
		double cuotaPorHoraDoble = cuotaPorHora * 2;
		double cuotaPorHoraTriple = cuotaPorHora * 3;
		double montoHorasExtrasTriples = 0.0; // si existe monto se utiliza para calcular el SBC

		int horasExtrasDobles = 0;
		int horasExtrasTriples = 0;
		int totalHorasExtrasDobles = 0;
		int totalHorasExtrasTriples = 0;
		double montoExentoHorasExtras = 0.0;
		double montoGravableHorasExtras = 0.0;
		double totalMontoExentoHorasExtras = 0.0;
		double totalMontoGravableHorasExtras = 0.0;
		double totalMontoHorasExtrasTriples = 0.0;
		
		for (int horasExtras : arrHorasExtras) {
			if (horasExtras > MAX_HORAS_EXTRAS_DOBLES) {
				horasExtrasTriples = horasExtras - 9;
				horasExtrasDobles = 9;
			} else {
				horasExtrasDobles = horasExtras;
			}

			if (salario == SALARIO_MINIMO & horasExtras <= MAX_HORAS_EXTRAS_DOBLES) {
				montoExentoHorasExtras = cuotaPorHoraDoble * horasExtras;
			} else if (salario == SALARIO_MINIMO & horasExtras > MAX_HORAS_EXTRAS_DOBLES) {
				montoExentoHorasExtras = cuotaPorHoraDoble * MAX_HORAS_EXTRAS_DOBLES;
				montoHorasExtrasTriples = cuotaPorHoraTriple * horasExtrasTriples;
				montoGravableHorasExtras = montoHorasExtrasTriples;
			} else if (salario > SALARIO_MINIMO) {
				if (horasExtras <= MAX_HORAS_EXTRAS_DOBLES){
					montoExentoHorasExtras = cuotaPorHoraDoble * horasExtras / 2;
				} else {
					montoExentoHorasExtras = cuotaPorHoraDoble * MAX_HORAS_EXTRAS_DOBLES / 2;
				}
				if (montoExentoHorasExtras > LIM_MONTO_EXENTO_HORAS_EXTRAS) {
					montoHorasExtrasTriples = cuotaPorHoraTriple * horasExtrasTriples;
					montoGravableHorasExtras = montoExentoHorasExtras
							+ (montoExentoHorasExtras - LIM_MONTO_EXENTO_HORAS_EXTRAS)
							+ montoHorasExtrasTriples;
				} else {
					montoHorasExtrasTriples = cuotaPorHoraTriple * horasExtrasTriples;
					montoGravableHorasExtras = montoExentoHorasExtras + montoHorasExtrasTriples;
				}
			}
			totalHorasExtrasDobles += horasExtrasDobles;
			totalHorasExtrasTriples += horasExtrasTriples;
			totalMontoExentoHorasExtras += montoExentoHorasExtras;
			totalMontoGravableHorasExtras += montoGravableHorasExtras;
			totalMontoHorasExtrasTriples += montoHorasExtrasTriples;
			horasExtrasTriples = 0;
		}
		return new double[] {  totalMontoExentoHorasExtras, totalMontoGravableHorasExtras, totalMontoHorasExtrasTriples, (double)totalHorasExtrasDobles, (double)totalHorasExtrasTriples};
	}
	
	
	/**
	 * Regresa el monto que recibe un trabajador por concepto de Prima Vacacional
	 * @param sueldo ingreso mensual que recibe el trabajador
	 * @param fechaDeIngreso fecha de ingreso del trabajador a la empresa con formato <tt>dd/MMM/YYYY</tt>
	 * @return monto de Prima Vacacional a pagar al trabajador 
	 */
	public static double calcularPrimaVacacional(double sueldo, Date fechaDeIngreso) {
		double salarioDiario = sueldo / 30;
		double primaVacacional = 0.0;
		
		primaVacacional = salarioDiario * calcularDiasDeVacaciones(fechaDeIngreso) * PrestacionesDeLey.PORCENTAJE_PRIMA_VACACIONAL;
		return primaVacacional;
	}
	
	/**
	 * Regresa el monto que recibe un trabajador por concepto de Prima Vacacional
	 * @param sueldo ingreso mensual que recibe el trabajador
	 * @param fechaDeIngreso cadena que representa la fecha de ingreso del trabajador a la 
	 *  empresa con formato <tt>dd/MMM/YYYY</tt>
	 * @return monto de Prima Vacacional a pagar al trabajador
	 * @throws ParseException si la <tt>fechaDeIngreso</tt> no esta en el formato especificado 
	 */
	public static double calcularPrimaVacacional(double sueldo, String fechaDeIngreso) throws ParseException {
		double salarioDiario = sueldo / 30;
		double primaVacacional = 0.0;
		Date fechaIngreso = Util.obtenerFecha(fechaDeIngreso);

		primaVacacional = salarioDiario * calcularDiasDeVacaciones(fechaIngreso) * PrestacionesDeLey.PORCENTAJE_PRIMA_VACACIONAL;
		return primaVacacional;
	}
	
	public static int calcularDiasDeVacaciones(Date fechaIngreso) {
		int diasDeVacaciones = PrestacionesDeLey.DIAS_DE_VACACIONES_POR_LEY;
		int years = getAniosDif(fechaIngreso);
		if (years == 1) {
			// no se aumentan días
		} else if (years == 2) {
			diasDeVacaciones =+ 2;
		} else if (years == 3) {
			diasDeVacaciones =+ 4;
		} else if (years == 4) {
			diasDeVacaciones =+ 6;
		} else if (years >= 5 & years <= 9) {
			diasDeVacaciones =+ 8;
		} else if (years >= 10 & years <= 14) {
			diasDeVacaciones =+ 10;
		} else if (years >= 15 & years <= 19) {
			diasDeVacaciones =+ 12;
		} else if (years >= 20 & years <= 24) {
			diasDeVacaciones =+ 14;
		} else if (years >= 25 & years <= 29) {
			diasDeVacaciones =+ 16;
		}
		
		return diasDeVacaciones;
	}
	
	/**
	 * Regresa la cuota diaria sobre el salario fijo que recibe un trabajador
	 * 
	 * @param sueldo monto ordinario que recibe el trabajador cada perido de pago
	 * @param periodo periodo de pago del trabajador (<tt>PeriodosDePago.MENSUAL</tt>, 
	 *  <tt>PeriodosDePago.QUINCENAL</tt>, <tt>PeriodosDePago.DECENAL</tt>, <tt>PeriodosDePago.SEMANAL</tt>)
	 * @return salario diario del trabajador
	 */
	public static double calcularCuotaDiariaSobreSalarioFijo(double sueldo, PeriodosDePago periodo) {
		switch (periodo) {
			case MENSUAL:
				return sueldo/30;
			case QUINCENAL:
				return sueldo/15;
			case DECENAL:
				return sueldo/10;
			case SEMANAL:
				return sueldo/7;
			default:
				return sueldo/30;
		}
	}
	
	
	/**
	 * Regresa la cantidad por Premio por puntualidad o Premio por asistencia que
	 * integra al Salario Base de Cotizaci&oacute;n (SBC). Si el Premio del que se trate
	 * no integra al SBC, éste método regresa 0.0.
	 * @param premio monto por Premio por puntualidad o por Premio por asistencia
	 * @param sbc el Salario Base de Cotizaci&oacute;n (SBC)
	 * @param periodo periodo de pago del trabajador
	 * @return el excedente del 10% del SBC del Premio o 0.0 si no hay excedente
	 */
	public static double integrarPremioASBC(double premio, double sbc, PeriodosDePago periodo) {
		double premioDiario = calcularCuotaDiariaSobreSalarioFijo(premio, periodo);
		double sbcDiezPct = sbc * IMSS.LIM_PREMIO_PUNTUALIDAD_Y_ASISTENCIA;
		if (premioDiario > sbcDiezPct)
			return premioDiario - sbcDiezPct;
		return 0.0;
	}
	
	
	/**
	 * Regresa el descuento por concepto de  <strong>Cuota sindical</strong> que se aplica al sueldo de un trabajador 
	 * @param sueldo sueldo del trabajador
	 * @param porcentaje porcentaje para aplicarse al <tt>sueldo</tt> especificado
	 * @return la cuota sindical a descontar
	 */
	public static double calcularCuotaSindical(double sueldo, double porcentaje) {
		if (porcentaje >= 1.0) {
			porcentaje /= 100;
		}
		return sueldo * porcentaje;
	}
	
	public static double calcularSubsidioPorIncapacidad(double sueldo, TipoIncapacidad incapacidad) {
		//TODO
		double subsidio = 0.0;
		switch (incapacidad) {
		case ENFERMEDAD_EN_GENERAL:
			
			break;
		case MATERNIDAD:
			break;
		case RIESGO_DE_TRABAJO:
			break;
		default:
			break;
		}
		
		return subsidio;
	}
	
	public static double calcularSubsidioPorIncapacidadConSeguro(double sueldo, TipoIncapacidad incapacidad) {
		// TODO
		
		return 0.0;
	}
	
	
	/**
	 * Regresa la Prima Dominical que se debe pagar al trabajador en el periodo especificado
	 * @param sueldo monto que recibe el trabajador cada periodo
	 * @param periodo periodo de pago del trabajador
	 * @param domingosLaborados cantidad de d&iacute;as domingo laborados por el trabajador durante el periodo
	 * @return el monto exento y gravado de la Prima Dominical
	 * @throws DomingosLaboradosException 
	 */
	public static double[] calcularPrimaDominical(double sueldo, PeriodosDePago periodo, int domingosLaborados) {
		if (domingosLaborados > 5) {
			throw new DomingosLaboradosException("No es posible laborar más de 5 domingos en un mes");
		}
		boolean primaMayorASM = false;
		
		double primaDominicalTotal = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo) * PORCENTAJE_PRIMA_DOMINICAL;
		if (primaDominicalTotal > SALARIO_MINIMO) 
			primaMayorASM = true;
		
		double[] primaDominical = new double[2];
		switch (periodo) {
			case SEMANAL:
				primaDominical = obtenerPrimaDominicalEyG(primaMayorASM, domingosLaborados, primaDominicalTotal);
				break; // como el periodo de pago es SEMANAL no es necesario modificar la prima dominical
			case DECENAL:
				primaDominical = obtenerPrimaDominicalEyG(primaMayorASM, domingosLaborados, primaDominicalTotal);
				break;
			case QUINCENAL:
				primaDominical = obtenerPrimaDominicalEyG(primaMayorASM, domingosLaborados, primaDominicalTotal);
				break;
			case MENSUAL:
				primaDominical = obtenerPrimaDominicalEyG(primaMayorASM, domingosLaborados, primaDominicalTotal);
				break;
		}
		return primaDominical;
	}
	
	
	public static double[] calcularPagosPorSeparacion(double pagosPorSeparacion, double primaAntiguedad, double indemnizaciones, double seguroDeRetiro) {
		//TODO
		double ingresoPorSepararcion = pagosPorSeparacion + primaAntiguedad + indemnizaciones + seguroDeRetiro;
		return new double[]{};
	}
	
	/**
	 * &Eacute;ste m&eacute;todo calcula la cantidad que debe recibir un empleado cuando termina su
	 * relaci&oacute;n laboral.
	 * @param antiguedad n&uacutemero de meses que ha durado la relaci&oacute;n laboral
	 * @param sueldo sueldo del empleado
	 * @param periodo periodicidad con que se realiza el pago al empleado
	 * @return el monto por la prima de antiguedad
	 */
	public static double calcularPrimaAntiguedad(int antiguedad, double sueldo, PeriodosDePago periodo) {
		double primaAntiguedad = 0.0;
		double salario = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo);
		int diasPorAnio = antiguedad * 12; //TODO completar con el número de meses
		if (salario <= 2 * SALARIO_MINIMO)
			primaAntiguedad = salario * diasPorAnio;
		else 
			primaAntiguedad = 2 * SALARIO_MINIMO * diasPorAnio;
		return primaAntiguedad;
	}
	
	/**
	 * &Eacute;ste m&eacute;todo calcula el descuento que se efectua al pago del trabajador
	 * por concepto de <strong>Descuento por incapacidad</strong>. Dependiendo de la incapacidad
	 * que presente el trabajador ser&aacute; la cantidad que dejar&aacute de percibir.
	 * @param ingreso cantidad que percibe el trabajador por cada periodo
	 * @param numDias cantidad de d&iacute;as que se ausent&oacute; el trabajador por su incapacidad
	 * @param periodo periodo de pago del trabajador
	 * @param incapacidad indica cual es la incapacidad que presenta el trabajador
	 * @return el descuento al ingreso del trabajador por la incapacidad
	 */
	public static double calcularDescuentoPorIncapacidad(double ingreso, int numDias, PeriodosDePago periodo, TipoIncapacidad incapacidad) {
		double salarioDiario = calcularCuotaDiariaSobreSalarioFijo(ingreso, periodo);
		double descuentoEG = 0.0;
		double descuentoMatMitad = 0.0;
		double descuentoMatTotal = 0.0;
		double descuentoRT = 0.0;
		switch (incapacidad) {
		case ENFERMEDAD_EN_GENERAL:
			descuentoEG = salarioDiario * numDias;
			return descuentoEG;
		case MATERNIDAD:
			if (numDias == DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_INTEGRO)
				return 0.0; // no se descuenta si la trabajadora no recibe un subsidio por este concepto por parte del IMMS
			else if (numDias <= DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_MITAD) {
				descuentoMatMitad = salarioDiario * (numDias - DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_INTEGRO) / 2;
				return descuentoMatMitad;
			}else if (numDias > DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_MITAD){
				descuentoMatTotal = salarioDiario * (numDias - DIAS_INCAPACIDAD_MATERNIDAD_SALARIO_MITAD) + descuentoMatMitad; 
				return descuentoMatTotal;
			}
			break;
		case RIESGO_DE_TRABAJO:
			// este descuento debe retribuirse como una percepción por Subsidio por incapacidad
			descuentoRT = salarioDiario * numDias;
			return descuentoRT;
		default:
			break;
		}
		return descuentoEG;
	}
	
	
	/**
	 * Regresa la cantidad que se descuenta mensualmente del sueldo del trabajador por concepto de 
	 * <strong>Pago de abonos INFONACOT</strong>
	 * @param sueldo sueldo mensual del trabajador
	 * @param porcentaje porcentaje que se aplica al sueldo
	 * @return descuento por <strong>Pago de abonos INFONACOT</strong>
	 */
	public static double calcularAbonoINFONACOT(double sueldo, double porcentaje) {
		//TODO agregar el plazo para pagar el crédito
		if (porcentaje <= LIM_PORCENTAJE_ABONO_INFONACOT) {
			return sueldo * porcentaje;
		}
		return 0.0;
		//TODO throw new PorcentajeDescuentoException("El porcentaje para descontar del sueldo es mayor al permitido por la ley");
	}
	
	/**
	 * Regresa la cantidad que se debe descontar al pago del trabajador por concepto de 
	 * <strong>pensi&oacute;n alimenticia</strong>
	 * @param ingreso total de ingresos que recibe un trabajador
	 * @param porcentaje porcentaje que se descuenta del total de ingresos del trabajador por concepto de 
	 * pensi&oacute;n alimenticia
	 * @return la cantidad que se debe descontar por concepto de <strong>pensi&oacute;n alimenticia</strong>
	 */
	public static double calcularPensionAlimenticia(double ingreso, double porcentaje) {
		if (porcentaje >= 1.0) {
			porcentaje /= 100;
		}
		return ingreso * porcentaje;
	}
	
	/**
	 * Regresa la cantidad que se deduce del pago del trabajador por concepto de 
	 * <strong>Ausencia (Ausentismo)</strong>
	 * @param sueldo sueldo del trabajador
	 * @param dias n&uacute;mero de d&iacute;as que se ausento el trabajador
	 * @param trabajaSeisDias indica si el trabajador labora o no 6 d&iacute;as
	 *        a la semana
	 * @param periodo periodo de pago del trabajador
	 * @return la cantidad que se deduce del pago del trabajador
	 */
	public static double calcularDescuentoPorAusencia(double sueldo, int dias, boolean trabajaSeisDias, PeriodosDePago periodo) {
		double proporcionDiaDescanso = 0.0;
		double salarioDiario = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo);
		if (trabajaSeisDias) {
			proporcionDiaDescanso = salarioDiario * 1 / 6;
		} else {
			proporcionDiaDescanso = salarioDiario * 2 / 5;
		}
		return (salarioDiario + proporcionDiaDescanso) * dias;
		
	}
	
	/**
	 * Verifica si el descuento por concepto de <strong>Cuotas para la constituc&iacute;n y fomento de 
	 * sociedades cooperativas y cajas de ahorro</strong> que se aplica al sueldo de un trabajador esta
	 * dentro de los l&iacute;mites establecidos por la Ley Federal del Trabajo
	 * @param sueldo sueldo que recibe el trabajador
	 * @param descuento cantidad que se deduce del sueldo del trabajador por el concepto arriba mencionado
	 * @param periodo periodo de pago del trabajador
	 * @return {@code true} si el <tt>descuento</tt> esta dentro de los l&iacute;mites, {@code false} en otro caso
	 */
	public static boolean verificarCuotaSCyCajasDeAhorro(double sueldo, double descuento, PeriodosDePago periodo) {
		double salarioMinimoPorPeriodo = calcularSalarioMinimoPorPeriodo(periodo);
		double limiteCuotaSCyCajasDeAhorro = (sueldo - salarioMinimoPorPeriodo) * LIM_PORCENTAJE_CUOTAS_SC_CAJA_AHORRO;
		if (descuento <= limiteCuotaSCyCajasDeAhorro) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica si el descuento por concepto de <strong>Renta</strong> que se aplica al sueldo de un trabajador 
	 * esta dentro de los l&iacute;mites establecidos por la Ley Federal del Trabajo
	 * @param sueldo sueldo que recibe el trabajador
	 * @param descuento cantidad que se deduce del sueldo del trabajador por concepto de <strong>Renta</strong>
	 * @param periodo periodo de pago del trabajador
	 * @return {@code true} si el <tt>descuento</tt> esta dentro de los l&iacute;mites, {@code false} en otro caso
	 */
	public static boolean verificarDescuentoRenta(double sueldo, double descuento, PeriodosDePago periodo) {
		double salarioMinimoPorPeriodo = calcularSalarioMinimoPorPeriodo(periodo);
		double limiteDescuentoRenta = (sueldo - salarioMinimoPorPeriodo) *  LIM_PORCENTAJE_DESCUENTO_RENTA;
		if (descuento <= limiteDescuentoRenta) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean verificarDeudasAlPatron(double sueldo ,double monto, PeriodosDePago periodo) {
		if (monto <= calcularMaxDescuentoDeuda(sueldo, periodo)) {
			return true;
		}
		return false;
	}
	
	public static double calcularMaxDescuentoDeuda(double sueldo, PeriodosDePago periodo) {
		double salarioMinimoPorPeriodo = calcularSalarioMinimoPorPeriodo(periodo);
		double limiteDeudaConPatron = (sueldo - salarioMinimoPorPeriodo) * LIM_PORCENTAJE_DEUDAS_CON_PATRON;
		return limiteDeudaConPatron;
	}
	
	/**
	 * &Eacute;ste m&eacute;todo crea una lista con los montos sugeridos para descontar al trabajador por
	 * concepto de <em>Deudas contra&iacute;das con el patr&oacute;n</em>, tomando en cuenta el monto total de
	 * la deuda y el descuento m&aacute;ximo permitido cada periodo de pago. 
	 * @param maxDescuento cantidad m&aacute;xima que se puede descontar del sueldo del trabajador cada 
	 *  periodo de pago
	 * @param monto monto total exigible al trabajador 
	 * @return una lista cuyos elementos son las cantidades a pagar para saldar la deuda con el patr&oacute;n
	 */
	public static List<Double> generarPropuestaPagosDeuda(double maxDescuento, double monto) {
		List<Double> lista = new ArrayList<>();
		double descuentoPropuesto = Util.truncar(maxDescuento); // puede servir como el descuento acordado entre el patrón y el trabajador
		int numeroDePagos = (int) (monto / descuentoPropuesto);
		double ultimoDescuento = monto - descuentoPropuesto * numeroDePagos;
		for (int i = 0; i < numeroDePagos; i++) {
			lista.add(descuentoPropuesto);
		}
		lista.add(ultimoDescuento);
		return lista;
	}//TODO convertir esta lista de doubles en una lista de objetos Deduccion

	/**
	 * &Eacute;ste m&eacute;todo regresa la cantidad de años que trascurrieron desde la fecha indicada
	 * hasta la fecha actual
	 * @param fechaContratacion fecha de contrataci&oacute;n del empleado
	 * @return el n&uacute;mero de años entre la fecha indicaca y la fecha actual
	 */
	static int obtenerAniosServicio(Date fechaContratacion) {
		int years = 0, modYears = 0;
		int meses = 0;
		
		Calendar presente = Calendar.getInstance();
		Calendar cFechaContratacion = Calendar.getInstance();
		cFechaContratacion.setTime(fechaContratacion);
		
		while (cFechaContratacion.before(presente)) {
			cFechaContratacion.add(Calendar.MONTH, 1);
			if (cFechaContratacion.before(presente))
				meses++;
		}
		years = meses/12;
		modYears = meses%12;
		if (modYears >= 6)
			years++;
		return years;
	}
	
	/**
	 * 
	 * 
	 * @param faltas cantidad de d&iacute;as que se ausent&oacute; el trabajador debido Faltas injustificadas
	 * @param incEnfGral cantidad de d&iacute;as que se ausent&oacute; el trabajador debido a Incapacidad por
	 *  enfermedad general
	 * @param diasFechaIngreso d&iacute;as transcurridos del inicio del ejercicio al d&iacute;a anterior
	 *  a la fecha de ingreso del trabajador
	 * @return d&iacute;as para el pago del aguinaldo
	 */
	static int calcularDiasParaElPagoDelAguinaldo(int faltas, int incEnfGral, int diasFechaIngreso) {
		return DIAS_DEL_EJERCICIO - faltas - incEnfGral - diasFechaIngreso;
	}
	
	static void validarDescuentoAlSalario() {
    
	}
	
	static double[] obtenerPrimaDominicalEyG(boolean primaMayorASM, int domingosLaborados, double primaDominicalTotal) {
		double primaDominicalExenta = 0.0;
		double primaDominicalGravada = 0.0;
		if(primaMayorASM){
			primaDominicalExenta = SALARIO_MINIMO * domingosLaborados;
			primaDominicalGravada = primaDominicalTotal - primaDominicalExenta;
		} else {
			primaDominicalExenta = primaDominicalTotal * domingosLaborados;
		}
		return new double[]{primaDominicalExenta, primaDominicalGravada};
	}
	
	/**
	 * 
	 * @param diasBase d&iacute;as base para el c&aacute;lculo del aguinaldo
	 * @param diasDeAguinaldo d&iacute;as de aguinaldo que otorga la empresa
	 * @return proporci&oacute;n de los d&iacute;as de aguinaldo a pagar al trabajador
	 */
	static double calcularDiasDeAguinaldoAPagar(int diasBase, int diasDeAguinaldo) {
		double proporcionDiasLaborados = (double)diasBase/DIAS_DEL_EJERCICIO;
		return diasDeAguinaldo * proporcionDiasLaborados;
	}
	
	static double calcularSalarioMinimoPorPeriodo(PeriodosDePago periodo) {
		// variable para guardar el número de días a elevar el salario mínimo
		int factorPeriodo = 1;
				
		switch (periodo) {
			case MENSUAL:
				factorPeriodo = 30;
				break;
			case QUINCENAL:
				factorPeriodo = 15;
				break;
			case DECENAL:
				factorPeriodo = 10;
				break;
			case SEMANAL:
				factorPeriodo = 7;
			default:
				break;
		}
		
		return SALARIO_MINIMO * factorPeriodo;
	}
	
	static double aplicarDeduccion(Deduccion deduccion, double descuento) {
		deduccion.setDescuento(descuento);
		return deduccion.getDescuento();
	}
	
	static TarifaSubsidio obtenerTarifa(double monto, PeriodosDePago periodo) {
		Class<? extends TarifaSubsidio> claseT = Util.obtenerClaseTarifaSubsidio(periodo);
		TarifaSubsidio tarifa = tarifasDAO.queryT(monto, claseT);
		return tarifa;
	}
	
	private static int getAniosDif(Date fechaIngreso) {
		Calendar presente = Calendar.getInstance();
	    Calendar pasado = Calendar.getInstance();
	    pasado.setTime(fechaIngreso);

	    int anios = 0;

	    while (pasado.before(presente)) {
	        pasado.add(Calendar.YEAR, 1);
	        if (pasado.before(presente)) {
	            anios++;
	        }
	    } return anios;
	}

}
