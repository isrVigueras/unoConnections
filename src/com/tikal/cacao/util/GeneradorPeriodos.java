package com.tikal.cacao.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.tikal.cacao.model.Periodo;
import com.tikal.cacao.model.PeriodosDePago;



/**
 * @author Tikal
 *
 */
public abstract class GeneradorPeriodos {
	static long unSegundo = 10L;
	
	static long unaHora = 3_600_000L;
	
	static long unDia = 86_400_000L;
	
	static long unaSemana = 604_800_000L;
	
	static long periodoDe8Dias =  691_200_000L;
	
	static long periodoDe10Dias = 864_000_000L;
	
	static long periodoDe11Dias = 950_400_000L;
	
	static long periodoDe13Dias = 1_123_200_000L;
	
	static long periodoDe15Dias = 1_296_000_000L;
	
	static long periodoDe16Dias = 1_382_400_000L;
	
	static long periodoDe28Dias = 2_419_200_000L;
	
	static long periodoDe29Dias = 2_505_600_000L;
	
	static long periodoDe30Dias = 2_592_000_000L;
	
	static long periodoDe31Dias = 2_678_400_000L;
	
	static long primerDia2017 = 1_483_250_400_000L;

	public static List<Periodo> generarPeriodos(Date fecha, PeriodosDePago periodicidad, int year) {
		List<Periodo> lista = new ArrayList<Periodo>();
		int i = 0;
		long longAux = 0L;
		long longFechaInicial = 0L;
		long longFechaFinal = 0L;
		
		long longFechaInicialSemana2 = 0L;
		long longFechaFinalSemana2 = 0L;
		long longAuxQuincenal = 0L;
		
		long longFechaInicial3 = 0L;
		long longFechaFinal3 = 0L;
		long longAuxQuincenal3 = 0L;
		
		switch (periodicidad) {
		case MENSUAL:
			for (i = 0; i < 12; i++) {
				switch (i) {
					case 0: //Enero
						longFechaInicial = fecha.getTime();
						longAux = periodoDe31Dias;
						break;
					case 2: //Marzo
					case 4: //Mayo
					case 6: //Julio
					case 7: //Agosto
					case 11: //Diciembre
						longAux = periodoDe31Dias;
						break;
					case 9: //Octubre
						longAux = periodoDe31Dias + unaHora;
						break;
					
				    
					case 1: //Febrero
						longAux = periodoDe28Dias;
						break;

					case 3: //Abril
						longAux = periodoDe30Dias - unaHora;
						break;
					case 5: //Junio
					case 8: //Septiembre
					case 10: //Noviembre
						longAux = periodoDe30Dias;
						break;
					default:
						break;
				} // fin switch de los meses
		
				longFechaFinal = longFechaInicial + longAux - unSegundo;
				Date fechaInicial = new Date(longFechaInicial);
				Date fechaFinal = new Date(longFechaFinal);
				Periodo periodo = new Periodo(fechaInicial, fechaFinal);
				lista.add(periodo);
				longFechaInicial = longFechaFinal + unSegundo;
			}
			break;
		case QUINCENAL: // falla en el mes de Octubre
			for (i = 0; i < 12; i++) {
				switch (i) {
				case 0: //Enero
					longFechaInicial = fecha.getTime();
					longAux = periodoDe15Dias;
					//longFechaFinal = longFechaInicial + longAux - unSegundo;
					longAuxQuincenal = periodoDe16Dias;
					
					break;
				case 2: //Marzo
				case 4: //Mayo
				case 6: //Julio
				case 7: //Agosto
				case 11: //Diciembre
					longAux = periodoDe15Dias;
					longAuxQuincenal = periodoDe16Dias;
					break;
				case 9: //Octubre
					longAux = periodoDe15Dias ;//+ unaHora;
					longAuxQuincenal = periodoDe16Dias + unaHora;
					break;
				
			    
				case 1: //Febrero
					longAux = periodoDe15Dias;
					longAuxQuincenal = periodoDe13Dias;
					break;

				case 3: //Abril
					longAux = periodoDe15Dias - unaHora;
					longAuxQuincenal = periodoDe15Dias;
					break;
				case 5: //Junio
				case 8: //Septiembre
				case 10: //Noviembre
					longAux = periodoDe15Dias;
					longAuxQuincenal = periodoDe15Dias;
					break;
				default:
					break;
			    } // fin switch de los meses
				
				
				longFechaFinal = longFechaInicial + longAux - unSegundo;
				longFechaInicialSemana2 = longFechaFinal + unSegundo;
				longFechaFinalSemana2 = longFechaInicialSemana2 + longAuxQuincenal - unSegundo;
				Date fechaInicial = new Date(longFechaInicial);
				Date fechaFinal = new Date(longFechaFinal);
				Date fechaInicialSemana2 = new Date(longFechaInicialSemana2);
				Date fechaFinalSemana2 = new Date(longFechaFinalSemana2);
				Periodo periodo1 = new Periodo(fechaInicial, fechaFinal);
				Periodo periodo2 = new Periodo(fechaInicialSemana2, fechaFinalSemana2);
				
				lista.add(periodo1);
				lista.add(periodo2);
				longFechaInicial = longFechaFinalSemana2 + unSegundo;
			}
			break;
		case DECENAL:
			for (i = 0; i < 12; i++) {
				switch (i) {
				case 0: //Enero
					longFechaInicial = fecha.getTime();
					longAux = periodoDe10Dias;
					//longFechaFinal = longFechaInicial + longAux - unSegundo;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe11Dias;
					break;
				case 2: //Marzo
				case 4: //Mayo
				case 6: //Julio
				case 7: //Agosto
				case 11: //Diciembre
					longAux = periodoDe10Dias;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe11Dias;
					break;
				case 9: //Octubre
					longAux = periodoDe10Dias ;//+ unaHora;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe11Dias + unaHora;
					break;
				
			    
				case 1: //Febrero
					longAux = periodoDe10Dias;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe8Dias;
					break;

				case 3: //Abril
					longAux = periodoDe10Dias - unaHora;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe10Dias;
					break;
				case 5: //Junio
				case 8: //Septiembre
				case 10: //Noviembre
					longAux = periodoDe10Dias;
					longAuxQuincenal = periodoDe10Dias;
					longAuxQuincenal3 = periodoDe10Dias;
					break;
				default:
					break;
			    } // fin switch de los meses
				
				longFechaFinal = longFechaInicial + longAux - unSegundo;
				longFechaInicialSemana2 = longFechaFinal + unSegundo;
				longFechaFinalSemana2 = longFechaInicialSemana2 + longAuxQuincenal - unSegundo;
				longFechaInicial3 = longFechaFinalSemana2 + unSegundo;
				longFechaFinal3 = longFechaInicial3 + longAuxQuincenal3 -unSegundo;
				Date fechaInicial = new Date(longFechaInicial);
				Date fechaFinal = new Date(longFechaFinal);
				Date fechaInicialSemana2 = new Date(longFechaInicialSemana2);
				Date fechaFinalSemana2 = new Date(longFechaFinalSemana2);
				Date fechaInicial3 = new Date(longFechaInicial3);
				Date fechaFinal3 = new Date(longFechaFinal3);
				Periodo periodo1 = new Periodo(fechaInicial, fechaFinal);
				Periodo periodo2 = new Periodo(fechaInicialSemana2, fechaFinalSemana2);
				Periodo periodo3 = new Periodo(fechaInicial3, fechaFinal3);
				lista.add(periodo1);
				lista.add(periodo2);
				lista.add(periodo3);
				longFechaInicial = longFechaFinal3 + unSegundo;
			}
			break;
		case SEMANAL: // ya quedo 
			long horaOctubre = 0L;
			for (i = 0; i < 52; i++) {
				if (i == 0)
					longFechaInicial = obtenerPrimerLunes(year).getTime();
				else if (i == 12) {
					longAux = unaHora;
					longFechaInicial = longFechaFinal + unSegundo;
				} else if (i == 42) {
					horaOctubre = unaHora;
					longFechaInicial = longFechaFinal + unSegundo;
				} else
					longFechaInicial = longFechaFinal + unSegundo;
				longFechaFinal = longFechaInicial + unaSemana - unSegundo - longAux + horaOctubre;
				Date fechaInicial = new Date(longFechaInicial);
				Date fechaFinal = new Date(longFechaFinal);
				Periodo periodo1 = new Periodo(fechaInicial, fechaFinal);
				lista.add(periodo1);
				longAux = 0L;
				horaOctubre = 0L;
			}
		}
		
		return lista;
	}
	
	public static Periodo obtenerPeriodo(List<Periodo> listaPeriodo, Date fecha) {
		for (Periodo periodo : listaPeriodo) {
			if (periodo.getFechaInicial().before(fecha) &&
				periodo.getFechaFinal().after(fecha))
				return periodo;
		}
		return null;
	}
	
	private static Date obtenerPrimerLunes(int year) {
		Calendar calendar = new GregorianCalendar(year, 0, 1);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar.getTime();
	}
}
