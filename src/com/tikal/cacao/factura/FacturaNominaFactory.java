package com.tikal.cacao.factura;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.Periodo;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.ObjectFactoryComprobante;
import com.tikal.cacao.sat.cfd.catalogos.C_Estado;
import com.tikal.cacao.sat.cfd.nomina.C_Banco;
import com.tikal.cacao.sat.cfd.nomina.C_PeriodicidadPago;
import com.tikal.cacao.sat.cfd.nomina.C_RiesgoPuesto;
import com.tikal.cacao.sat.cfd.nomina.C_TipoContrato;
import com.tikal.cacao.sat.cfd.nomina.C_TipoDeduccion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoHoras;
import com.tikal.cacao.sat.cfd.nomina.C_TipoJornada;
import com.tikal.cacao.sat.cfd.nomina.C_TipoNomina;
import com.tikal.cacao.sat.cfd.nomina.C_TipoPercepcion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoRegimen;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;
import com.tikal.cacao.sat.cfd.nomina.NominaElement.Percepciones;
import com.tikal.cacao.sat.cfd.nomina.ObjectFactory;
import com.tikal.cacao.springController.viewObjects.PagoVO;
import com.tikal.cacao.util.GeneradorPeriodos;
import com.tikal.cacao.util.Util;


/**
 * @author Tikal
 *
 */
public class FacturaNominaFactory {
	
	ObjectFactoryComprobante comprobanteOF = new ObjectFactoryComprobante();
	ObjectFactory nominaOF = new ObjectFactory();
	
	public Comprobante generarFactura(PagoVO pagoVO) {
		Comprobante comprobante = construirComprobante();
		String cp = pagoVO.getCpLugarExpedicion();
		if (cp != null){
			if (cp.equals(""))
				cp = "70000";
		}
		else
			cp = "70000";
		comprobante.setLugarExpedicion(cp);
		comprobante.setEmisor(construirEmisor(pagoVO.getRfcEmpresa(), pagoVO.getRazonSocial()));
		comprobante.setReceptor(construirReceptor(pagoVO.getRfc(), pagoVO.getNombre()));
		comprobante.setImpuestos(construiImpuestos());
		
		NominaElement nominaElm = construirNodoNomina(pagoVO);
		comprobante.setComplemento(construirComplementoNomina(nominaElm));
		comprobante.setConceptos(construirNodoConceptos(nominaElm));
		comprobante.setSubTotal(nominaElm.getTotalPercepciones()); // más TotalOtrosPagos
    	comprobante.setDescuento(nominaElm.getTotalDeducciones());
    	comprobante.setTotal(nominaElm.getTotalPercepciones().
    			subtract(nominaElm.getTotalDeducciones())); // más TotalOtrosPagos
    	
		return comprobante;
	}
	
	public Comprobante construirComprobante() {
    	Comprobante comprobante = comprobanteOF.createComprobante();
    	comprobante.setVersion("3.2");
    	comprobante.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
    	comprobante.setMetodoDePago("NA");
    	comprobante.setFormaDePago("Pago en una sola exhibición");
    	comprobante.setMoneda("MXN");
    	comprobante.setTipoDeComprobante("egreso");
		return comprobante;
	}
	
	public Comprobante.Complemento construirComplementoNomina(NominaElement nominaElm) {
		Comprobante.Complemento complementoNomina = comprobanteOF.createComprobanteComplemento();
    	complementoNomina.getAny().add(nominaElm);
    	return complementoNomina;
	}
	
	public Comprobante.Conceptos construirNodoConceptos(NominaElement nominaElm) {
		Comprobante.Conceptos conceptos = comprobanteOF.createComprobanteConceptos();
    	Comprobante.Conceptos.Concepto concepto = comprobanteOF.createComprobanteConceptosConcepto();
    	concepto.setCantidad(new BigDecimal(1));
    	concepto.setUnidad("ACT"); //A unit of count defining the number of activities (activity: a unit of work or action).
    	concepto.setDescripcion("Pago de nómina");
    	concepto.setValorUnitario(nominaElm.getTotalPercepciones()); // más TotalOtrosPagos
    	concepto.setImporte(nominaElm.getTotalPercepciones()); // más TotalOtrosPagos
    	conceptos.getConcepto().add(concepto);
    	return conceptos;
	}
	
