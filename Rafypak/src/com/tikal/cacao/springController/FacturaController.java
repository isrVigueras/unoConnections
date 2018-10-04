package com.tikal.cacao.springController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.ws.client.WebServiceIOException;

import com.google.gson.internal.LinkedTreeMap;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.DatosDAO;
import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.FormatoFecha;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.model.Receptor;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.reporte.ReporteRenglon;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd.ObjectFactoryComprobante;
import com.tikal.cacao.sat.cfd.TUbicacion;
import com.tikal.cacao.sat.cfd.TUbicacionFiscal;
import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;
import com.tikal.cacao.springController.viewObjects.ComprobanteConComentarioVO;
import com.tikal.cacao.springController.viewObjects.ComprobanteVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.EmailSender;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.PDFFactura;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.exception.DatosTxtException;
import com.tikal.unoconnections.tralix.Datos;

import localhost.CancelaCFDIAckResponse;
import localhost.CancelaCFDIResponse;
import localhost.EncodeBase64;
import localhost.ObtieneCFDIResponse;
import localhost.ObtieneTimbresDisponiblesResponse;
import localhost.RegistraEmisorResponse;
import localhost.TimbraCFDIResponse;
import mx.gob.sat.cancelacfd.Acuse;
import mx.gob.sat.implocal.ImpuestosLocales;
import mx.gob.sat.implocal.ImpuestosLocales.RetencionesLocales;
import mx.gob.sat.implocal.ObjectFactory;

@Controller
@RequestMapping(value = { "/facturacion" })
public class FacturaController {
	
	@Autowired
	private WSClient client;
	
	@Autowired
	private WSClientCfdi33 client33;
	
	@Autowired
	private FacturaDAO facturaDAO;
	
	@Autowired
	private EmisorDAO emisorDAO;
	
	@Autowired
	private SerialDAO serialDAO;
	
	@Autowired
	private ImagenDAO imagenDAO;
	
	@Autowired
	private ReporteRenglonDAO repRenglonDAO;
	
	@Autowired
	private BitacoraDAO bitacoradao;
	
	@Autowired
	private DatosDAO datosdao;
	
	@PostConstruct
	public void init() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package with the WSDL java classes
		marshaller.setContextPath("localhost");
		
		client.setDefaultUri("http://www.timbracfdipruebas.mx/serviciointegracionpruebas/Timbrado.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		
		//init Web Service Timbrado Cfdi v3.3
		Jaxb2Marshaller marshallerV33 = new Jaxb2Marshaller();
		marshallerV33.setContextPath("org.tempuri");
		
		client33.setDefaultUri("https://cfdi33-pruebas.buzoncfdi.mx:1443/Timbrado.asmx");
		client33.setMarshaller(marshallerV33);
		client33.setUnmarshaller(marshallerV33);
	}
	
