/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tikal.cacao.dao.CatalogosDAO;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.PagosDAO;
import com.tikal.cacao.exception.DomingosLaboradosException;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionAusentismo;
import com.tikal.cacao.model.DeduccionIncapacidad;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.util.DeduccionAscendingComparator;
import com.tikal.cacao.util.DeduccionDescendigComparator;
import com.tikal.cacao.util.Util;

/**
 * @author Tikal
 *
 */
@Service // definir scope dependiendo de cuántos usuarios usaran el sistema y si pueden hacer peticiones simultaneas con un mismo usuario
// seguir trabajando con los archivos de excel sobre las percepciones
public class Nomina {
	
	/*static*/ final int DIAS_DEL_EJERCICIO = 365;
	
	@Autowired
	private EmpleadosDAO EMPLEADOS_DAO;
	
	@Autowired
	@Qualifier("tipoPercepciondao")
	private CatalogosDAO<EntTipoPercepcion, Long, String> TIPO_PERCEPCION_DAO;
	
	@Resource(name="tipoDeducciondao")
	private CatalogosDAO<EntTipoDeduccion, Long, String> TIPO_DEDUCCION_DAO;
	
	@Resource(name="pagosdao")
	private PagosDAO pagosDAO;
	
	private List<EntTipoPercepcion> catalogoTipoPercepcion;
	
	private List<EntTipoDeduccion> catalogoTipoDeduccion;
	
	private Map<Long, Date> mapaFechasDeContratacion = new HashMap<Long, Date>();
	
	private Map<Long, Double> mapaTiempoExtraParaSBC = new HashMap<Long, Double>();
	
	private Map<Long, Double> mapaPremiosPuntualidadSBC = new HashMap<Long, Double>();
	
	private Map<Long, Date> mapaFechasCambioSBC = new HashMap<Long, Date>();
	
	private Map<Long, Boolean> mapaCambiosConceptosSBC = new HashMap<Long, Boolean>();
	
	private /*static*/ double montoDeudaConPatron; // monto total de deudas del trabajador contraidas con el patrón
	
	private /*static*/ boolean existeDeudaConPatron;
	
	@Autowired
	private ProcesadorPercepcionFactory procesadorPFactory;
	
	@Autowired
	private ProcesadorDeduccionFactory procesadorDFactory;
	
	private ProcesadorPercepcion procesadorP;
	
	private ProcesadorDeduccion procesadorD;
	
	private boolean cambioSBC = false;
	private boolean cambioPremioSBC = false;
		
	// atributo para probar el algortimo del cambio en SBC
	//public int i;
	
	Nomina() {
	    // simular que la aplicación no se detuvo en su ejecución
		//mapaPremiosPuntualidadSBC.put(6583875627122688L, 31.82 + 25.16);
		//mapaTiempoExtraParaSBC.put(6583875627122688L, 125.0);
		//mapaFechasCambioSBC.put(6583875627122688L, new Date(1_487_170_800_000L));
	}
	
//	@PostConstruct
//	public void postConstruct() {
//		this.catalogoTipoPercepcion = getCatalogoPercepciones();
//	}
	
