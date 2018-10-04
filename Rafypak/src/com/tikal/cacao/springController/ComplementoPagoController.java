package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.ComplementoRenglonDAO;
import com.tikal.cacao.dao.PagosFacturaVttDAO;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.reporte.ComplementoRenglon;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_UsoCFDI;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Receptor;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.PagosFacturaVTTService;
import com.tikal.cacao.service.impl.FacturaVTTServiceImpl;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

import mx.gob.sat.pagos.Pagos;
import mx.gob.sat.pagos.Pagos.Pago;

@Controller
@RequestMapping(value={"/complementos"})
public class ComplementoPagoController {
	
	@Autowired
	FacturaVTTServiceImpl fvttservice;
	
	@Autowired
	ComplementoRenglonDAO complementosDAO;
	
	@Autowired
	PagosFacturaVTTService pagoService;
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	PerfilDAO perfilDAO;
	
	@Autowired
	PagosFacturaVttDAO pagosFacturaDAO;
	
	
	@RequestMapping(value={"timbrar"}, method= RequestMethod.POST, consumes="application/json")
	public void timbrar(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws UnsupportedEncodingException{
			AsignadorDeCharset.asignar(req, res);
			ComprobanteConComplementoPagosVO cVO=(ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
			FacturaVTT factura= fvttservice.consultar(cVO.getUuid());
			Comprobante c= Util.unmarshallCFDI33XML(factura.getCfdiXML());
			c.setSerie(cVO.getSerie().getSerie());
			c= this.prepararComprobante(c);
			
			Pagos complementoPagos= cVO.getComplementoPagos();
			Pago pago=complementoPagos.getPago().get(0);
			pago.getDoctoRelacionado().get(0).setFolio(c.getFolio());
			pago.getDoctoRelacionado().get(0).setSerie(c.getSerie());
			Complemento complemento= new Comprobante.Complemento();
//			c.setFecha(pago.getFechaPago());
			RespuestaWebServicePersonalizada respuesta=	pagoService.timbrar(cVO, c, cVO.getUuid());
			
			
			System.out.println(respuesta.getMensajeRespuesta());
	}
	
	@RequestMapping(value = "/cancelarAck", method = RequestMethod.POST)
	public void cancelarConAcuse(HttpServletRequest req, HttpServletResponse res, @RequestBody String body) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				String[] uuidYrfc = body.split(",");
				String textoRespuesta = pagoService.cancelarAck(uuidYrfc[0], uuidYrfc[1], req.getSession());
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value={"timbrarManual"}, method= RequestMethod.POST, consumes="application/json")
	public void timbrar2(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws IOException{
			AsignadorDeCharset.asignar(req, res);
			ComprobanteConComplementoPagosVO cVO=(ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
			
			Comprobante c= this.nuevoComplemento(cVO.getCfdi());
			
			Pagos complementoPagos= cVO.getComplementoPagos();
			
			Pago pago=complementoPagos.getPago().get(0);
			Complemento complemento= new Comprobante.Complemento();
//			c.setFecha(pago.getFechaPago());
			c.setSerie(cVO.getSerie().getSerie());
			RespuestaWebServicePersonalizada respuesta=	pagoService.timbrar(cVO, c, cVO.getUuid());
			
			res.getWriter().print(respuesta.getMensajeRespuesta());
			System.out.print(respuesta);
	}
	
	@RequestMapping(value = "/consultar/{rfc}/{page}", method = RequestMethod.GET)
	public void byrfc(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,@PathVariable int page) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		List<ComplementoRenglon> listaR = complementosDAO.consultarPag(rfc, page);
		res.getWriter().println(JsonConvertidor.toJson(listaR));
		
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void obtenerPDF(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				
				PagosFacturaVTT factura = pagosFacturaDAO.consultarUUID(uuid);
				PdfWriter pdfWriter = pagoService.obtenerPDF(factura, res.getOutputStream());
				if (pdfWriter != null) {
					res.setContentType("Application/Pdf");
					res.getOutputStream().flush();
					res.getOutputStream().close();
				} // FIN IF CUANDO EXISTE LA FACTURA
				
				// SI NO EXISTE LA FACTURA CON EL UUID ESPECIFICADO
				else {
					AsignadorDeCharset.asignar(req, res);
					PrintWriter writer = res.getWriter();
					writer.println(
							"El Número de Folio Fiscal (UUID): ".concat(uuid).concat(" no pertenece a ninguna factura"));
				}
				
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET, produces = "text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				PagosFacturaVTT factura = pagosFacturaDAO.consultarUUID(uuid);
				PrintWriter writer = res.getWriter();
				
				if (factura != null) {
					res.setContentType("text/xml");
					switch (factura.getEstatus()) {
						case TIMBRADO:
						case GENERADO:
							writer.println(factura.getCfdiXML());
							break;
						case CANCELADO:
							writer.println(factura.getAcuseCancelacionXML());
							break;
					}
				} else {
					writer.println("La factuca con el folio fiscal (uuid) ".concat(uuid).concat(" no existe"));
				}
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Comprobante nuevoComplemento(Comprobante c){
		ObjectFactoryComprobante33 factory= new ObjectFactoryComprobante33();
		Comprobante aux=factory.createComprobante();
		Conceptos conceptos= c.getConceptos();
		conceptos= factory.createComprobanteConceptos();
		Concepto conceptoPago= factory.createComprobanteConceptosConcepto();
		conceptoPago.setCantidad(new BigDecimal(1));
		conceptoPago.setClaveProdServ("84111506");
		conceptoPago.setClaveUnidad(new C_ClaveUnidad("ACT"));
		conceptoPago.setDescripcion("Pago");
		conceptoPago.setValorUnitario(new BigDecimal(0));
		conceptoPago.setImporte(new BigDecimal(0));
		conceptos.getConcepto().add(conceptoPago);
		aux.setEmisor(c.getEmisor());
		aux.setCertificado(null);
		aux.setTotal(new BigDecimal(0d));
		aux.setSubTotal(new BigDecimal(0d));
		aux.setImpuestos(null);
		aux.setNoCertificado(null);
		aux.setSello(null);
		aux.setConceptos(conceptos);
//		aux.setCondicionesDePago(c.getCondicionesDePago());
		aux.setReceptor(c.getReceptor());
		aux.setFecha(c.getFecha());
		aux.setFolio(c.getFolio());
//		aux.setFormaPago(c.getFormaPago());
		aux.setLugarExpedicion(c.getLugarExpedicion());
//		aux.setMetodoPago(c.getMetodoPago());
		aux.setMoneda(new C_Moneda("XXX"));
		aux.setSerie(c.getSerie());
		aux.setTipoDeComprobante(new C_TipoDeComprobante("P"));
		aux.setVersion("3.3");
		return aux;
	}
	
	private Comprobante prepararComprobante(Comprobante c){
		ObjectFactoryComprobante33 factory= new ObjectFactoryComprobante33();
		Comprobante aux=factory.createComprobante();
		Conceptos conceptos= c.getConceptos();
		conceptos= factory.createComprobanteConceptos();
		Concepto conceptoPago= factory.createComprobanteConceptosConcepto();
		conceptoPago.setCantidad(new BigDecimal(1));
		conceptoPago.setClaveProdServ("84111506");
		conceptoPago.setClaveUnidad(new C_ClaveUnidad("ACT"));
		conceptoPago.setDescripcion("Pago");
		conceptoPago.setValorUnitario(new BigDecimal(0));
		conceptoPago.setImporte(new BigDecimal(0));
		conceptos.getConcepto().add(conceptoPago); 	
		c.setCertificado(null);
		c.setTotal(new BigDecimal(0d));
		c.setSubTotal(new BigDecimal(0d));
		c.setImpuestos(null);
		c.setNoCertificado(null);
		c.setSello(null);
		aux.setConceptos(conceptos);
//		aux.setCondicionesDePago(c.getCondicionesDePago());
		aux.setEmisor(c.getEmisor());
		aux.setFecha(c.getFecha());
		aux.setFolio(c.getFolio());
//		aux.setFormaPago(c.getFormaPago());
		aux.setLugarExpedicion(c.getLugarExpedicion());
//		aux.setMetodoPago(c.getMetodoPago());
		aux.setMoneda(new C_Moneda("XXX"));
		Receptor receptor= c.getReceptor();
		receptor.setUsoCFDI(new C_UsoCFDI("P01"));
		aux.setReceptor(c.getReceptor());
		aux.setSerie(c.getSerie());
		aux.setSubTotal(c.getSubTotal());
		aux.setTipoCambio(c.getTipoCambio());
		aux.setTipoDeComprobante(new C_TipoDeComprobante("P"));
		aux.setTotal(c.getTotal());
		aux.setVersion(c.getVersion());
		
		return aux;
	}
	
	@RequestMapping(value = "/paginas/{rfc}", method = RequestMethod.GET,produces="text/xml")
	public void numpags(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		int paginas=complementosDAO.pags(rfc);
		res.getWriter().print(paginas);
	}

}