	public Comprobante.Emisor construirEmisor(String rfc, String razonSocial) {
		Comprobante.Emisor comprobanteEmisor = comprobanteOF.createComprobanteEmisor();
		//rfc para pruebas
		comprobanteEmisor.setRfc("AAA010101AAA");//comprobanteEmisor.setRfc(rfc);
    	comprobanteEmisor.setNombre(razonSocial);
    	
    	//TODO agregar RegimenFiscal dinámicamente
    	Comprobante.Emisor.RegimenFiscal compEmisorRegimenFiscal = comprobanteOF.createComprobanteEmisorRegimenFiscal();
    	compEmisorRegimenFiscal.setRegimen("601"); // General de Ley Personas Morales
    	comprobanteEmisor.getRegimenFiscal().add(compEmisorRegimenFiscal);
    	
    	return comprobanteEmisor;
	}
	
	public Comprobante.Receptor construirReceptor(String rfc, String nombre) {
		Comprobante.Receptor comprobanteReceptor = comprobanteOF.createComprobanteReceptor();
		comprobanteReceptor.setRfc(rfc);
    	//comprobanteReceptor.setRfc("AAGF920831AHA");//
//    	String apMaterno = nombre.getApellidoMaterno();
//    	apMaterno = apMaterno != null ? apMaterno : "";
//    	String strNombre = nombre.getNombresDePila().concat(" ").concat(nombre.getApellidoPaterno()).
//    			concat(" ").concat(apMaterno);
    	comprobanteReceptor.setNombre(nombre);
    	return comprobanteReceptor;
	}
	
	public Comprobante.Impuestos construiImpuestos() {
		Comprobante.Impuestos comprobanteImpuestos = comprobanteOF.createComprobanteImpuestos();
		return comprobanteImpuestos;
	}
	
	public NominaElement construirNodoNomina(PagoVO pagoVO) {
		NominaElement nominaElm = nominaOF.createNominaElement();
		nominaElm.setVersion("1.2");
        nominaElm.setTipoNomina(C_TipoNomina.O);
        
        Date fechaDePago = pagoVO.getFechaDePago();
        Date primerDiadel2017 = new Date(1_483_250_400_000L);
        nominaElm.setFechaPago(Util.getXMLDate(fechaDePago, FormatoFecha.NOMINA));
        PeriodosDePago periodicidad = PeriodosDePago.valueOf(pagoVO.getFormaPago());
        Periodo periodo = GeneradorPeriodos.obtenerPeriodo(
        		GeneradorPeriodos.generarPeriodos(primerDiadel2017, periodicidad, 2017), fechaDePago);
		nominaElm.setFechaInicialPago(Util.getXMLDate(periodo.getFechaInicial(), FormatoFecha.NOMINA));
		nominaElm.setFechaFinalPago(Util.getXMLDate(periodo.getFechaFinal(), FormatoFecha.NOMINA));
		nominaElm.setNumDiasPagados( BigDecimal.valueOf( Double.parseDouble( pagoVO.getDiasPagados() ) ) );
        
		nominaElm.setEmisor(construirEmisor(pagoVO.getRegistroPatronal()));
		nominaElm.setReceptor(construirReceptor(pagoVO, nominaElm.getFechaFinalPago().toGregorianCalendar().getTime()));
		
		nominaElm.setPercepciones(construirPercepciones(pagoVO.getPercepciones()));
		nominaElm.setDeducciones(construirDeducciones(pagoVO.getDeducciones()));
		
		nominaElm.setTotalPercepciones(nominaElm.getPercepciones().getTotalSueldos().
				setScale(2, RoundingMode.HALF_UP)); //TODO + TotalSeparacionIndemnizacion + TotalJubilacionPensionRetiro
		
		nominaElm.setTotalDeducciones(nominaElm.getDeducciones().getTotalImpuestosRetenidos().
				add(nominaElm.getDeducciones().getTotalOtrasDeducciones()).
				setScale(2, RoundingMode.HALF_UP));
		
		return nominaElm;
	}
	