	/**
	 * Regresa una lista de pagos de los trabajadores del <tt>regimen</tt>
	 * @param regimen el esquema con el que se generar&aacute; la lista de pagos
	 * @return una lista de los pagos que se deben hacer a los trabajadores cuyo r&eacute;gimen es el especificado
	 */
	public List<Pago> calcularPagos(Regimen regimen) {
		List<Pago> listaDePagos = new ArrayList<Pago>();
		List<Long> idsEmpleados = regimen.getIdEmpleados();
		PeriodosDePago periodo = PeriodosDePago.valueOf(regimen.getFormaPago().toUpperCase());
		
		for (Long id : idsEmpleados) {
			Pago pago = new Pago();
			pago.setIdEmpleado(id);
			pago.setIdRegimen(regimen.getId());
			pago.setTipoRegimen(regimen.getRegimenContratacion());
			pago.setPercepciones(regimen.getPercepciones());
			pago.setDeducciones(regimen.getDeducciones());
			
			// Obtener el empleado asociado al pago
			Empleado empleado = EMPLEADOS_DAO.consultar(pago.getIdEmpleado());
			
			pago.setTrabajadorAsegurado( (empleado.getNumSeguroSocial() == null ? false : true) );
			
			// Crear el objeto java.util.Date a partir de la fecha de contratación del empleado
			Date fechaContratacion = Util.obtenerFecha(empleado.getFechaDeContratacion());
			
			// Agregar la fechaContratacion al mapa para usarlo en caso de realizar ajustes a los pagos
			mapaFechasDeContratacion.put(pago.getIdEmpleado(), fechaContratacion);
			
			pago = calcularPago(periodo, fechaContratacion, pago);
			
			pago.setFormaPago(periodo.toString());
			
			pago.setDiasPagados(String.valueOf(IMSS.calcularDiasCotizados(periodo)));
			
			Calendar calendar = new GregorianCalendar(new Locale("es", "MX"));
//			pago.setFechaDePago(calendar.getTime());
			
//			switch (i) {
//			case 0:
//				pago.setFechaDePago(new Date(1_490_972_400_000L));
//				break;
//			case 1:
//				pago.setFechaDePago(new Date(1_492_268_400_000L));
//				break;
//			case 0: //antes 2
//				pago.setFechaDePago(new Date(1_493_564_400_000L));
//				break;
//			}
				
			
			//pago.setFechaDePago(new Date(1_487_170_800_000L)); // harcodear fecha de pago
			pago.setFechaDePago(new Date());
			int diaDeHoy = calendar.get(Calendar.DAY_OF_MONTH);
			pago.setFechaDePagoEsquema(Util.regresarFecha(
					Util.obtenerDiaDePago(regimen.getDiasDePago(), diaDeHoy, periodo)));
			listaDePagos.add(pago);
			
		}
		
		return listaDePagos;
	}
	