	@RequestMapping(value = "/obtenerReceptores/{rfcEmisor}", method = RequestMethod.GET, produces = "application/json")
	public void obtenerReceptores(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfcEmisor) {
		Emisor emisor = emisorDAO.consultar(rfcEmisor);
		List<Receptor> receptores = emisor.getReceptores();
		try {
			PrintWriter writer = res.getWriter();
			writer.println(receptores);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/multiple/{rfc}", method = RequestMethod.POST, produces="application/json")
 	public void multiple(HttpServletRequest req, HttpServletResponse res, @RequestBody String json, @PathVariable String rfc) throws IOException {
 		String[] args = json.split("CADENADEESCAPE");
 		List<Datos> lista= new ArrayList<Datos>();
 		for(String arg:args){
 			try {
 				Datos datos = new Datos(arg);
 	 			datos.setRfcEmisor(rfc);
 	 			lista.add(datos);
 			} catch (DatosTxtException e) {
 				Datos datosConExcepetion = new Datos();
 				datosConExcepetion.setRfcEmisor(rfc);
 				datosConExcepetion.setError(e.getMessage());
 				datosConExcepetion.setPausada(true);
 				lista.add(datosConExcepetion);
 			}
 			
 		}

 		datosdao.guardar(lista);
 		
 		res.getWriter().print(JsonConvertidor.toJson(lista));
	}
	
	@RequestMapping(value = "/eliminarFR", method = RequestMethod.POST, produces="application/json")
	public void eliminarFR(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		Datos fr= (Datos) JsonConvertidor.fromJson(json, Datos.class);
		datosdao.elimiar(fr);
	}
	
	@RequestMapping(value = "/activar", method = RequestMethod.POST, produces="application/json")
	public void activarFR(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		Datos fr= (Datos) JsonConvertidor.fromJson(json, Datos.class);
		fr.setPausada(false);
		datosdao.guardar(fr);
	}
	
	@RequestMapping(value = "/pendientes/{rfc}", method = RequestMethod.GET, produces="application/json")
 	public void pendientes(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		AsignadorDeCharset.asignar(req, res);
 		List<Datos> lista= new ArrayList<Datos>();
 		lista= datosdao.getByRFC(rfc);
 		res.getWriter().print(JsonConvertidor.toJson(lista));
	}
	
	@RequestMapping(value = "/emailTo", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void enviarEmail(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		String[] args= json.split(",");
		String email= args[0];
		String uuid= args[1];
		
		Factura factura = facturaDAO.consultar(uuid);
		
		EmailSender mailero = new EmailSender();
		
		Comprobante cfdi= Util.unmarshallXML(factura.getCfdiXML());
		Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
		String evento = "Se envió  la factura con id: " +factura.getUuid()+" al correo: "+email;
		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(req.getSession(), "Operacional", evento);
		bitacoradao.addReg(registroBitacora);
		mailero.enviaFactura(email, factura, "", cfdi.getComplemento().getAny().get(0).toString(),
				imagen);
	}
	
	@RequestMapping(value = "/timbresDisponibles", method = RequestMethod.GET, produces = "application/json")
	public void obtenerTimbresDisponibles(HttpServletRequest req, HttpServletResponse res) {
		ObtieneTimbresDisponiblesResponse timbresDispResponse = client.getObtieneTimbresDisponiblesResponse();
		List<Object> respuesta = timbresDispResponse.getObtieneTimbresDisponiblesResult().getAnyType();
		try {
			PrintWriter writer = res.getWriter();
			writer.println(JsonConvertidor.toJson(respuesta));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/numPaginasSerie/{rfc}/{serie}", method = RequestMethod.GET, produces = "text/xml")
	public void numpagsSerie(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,@PathVariable String serie) throws IOException {
		int paginas = repRenglonDAO.pags(rfc, serie);
		res.getWriter().print(paginas);
	}
	
	@RequestMapping(value = "/buscarSerie/{rfc}/{serie}/{page}", method = RequestMethod.GET, produces = "application/json")
	public void buscarSerie(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc, @PathVariable String serie,@PathVariable int page) {
		try {
			AsignadorDeCharset.asignar(req, res);
			List<ReporteRenglon> listaR = repRenglonDAO.consultarPag(rfc, serie, page);

			res.getWriter().println(JsonConvertidor.toJson(listaR));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/generar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void generar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		try {
			AsignadorDeCharset.asignar(req, res);
			PrintWriter writer = res.getWriter();
			
			ComprobanteConComentarioVO compConComent = (ComprobanteConComentarioVO) JsonConvertidor.fromJson(json, ComprobanteConComentarioVO.class);
			Comprobante c = compConComent.getComprobante();
			
			ImpuestosLocales impuestosLocales = compConComent.getImpuestosLocales();
			if (impuestosLocales != null) {
				ObjectFactoryComprobante of = new ObjectFactoryComprobante();
				c.setComplemento(of.createComprobanteComplemento());
				this.convertirImpuestosLocales(impuestosLocales);
				c.getComplemento().getAny().add(impuestosLocales);
			}
			//Comprobante c = (Comprobante) JsonConvertidor.fromJson(json, Comprobante.class);
			//incrementarFolio(c.getEmisor().getRfc(), c.getSerie());
			String cadenaComprobante = Util.marshallComprobante(c,false);
			
			Factura factura = new Factura(Util.randomString(10), cadenaComprobante, c.getEmisor().getRfc(), c.getReceptor().getNombre(),
					c.getFecha().toGregorianCalendar().getTime(), null, null);
			factura.setComentarios(compConComent.getComentarios());
			facturaDAO.guardar(factura);
			crearReporteRenglon(factura);
			writer.println("¡La factura se generó con éxito!"); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/actualizar/{uuid}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void actualizar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json,@PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			PrintWriter writer = res.getWriter();
			
			ComprobanteConComentarioVO compConComent = (ComprobanteConComentarioVO) JsonConvertidor.fromJson(json, ComprobanteConComentarioVO.class);
			Comprobante c = compConComent.getComprobante();
			
			ImpuestosLocales impuestosLocales = compConComent.getImpuestosLocales();
			if (impuestosLocales != null) {
				ObjectFactoryComprobante of = new ObjectFactoryComprobante();
				c.setComplemento(of.createComprobanteComplemento());
				this.convertirImpuestosLocales(impuestosLocales);
				c.getComplemento().getAny().add(impuestosLocales);
			}
			
			//Comprobante c = (Comprobante) JsonConvertidor.fromJson(json, Comprobante.class);
			//incrementarFolio(c.getEmisor().getRfc(), c.getSerie());
			String cadenaComprobante = Util.marshallComprobante(c,false);
			
			Factura factura = new Factura(uuid, cadenaComprobante, c.getEmisor().getRfc(), c.getReceptor().getNombre(),
					c.getFecha().toGregorianCalendar().getTime(), null, null);
			factura.setComentarios(compConComent.getComentarios());
			facturaDAO.guardar(factura);
			crearReporteRenglon(factura);
			writer.println("¡La factura se generó con éxito!"); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrar/{uuid}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void validarUuid(HttpServletRequest req, HttpServletResponse res, @RequestBody String json, @PathVariable String uuid) throws IOException {
		
		ComprobanteVO cVO = (ComprobanteVO) JsonConvertidor.fromJson(json, ComprobanteVO.class);
		Comprobante c = cVO.getComprobante();
		//Comprobante c = (Comprobante) JsonConvertidor.fromJson(json, Comprobante.class);
		
		ImpuestosLocales impuestosLocales = cVO.getImpuestosLocales();
		if (impuestosLocales != null) {
			ObjectFactoryComprobante of = new ObjectFactoryComprobante();
			c.setComplemento(of.createComprobanteComplemento());
			this.convertirImpuestosLocales(impuestosLocales);
			c.getComplemento().getAny().add(impuestosLocales);
		}
		
		this.redondearCantidades(c);
		
		Serial s= serialDAO.consultar(c.getEmisor().getRfc(), c.getSerie());
		c.setFolio(s.getFolio()+"");
		String cadenaComprobante = Util.marshallComprobante(c,false);
		TimbraCFDIResponse timbraCFDIResponse = client.getTimbraCFDIResponse(cadenaComprobante);
		
		try {
			AsignadorDeCharset.asignar(req, res);
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
			int codigoError = (int) respuesta.get(1);
			if (codigoError == 0) {
				String cfdiXML = (String) respuesta.get(3);
				Comprobante cfdi = Util.unmarshallXML(cfdiXML);
				String selloDigital = (String) respuesta.get(5);
				byte[] bytesQRCode = (byte[]) respuesta.get(4);
				
				//TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
				TimbreFiscalDigital timbreFD = Util.obtenerTimbreFiscalDigital(cfdi);
				Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
				Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(), cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
				factura.setComentarios(cVO.getComentarios());
				facturaDAO.guardar(factura);
				
				crearReporteRenglon(factura);
				incrementarFolio(c.getEmisor().getRfc(), c.getSerie());
				EmailSender mailero= new EmailSender();
				Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
				mailero.enviaFactura(cVO.getEmail(), factura, "", cfdi.getComplemento().getAny().get(0).toString(), imagen);
				//mailero.enviaFactura("israel.vigueras.ico@gmail.com", factura, "", cfdi.getComplemento().getAny().get(0).toString());
				/*Emisor emisor = emisorDAO.consultar(factura.getRfcEmisor());
				Receptor receptor = new Receptor(cfdi.getReceptor().getRfc(), cfdi.getReceptor().getNombre());
				if (emisor == null) {
					emisor = new Emisor(factura.getRfcEmisor());
				}
				emisor.getReceptores().add(receptor);
				emisorDAO.crear(emisor);*/
				facturaDAO.eliminar(uuid);
				repRenglonDAO.eliminar(uuid);
				writer.println("¡La factura se timbró con éxito!"); 
			}
			else {
				writer.println("Excepción en caso de error: " + respuesta.get(0));
				writer.println("Código de error: " + respuesta.get(1));
				writer.println("Mensaje de respuesta: " + respuesta.get(2));
			}
			
		} catch (WebServiceIOException e) {
			res.sendError(408);
		}
	}
	
	@RequestMapping(value = "/timbrar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void validar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		ComprobanteVO cVO = (ComprobanteVO) JsonConvertidor.fromJson(json, ComprobanteVO.class);
		Comprobante c = cVO.getComprobante();
		
		ImpuestosLocales impuestosLocales = cVO.getImpuestosLocales();
		if (impuestosLocales != null) {
			ObjectFactoryComprobante of = new ObjectFactoryComprobante();
			c.setComplemento(of.createComprobanteComplemento());
			this.convertirImpuestosLocales(impuestosLocales);
			c.getComplemento().getAny().add(impuestosLocales);
		}
		
		this.redondearCantidades(c);
		
		//Comprobante c = (Comprobante) JsonConvertidor.fromJson(json, Comprobante.class);
		Serial s= serialDAO.consultar(c.getEmisor().getRfc(), c.getSerie());
		c.setFolio(s.getFolio()+"");
		String cadenaComprobante = Util.marshallComprobante(c,false);
		TimbraCFDIResponse timbraCFDIResponse = client.getTimbraCFDIResponse(cadenaComprobante);
		
		try {
			AsignadorDeCharset.asignar(req, res);
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
			int codigoError = (int) respuesta.get(1);
			if (codigoError == 0) {
				String cfdiXML = (String) respuesta.get(3);
				Comprobante cfdi = Util.unmarshallXML(cfdiXML);
				String selloDigital = (String) respuesta.get(5);
				byte[] bytesQRCode = (byte[]) respuesta.get(4);
				//TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
				TimbreFiscalDigital timbreFD = Util.obtenerTimbreFiscalDigital(cfdi);
				Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
				Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(), cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
				factura.setComentarios(cVO.getComentarios());
				facturaDAO.guardar(factura);
				
				crearReporteRenglon(factura);
				EmailSender mailero= new EmailSender();
				Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
				incrementarFolio(c.getEmisor().getRfc(), c.getSerie());
				try{
					mailero.enviaFactura(cVO.getEmail(), factura, "", cfdi.getComplemento().getAny().get(0).toString(), imagen);
				}catch(Exception e){
					writer.println("¡La factura se timbró con éxito!, ocurrió un error al enviar a "+ cVO.getEmail());
				}
				writer.println("¡La factura se timbró con éxito!"); 
			}
			else {
				writer.println("Excepción en caso de error: " + respuesta.get(0));
				writer.println("Código de error: " + respuesta.get(1));
				writer.println("Mensaje de respuesta: " + respuesta.get(2));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/timbrarGenerado", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void timbrarCfdiGenerado(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		//FacturaRO objResp = (FacturaRO) JsonConvertidor.fromJson(json, FacturaRO.class);
		//String strResp = (String) JsonConvertidor.fromJson(json, String.class);
		String[] uuidYEmail = json.split(",");
		Factura f = facturaDAO.consultar(uuidYEmail[0]);
		
		Comprobante comprobante = Util.unmarshallXML(f.getCfdiXML());
		Serial s= serialDAO.consultar(comprobante.getEmisor().getRfc(), comprobante.getSerie());
		
		comprobante.setFolio(s.getFolio()+"");
		
		this.redondearCantidades(comprobante);
		//Comprobante comprobante = objResp.getComprobante();
		//List<Serial> listaSerial = serialDAO.consultar(comprobante.getEmisor().getRfc());
		//String uuidTemp = objResp.getUuid();
		
		//Factura factSinTimbrar = facturaDAO.consultar(uuidTemp);
		//String cadenaComprobante = Util.marshallComprobante(comprobante, false);
		//String cadenaComprobante = factSinTimbrar.getCfdiXML();
		String cadenaComprobante = Util.marshallComprobante(comprobante,false);
		TimbraCFDIResponse timbraCFDIResponse = client.getTimbraCFDIResponse(cadenaComprobante);
		
		try {
			AsignadorDeCharset.asignar(req, res);
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
			int codigoError = (int) respuesta.get(1);
			if (codigoError == 0) {
				String cfdiXML = (String) respuesta.get(3);
				Comprobante cfdi = Util.unmarshallXML(cfdiXML);
				String selloDigital = (String) respuesta.get(5);
				byte[] bytesQRCode = (byte[]) respuesta.get(4);
				
				//TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
				TimbreFiscalDigital timbreFD = Util.obtenerTimbreFiscalDigital(cfdi);
				Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
				Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(), cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
				factura.setComentarios(f.getComentarios());
				facturaDAO.guardar(factura);
				
				crearReporteRenglon(factura);
				EmailSender mailero= new EmailSender();
				Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
				
				//mailero.enviaFactura("israel.vigueras.ico@gmail.com", factura, "", cfdi.getComplemento().getAny().get(0).toString());
				// eliminar la factura no timbrada del datastore
				facturaDAO.eliminar(uuidYEmail[0]);
				repRenglonDAO.eliminar(uuidYEmail[0]);
				/*Emisor emisor = emisorDAO.consultar(factura.getRfcEmisor());
				Receptor receptor = new Receptor(cfdi.getReceptor().getRfc(), cfdi.getReceptor().getNombre());
				if (emisor == null) {
					emisor = new Emisor(factura.getRfcEmisor());
				}
				emisor.getReceptores().add(receptor);
				emisorDAO.crear(emisor);*/
				incrementarFolio(cfdi.getEmisor().getRfc(), cfdi.getSerie());
				mailero.enviaFactura(uuidYEmail[1], factura, "", cfdi.getComplemento().getAny().get(0).toString(), imagen);
				writer.println("¡La factura se timbró con éxito!"); 
			}
			else {
				writer.println("Excepción en caso de error: " + respuesta.get(0));
				writer.println("Código de error: " + respuesta.get(1));
				writer.println("Mensaje de respuesta: " + respuesta.get(2));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*@RequestMapping(value = "/validarCFD32", method = RequestMethod.GET)
	public void validarCFD32(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Comprobante c = crearComprobante();
		res.getWriter().println(this.validarComprobante(c));
	}*/
	
	@RequestMapping(value = "/registrarEmisor", method = RequestMethod.POST)
	public void registrarEmisor(HttpServletRequest req, HttpServletResponse res, @RequestBody String datos) {
//		String[] arrDatos = datos.split(","); //[0]-cer, [1]-ker, [2]-pwd, [3]-rfc
//		DatosEmisor datosEmisor = new DatosEmisor();
//		datosEmisor.setRfc(arrDatos[3]);
//		datosEmisor.setPswd(arrDatos[2]);
//		ofy().save().entity(datosEmisor).now();
		
		HttpURLConnection connCer = null;
		HttpURLConnection connKey = null;
		//ByteArrayInputStream objKey = null;
		ByteArrayInputStream objCer = null;
		String[] arrDatos = datos.split(","); //[0]-cer, [1]-ker, [2]-pwd, [3]-rfc
		
		String strUrlCer = "https://facturacion.tikal.mx/cers/".concat(arrDatos[0]);
		String strUrlKey = "https://facturacion.tikal.mx/cers/".concat(arrDatos[1]);
		try {
			
			URL urlCer = new URL(strUrlCer);
			
			connCer = (HttpURLConnection) urlCer.openConnection();
			connCer.connect();
			objCer = (ByteArrayInputStream) connCer.getContent();
			connCer.disconnect();
			connCer = null;
			urlCer = null;
			
			URL urlKey = new URL(strUrlKey);
			
			connKey = (HttpURLConnection) urlKey.openConnection(); //TODO probar
			connKey.connect();
			InputStream objKey = connKey.getInputStream();
			//objKey = (ByteArrayInputStream) urlKey.getContent();
			
			RegistraEmisorResponse regEmResponse = client.getRegistraEmisorResponse(arrDatos[3], arrDatos[2], objCer, objKey);
		
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = regEmResponse.getRegistraEmisorResult().getAnyType();
			int codigoError = (int) respuesta.get(1);
			String mensajeRespuesta = (String) respuesta.get(2);
			if (codigoError == 0) {
				writer.print("Los archivos del emisor fueron registrados. ".concat(mensajeRespuesta));
			} else {
				writer.print(mensajeRespuesta);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	/*@RequestMapping(value = "/registrarEmisor33", method = RequestMethod.GET)
	public void registrarEmisor33(HttpServletRequest req, HttpServletResponse res) {
		org.tempuri.RegistraEmisorResponse regEmResponse = client33.getRegistraEmisorResponse();
		try {
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = regEmResponse.getRegistraEmisorResult().getAnyType();
			escribirRespuesta(respuesta, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	@RequestMapping(value = "/pruebaTimbrar", method = RequestMethod.GET)
	public void timbrar(HttpServletRequest req, HttpServletResponse res) {
		Comprobante comprobante = crearComprobante();
		String cadenaComprobante = Util.marshallComprobante(comprobante, false);
		TimbraCFDIResponse timbraCFDIResponse = client.getTimbraCFDIResponse(cadenaComprobante);
	
		try {
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
			writer.println("Excepción en caso de error: " + respuesta.get(0));
			writer.println("Código de error: " + respuesta.get(1));
			writer.println("Mensaje de respuesta: " + respuesta.get(2));
			writer.println("XML certificado o timbrado: " + respuesta.get(3)); //TODO descargar el archivo xml
			writer.println("QRCode: " + respuesta.get(4)); //TODO transformar el arreglo de bytes para obtener la imagen del QRCode
			writer.println("Sello digital del comprobante: " + respuesta.get(5));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrar33", method = RequestMethod.GET)
	public void timbrar33(HttpServletRequest req, HttpServletResponse res) {
		org.tempuri.TimbraCFDIResponse respuesta = client33.getTimbraCFDIResponse("");
		try {
			PrintWriter writer = res.getWriter();
			List<Object> list = respuesta.getTimbraCFDIResult().getAnyType();
			writer.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public void cancelar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		String[] uuidYrfc = json.split(",");
		
		CancelaCFDIResponse cancelaCFDIResponse = client.getCancelaCFDIResponse(uuidYrfc[0], uuidYrfc[1]);
		
		try {
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = cancelaCFDIResponse.getCancelaCFDIResult().getAnyType();
			escribirRespuesta(respuesta, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cancelarAck", method = RequestMethod.POST)
	public void cancelarAck(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) {
		String[] uuidYrfc = json.split(",");
		CancelaCFDIAckResponse cancelaCFDIAckResponse = client.getCancelaCFDIAckResponse(uuidYrfc[0], uuidYrfc[1]);
		Factura factura = facturaDAO.consultar(uuidYrfc[0]);
		ReporteRenglon repRenglon = repRenglonDAO.consultar(uuidYrfc[0]);
		try {
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = cancelaCFDIAckResponse.getCancelaCFDIAckResult().getAnyType();
			int codigoError = (int) respuesta.get(1);
			
			if (codigoError == 0) {
				String acuseXML = (String) respuesta.get(3);
				StringBuilder stringBuilder = new StringBuilder(acuseXML);
				stringBuilder.insert(99, " xmlns=\"http://cancelacfd.sat.gob.mx\" ");
				String acuseXML2 = stringBuilder.toString();
				factura.setAcuseCancelacionXML(acuseXML2);
				Acuse acuse = Util.unmarshallAcuseXML(acuseXML2);
				if (acuse != null) {
					EncodeBase64 encodeBase64 = new EncodeBase64();
					String sello = new String(acuse.getSignature().getSignatureValue(),"ISO-8859-1");
					String selloBase64 = encodeBase64.encodeStringSelloCancelacion(sello);
					factura.setFechaCancelacion(acuse.getFecha().toGregorianCalendar().getTime());
					factura.setSelloCancelacion(selloBase64);
				} else {
					stringBuilder = new StringBuilder(acuseXML);
					stringBuilder.insert(100, " xmlns=\"http://cancelacfd.sat.gob.mx\" ");
					acuseXML2 = stringBuilder.toString();
					factura.setAcuseCancelacionXML(acuseXML2);
					acuse = Util.unmarshallAcuseXML(acuseXML2);
					if (acuse != null) {
						EncodeBase64 encodeBase64 = new EncodeBase64();
						String sello = new String(acuse.getSignature().getSignatureValue(),"ISO-8859-1");
						String selloBase64 = encodeBase64.encodeStringSelloCancelacion(sello);
						factura.setFechaCancelacion(acuse.getFecha().toGregorianCalendar().getTime());
						factura.setSelloCancelacion(selloBase64);
					}
				}
				factura.setEstatus(Estatus.CANCELADO);
				repRenglon.setStatus(Estatus.CANCELADO.toString());
				facturaDAO.guardar(factura);
				repRenglonDAO.guardar(repRenglon);
			}
			writer.println(JsonConvertidor.toJson(respuesta));
			//escribirRespuesta(respuesta, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET,produces="application/pdf")
	public void consultar(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) throws MalformedURLException, DocumentException, IOException {
		Factura factura = facturaDAO.consultar(uuid);
		if (factura != null) {
				res.setContentType("Application/Pdf");
				Comprobante cfdi = Util.unmarshallXML(factura.getCfdiXML());
				Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
				
				PDFFactura pdfFactura = new PDFFactura();
				PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), res.getOutputStream());
				pdfFactura.getPieDePagina().setUuid(uuid);
				if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
					pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());;
				}
				writer.setPageEvent(pdfFactura.getPieDePagina());
				
				pdfFactura.getDocument().open();
				if (factura.getEstatus().equals(Estatus.TIMBRADO))
					pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen, factura.getEstatus(), factura.getComentarios());
				else if (factura.getEstatus().equals(Estatus.GENERADO)) {
					pdfFactura.construirPdf(cfdi, imagen, factura.getEstatus(), factura.getComentarios());
					
					PdfContentByte fondo = writer.getDirectContent();
					Font fuente = new Font(FontFamily.HELVETICA, 45);
					Phrase frase = new Phrase("Pre-factura", fuente);
					fondo.saveState();
					PdfGState gs1 = new PdfGState();
					gs1.setFillOpacity(0.5f);
					fondo.setGState(gs1);
					ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 650, 45);
					fondo.restoreState();
				}
				
				else if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), 
							factura.getCodigoQR(), imagen, factura.getEstatus(), factura.getSelloCancelacion(), factura.getFechaCancelacion());
					
					pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
				}
				
				pdfFactura.getDocument().close();
				res.getOutputStream().flush();
				res.getOutputStream().close();
		
		}
		else {
			try {
				AsignadorDeCharset.asignar(req, res);
				PrintWriter writer = res.getWriter();
				writer.println("El Número de Folio Fiscal (UUID): ".concat(uuid).concat(" no pertenece a ninguna factura"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET,produces="text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			Factura factura = facturaDAO.consultar(uuid);
			PrintWriter writer = res.getWriter();
			if (factura != null) {
				res.setContentType("text/xml");
				writer.println(factura.getCfdiXML());
			}
			else {
				writer.println("La factuca con el folio fiscal (uuid) ".concat(uuid).concat(" no existe"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * &Eacute;ste m&eacute;todo no debe ser visible en el frontend. Se debe invocar cuando no
	 * aparezca un objeto {@code Factura} en el Datastore pero si se encuentre su correspondiente
	 * con el proveedor 
	 * @param req
	 * @param res
	 * @param rfcEmisor
	 * @param uuid
	 */
	@RequestMapping(value = "/obtenerFactura/{rfcEmisor}/{uuid}", method = RequestMethod.GET,produces="application/json")
	public void obtenerFactura(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfcEmisor, @PathVariable String uuid) {
		try {
			PrintWriter writer = res.getWriter();
			Factura fac = facturaDAO.consultar(uuid);
			if (fac != null) {
				writer.print(JsonConvertidor.toJson(fac));
			} 
			
			else {
				ObtieneCFDIResponse obtiCfdiResponse = client.getObtieneCFDIResponse(uuid, rfcEmisor);
				List<Object> resultado = obtiCfdiResponse.getObtieneCFDIResult().getAnyType();
				int codigoError = (int) resultado.get(1);
				if (codigoError == 0) {
					String cfdiXML = (String) resultado.get(3);
					Comprobante cfdi = Util.unmarshallXML(cfdiXML);
					String selloDigital = (String) resultado.get(5);
					byte[] bytesQRCode = (byte[]) resultado.get(4);
					TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
					Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
					Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(), cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
					facturaDAO.guardar(factura);
					crearReporteRenglon(factura);
					writer.print("La factura con uuid: " + factura.getUuid() + " ha sido guardada");
				}
				
				else {
					writer.println("Excepción en caso de error: " + resultado.get(0));
					writer.println("Código de error: " + resultado.get(1));
					writer.println("Mensaje de respuesta: " + resultado.get(2));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Si se usa agregar los parámetros del rfc
	/*@RequestMapping(value = "/wsObtenerPDF/{uuid}", method = RequestMethod.GET,produces="application/pdf")
	public void obtenerPDF(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		ObtieneCFDIResponse obtieneCFDIResponse = client.getObtieneCFDIResponse(uuid);
		//ObtieneCFDIResponse obtieneCFDIResponse = client.getObtieneCFDIResponse("92814078-E094-4FE6-AB0A-AC55EFC14D23");
		try {
			res.setContentType("Application/Pdf");
			List<Object> respuesta = obtieneCFDIResponse.getObtieneCFDIResult().getAnyType();
			Comprobante cfdi = Util.unmarshallXML((String) respuesta.get(3));
			Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
			byte[] arregloBytesQR = (byte[]) respuesta.get(4);
			String selloDigital = (String) respuesta.get(5);
			
			PDFFactura pdfFactura = new PDFFactura();
			PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), res.getOutputStream());
			pdfFactura.getDocument().open();
			pdfFactura.construirPdf(cfdi, selloDigital, arregloBytesQR, imagen);
			pdfFactura.getDocument().close();
			res.getOutputStream().flush();
			res.getOutputStream().close();
	
		} catch (SocketTimeoutException e) {
			try {
				PrintWriter writer = res.getWriter();
				writer.println("El servicio de consulta de factura no responde. Por favor intenté de nuevo (presione F5)");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}*/
	
	/*@RequestMapping(value = "/obtener33", method = RequestMethod.GET)
	public void obtener33(HttpServletRequest req, HttpServletResponse res) {
		// el UUID esta hardcoded
		org.tempuri.ObtieneCFDIResponse obtieneCFDIResponse = client33.getObtieneCFDIResponse("f0ff847b-e68c-43d4-8e95-29a8393d1b67");
		try {
			PrintWriter writer = res.getWriter();
			List<Object> respuesta = obtieneCFDIResponse.getObtieneCFDIResult().getAnyType();
			writer.println(respuesta);
//			escribirRespuesta(respuesta, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	@RequestMapping(value = "/editar/{uuid}", method = RequestMethod.GET)
	public void cargaComprobante(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		Factura f=facturaDAO.consultar(uuid);
		ComprobanteConComentarioVO compComentariosVO = new ComprobanteConComentarioVO();
		Comprobante c= Util.unmarshallXML(f.getCfdiXML());
		if (c.getComplemento() != null) {
			List<Object> any = c.getComplemento().getAny();
			ImpuestosLocales impLocales = null;
			for (Object object : any) {
				if (object instanceof ImpuestosLocales) {
					impLocales = (ImpuestosLocales) object;
					break;
				}
			}
			compComentariosVO.setImpuestosLocales(impLocales);
		}
		c.setFecha(null);
		
		compComentariosVO.setComentarios(f.getComentarios());
		compComentariosVO.setComprobante(c);
		res.getWriter().println(JsonConvertidor.toJsonComprobantes(compComentariosVO));
	}
	
	
	@RequestMapping(value = "/consultar/{rfc}/{page}", method = RequestMethod.GET)
	public void byrfc(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,@PathVariable int page) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		List<ReporteRenglon> listaR = repRenglonDAO.consultarPag(rfc, page);
		res.getWriter().println(JsonConvertidor.toJson(listaR));
		
		
		/*List<Factura>lista= facturaDAO.consutarTodas(rfc,page);
		List<FacturaVO> listaVO = new ArrayList<>();
		for(Factura f:lista){
			Comprobante c= Util.unmarshallXML(f.getCfdiXML());
			f.setCfdi(c);
			FacturaVO fVO = new FacturaVO();
			fVO.setUuid(f.getUuid());
			fVO.setEstatus(f.getEstatus());
			fVO.setFolio(c.getSerie()+c.getFolio());
			fVO.setTotal(NumberFormat.getCurrencyInstance().format(f.getCfdi().getTotal().doubleValue()));
			fVO.setFechaCertificacion(f.getFechaCertificacion());
			fVO.setRfcReceptor(f.getCfdi().getReceptor().getRfc());
			listaVO.add(fVO);
			
			if (f.getFechaCertificacion() == null && f.getEstatus().equals(Estatus.GENERADO)) {
				f.setFechaCertificacion(c.getFecha().toGregorianCalendar().getTime());
			}
		}
		//res.getWriter().println(JsonConvertidor.toJsonComprobantes(lista));
		res.getWriter().println(JsonConvertidor.toJson(listaVO));*/
	}
	@RequestMapping(value = "/numPaginas/{rfc}", method = RequestMethod.GET,produces="text/xml")
	public void numpags(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		int paginas=facturaDAO.getPaginas(rfc);
		res.getWriter().print(paginas);
	}
	
	@RequestMapping(value = "/numPaginasRec/{rfc}/{receptor}", method = RequestMethod.GET, produces = "text/xml")
	public void numpagsRec(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,@PathVariable String receptor) throws IOException {
		int paginas = repRenglonDAO.pagsRec(rfc, receptor);
		res.getWriter().print(paginas);
	}
	
	@RequestMapping(value = "/buscarRec/{rfc}/{receptor}/{page}", method = RequestMethod.GET, produces = "application/json")
	public void buscarRec(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc, @PathVariable String receptor,@PathVariable int page) {
		try {
			List<ReporteRenglon> listaR = repRenglonDAO.consultarPagRec(rfc, receptor, page);

			res.getWriter().println(JsonConvertidor.toJson(listaR));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/buscar/{rfc}", method = RequestMethod.GET,produces="application/json")
	public void buscar(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) {
		try {
			AsignadorDeCharset.asignar(req, res);
			String fi= (String)req.getParameter("fi");
			String ff= (String)req.getParameter("ff");
		    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	        Date datei = formatter.parse(fi);
	        Date datef= formatter.parse(ff);
	        
	      //agregar serie en el @RequestMapping
	        List<ReporteRenglon> listaR = repRenglonDAO.consultar(rfc, null, datei, datef);
	        
	        /*List<Factura> lista=facturaDAO.buscar(datei, datef,rfc);
	        List<FacturaVO> listaVO=new ArrayList<FacturaVO>();
	    	for(Factura f:lista){
				Comprobante c= Util.unmarshallXML(f.getCfdiXML());
				f.setCfdi(c);
				FacturaVO fVO = new FacturaVO();
				fVO.setUuid(f.getUuid());
				fVO.setEstatus(f.getEstatus());
				fVO.setTotal(NumberFormat.getCurrencyInstance().format(f.getCfdi().getTotal().doubleValue()));
				fVO.setFechaCertificacion(f.getFechaCertificacion());
				fVO.setRfcReceptor(f.getCfdi().getReceptor().getRfc());
				listaVO.add(fVO);
				
				if (f.getFechaCertificacion() == null && f.getEstatus().equals(Estatus.GENERADO)) {
					f.setFechaCertificacion(c.getFecha().toGregorianCalendar().getTime());
				}
			}
	        res.getWriter().println(JsonConvertidor.toJson(listaVO));*/
	        res.getWriter().println(JsonConvertidor.toJson(listaR));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	

	
	private void incrementarFolio(String rfc, String serie) {
		if (rfc != null && serie != null) {
			Serial serial = serialDAO.consultar(rfc, serie);
			if (serial != null) {
				serial.incrementa();
				serialDAO.guardar(serial);
			}
		}
	}
	
	private void crearReporteRenglon(Factura factura) {
		ReporteRenglon reporteRenglon = new ReporteRenglon(factura);
		repRenglonDAO.guardar(reporteRenglon);
	}
	
	private void escribirRespuesta(List<Object> respuesta, PrintWriter writer) {
		writer.println("Excepción en caso de error: " + respuesta.get(0));
		writer.println("Código de error: " + respuesta.get(1));
		writer.println("Mensaje de respuesta: " + respuesta.get(2));
		writer.println("XML certificado o timbrado: " + respuesta.get(3)); //TODO descargar el archivo xml
		writer.println("QRCode: " + respuesta.get(4)); //TODO transformar el arreglo de bytes para obtener la imagen del QRCode
		writer.println("Sello digital del comprobante: " + respuesta.get(5));
	}

	/*private String validarComprobante(Comprobante c) {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("org.tempuri");
		
		ValidarCfd32Client client = new ValidarCfd32Client();
		client.setDefaultUri("http://sandbox.timbrevirtual.com/VirtualCFDIWS_TEST/WSTimbreVirtual.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		
		ValidaCfd32Response validaCfd32Response = client.getValidaCfd32(Util.marshallComprobante(c, false));
		return validaCfd32Response.getValidaCfd32Result();
	}*/
	
	
	
	private Comprobante crearComprobante() {
		ObjectFactoryComprobante ofComprobante = new ObjectFactoryComprobante();
		Comprobante comprobante = ofComprobante.createComprobante();
		
		comprobante.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
		comprobante.setLugarExpedicion("MÉXICO");
		comprobante.setTipoDeComprobante("ingreso");
		comprobante.setFormaDePago("CONTADO");
		comprobante.setMetodoDePago("CONTADO");
		comprobante.setSubTotal(new BigDecimal(200));
		comprobante.setTotal(new BigDecimal(200.0));
		comprobante.setVersion("3.2");
		
		Comprobante.Emisor emisor = ofComprobante.createComprobanteEmisor();
		emisor.setRfc("AAA010101AAA");//para prueba con ValidarCfd32Client CARC7302234T5
		emisor.setNombre("CARLOS CASTILLO ROJAS");
		
		TUbicacionFiscal ubicacionFiscalEmisor = ofComprobante.createTUbicacionFiscal();
		ubicacionFiscalEmisor.setCalle("MARTIN CARRERA");
		ubicacionFiscalEmisor.setNoExterior("94");
		ubicacionFiscalEmisor.setColonia("MARTIN CARRERA");
		ubicacionFiscalEmisor.setLocalidad("MEXICO, D.F.");
		ubicacionFiscalEmisor.setMunicipio("GUSTAVO A. MADERO");
		ubicacionFiscalEmisor.setEstado("Ciudad de México");
		ubicacionFiscalEmisor.setPais("MEXICO");
		ubicacionFiscalEmisor.setCodigoPostal("07070");
		emisor.setDomicilioFiscal(ubicacionFiscalEmisor);
		
		Comprobante.Emisor.RegimenFiscal regimenFiscalEmisor = ofComprobante.createComprobanteEmisorRegimenFiscal();
		regimenFiscalEmisor.setRegimen("REGIMEN DE INCORPORACION FISCAL");
		emisor.getRegimenFiscal().add(regimenFiscalEmisor);
		comprobante.setEmisor(emisor);
		
		Comprobante.Receptor receptor = ofComprobante.createComprobanteReceptor();
		receptor.setRfc("RCU100301BB8");
		receptor.setNombre("REPARACIÓN DE CAMIONES Y UNIDADES ACCIDENTADAS LA VILLA S.A. DE C.V.");
		
		TUbicacion ubicacionReceptor = ofComprobante.createTUbicacion();
		ubicacionReceptor.setCalle("FRANCISCO MORENO LOCAL A");
		ubicacionReceptor.setColonia("LA VILLA DE GUADALUPE");
		ubicacionReceptor.setMunicipio("GUSTAVO A. MADERO");
		ubicacionReceptor.setEstado("Ciudad de México");
		ubicacionReceptor.setPais("MÉXICO");
		ubicacionReceptor.setCodigoPostal("CP. 07050");
		receptor.setDomicilio(ubicacionReceptor);
		comprobante.setReceptor(receptor);
		
		Comprobante.Conceptos conceptos = ofComprobante.createComprobanteConceptos();
		Comprobante.Conceptos.Concepto concepto = ofComprobante.createComprobanteConceptosConcepto();
		concepto.setCantidad(new BigDecimal(4));
		concepto.setUnidad("PIEZA");
		concepto.setDescripcion("ACEITE");
		concepto.setValorUnitario(new BigDecimal(50));
		concepto.setImporte(new BigDecimal(200));
		conceptos.getConcepto().add(concepto);
		comprobante.setConceptos(conceptos);
		
		Comprobante.Impuestos impuestos = ofComprobante.createComprobanteImpuestos();
		impuestos.setTotalImpuestosTrasladados(new BigDecimal(32));
		Comprobante.Impuestos.Traslados traslados = ofComprobante.createComprobanteImpuestosTraslados();
		Comprobante.Impuestos.Traslados.Traslado traslado = ofComprobante.createComprobanteImpuestosTrasladosTraslado();
		traslado.setImpuesto("IVA");
		traslado.setTasa(new BigDecimal(16));
		traslado.setImporte(new BigDecimal(32));
		traslados.getTraslado().add(traslado);
		impuestos.setTraslados(traslados);
		comprobante.setImpuestos(impuestos);
		
		return comprobante;
	}
	
	private void redondearCantidades(Comprobante comprobante) {
		List<Concepto> listaConceptos = comprobante.getConceptos().getConcepto();
		for (Concepto concepto : listaConceptos) {
			double valorUnitario = concepto.getValorUnitario().doubleValue();
			int cantidadDecimales = Util.obtenerDecimales(valorUnitario);
			if (cantidadDecimales > 6) {
				concepto.setValorUnitario(
						Util.redondearBigD(concepto.getValorUnitario(), 6));
			} else if (cantidadDecimales == 1){
				concepto.setValorUnitario(
						Util.redondearBigD(concepto.getValorUnitario(), 2));
			} else {
				concepto.setValorUnitario(
						Util.redondearBigD(concepto.getValorUnitario(), cantidadDecimales));
			}
		}
	}
	
	private void convertirImpuestosLocales(ImpuestosLocales impuestosLocales) {
 		List<Object> retencionesLocalesAndTrasladosLocales = impuestosLocales.getRetencionesLocalesAndTrasladosLocales();
 		List<Object> retencionesLocales = new ArrayList<>();
 		RetencionesLocales retencionLocal;
 		for (Object object : retencionesLocalesAndTrasladosLocales) {
 			if (object instanceof LinkedTreeMap<?, ?>) {
 				ObjectFactory of = new ObjectFactory();
 				LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) object;
 				
 				if ( linkedTreeMap.containsKey("impLocRetenido") ) {
 					//retencionesLocales = new ArrayList<>();
 					retencionLocal = of.createImpuestosLocalesRetencionesLocales();
 					
 					retencionLocal.setImpLocRetenido((String) linkedTreeMap.get("impLocRetenido"));
 					Double tasaDeRetencion = (Double) linkedTreeMap.get("tasadeRetencion");
 					retencionLocal.setTasadeRetencion( new BigDecimal( tasaDeRetencion.doubleValue() ) );
 					Double importe = (Double) linkedTreeMap.get("importe");
 					retencionLocal.setImporte( Util.redondearBigD( importe.doubleValue() ) );
 					
 					retencionesLocales.add(retencionLocal);
 				}
 			}
 		}
 		impuestosLocales.getRetencionesLocalesAndTrasladosLocales().clear();
 		impuestosLocales.getRetencionesLocalesAndTrasladosLocales().addAll(retencionesLocales);
 	}
	
}