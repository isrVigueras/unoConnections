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
import com.tikal.cacao.dao.DomicilioCEDAO;
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
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.C_Pais;
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
import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_FraccionArancelaria;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Addenda;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Traslados;
import com.tikal.cacao.service.FacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.tralix.AddendaFactory;
import com.tikal.unoconnections.tralix.Datos;
import com.tikal.unoconnections.tralix.DatosConcepto;

import mx.gob.sat.comercioexterior11.ComercioExterior;
import mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias.Mercancia;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CClavePedimento;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CINCOTERM;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CTipoOperacion;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CUnidadAduana;

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
	DomicilioCEDAO domdao;

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
			if (respuesta.compareTo("¡La factura se timbró con éxito!") == 0) {
				datosdao.elimiar(fr);
			} else {
				// something to do
			}

		}
		response.getWriter().print("OK");

	}

	private String timbrarDatos(Datos f, HttpSession sesion) {

		int tipo = this.tipoComprobante(f.getSerie());
		Comprobante c = new Comprobante();
		Comprobante.Emisor emisor = new Comprobante.Emisor();
		Empresa empresa = empresasDAO.consultar(f.getRfcEmisor());
		emisor.setNombre(empresa.getNombre());
		emisor.setRfc(empresa.getRFC());
		emisor.setRegimenFiscal(new C_RegimenFiscal("601"));
		c.setEmisor(emisor);
		Comprobante.Receptor receptor = new Comprobante.Receptor();
		if (f.getRFC().compareTo("XEXX010101000") == 0) {
			receptor.setNumRegIdTrib(f.getNumRegIdTrib());
			tipo = 2;
		}
		receptor.setNombre(f.getNombreReceptor());
		receptor.setRfc(f.getRFC());
		receptor.setUsoCFDI(new C_UsoCFDI("P01"));
		receptor.setResidenciaFiscal(new com.tikal.cacao.sat.cfd.catalogos.dyn.C_Pais(f.getPais()));

		// receptor.setDomicilio(recept.getDomicilio());
		c.setReceptor(receptor);
		c.setVersion("3.3");
		c.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));

		c.setLugarExpedicion(new C_CodigoPostal(empresa.getDireccion().getCodigoPostal()));
		if (tipo != 1) {
			c.setTipoDeComprobante(new C_TipoDeComprobante("I"));
		} else {
			c.setTipoDeComprobante(new C_TipoDeComprobante("E"));
		}
		c.setFormaPago(new C_FormaDePago(this.regresaClaveFormaDePago(f.getMetodoPago().toUpperCase())));
		c.setMetodoPago(new C_MetodoDePago("PPD"));
		if (f.getFormaPago().toLowerCase().contains("sola")) {
			c.setMetodoPago(new C_MetodoDePago("PUE"));
		}

		// c.setMoneda(new C_Moneda("MXN"));
		c.setMoneda(new C_Moneda(f.getMoneda()));
		if (!c.getMoneda().getValor().contentEquals("MXN")) {
			c.setTipoCambio(new BigDecimal(Float.toString(f.getTipoCambio())));
		}

		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos imps = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos();
		Traslados t = new Traslados();
		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado tras = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado();
		tras.setImpuesto(new C_Impuesto("IVA"));
		if (tipo == 0) {
			tras.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
		} else {
			tras.setTasaOCuota(new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP));
		}
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
			con.setImporte(Util.redondearBigD(new BigDecimal(d.getImporte()), 6));
			con.setNoIdentificacion(d.getClave());
			
			d.setUnidadAduana(conce.getUnidadAduana());

			Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
			traslado.setBase(con.getImporte());
			if (tipo == 0) {
				traslado.setImporte(Util.redondearBigD(new BigDecimal(d.getImporte() * 0.16), 6));
				traslado.setTasaOCuota(new BigDecimal(f.getTasa() / 100));
			} else {
				traslado.setImporte(Util.redondearBigD(new BigDecimal(0), 2));
				traslado.setTasaOCuota(new BigDecimal(0));
			}
			traslado.setImpuesto(new C_Impuesto("002"));
			traslado.setTipoFactor(new C_TipoFactor("Tasa"));
			traslados.getTraslado().add(traslado);
			impuestos.setTraslados(traslados);
			con.setImpuestos(impuestos);

			total += d.getValorUnit() * d.getCantidad();
			conceptos.getConcepto().add(con);
			// t.getTraslado().add(traslado);

		}

		c.setConceptos(conceptos);
		Comprobante.Impuestos.Traslados trask = new Comprobante.Impuestos.Traslados();
		Comprobante.Impuestos.Traslados.Traslado trasl = new Comprobante.Impuestos.Traslados.Traslado();
		trasl.setImporte(Util.redondearBigD(new BigDecimal(f.getImp()), 6));
		trasl.setImpuesto(new C_Impuesto("002"));
		trasl.setTipoFactor(new C_TipoFactor("Tasa"));
		if (tipo == 0) {
			trasl.setTasaOCuota(Util.redondearBigD(new BigDecimal(f.getTasa() / 100), 6));
		} else {
			trasl.setTasaOCuota(Util.redondearBigD(new BigDecimal(0.0), 6));
		}

		trask.getTraslado().add(trasl);

		imps.setTraslados(trask);
		imps.setTotalImpuestosTrasladados(Util.redondearBigD(new BigDecimal(f.getImpTrasladados()), 2));
		c.setImpuestos(imps);

		c.setSerie(f.getSerie());
		c.setFolio(f.getFolio());

		c.setTotal(Util.redondearBigD(new BigDecimal(f.getTotal()), 2));
		c.setSubTotal(Util.redondearBigD(new BigDecimal(f.getSubtotal()), 2));

		if (tipo == 2) {
			this.agregarComercioExterno(c, f);
		}

		ComprobanteVO vo = new ComprobanteVO();
		vo.setComprobante(c);

		FacturaVTT factura = new FacturaVTT();
		FacturaVTT.DatosExtra extra = factura.getDatosExtra();
		extra.setIdCliente(f.getIdCFD());
		extra.setIdShip(f.getIdShip());
		extra.setCondicionesPago(f.getCondPago());
		extra.setImporteichon(f.getTotalLetra());
		extra.setNuestroPedido(f.getnPedido());
		extra.setRepresentante(f.getRepVentas());
		extra.setShipCalle(f.getShipCalle());
		extra.setShipLocalidad(f.getShipColonia());
		extra.setShipPais(f.getShipPais());
		extra.setShipPostCode(f.getShipEstado());
		extra.setSuPedido(f.getsPedido());
		extra.setViaEmbarque(f.getViaEmbarque());
		extra.setSoldTo(f.getDireccion());
		
		this.addAddenda(c, f);

		String respuesta = servicioFact.timbrar(vo, sesion, true, extra);
		// facturarenglondao.eliminar(f.getId());
		RegistroBitacora bit = new RegistroBitacora();
		bit.setEvento(respuesta);
		bit.setTipo("Operacional");
		bit.setUsuario("Proceso Back");
		bit.setFecha(new Date());
		bitacoradao.addReg(bit);
		return respuesta;

	}

	private String regresaClaveFormaDePago(String formaDePago) {
		switch (formaDePago.toUpperCase()) {
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

	private void agregarComercioExterno(Comprobante c, Datos d) {
		mx.gob.sat.comercioexterior11.ObjectFactory of = new mx.gob.sat.comercioexterior11.ObjectFactory();
		ComercioExterior com = of.createComercioExterior();

		if(d.getIncoterm()!=null){
			com.setIncoterm(CINCOTERM.valueOf(d.getIncoterm()));
		}
		com.setNumeroExportadorConfiable(d.getNumExportConfiable());
		com.setObservaciones(d.getObservaciones());
		if (d.getSubdiv() != null) {
			com.setSubdivision(Integer.parseInt(d.getSubdiv()));
		}
		com.setTipoCambioUSD(Util.redondearBigD(new BigDecimal(d.getTipoCambio()), 2));
		com.setTotalUSD(Util.redondearBigD(new BigDecimal(d.getTotalUSD()), 2));
		if (d.getNumCertOrigen() == null) {
			com.setCertificadoOrigen(0);
		} else {
			if (d.getCertOrigen() != null) {
				com.setNumCertificadoOrigen(d.getNumCertOrigen());
				com.setCertificadoOrigen(Integer.parseInt(d.getCertOrigen()));
			} else {
				com.setCertificadoOrigen(0);
			}
		}
		com.setClaveDePedimento(CClavePedimento.A_1);

		mx.gob.sat.comercioexterior11.ComercioExterior.Emisor emisor = of.createComercioExteriorEmisor();
		mx.gob.sat.comercioexterior11.ComercioExterior.Emisor.Domicilio domicilio = of
				.createComercioExteriorEmisorDomicilio();

		emisor.setCurp(d.getCURP());

		emisor.setDomicilio(
				(mx.gob.sat.comercioexterior11.ComercioExterior.Emisor.Domicilio) domdao.get(d.getRfcEmisor()));
		com.setEmisor(emisor);

		mx.gob.sat.comercioexterior11.ComercioExterior.Receptor receptor = of.createComercioExteriorReceptor();
		mx.gob.sat.comercioexterior11.ComercioExterior.Receptor.Domicilio domrec = of
				.createComercioExteriorReceptorDomicilio();

		Direccion direccion = d.getDireccion();
		domrec.setCalle(direccion.getCalle());
		domrec.setCodigoPostal(direccion.getCodigoPostal());
		domrec.setColonia(direccion.getColonia());
		domrec.setEstado(direccion.getEstado());
		domrec.setLocalidad(direccion.getColonia());
		domrec.setNumeroExterior(direccion.getNumExterior());
		domrec.setPais(C_Pais.valueOf(d.getPais()));

		// receptor.setNumRegIdTrib(d.getNumRegIdTrib());
		receptor.setDomicilio(domrec);
		com.setReceptor(receptor);

		mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias mercs = of.createComercioExteriorMercancias();

		List<mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias.Mercancia> lista = mercs.getMercancia();

		List<Concepto> listac = c.getConceptos().getConcepto();

		for (int i = 0; i < d.getConceptos().size(); i++) {

			Concepto conc = listac.get(i);
			DatosConcepto de = d.getConceptos().get(i);

			Mercancia m = of.createComercioExteriorMercanciasMercancia();
			m.setCantidadAduana(conc.getCantidad());
			m.setFraccionArancelaria(new C_FraccionArancelaria(de.getFraccionArancelaria()));
			m.setNoIdentificacion(conc.getNoIdentificacion());
			String cua = de.getUnidadMed();
			if (cua.length() == 1) {
				cua = "0" + cua;
			}
			m.setUnidadAduana(CUnidadAduana.fromValue(de.getUnidadAduana()));
			m.setValorDolares(Util.redondearBigD(conc.getImporte(), 2));
			m.setValorUnitarioAduana(Util.redondearBigD(conc.getValorUnitario(), 2));
			lista.add(m);
			// m.setCantidadAduana(con.getCantidad());
			// m.setFraccionArancelaria();
		}
		com.setMercancias(mercs);
		com.setVersion("1.1");
		com.setTipoOperacion(CTipoOperacion.VALUE_1);
		com.setSubdivision(0);
		Complemento compl = new Comprobante.Complemento();
		compl.getAny().add(com);
		c.getComplemento().add(compl);
	}

	private void addAddenda(Comprobante c, Datos d) {

		Addenda ad = new Comprobante.Addenda();
		Object adden = AddendaFactory.getAdenda(d.getRFC(), d, c);
		ad.getAny().add(adden);
		if (adden != null) {
			c.setAddenda(ad);
		}
	}

	private int tipoComprobante(String formaDePago) {
		switch (formaDePago.toUpperCase()) {
		case "ATL":
			return 0;
		case "ATNC":
			return 1;

		}
		return 2;
	}
}