	// antes static
	public List<Pago> calcularPagoConAjuste(List<Pago> pagos) {
		PeriodosDePago periodo = PeriodosDePago.valueOf(pagos.get(0).getFormaPago());
		Date fechaContratacion = null;
		if (procesadorP != null)
			procesadorP.reiniciarRecursos();
		for(Pago pago : pagos) {
			fechaContratacion = mapaFechasDeContratacion.get(pago.getIdEmpleado());
			calcularPago(periodo, fechaContratacion, pago);
		}
		return pagos;
	}
	
	
	// antes static
	Pago calcularPago(PeriodosDePago periodo, Date fechaContratacion, Pago pago) {
		
		if (procesadorP != null && procesadorD != null) 
			procesadorP.reiniciarRecursos();
		
		procesarPercepciones(pago, periodo, fechaContratacion);
		
		procesarDeducciones(pago, periodo, fechaContratacion);
		
		// Bloque hardcodeado
		
		
//		for (Percepcion percepcion : pago.getPercepciones()) {
//		    EntTipoPercepcion tipoP = TIPO_PERCEPCION_DAO.consultarPorIndice(percepcion.getTipo(), EntTipoPercepcion.class);
//		    if (catalogoTipoPercepcion.contains(tipoP)) {
//		    	switch(tipoP.getClave()) {
//		    	case "001": // Sueldos, Salarios  Rayas y Jornales
//		    		sueldo = percepcion.getCantidad();
//					ingresoGravable += sueldo;
//					ingresoCotizable += sueldo;
//					totalAPagar += percepcion.getCantidad();
//					pago.setSalarioDiario(calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo));
//					break;
//		    	case "002": // Gratificación Anual (Aguinaldo)
//		    		double aguinaldoBruto = calcularAguinaldo(sueldo, PrestacionesDeLey.DIAS_DE_AGUINALDO, periodo);
//		    		ingresoGravable += aguinaldoBruto - AGUINALDO_EXENTO;
//		    		percepcion.setCantidad(aguinaldoBruto);
//		    		totalAPagar += percepcion.getCantidad();
//		    		break;
//		    	case "003": // Participación de los Trabajadores en las Utilidades PTU
//		    		
//		    	case "004": // Reembolso de Gastos Médicos Dentales y Hospitalarios
//		    		totalAPagar += percepcion.getCantidad();
//		    		break;
//		    	case "005": // Fondo de Ahorro
//		    		/* Para calcular la cantidad a pagar de esta percepción se necesita:
//		    		   Un módulo para configurar las prestaciones que se otorgan a los
//		    		   trabajadores. En éste módulo se deben establecer los porcentajes que 
//		    		   el patrón y el trabajador aportarán al fondo de ahorro. Por defecto,
//		    		   al final del año laboral, el trabajador podrá retirar el monto acumulado
//		    		   en el fondo de ahorro
//		    		*/
//		    	case "006": // Caja de ahorro
//		    		/* Para calcular la cantidad a pagar por esta percepción se necesita:
//		    		   Un módulo para configurar la administración de la Caja de ahorro. En éste
//		   			   módulo se debe especificar el monto que se descuenta al trabajador para
//		   			   depositarse a la Caja de ahorro para que después de un lapso de tiempo
//		   			   se pueda retirar una cantidad de esa Caja
//		    		 */
//		    	case "009": // Contribuciones a Cargo del Trabajador Pagadas por el Patrón
//		    		/* de ejecutarse este caso, el ingresoGravable debe ser el mismo
//		    		   que el ingresoGravable que se utiliza para calcular el ISR
//		    		*/
//		    		if (tarifa == null)
//		    			tarifa = obtenerTarifa(ingresoGravable, periodo);
//		    		double monto = calcularISR(ingresoGravable, tarifa, false);
//		    		percepcion.setCantidad(monto);
//		    		totalAPagar += percepcion.getCantidad();
//		    		break;
//		    	case "010": // Premios por puntualidad
//		    		double premioPuntualidad = percepcion.getCantidad();
//		    		if (sbc == 0.0) {
//		    			sbc = IMSS.calcularSBC(sueldo, fechaContratacion, periodo);
//		    		}
//		    		montoPremioASBC = integrarPremioASBC(premioPuntualidad, sbc, periodo);
//		    		ingresoGravable += premioPuntualidad;
//		    		totalAPagar += premioPuntualidad;
//		    		break;
//		    	case "011": // Prima de Seguro de vida
//		    		/* añadir atributo(s) para especificar quien pago la prima y quienes son los
//		    		   beneficiarios de dicha prima, lo anterior con el fin de establecer si el monto
//		    		   de la prima exenta o no del pago de ISR
//		    		*/
//		    	case "012": // Seguro de Gastos Médicos Mayores
//		    		// no causa ISR si se entregan como prestación de prevención social
//		    		// Agregar un atributo que indique si esta percepción la otorgo el patrón 
//		    		totalAPagar += percepcion.getCantidad();
//		    	case "013": // Cuotas Sindicales Pagadas por el Patrón
//		    		// Agregar atributo que represente el porcentaje correspondiente a la Cuota Sindical
//		    		totalAPagar += calcularCuotaSindical(sueldo, percepcion.getCantidad());
//		    	case "014": // Subsidios por incapacidad
//		    		totalAPagar += percepcion.getCantidad();
//		    	case "015": // Becas para trabajadores y/o hijos
//		    		totalAPagar += percepcion.getCantidad();
//		    	case "019": // Horas extra
//		    		PercepcionHorasExtra pHorasExtra = (PercepcionHorasExtra) percepcion;
//					double salario = calcularCuotaDiariaSobreSalarioFijo(sueldo, periodo);
//					double horasExtras[] = calcularPagoDeHorasExtras(salario, pHorasExtra.getHorasExtra());
//					ingresoGravable += horasExtras[1];
//					ingresoCotizable += horasExtras[2];
//					totalAPagar += horasExtras[0] + horasExtras[1];
//					pHorasExtra.setCantidad(horasExtras[0] + horasExtras[1]);
//					break;
//		    	case "020": // Prima dominical
//		    		// el atributo cantidad en esta percepcion representa los domingos trabajados
//					double[] primaDominical = calcularPrimaDominical(sueldo, periodo, (int)percepcion.getCantidad());
//					ingresoGravable += primaDominical[1];
//					double primaDominicalT = primaDominical[0] + primaDominical[1];
//					ingresoCotizable += primaDominicalT;
//					totalAPagar += primaDominicalT;
//					break;
//		    	case "021": // Prima vacacional
//		    		double primaVacacional = calcularPrimaVacacional(sueldo, fechaContratacion);
//					if (primaVacacional > PRIMA_VACACIONAL_EXENTA)
//						ingresoGravable += primaVacacional - PRIMA_VACACIONAL_EXENTA;
//					totalAPagar += primaVacacional;
//					percepcion.setCantidad(primaVacacional);
//					break;
//		    	case "022": // Prima por antigüedad
//		    	case "023": // Pagos por separación
//		    	case "024": // Seguro de retiro
//		    	case "025": // Indemnizaciones
//		    	case "026": // Reembolso por funeral
//		    	case "027": // Cuotas de seguridad social pagadas por el patrón
//		    	case "028": // Comisiones
//		    	case "029": // Vales de despensa
//		    	case "030": // Vales de restaurante
//		    	case "031": // Vales de gasolina
//		    	case "032": // Vales de ropa
//		    	case "033": // Ayuda para renta
//		    	case "034": // Ayuda para artículos escolares
//		    	case "035": // Ayuda para anteojos
//		    	case "036": // Ayuda para transporte
//		    	case "037": // Ayuda para gastos de funeral
//		    	case "038": // Otros ingresos por salarios
//		    	case "039": // Jubilaciones, pensiones o haberes de retiro
//		    	case "044": // Jubilaciones, pensiones o haberes de retiro en parcialidades
//		    	case "045": // Ingresos en acciones o títulos valor que representan bienes
//		    	case "046": // Ingresos asimilados a salarios
//		    	case "047": // Alimentación
//		    	case "048": // Habitación
//		    	case "049": // Premios por asistencia
//		    	}
//		    }
		    

		
		// verificar si es necesario obtener otra tarifa para el ISR
//		if (ingresoGravable > sueldo)
//			tarifa = obtenerTarifa(ingresoGravable, periodo);
//	    else if (tarifa == null) 
//			tarifa = obtenerTarifa(sueldo, periodo); // LLAMAR ESTE MÉTODO DESDE EL MÉTODO ejecutar() DE LAS CLASES PROCESADOR
//		
//		for (Deduccion deduccion : pago.getDeducciones()) {
//			double descuento = deduccion.getDescuento();
//			switch (deduccion.getTipoEnum()) {
//			case SEGURIDAD_SOCIAL:
//				if (sbc == 0.0) {
//					sbc = IMSS.calcularSBC(ingresoCotizable, fechaContratacion, periodo);
//					sbc += montoPremioASBC;
//				}
//				totalAPagar -= aplicarDeduccion(deduccion, IMSS.calcularCuota(sbc, periodo));
//				pago.setSalarioDiarioIntegrado(sbc);
//				break;
//			case ISR:
//				// recalcular la tarifa para que corresponda con el ingresoGravable
//				double isr = calcularISR(ingresoGravable, tarifa, false);
//				totalAPagar -= aplicarDeduccion(deduccion, isr);
//				break;
//			case APORTACIONES_A_RETIRO_CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ:
//				if (sbc == 0.0) sbc = IMSS.calcularSBC(ingresoCotizable, fechaContratacion, periodo);
//				totalAPagar -= aplicarDeduccion(deduccion, IMSS.calcularAportacionesRCV(sbc, periodo));
//				break;
//			case OTROS:
//				break;
//			case APORTACIONES_A_FONDO_DE_VIVIENDA: // esta deduccion se aplica solo a los patrones
//				
//				break;
//			case DESCUENTO_POR_INCAPACIDAD:
//				// en este caso el atributo 'descuento' del objeto 'deduccion' represtenta el número de días de incapacidad
//				double descuentoIncapacidad = calcularDescuentoPorIncapacidad(sueldo, deduccion.getDiasIncapacidad(), periodo, deduccion.getIncapacidad());
//				totalAPagar -= aplicarDeduccion(deduccion, descuentoIncapacidad);
//				break;
//			case PENSION_ALIMENTICIA:
//				// en este caso el atributo 'descuento' del objeto 'deduccion' represtenta un porcentaje
//				totalAPagar -= calcularPensionAlimenticia(totalAPagar, deduccion.getDescuento());;
//				break;
//			case RENTA:
//				// double renta = calcularPagoRenta(ingreso, deduccion.getDescuento()); agregar throws en la fima del metodo
//				if (verificarDescuentoRenta(sueldo, deduccion.getDescuento(), periodo)) {
//					totalAPagar -= deduccion.getDescuento();
//				}
//				break;
//			case PRESTAMOS_PROVENIENTES_DEL_FONDO_NACIONAL_DE_LA_VIVIENDA_PARA_LOS_TRABAJADORES:
//				break;
//			case PAGO_POR_CREDITO_DE_VIVIENDA:
//				DeduccionInfonavit dInfonavit = (DeduccionInfonavit)deduccion;
//				double pagoCredito = Infonavit.calcularPagoCredito(sbc,periodo, dInfonavit.getModalidad(), dInfonavit.getValorDeCredito());
//				totalAPagar -= aplicarDeduccion(dInfonavit, pagoCredito);
//				break;
//			case PAGO_DE_ABONOS_INFONACOT:
//				totalAPagar -= calcularAbonoINFONACOT(sueldo, descuento);
//				break;
//			case ANTICIPO_DE_SALARIOS:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case PAGOS_HECHOS_CON_EXCESO_AL_TRABAJADOR:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case ERRORES:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case PERDIDAS:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case AVERIAS:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case ADQUISICION_DE_ARTICULOS_PRODUCIDOS_POR_LA_EMPRESA_O_ESTABLECIMIENTO:
//				aplicarMontoDeuda(deduccion.getDescuento());
//				break;
//			case CUOTAS_PARA_LA_CONSTITUCION_Y_FOMENTO_DE_SOCIEDADES_COOPERATIVAS_Y_DE_CAJAS_DE_AHORRO:
//				if (verificarCuotaSCyCajasDeAhorro(sueldo, deduccion.getDescuento(), periodo)) {
//					totalAPagar -= deduccion.getDescuento();
//				}
//				break;
//			case CUOTAS_SINDICALES:
//				// en este caso el atributo 'descuento' del objeto 'deduccion' represtenta un porcentaje
//				totalAPagar -= calcularCuotaSindical(sueldo, deduccion.getDescuento());
//				break;
//			case AUSENCIA:
//				// en este caso el atributo 'descuento' del objeto 'deduccion' represtenta el número de días de ausencia
//				totalAPagar -= calcularDeduccionPorAusencia(sueldo, (int)deduccion.getDescuento(), periodo);
//				break;
//			case CUOTAS_OBRERO_PATRONALES: // esta deducción se aplica sólo a los patrones
//				break;
//			default:
//				break;
//			}
//			
//		}
//		
//		if (existeDeudaConPatron) {
//			if (verificarDeudasAlPatron(sueldo, montoDeudaConPatron, periodo)) {
//				totalAPagar -= montoDeudaConPatron;
//			}
//		}
		
		pago.setSalarioDiario(CalculadoraNomina.calcularCuotaDiariaSobreSalarioFijo(this.procesadorP.getSueldo(), periodo));
		pago.setSalarioDiarioIntegrado(this.procesadorP.getSBC());
		pago.setCantidadAPagar(this.procesadorP.getTotalAPagar());
		
		return pago;
		
	}
	
