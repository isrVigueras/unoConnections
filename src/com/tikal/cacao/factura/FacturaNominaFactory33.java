package com.tikal.cacao.factura;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.Periodo;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.sat.cfd.catalogos.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.C_Estado;
import com.tikal.cacao.sat.cfd.catalogos.C_FormaPago;
import com.tikal.cacao.sat.cfd.catalogos.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.C_RegimenFiscal;
import com.tikal.cacao.sat.cfd.catalogos.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.C_UsoCFDI;
import com.tikal.cacao.sat.cfd.nomina.C_Banco;
import com.tikal.cacao.sat.cfd.nomina.C_RiesgoPuesto;
import com.tikal.cacao.sat.cfd.nomina.C_TipoContrato;
import com.tikal.cacao.sat.cfd.nomina.C_TipoDeduccion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoHoras;
import com.tikal.cacao.sat.cfd.nomina.C_TipoJornada;
import com.tikal.cacao.sat.cfd.nomina.C_TipoNomina;
import com.tikal.cacao.sat.cfd.nomina.C_TipoPercepcion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoRegimen;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;
import com.tikal.cacao.sat.cfd.nomina.ObjectFactory;
import com.tikal.cacao.sat.cfd.nomina.NominaElement.Percepciones;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.springController.viewObjects.PagoVO;
import com.tikal.cacao.util.GeneradorPeriodos;
import com.tikal.cacao.util.Util;

/**
 * Esta clase crea un CFDI en la versi&oacute;n 3.3 con el complemento para n&oacute;minas
 * en la versi&oacute;n 1.2.
 * @author Tikal
 *
 */
@Service
public class FacturaNominaFactory33 {
	
	private ObjectFactoryComprobante33 comprobanteOF = new ObjectFactoryComprobante33();
	
	private ObjectFactory nominaOF = new ObjectFactory();
	
	public Comprobante generarFactura(PagoVO pagoVO) {
		Comprobante comprobante = construirComprobante();
		comprobante.setEmisor(construirEmisor(pagoVO.getRfcEmpresa(), pagoVO.getRazonSocial(), "601")); //TODO agregar RegimenFiscal dinámicamente
		comprobante.setReceptor(construirReceptor(pagoVO.getRfc(), pagoVO.getNombre()));
		
		NominaElement nominaElm = construirNodoNomina(pagoVO);
		comprobante.setConceptos(construirNodoConceptos(nominaElm));
		comprobante.getComplemento().add(construirComplementoNomina(nominaElm));
		comprobante.setSubTotal(nominaElm.getTotalPercepciones()); // más TotalOtrosPagos
    	comprobante.setDescuento(nominaElm.getTotalDeducciones());
    	comprobante.setTotal(nominaElm.getTotalPercepciones().
    			subtract(nominaElm.getTotalDeducciones())); // más TotalOtrosPagos
		return comprobante;
	}
	
	public Comprobante construirComprobante() {
		Comprobante comprobante = comprobanteOF.createComprobante();
		comprobante.setVersion("3.3");
		comprobante.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
		comprobante.setMoneda(C_Moneda.MXN);
		comprobante.setFormaPago(C_FormaPago.VALUE_20);
		comprobante.setTipoDeComprobante(C_TipoDeComprobante.N);
		return comprobante;
	}
	
	public Comprobante.Emisor construirEmisor(String rfc, String nombre, String regimenFiscal) { 
		Comprobante.Emisor comprobanteEmisor = comprobanteOF.createComprobanteEmisor();
		comprobanteEmisor.setRfc(rfc);
		comprobanteEmisor.setNombre(nombre);
		comprobanteEmisor.setRegimenFiscal(C_RegimenFiscal.fromValue(regimenFiscal));
		return comprobanteEmisor;
	}
	
	public Comprobante.Receptor construirReceptor(String rfc, String nombre) {
		Comprobante.Receptor comprobanteReceptor = comprobanteOF.createComprobanteReceptor();
		comprobanteReceptor.setRfc(rfc);
		comprobanteReceptor.setNombre(nombre);
		comprobanteReceptor.setUsoCFDI(C_UsoCFDI.P_01); //TODO agregar dinámicamente
		return comprobanteReceptor;
	}
	
	public Comprobante.Conceptos construirNodoConceptos(NominaElement nominaElm) {
		Comprobante.Conceptos conceptos = comprobanteOF.createComprobanteConceptos();
		Comprobante.Conceptos.Concepto concepto = comprobanteOF.createComprobanteConceptosConcepto();
		concepto.setClaveProdServ("84111505");
		concepto.setCantidad(new BigDecimal(1));
		concepto.setClaveUnidad(C_ClaveUnidad.VALUE_1358);
		concepto.setDescripcion("Pago de nómina");
		concepto.setValorUnitario(nominaElm.getTotalPercepciones().add(nominaElm.getTotalOtrosPagos()));
		concepto.setImporte(concepto.getValorUnitario());
		concepto.setDescuento(nominaElm.getTotalDeducciones());
		conceptos.getConcepto().add(concepto);
		return conceptos;
	}
	
	public Comprobante.Complemento construirComplementoNomina(NominaElement nominaElm) {
		Comprobante.Complemento complementoNomina = comprobanteOF.createComprobanteComplemento();
		complementoNomina.getAny().add(nominaElm);
		return complementoNomina;
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
		nominaEmisor.setRegistroPatronal(registroPatronal);
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
		nominaReceptor.setPeriodicidadPago( Util.convertir( PeriodosDePago.valueOf( pagoVO.getFormaPago() ) ) );//Quincenal
		nominaReceptor.setBanco(C_Banco.VALUE_7);//HSBC
		nominaReceptor.setCuentaBancaria(BigInteger.valueOf(12345678910L));;
		nominaReceptor.setClaveEntFed(C_Estado.MEX);//Estado de México
		nominaReceptor.setNumSeguridadSocial(pagoVO.getNss());
		nominaReceptor.setNumEmpleado( String.valueOf( pagoVO.getIdEmpleado() ) );
		nominaReceptor.setSalarioBaseCotApor(BigDecimal.valueOf(pagoVO.getSalarioDiarioIntegrado()));
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

	
}
