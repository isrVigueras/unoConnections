package com.tikal.cacao.facturacion.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.ConceptosDAO;
import com.tikal.cacao.dao.DatosDAO;
import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.factura.FormatoFecha;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Receptor;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_CodigoPostal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Impuesto;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_MetodoDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_RegimenFiscal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoFactor;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_UsoCFDI;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Traslados;
import com.tikal.cacao.service.FacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.tralix.Datos;
import com.tikal.unoconnections.tralix.DatosConcepto;

public class FacturacionMultipleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private WSClient client;

	@Autowired
	private WSClientCfdi33 client33;

	@Autowired
	EmpresasDAO empresasDAO;

	@Autowired
	EmisorDAO emisorDAO;

	@Autowired
	SerialDAO serialDAO;

	@Autowired
	FacturaDAO facturaDAO;

	@Autowired
	BitacoraDAO bitacoradao;

	@Autowired
	ImagenDAO imagenDAO;

	@Autowired
	ReporteRenglonDAO repRenglonDAO;

	@Autowired
	FacturaVTTService servicioFact;

	@Autowired
	DatosDAO datosdao;

	@Autowired
	ConceptosDAO conceptodao;
	
	@Autowired
	@Qualifier("formaDePagoDAOH")
	SimpleHibernateDAO<FormaDePago> formaDePagoDAO;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Datos> lista = datosdao.todos();
		for (Datos fr : lista) {
			String respuesta = this.timbrarDatos(fr, request.getSession());
			if (respuesta.compareTo("OK") == 0) {
				datosdao.elimiar(fr);
			} else {
				// something to do
			}

		}
		response.getWriter().print("OK");

	}

	private String timbrarDatos(Datos f, HttpSession sesion) {

		Comprobante c = new Comprobante();
		Comprobante.Emisor emisor = new Comprobante.Emisor();
		Empresa empresa = empresasDAO.consultar(f.getRfcEmisor());
		emisor.setNombre(empresa.getNombre());
		emisor.setRfc(empresa.getRFC());
		emisor.setRegimenFiscal(new C_RegimenFiscal("601"));
		c.setEmisor(emisor);
		Comprobante.Receptor receptor = new Comprobante.Receptor();
//		if (f.getRFC().compareTo("XEXX010101000") != 0) {
//			List<Receptor> receptores = emisorDAO.consultar(f.getRfcEmisor()).getReceptores();
//			Receptor recept = null;
//			for (Receptor r : receptores) {
//				if (r.getRfc().compareToIgnoreCase(f.getRFC()) == 0) {
//					recept = r;
//					break;
//				}
//			}
//			if (recept == null) {
//				return "No se encontró al receptor con RFC: " + f.getRFC();
//			}
//		}
		receptor.setNombre(f.getNombreReceptor());
		receptor.setRfc(f.getRFC());
		receptor.setUsoCFDI(new C_UsoCFDI("P01"));
		// receptor.setResidenciaFiscal(new C_Pais("MEX"));

		// receptor.setDomicilio(recept.getDomicilio());
		c.setReceptor(receptor);
		c.setVersion("3.3");
		c.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));

		c.setLugarExpedicion(new C_CodigoPostal(empresa.getDireccion().getCodigoPostal()));
		c.setTipoDeComprobante(new C_TipoDeComprobante("I"));
		c.setFormaPago(new C_FormaDePago(this.regresaClaveFormaDePago(f.getMetodoPago().toUpperCase())));
		c.setMetodoPago(new C_MetodoDePago("PPD"));
		if(f.getFormaPago().toLowerCase().contains("sola")){
			c.setMetodoPago(new C_MetodoDePago("PUE"));
		}
		
		c.setMoneda(new C_Moneda("MXN"));

		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos imps = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos();
		Traslados t = new Traslados();
		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado tras = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado();
		tras.setImpuesto(new C_Impuesto("IVA"));
		tras.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
		tras.setTipoFactor(new C_TipoFactor("Tasa"));
		tras.setImpuesto(new C_Impuesto("002"));
		Conceptos conceptos = new Conceptos();
		com.tikal.cacao.model.Conceptos cps = conceptodao.consultar(f.getRfcEmisor());
		Map<String, com.tikal.cacao.model.Concepto> mapa = new HashMap<String, com.tikal.cacao.model.Concepto>();
		for (com.tikal.cacao.model.Concepto conce : cps.getConceptos()) {
			mapa.put(conce.getNoIdentificacion(), conce);
		}
		
		float total = 0;
		for (DatosConcepto d : f.getConceptos()) {
			Comprobante.Conceptos.Concepto con = new Comprobante.Conceptos.Concepto();
			com.tikal.cacao.model.Concepto conce = mapa.get(d.getClave());

			con.setCantidad(new BigDecimal(d.getCantidad()));
			con.setClaveProdServ(conce.getClaveProdServ());
			con.setUnidad(d.getUnidadMed());
			con.setClaveUnidad(new C_ClaveUnidad(conce.getClaveUnidad()));
			con.setClaveProdServ(conce.getClaveProdServ());
			con.setDescripcion(d.getDescripcion());
			con.setValorUnitario(new BigDecimal(d.getValorUnit()));
			con.setImporte(new BigDecimal(d.getImporte()));
			Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
			traslado.setBase(con.getImporte());
			traslado.setImporte(Util.redondearBigD(new BigDecimal(d.getImporte()*0.16), 6));
			traslado.setImpuesto(new C_Impuesto("002"));
			traslado.setTasaOCuota(new BigDecimal(f.getTasa()/100));
			traslado.setTipoFactor(new C_TipoFactor("Tasa"));
			traslados.getTraslado().add(traslado);
			impuestos.setTraslados(traslados);
			con.setImpuestos(impuestos);
			total += d.getValorUnit() * d.getCantidad();
			conceptos.getConcepto().add(con);
			t.getTraslado().add(traslado);
			
		}
		
		c.setConceptos(conceptos);
		Comprobante.Impuestos.Traslados trask= new Comprobante.Impuestos.Traslados();
		Comprobante.Impuestos.Traslados.Traslado trasl= new Comprobante.Impuestos.Traslados.Traslado();
		trasl.setImporte(Util.redondearBigD(new BigDecimal(f.getImp()),6));
		trasl.setImpuesto(new C_Impuesto("002"));
		trasl.setTipoFactor(new C_TipoFactor("Tasa"));	
		trasl.setTasaOCuota(Util.redondearBigD(new BigDecimal(f.getTasa()/100),6));
		
		trask.getTraslado().add(trasl);
		
		imps.setTraslados(trask);
		imps.setTotalImpuestosTrasladados(Util.redondearBigD(new BigDecimal(f.getImpTrasladados()),2));
		c.setImpuestos(imps);

		c.setSerie(f.getSerie());
		c.setFolio(f.getFolio());

		c.setTotal(Util.redondearBigD(new BigDecimal(f.getTotal()),2));
		c.setSubTotal(Util.redondearBigD(new BigDecimal(f.getSubtotal()),2));
		ComprobanteVO vo = new ComprobanteVO();
		vo.setComprobante(c);
		String respuesta = servicioFact.timbrar(vo, sesion, true);
		// facturarenglondao.eliminar(f.getId());
		RegistroBitacora bit = new RegistroBitacora();
		bit.setEvento(respuesta);
		bit.setTipo("Operacional");
		bit.setUsuario("Proceso Back");
		bit.setFecha(new Date());
		bitacoradao.addReg(bit);
		return "OK";

		// Comprobante c = new Comprobante();
		// Comprobante.Emisor emisor = new Comprobante.Emisor();
		// Empresa empresa = empresasDAO.consultar(f.getRfcEmisor());
		// emisor.setNombre(empresa.getNombre());
		// emisor.setRfc(empresa.getRFC());
		//
		// emisor.setRegimenFiscal(new
		// C_RegimenFiscal(f.getRegimen().split("-")[0]));
		//// emisor.getRegimenFiscal().add(reg);
		// c.setEmisor(emisor);
		// Comprobante.Receptor receptor = new Comprobante.Receptor();
		// List<Receptor> receptores =
		// emisorDAO.consultar(f.getRfcEmisor()).getReceptores();
		// Receptor recept = null;
		// for (Receptor r : receptores) {
		// if (r.getRfc().compareToIgnoreCase(f.getRfcReceptor()) == 0) {
		// recept = r;
		// break;
		// }
		// }
		// if(recept==null){
		// return "No se encontró al receptor con RFC: "+f.getRfcReceptor();
		// }
		// receptor.setNombre(recept.getNombre());
		// receptor.setRfc(recept.getRfc());
		// receptor.setUsoCFDI(new C_UsoCFDI(f.getUsoCFDI().split("-")[0]));
		//// receptor.setResidenciaFiscal(new C_Pais("MEX"));
		//
		//// receptor.setDomicilio(recept.getDomicilio());
		// c.setReceptor(receptor);
		// c.setVersion("3.3");
		// c.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
		//
		// c.setLugarExpedicion(new
		// C_CodigoPostal(empresa.getDireccion().getCodigoPostal()));
		// c.setTipoDeComprobante(new
		// C_TipoDeComprobante(f.getTipoComprobante().split("-")[0]));
		// String[] formadepago=f.getFormaPago().split("-");
		// c.setFormaPago(new C_FormaDePago(formadepago[0]));
		// String[] metododepago= f.getMetodoPago().split("-");
		// c.setMetodoPago(new C_MetodoDePago(metododepago[0]));
		// c.setMoneda(new C_Moneda("MXN"));
		//
		// Impuestos imps = new Impuestos();
		// Traslados t = new Traslados();
		// Traslado tras = new Traslado();
		// tras.setImpuesto(new C_Impuesto("IVA"));
		// tras.setTasaOCuota(new BigDecimal(0.16).setScale(2,
		// RoundingMode.HALF_UP));
		// tras.setTipoFactor(new C_TipoFactor("Tasa"));
		// tras.setImpuesto(new C_Impuesto("002"));
		//
		// Conceptos conceptos = new Conceptos();
		// Comprobante.Conceptos.Concepto con = new
		// Comprobante.Conceptos.Concepto();
		// con.setCantidad(new BigDecimal(f.getCantidad()).setScale(2,
		// RoundingMode.HALF_UP));
		// con.setDescripcion(f.getDescripcion());
		// con.setUnidad(f.getUnidad());
		// con.setClaveUnidad(new
		// C_ClaveUnidad(f.getCveUnidad().split("-")[0]));
		// con.setClaveProdServ(f.getCveProdServ().split("-")[0]);
		// float vu = Float.parseFloat(f.getValorUnitario());
		// float total= vu*con.getCantidad().floatValue();
		// if (f.getIvaIncluido().compareToIgnoreCase("si") == 0) {
		//
		// vu = vu / 1.16f;
		// float importe = vu * con.getCantidad().floatValue();
		// float impuesto = total-importe;
		// con.setValorUnitario(new BigDecimal(vu).setScale(2,
		// RoundingMode.HALF_UP));
		// con.setImporte(new BigDecimal(importe).setScale(2,
		// RoundingMode.HALF_UP));
		// Comprobante.Conceptos.Concepto.Impuestos impuestos= new
		// Comprobante.Conceptos.Concepto.Impuestos();
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados= new
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados();
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado=
		// new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
		// traslado.setBase(new BigDecimal(importe).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setImporte(new BigDecimal(impuesto).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setTasaOCuota(new BigDecimal(0.16).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setTipoFactor(new C_TipoFactor("Tasa"));
		// traslado.setImpuesto(new C_Impuesto("002"));
		// traslados.getTraslado().add(traslado);
		// impuestos.setTraslados(traslados);
		// con.setImpuestos(impuestos);
		//
		// c.setSubTotal(new BigDecimal(importe).setScale(2,
		// RoundingMode.HALF_UP));
		// c.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP));
		// tras.setImporte(new BigDecimal(impuesto).setScale(2,
		// RoundingMode.HALF_UP));
		//
		// } else {
		// con.setValorUnitario(new BigDecimal(vu).setScale(2,
		// RoundingMode.HALF_UP));
		// con.setImporte(new BigDecimal(total).setScale(2,
		// RoundingMode.HALF_UP));
		// Comprobante.Conceptos.Concepto.Impuestos impuestos= new
		// Comprobante.Conceptos.Concepto.Impuestos();
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados= new
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados();
		// Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado=
		// new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
		// traslado.setBase(new BigDecimal(total).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setImporte(new BigDecimal(total*0.16).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setTasaOCuota(new BigDecimal(0.16).setScale(2,
		// RoundingMode.HALF_UP));
		// traslado.setTipoFactor(new C_TipoFactor("Tasa"));
		// traslado.setImpuesto(new C_Impuesto("002"));
		// traslados.getTraslado().add(traslado);
		// impuestos.setTraslados(traslados);
		// con.setImpuestos(impuestos);
		// c.setSubTotal(new BigDecimal(total).setScale(2,
		// RoundingMode.HALF_UP));
		// c.setTotal(new BigDecimal(total*1.16).setScale(2,
		// RoundingMode.HALF_UP));
		// tras.setImporte(new BigDecimal(total*0.16).setScale(2,
		// RoundingMode.HALF_UP));
		// }
		// conceptos.getConcepto().add(con);
		// t.getTraslado().add(tras);
		// imps.setTraslados(t);
		// imps.setTotalImpuestosTrasladados(t.getTraslado().get(0).getImporte());
		// c.setImpuestos(imps);
		// c.setConceptos(conceptos);
		// Serial s = serialDAO.consultar(f.getRfcEmisor(), f.getSerie());
		//
		// if(s==null){
		// return "No se encontró la serie: "+f.getSerie();
		// }
		//
		// c.setSerie(s.getSerie());
		// c.setFolio(s.getFolio() + "");
		//
		// ComprobanteVO vo= new ComprobanteVO();
		// vo.setComprobante(c);
		// String respuesta=servicioFact.timbrar(vo,sesion);
		//// facturarenglondao.eliminar(f.getId());
		// RegistroBitacora bit= new RegistroBitacora();
		// bit.setEvento(respuesta);
		// bit.setTipo("Operacional");
		// bit.setUsuario("Proceso Back");
		// bit.setFecha(new Date());
		// bitacoradao.addReg(bit);
		// return "OK";
	}
	
	private String regresaClaveFormaDePago(String formaDePago) {
		switch(formaDePago.toUpperCase()) {
			case "EFECTIVO":
				return "01";
			case "CHEQUE NOMINATIVO":
				return "02";
			case "TRANSFERENCIA ELECTRÓNICA DE FONDOS":
				return "03";
			case "TARJETA DE CRÉDITO":
				return "04";
			case "TARJETA DE DÉBITO":
				return "28";
			case "POR DEFINIR":
				return "99";
		}
		return "";
	}
}