	// antes static
	void procesarPercepciones(Pago pago, PeriodosDePago periodo, Date fechaContratacion) {
		long idEmpleado = pago.getIdEmpleado();
		Date ultimaFechaPago = obtenerUltimaFechaDePago(periodo);
		establecerUltimoSBC(idEmpleado, ultimaFechaPago);

		for (Percepcion percepcion : pago.getPercepciones()) {
			EntTipoPercepcion tipoP = TIPO_PERCEPCION_DAO.consultarPorIndice(percepcion.getTipo(), EntTipoPercepcion.class);
			if (catalogoTipoPercepcion == null) {
				catalogoTipoPercepcion = getCatalogoPercepciones();
			} 
			if (catalogoTipoPercepcion.contains(tipoP)) {
				procesadorP = procesadorPFactory.getProcesador(tipoP.getClave());
				procesadorP.setAsegurado(pago.isTrabajadorAsegurado());
				procesadorP.ejecutar(percepcion, periodo, fechaContratacion);
				
				// VERIFICAR EL ALGORITMO PARA CAMBIAR EL SBC
				if (( mapaTiempoExtraParaSBC.containsKey(idEmpleado) 
					|| mapaPremiosPuntualidadSBC.containsKey(idEmpleado) )
					&& mapaFechasCambioSBC.containsKey(idEmpleado)
					&& procesadorP.getMontoPremioASBC() == 0.0 
					&& procesadorP.getTiempoExtraParaSBC() == 0.0
					&& procesadorP.getSBC() != 0.0) {
					
					 //ajusteSBCBimestral(procesadorP, idEmpleado, periodo, fechaContratacion);
					// mapaFechasCambioSBC.put(idEmpleado, new Date(1_480_140_000_000L)); // se hardcodeo un fecha de 61 antes de la fecha actual
					/* investigar como establecer una fecha de cambio para SBC solo la primera vez
					   y cuando se cambie el SBC borrar la fecha guardada*/
				} else {
					ajusteSBCTiempoExtra(procesadorP, idEmpleado);
					ajusteSBCPremiosPA(procesadorP, idEmpleado);
					// verificar con dos o más empleados y un valor en el mapaFechasCambioSBC
					if ( cambioSBC || cambioPremioSBC && !(mapaFechasCambioSBC.containsKey(idEmpleado)) ) 
						mapaFechasCambioSBC.put(idEmpleado, new Date());
						/*mapaFechasCambioSBC.put(idEmpleado, new Date(1_487_170_800_000L));*/// solo se hace una vez cada 61 días
				}			
			}
		}
		ajusteSBCBimestral(procesadorP, idEmpleado, periodo, fechaContratacion);
		pago.setMontoPrevisionSocial(procesadorP.getMontoPrevisionSocial());
	}
	
	
	void procesarDeducciones(Pago pago, PeriodosDePago periodo, Date fechaContratacion) {
		List<Deduccion> deducciones = pago.getDeducciones();
		Collections.sort(deducciones, new DeduccionDescendigComparator());
		for (Deduccion deduccion : deducciones) {
			EntTipoDeduccion tipoD = TIPO_DEDUCCION_DAO.consultarPorIndice(deduccion.getTipo(), EntTipoDeduccion.class);
			if (catalogoTipoDeduccion == null) {
				catalogoTipoDeduccion = getCatalogoDeducciones();
			}
			if (catalogoTipoDeduccion.contains(tipoD)) {
				procesadorD = procesadorDFactory.getProcesador(tipoD.getClave());
				if (procesadorD instanceof ProcesadorDeduccionSeguridadSocial) {
					procesadorP.setAsegurado(true);
				} else if (procesadorD instanceof ProcesadorDeduccionAusentismo ||
						procesadorD instanceof ProcesadorDeduccionIncapacidad) {
					pago.setDiasPagados(String.valueOf(ajustarDiasPagados(deduccion, pago.getDiasPagados())));
				}
				procesadorD.ejecutar(deduccion, periodo, fechaContratacion);
				
//				if ( procesadorD.getSubsidioAlEmpleo() > 0 ) {
//					
//				}
				//TODO implementar TipoOtrosPagos: Ejemplo Subsidio Al Empleo
				/* if (procesadorD.getSubsidioAlEmpleo() > 0)
				 *    usar un objeto List<OtrosPagos> y añadir un elemento con el monto del subsidio efectivamente
				 *    entregado al trabajador */
			}
		}
		Collections.sort(deducciones, new DeduccionAscendingComparator());
	}
	