	public NominaElement.Emisor construirEmisor(String registroPatronal) {
		NominaElement.Emisor nominaEmisor = nominaOF.createNominaElementEmisor();
		//nominaEmisor.setRfcPatronOrigen("TIKA070401AZ");
		if (registroPatronal != null)
			nominaEmisor.setRegistroPatronal(registroPatronal);
		else 
			nominaEmisor.setRegistroPatronal("Y6813217103");
		return nominaEmisor;
	}
	
	public NominaElement.Receptor construirReceptor(PagoVO pagoVO, Date fechaFinalPago) {
		NominaElement.Receptor nominaReceptor = nominaOF.createNominaElementReceptor();
		nominaReceptor.setCurp(pagoVO.getCurp());
		nominaReceptor.setTipoContrato(C_TipoContrato.VALUE_1); //Contrato de trabajo por tiempo indeterminado
		nominaReceptor.setTipoJornada(C_TipoJornada.VALUE_1); //Diurna
		nominaReceptor.setFechaInicioRelLaboral(Util.getXMLDate(Util.obtenerFecha(pagoVO.getFechaIngreso()), FormatoFecha.NOMINA));
		
		int semanasAntiguedad = Util.obtenerDiasEntre(nominaReceptor.getFechaInicioRelLaboral().toGregorianCalendar().getTime(), fechaFinalPago) / 7;
		nominaReceptor.setAntiguedad("P".concat(String.valueOf(semanasAntiguedad)).concat("W"));
		
		nominaReceptor.setTipoRegimen(C_TipoRegimen.fromValue(pagoVO.getTipoRegimen()));
		nominaReceptor.setRiesgoPuesto(C_RiesgoPuesto.VALUE_1);//Clase I
		nominaReceptor.setPeriodicidadPago( convertir( PeriodosDePago.valueOf( pagoVO.getFormaPago() ) ) );//Quincenal
		//nominaReceptor.setPeriodicidadPago(C_PeriodicidadPago.VALUE_10);
		
		
		if (pagoVO.getClaveBanco() != null) {
			nominaReceptor.setBanco(C_Banco.fromValue(pagoVO.getClaveBanco()));//HSBC C_Banco.VALUE_7
			nominaReceptor.setCuentaBancaria(BigInteger.valueOf(12345678910L));;
		}
		
		nominaReceptor.setClaveEntFed(C_Estado.MEX);//Estado de México //TODO Crear una clase que convierta los Nombres de los estados en el Enum que acepta el comprobante
		nominaReceptor.setNumSeguridadSocial(pagoVO.getNss());
		
		String strNumEmpleado = String.valueOf( pagoVO.getIdEmpleado() );
		if (strNumEmpleado.length() <= 15) 
			nominaReceptor.setNumEmpleado(strNumEmpleado);
		else 
			nominaReceptor.setNumEmpleado(strNumEmpleado.substring(0, strNumEmpleado.length()-1)); //TODO solucionar la validación de la longitud del numEmpleado de otra forma
			
		nominaReceptor.setSalarioBaseCotApor(BigDecimal.valueOf(pagoVO.getSalarioDiarioIntegrado()));
		nominaReceptor.setSalarioDiarioIntegrado(BigDecimal.valueOf(pagoVO.getSalarioDiarioIntegrado()));
		return nominaReceptor;
	}
	
	public NominaElement.Percepciones construirPercepciones(List<Percepcion> listaP) {
		NominaElement.Percepciones percepciones = nominaOF.createNominaElementPercepciones();
		NominaElement.Percepciones.Percepcion percepcion = null;
		for (Percepcion p : listaP) {
			percepcion = nominaOF.createNominaElementPercepcionesPercepcion();
			percepcion.setTipoPercepcion(C_TipoPercepcion.fromValue(p.getTipo()));
			percepcion.setClave(p.getClave());
			percepcion.setConcepto(p.getDescripcion());
			percepcion.setImporteExento(BigDecimal.valueOf(p.getImporteExento()));
			percepcion.setImporteGravado(BigDecimal.valueOf(p.getImporteGravado()));
			
			if (p instanceof PercepcionHorasExtra) {
				construirNodoHorasExtra(percepcion, (PercepcionHorasExtra)p);
			}
			
			percepciones.getPercepcion().add(percepcion);
		}
		
		double totalExento = 0, totalGravado = 0;
		for (NominaElement.Percepciones.Percepcion p : percepciones.getPercepcion()) {
			totalExento += p.getImporteExento().doubleValue();
			totalGravado += p.getImporteGravado().doubleValue();
		}
		percepciones.setTotalExento(new BigDecimal(totalExento));
		percepciones.setTotalGravado(new BigDecimal(totalGravado));
		percepciones.setTotalSueldos(percepciones.getTotalExento().add(percepciones.getTotalGravado()));
		return percepciones;
	}
	
