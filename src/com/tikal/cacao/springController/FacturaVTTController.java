package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.FacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComentarioVO;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.PDFFacturaV33;
import com.tikal.cacao.util.Util;

@Controller
@RequestMapping(value = {"/factura33"})
public class FacturaVTTController {
	
	@Autowired
	private FacturaVTTService facturaVTTService;
	
	@Autowired
	private WSClientCfdi33 client33;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired 
	private PerfilDAO perfilDAO;
	
	ObjectFactoryComprobante33 of = new ObjectFactoryComprobante33();
	
	@PostConstruct
	public void init() {
		// init Web Service Timbrado Cfdi v3.3
		Jaxb2Marshaller marshallerV33 = new Jaxb2Marshaller();
		marshallerV33.setContextPath("org.tempuri");

		client33.setDefaultUri("https://cfdi33-pruebas.buzoncfdi.mx:1443/Timbrado.asmx");
		client33.setMarshaller(marshallerV33);
		client33.setUnmarshaller(marshallerV33);
	}
	
	@RequestMapping(value = "/registrarEmisor", method = RequestMethod.POST)
	public void registrarEmisor(HttpServletRequest req, HttpServletResponse res, @RequestBody String datos) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				String[] arrDatos = datos.split(",");
				String resultado = facturaVTTService.registrarEmisor(
						arrDatos[0], arrDatos[1], arrDatos[2], arrDatos[3], req.getSession());
				res.getWriter().println(resultado);
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			
		}
	}
	
	@RequestMapping(value = "/generar", method = RequestMethod.POST, consumes = "application/json")
	public void generar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		//ServicioSesion.verificarPermiso(req, usuariodao, perfildao, per);
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				ComprobanteConComentarioVO comprobanteConComentario = 
						(ComprobanteConComentarioVO) JsonConvertidor.fromJson(json, ComprobanteConComentarioVO.class);
				String resultado = facturaVTTService.generar(comprobanteConComentario, req.getSession());
				
				res.getWriter().println(resultado);
			} else {
				res.sendError(403);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/actualizar/{uuid}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void actualizar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json,
			@PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				ComprobanteConComentarioVO comprobanteConComentario = 
						(ComprobanteConComentarioVO) JsonConvertidor.fromJson(json, ComprobanteConComentarioVO.class);
				String resultado = facturaVTTService.actualizar(comprobanteConComentario, uuid, req.getSession());
				
				res.getWriter().println(resultado);
			} else {
				res.sendError(403);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/editar/{uuid}", method = RequestMethod.GET)
	public void cargaComprobante(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) throws IOException {
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				FacturaVTT prefactura = facturaVTTService.consultar(uuid);
				ComprobanteConComentarioVO compComentariosVO = new ComprobanteConComentarioVO();
				Comprobante c = Util.unmarshallCFDI33XML(prefactura.getCfdiXML());
				c.setFecha(null);
				
				compComentariosVO.setComentario(prefactura.getComentarios());
				compComentariosVO.setComprobante(c);
				res.getWriter().println(JsonConvertidor.toJsonComprobantes(compComentariosVO));
			} else {
				res.sendError(403);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrar", method = RequestMethod.POST, consumes = "application/json", produces = "text/html")
	public void timbrar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				ComprobanteVO cVO = (ComprobanteVO) JsonConvertidor.fromJson(json, ComprobanteVO.class);
				String textoRespuesta = facturaVTTService.timbrar(cVO, req.getSession(), false, null);
				res.setContentType("text/html");
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(403);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrar/{uuid}", method = RequestMethod.POST, consumes = "application/json")
	public void timbrarUUID(HttpServletRequest req, HttpServletResponse res, @RequestBody String json, @PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				String textoRespuesta = facturaVTTService.timbrar(json, uuid,req.getSession());
				res.setContentType("text/html");
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(403);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrarPrefactura", method = RequestMethod.POST, consumes = "application/json")
	public void timbrarPrefactura(HttpServletRequest req, HttpServletResponse res, @RequestBody String body) {
		try {
			AsignadorDeCharset.asignar(req, res);
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				String[] uuidYEmail = body.split(",");
				String textoRespuesta = facturaVTTService.timbrarCFDIGenerado(uuidYEmail[0], uuidYEmail[1], req.getSession() );
				res.setContentType("text/html");
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(403);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cancelarAck", method = RequestMethod.POST)
	public void cancelarConAcuse(HttpServletRequest req, HttpServletResponse res, @RequestBody String body) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				String[] uuidYrfc = body.split(",");
				String textoRespuesta = facturaVTTService.cancelarAck(uuidYrfc[0], uuidYrfc[1], req.getSession());
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void obtenerPDF(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				FacturaVTT factura = facturaVTTService.consultar(uuid);
				PdfWriter pdfWriter = facturaVTTService.obtenerPDF(factura, res.getOutputStream());
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
				FacturaVTT factura = facturaVTTService.consultar(uuid);
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
	
	
	@RequestMapping(value = "/emailTo", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void enviarEmail(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				String[] args= json.split(",");
				String email= args[0];
				String uuid= args[1];
				facturaVTTService.enviarEmail(email, uuid, req.getSession());
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/numPaginas/{rfc}", method = RequestMethod.GET, produces = "text/xml")
	public void numpags(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		int paginas = facturaVTTService.obtenerNumeroPaginas(rfc);
		res.getWriter().print(paginas);
	}
	
	@RequestMapping(value = "/obtenerFactura/{rfcEmisor}/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public void obtenerFactura() {
		
	}
	
	@RequestMapping(value = "/pruebaCFDI33", method = RequestMethod.GET, produces = "text/xml")
	public void pruebaCFDI33(HttpServletRequest req, HttpServletResponse res) {
		try {
			AsignadorDeCharset.asignar(req, res);
			res.setContentType("text/xml");
			Comprobante cfdi = this.getCFDI3p3();
			String xmlCFDI = Util.marshallComprobante33(cfdi);
			
			PrintWriter writer = res.getWriter(); 
			if (xmlCFDI != null) {
				writer.println(xmlCFDI);	
			} else {
				writer.println("<error>No se pudo generar el xml del cfdi</error>");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Comprobante getCFDI3p3() {
		Comprobante cfdi33 = of.createComprobante();
		cfdi33.setVersion("3.3");
		cfdi33.setSerie("S");
		cfdi33.setFolio("1");
		cfdi33.setTipoDeComprobante( new C_TipoDeComprobante("I") );
		return cfdi33;
	}
}