	void ajusteSBCBimestral(ProcesadorPercepcion procesadorP, long idEmpleado, PeriodosDePago periodo, Date fechaContratacion) {
		Date fechaCambioSBC = mapaFechasCambioSBC.get(idEmpleado);
		int diasBimestre = 61;
		int valorDelMes = Calendar.getInstance().get(Calendar.MONTH);
		if (valorDelMes == Calendar.JANUARY || valorDelMes == Calendar.FEBRUARY) {
			diasBimestre = 59;
		}
		if (fechaCambioSBC != null) {
			if (Util.obtenerDiasEntre(mapaFechasCambioSBC.get(idEmpleado)) == diasBimestre) {
				 Double montoTotalTiempoExtraSBC = mapaTiempoExtraParaSBC.get(idEmpleado);
				 Double montoTotalPremioPuntualidadSBC = mapaPremiosPuntualidadSBC.get(idEmpleado);
				 double sbc = IMSS.calcularSBC(procesadorP.getSueldo(),fechaContratacion, periodo);
				 if (montoTotalTiempoExtraSBC != null && montoTotalPremioPuntualidadSBC != null) {
					 sbc += IMSS.calcularSBCVariable(montoTotalTiempoExtraSBC +  montoTotalPremioPuntualidadSBC);	 
				 } else if (montoTotalTiempoExtraSBC != null) {
					 sbc += IMSS.calcularSBCVariable(montoTotalTiempoExtraSBC);
				 } else {
					 sbc += IMSS.calcularSBCVariable(montoTotalPremioPuntualidadSBC);
				 }
				 procesadorP.setSBC(sbc);
				 
				 mapaTiempoExtraParaSBC.remove(idEmpleado);
				 mapaPremiosPuntualidadSBC.remove(idEmpleado);
				 mapaFechasCambioSBC.remove(idEmpleado);
				 mapaCambiosConceptosSBC.remove(idEmpleado);
			 }
		}
	}
	