	void construirNodoHorasExtra(Percepciones.Percepcion percepcion, PercepcionHorasExtra pHorasExtra) {
		int tercerDia = 0;
		int horasDobles = pHorasExtra.getHorasDobles();
		int horasTriples = pHorasExtra.getHorasTriples();
		double montoHorasTriples = pHorasExtra.getMontoHorasTriples();
		Percepciones.Percepcion.HorasExtra nodoHE = nominaOF.createNominaElementPercepcionesPercepcionHorasExtra();
		if (horasDobles > 0) {
			nodoHE.setTipoHoras(C_TipoHoras.VALUE_1);
			nodoHE.setHorasExtra(horasDobles);
			nodoHE.setImportePagado(percepcion.getImporteExento().
					add(percepcion.getImporteGravado().
					subtract(BigDecimal.valueOf(montoHorasTriples))));
			
			if (horasDobles % 3 >= 1) {
				tercerDia = 1;
			}
			nodoHE.setDias(horasDobles / 3 + tercerDia);
			percepcion.getHorasExtra().add(nodoHE);
		}
		
		if (horasTriples > 0) {
			nodoHE = nominaOF.createNominaElementPercepcionesPercepcionHorasExtra();
			nodoHE.setTipoHoras(C_TipoHoras.VALUE_2);
			nodoHE.setHorasExtra(horasTriples);
			nodoHE.setImportePagado(BigDecimal.valueOf(montoHorasTriples));
			
			if (horasTriples % 3 >= 1) {
				tercerDia = 1;
			}
			nodoHE.setDias(horasTriples / 3 + tercerDia);
			percepcion.getHorasExtra().add(nodoHE);
		}
		
		//TODO agregar caso de horas Simples C_TipoHoras.VALUE_3
	}
	
	public NominaElement.Deducciones construirDeducciones(List<Deduccion> listaD) {
		NominaElement.Deducciones deducciones = nominaOF.createNominaElementDeducciones();
		NominaElement.Deducciones.Deduccion deduccion = null;
		for (Deduccion d : listaD) {
			deduccion = nominaOF.createNominaElementDeduccionesDeduccion();
			deduccion.setTipoDeduccion(C_TipoDeduccion.fromValue(d.getTipo()));
			deduccion.setClave(d.getClave());
			deduccion.setConcepto(d.getDescripcion());
			deduccion.setImporte( Util.redondearBigD( d.getDescuento() ));
			deducciones.getDeduccion().add(deduccion);
		}
		
		double totalImporte = 0;
		double totalImpuestos = 0;
		for (NominaElement.Deducciones.Deduccion d : deducciones.getDeduccion()) {
			if (!d.getTipoDeduccion().equals(C_TipoDeduccion.VALUE_2))
				totalImporte += d.getImporte().doubleValue();
			else 
				totalImpuestos += d.getImporte().doubleValue();
		}
		deducciones.setTotalOtrasDeducciones(Util.redondearBigD(totalImporte));
		deducciones.setTotalImpuestosRetenidos(Util.redondearBigD(totalImpuestos));
		return deducciones;
	}
	 
	
	static C_PeriodicidadPago convertir(PeriodosDePago periodosDePago) {
		C_PeriodicidadPago periodicidadPago = null;
		switch (periodosDePago) {
		case BIMESTRAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_6;
			break;
		case MENSUAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_5;
			break;
		case QUINCENAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_4;
			break;
		case DECENAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_10;
			break;
		case SEMANAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_2;
			break;
		default:
			break;
		}
		return periodicidadPago;
	}
}