	void ajusteSBCTiempoExtra(ProcesadorPercepcion procesadorP, long idEmpleado) {
		double montoTiempoExtraSBC = procesadorP.getTiempoExtraParaSBC();
		if (montoTiempoExtraSBC > 0.0 && !(mapaTiempoExtraParaSBC.containsKey(idEmpleado))){
			mapaTiempoExtraParaSBC.put(idEmpleado, montoTiempoExtraSBC);
			cambioSBC = true;
			mapaCambiosConceptosSBC.put(idEmpleado, true);
		} else if (montoTiempoExtraSBC > 0.0 && mapaTiempoExtraParaSBC.containsKey(idEmpleado) && /*!(mapaCambiosConceptosSBC.get(idEmpleado)) && */!(cambioSBC)) {
			double montoAuxTE = mapaTiempoExtraParaSBC.get(idEmpleado);
			mapaTiempoExtraParaSBC.put(idEmpleado, montoTiempoExtraSBC + montoAuxTE);
			cambioSBC = true;
			mapaCambiosConceptosSBC.put(idEmpleado, true);
		}
	}
	
	void ajusteSBCPremiosPA(ProcesadorPercepcion procesadorP, long idEmpleado) {
		double montoPremioPuntualidadSBC = procesadorP.getMontoPremioASBC();
		if (montoPremioPuntualidadSBC > 0.0 && !(mapaPremiosPuntualidadSBC.containsKey(idEmpleado))) {
			mapaPremiosPuntualidadSBC.put(idEmpleado, montoPremioPuntualidadSBC);
			cambioPremioSBC = true;
			mapaCambiosConceptosSBC.put(idEmpleado, true);
		} else if (montoPremioPuntualidadSBC > 0.0 && mapaPremiosPuntualidadSBC.containsKey(idEmpleado) && /*!(mapaCambiosConceptosSBC.get(idEmpleado)) &&*/ !(cambioPremioSBC)) {
			double montoAuxPremio = mapaPremiosPuntualidadSBC.get(idEmpleado);
			mapaPremiosPuntualidadSBC.put(idEmpleado, montoPremioPuntualidadSBC + montoAuxPremio);
			cambioPremioSBC = true;
			mapaCambiosConceptosSBC.put(idEmpleado, true);
		}
	}
	
	
	private List<EntTipoPercepcion> getCatalogoPercepciones() {
		return TIPO_PERCEPCION_DAO.consultarTodos(EntTipoPercepcion.class);
	}
	
	private List<EntTipoDeduccion> getCatalogoDeducciones() {
		return TIPO_DEDUCCION_DAO.consultarTodos(EntTipoDeduccion.class);
	}
	
	private void establecerUltimoSBC (long idEmpleado, Date fecha) {
		if (this.procesadorP != null) {
			this.procesadorP.setUltimoSBC(pagosDAO.consultarSBC(idEmpleado, fecha));
		}
	}
	
	private Date obtenerUltimaFechaDePago(PeriodosDePago periodo) {
		// invocar un metodo de PAGOSDAO para obtener los últimos pagos y de ahí
		// el último salario integrado
		Calendar ultimaFecha = Calendar.getInstance();
		switch (periodo) {
			case SEMANAL:
				ultimaFecha.add(Calendar.DATE, -14);
				break;
			case DECENAL:
				ultimaFecha.add(Calendar.DATE, -20);
				break;
			case QUINCENAL:
				ultimaFecha.add(Calendar.DATE, -30);
				break;
			case MENSUAL:
				ultimaFecha.add(Calendar.MONTH, -2);
				break;
		}
		return ultimaFecha.getTime();
	}
	
	private int ajustarDiasPagados(Deduccion deduccion, String strDiasPagados) {
		String subStrDiasPagados = strDiasPagados.substring(0, strDiasPagados.indexOf("."));
		int diasPagados = Integer.parseInt(subStrDiasPagados);
		DeduccionAusentismo dA = null;
		DeduccionIncapacidad dI = null;
		if (deduccion instanceof DeduccionAusentismo) {
			dA = (DeduccionAusentismo) deduccion;
			diasPagados -= dA.getDiasAusente();
		} else if (deduccion instanceof DeduccionIncapacidad) {
			dI = (DeduccionIncapacidad) deduccion;
			diasPagados -= dI.getDiasIncapacidad();
		}
		return diasPagados;
	}
	
	
}
