package com.tikal.cacao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tempuri.CancelaCFDIAckResponse;
import org.tempuri.RegistraEmisorResponse;
import org.tempuri.TimbraCFDIResponse;

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
import com.tikal.cacao.dao.ComplementoRenglonDAO;
import com.tikal.cacao.dao.FacturaVttDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.dao.sql.RegimenFiscalDAO;
import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.dao.sql.UsoDeCFDIDAO;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.model.orm.RegimenFiscal;
import com.tikal.cacao.model.orm.TipoDeComprobante;
import com.tikal.cacao.model.orm.UsoDeCFDI;
import com.tikal.cacao.reporte.ComplementoRenglon;
import com.tikal.cacao.reporte.ReporteRenglon;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_MetodoDePago;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;
import com.tikal.cacao.sat.cfd33.Comprobante.Impuestos;
import com.tikal.cacao.service.FacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComentarioVO;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.EmailSender;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.PDFFacturaV33;
import com.tikal.cacao.util.Util;

import localhost.EncodeBase64;
import mx.gob.sat.cancelacfd.Acuse;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

@Service
public class FacturaVTTServiceImpl implements FacturaVTTService {

	@Autowired
	private WSClientCfdi33 webServiceClient33;

	@Autowired
	private FacturaVttDAO facturaVTTDAO;

	@Autowired
	private ReporteRenglonDAO repRenglonDAO;

	@Autowired ComplementoRenglonDAO complementoDAO;
	
	@Autowired
	private BitacoraDAO bitacoradao;

	@Autowired
	private SerialDAO serialDAO;

	@Autowired
	private ImagenDAO imagenDAO;

	@Autowired
	@Qualifier("usoDeCfdiDAOH")
	private UsoDeCFDIDAO usoDeCFDIDAO;

	@Autowired
	@Qualifier("regimenFiscalDAOH")
	private RegimenFiscalDAO regimenFiscalDAO;

	@Autowired
	@Qualifier("formaDePagoDAOH")
	private SimpleHibernateDAO<FormaDePago> formaDePagoDAO;
	
	@Autowired
	@Qualifier("tipoDeComprobanteDAOH")
	private SimpleHibernateDAO<TipoDeComprobante> tipoDeComprobanteDAO;

	@Override
	public String registrarEmisor(String cadenaUrlCer, String cadenaUrlKey, String pwd, String rfc,
			HttpSession sesion) {
		HttpURLConnection connCer = null;
		HttpURLConnection connKey = null;

		ByteArrayInputStream objCer = null;
		String strUrlCer = "https://facturacion.tikal.mx/cers/".concat(cadenaUrlCer);
		String strUrlKey = "https://facturacion.tikal.mx/cers/".concat(cadenaUrlKey);

		try {
			URL urlCer = new URL(strUrlCer);
 
			connCer = (HttpURLConnection) urlCer.openConnection();
			connCer.connect();
			objCer = (ByteArrayInputStream) connCer.getContent();
			connCer.disconnect();
			connCer = null;
			urlCer = null;

			URL urlKey = new URL(strUrlKey);

			connKey = (HttpURLConnection) urlKey.openConnection(); // TODO
																	// probar
			connKey.connect();
			InputStream objKey = connKey.getInputStream();

			RegistraEmisorResponse registraEmisorResponse = webServiceClient33.getRegistraEmisorResponse(rfc, pwd,
					objCer, objKey);
			List<Object> respuesta = registraEmisorResponse.getRegistraEmisorResult().getAnyType();
			int codigoRespuesta = (int) respuesta.get(6);
			String mensajeRespuesta = (String) respuesta.get(2);

			if (codigoRespuesta == 0) {
				String evento = "Se registró al emisor del rfc: " + rfc;
				RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
				bitacoradao.addReg(registroBitacora);
				return "Los archivos del emisor fueron registrados. ".concat(mensajeRespuesta);
			} else {
				return mensajeRespuesta;
			}

		} catch (MalformedURLException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}

	}

	@Override
	public String generar(ComprobanteConComentarioVO comprobanteConComentario, HttpSession sesion) {
		Comprobante c = comprobanteConComentario.getComprobante();
		String xmlComprobante = Util.marshallComprobante33(c, false);

		FacturaVTT factura = new FacturaVTT(Util.randomString(10), xmlComprobante, c.getEmisor().getRfc(),
				c.getReceptor().getNombre(), Util.xmlGregorianAFecha(c.getFecha()), null, null);
		factura.setComentarios(comprobanteConComentario.getComentario());

		facturaVTTDAO.guardar(factura);
		this.crearReporteRenglon(factura, c.getMetodoPago(), c.getTipoDeComprobante().getValor());

		String evento = "Se guardó la prefactura con id: " + factura.getUuid();
		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
		bitacoradao.addReg(registroBitacora);

		return "¡La factura se generó con éxito!";
	}

	@Override
	public String actualizar(ComprobanteConComentarioVO comprobanteConComentario, String uuid, HttpSession sesion) {
		Comprobante c = comprobanteConComentario.getComprobante();
		String xmlComprobante = Util.marshallComprobante33(c, false);

		FacturaVTT factura = new FacturaVTT(uuid, xmlComprobante, c.getEmisor().getRfc(), c.getReceptor().getNombre(),
				Util.xmlGregorianAFecha(c.getFecha()), null, null);
		factura.setComentarios(comprobanteConComentario.getComentario());

		facturaVTTDAO.guardar(factura);
		this.crearReporteRenglon(factura, c.getMetodoPago(), c.getTipoDeComprobante().getValor());

		String evento = "Se actualizó la prefactura con id: " + factura.getUuid();
		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
		bitacoradao.addReg(registroBitacora);

		return "¡La factura se actualizó con éxito!";
	}

	@Override
	public String timbrarCFDIGenerado(String uuid, String email, HttpSession sesion) {
		FacturaVTT factura = facturaVTTDAO.consultar(uuid);
		Comprobante comprobante = Util.unmarshallCFDI33XML(factura.getCfdiXML());
		RespuestaWebServicePersonalizada respWBPersonalizada = this.timbrar(comprobante, factura.getComentarios(),
				email, false, null);

		if (respWBPersonalizada.getUuidFactura() != null) {
			// SE TIMBRÓ LA FACTURA CON ÉXITO
			String evento = "Se timbró la factura guardada con el id: " + uuid + " y se generó el CFDI con UUID: "
					+ respWBPersonalizada.getUuidFactura();
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
			bitacoradao.addReg(registroBitacora);
			facturaVTTDAO.eliminar(factura);
			repRenglonDAO.eliminar(uuid);
		} else {
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", respWBPersonalizada.getMensajeRespuesta() + " UUID: " + uuid);
			bitacoradao.addReg(registroBitacora);
		}

		return respWBPersonalizada.getMensajeRespuesta();
	}

	@Override
	public String timbrar(String json, String uuid, HttpSession sesion) {
		ComprobanteVO cVO = (ComprobanteVO) JsonConvertidor.fromJson(json, ComprobanteVO.class);
		RespuestaWebServicePersonalizada respWBPersonalizada = this.timbrar(cVO.getComprobante(), cVO.getComentarios(),
				cVO.getEmail(), false, null);

		if (respWBPersonalizada.getUuidFactura() != null) {
			String evento = "Se actualizo y se timbró la factura guardada con el id: " + uuid
					+ " y se generó el CFDI con UUID: " + respWBPersonalizada.getUuidFactura();
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
			bitacoradao.addReg(registroBitacora);
			facturaVTTDAO.eliminar(facturaVTTDAO.consultar(uuid));
			repRenglonDAO.eliminar(uuid);
		} else {
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", respWBPersonalizada.getMensajeRespuesta() + " UUID: " + uuid);
			bitacoradao.addReg(registroBitacora);
		}
		return respWBPersonalizada.getMensajeRespuesta();
	}

	@Override
	public String timbrar(ComprobanteVO comprobanteVO, HttpSession sesion, boolean auto, FacturaVTT.DatosExtra extra) {
		RespuestaWebServicePersonalizada respWBPersonalizada = this.timbrar(comprobanteVO.getComprobante(),
				comprobanteVO.getComentarios(), comprobanteVO.getEmail(), auto, extra);

		if (respWBPersonalizada.getUuidFactura() != null) {
			if (sesion.getAttribute("userName") != null) {
				String evento = "Se timbró la factura con UUID: " + respWBPersonalizada.getUuidFactura();
				RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
				bitacoradao.addReg(registroBitacora);
			}
		} else {
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional",
					respWBPersonalizada.getMensajeRespuesta() + " Serie y Folio del CFDI: " + comprobanteVO.getComprobante().getSerie() + comprobanteVO.getComprobante().getFolio() );
			bitacoradao.addReg(registroBitacora);
		}
		return respWBPersonalizada.getMensajeRespuesta();
	}

	@Override
	public String cancelarAck(String uuid, String rfcEmisor, HttpSession sesion) {
		CancelaCFDIAckResponse cancelaCFDIAckResponse = webServiceClient33.getCancelaCFDIAckResponse(uuid, rfcEmisor);
		List<Object> respuestaWB = cancelaCFDIAckResponse.getCancelaCFDIAckResult().getAnyType();
		int codigoRespuesta = -1;
		String strCodigoRespuesta = "";
		if (respuestaWB.get(6) instanceof String) {
			//codigoRespuesta = (int) respuestaWB.get(6);
			strCodigoRespuesta = (String) respuestaWB.get(6);
			if (strCodigoRespuesta.contentEquals("0")) {
			//if (codigoRespuesta == 0) {
				FacturaVTT facturaACancelar = facturaVTTDAO.consultar(uuid);
				ReporteRenglon repRenglon = repRenglonDAO.consultar(uuid);
				
				String acuseXML = (String) respuestaWB.get(3);
				StringBuilder stringBuilder = new StringBuilder(acuseXML);
				stringBuilder.insert(106, " xmlns=\"http://cancelacfd.sat.gob.mx\" ");
				String acuseXML2 = stringBuilder.toString();
				facturaACancelar.setAcuseCancelacionXML(acuseXML2);
				Acuse acuse = Util.unmarshallAcuseXML(acuseXML2);
				
				if (acuse != null) {
					try {
						EncodeBase64 encodeBase64 = new EncodeBase64();
						String sello = new String(acuse.getSignature().getSignatureValue(), "ISO-8859-1");
						String selloBase64 = encodeBase64.encodeStringSelloCancelacion(sello);
						facturaACancelar.setFechaCancelacion(acuse.getFecha().toGregorianCalendar().getTime());
						facturaACancelar.setSelloCancelacion(selloBase64);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return "";
					}	
				} 
				
				facturaACancelar.setEstatus(Estatus.CANCELADO);
				repRenglon.setStatus(Estatus.CANCELADO.toString());
				facturaVTTDAO.guardar(facturaACancelar);
				repRenglonDAO.guardar(repRenglon);
				
				String evento = "Se canceló la factura guardada con el id:"+facturaACancelar.getUuid();
				RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
				bitacoradao.addReg(registroBitacora);
				return (String)respuestaWB.get(2); // regresa "Comprobante cancelado"
			}
			
			// ERROR EN LA CANCELACIÓN DEL CFDI
			else {
				RespuestaWebServicePersonalizada respPersonalizada = this.construirMensajeError(respuestaWB);
				RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", respPersonalizada.getMensajeRespuesta() + "Operación CancelaAck (codigoRespuesta != 0), UUID:"+uuid);
				bitacoradao.addReg(registroBitacora);
				return respPersonalizada.getMensajeRespuesta();
			}
		} 
		else {
			if (respuestaWB.get(6) instanceof  String) {
				String strRespuesta = (String) respuestaWB.get(6); 
				if (strRespuesta.contentEquals("0")) {
					RespuestaWebServicePersonalizada respPersonalizada = this.construirMensaje(respuestaWB);
					RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", respPersonalizada.getMensajeRespuesta() + " UUID:"+uuid);
					bitacoradao.addReg(registroBitacora);
					return respPersonalizada.getMensajeRespuesta();
				}
			}
			RespuestaWebServicePersonalizada respPersonalizada = this.construirMensajeError(respuestaWB);
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", respPersonalizada.getMensajeRespuesta() + "Operación CancelaAck (codigoRespuesta no es Integer) UUID:"+uuid);
			bitacoradao.addReg(registroBitacora);
			return respPersonalizada.getMensajeRespuesta();
		}

	}

	@Override
	public FacturaVTT consultar(String uuid) {
		if (uuid != null) {
			return facturaVTTDAO.consultar(uuid);
		}
		return null;
	}

	@Override
	public int obtenerNumeroPaginas(String rfcEmisor) {
		return repRenglonDAO.pags(rfcEmisor);
	}

	@Override
	public PdfWriter obtenerPDF(FacturaVTT factura, OutputStream os)
			throws MalformedURLException, DocumentException, IOException {
		if (factura != null) {
			Comprobante cfdi = Util.unmarshallCFDI33XML(factura.getCfdiXML());
			Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());

			PDFFacturaV33 pdfFactura;
			UsoDeCFDI usoCFDIHB = usoDeCFDIDAO.consultarPorId(cfdi.getReceptor().getUsoCFDI().getValor());
			RegimenFiscal regimenFiscal = regimenFiscalDAO
					.consultarPorId(cfdi.getEmisor().getRegimenFiscal().getValor());
			FormaDePago formaDePago = formaDePagoDAO.consultar(cfdi.getFormaPago().getValor());
			TipoDeComprobante tipoDeComprobante = tipoDeComprobanteDAO.consultar(cfdi.getTipoDeComprobante().getValor());
			if (usoCFDIHB != null && regimenFiscal != null && formaDePago != null && tipoDeComprobante != null) {
				pdfFactura = new PDFFacturaV33(usoCFDIHB.getDescripcion(), regimenFiscal.getDescripcion(),
						formaDePago.getDescripcion(), tipoDeComprobante.getDescripcion());
			} else {
				pdfFactura = new PDFFacturaV33("", "", "", "");
			}

			PdfWriter writer = PdfWriter.getInstance(pdfFactura.getDocument(), os);
			pdfFactura.getPieDePagina().setUuid(factura.getUuid());
			if (factura.getEstatus().equals(Estatus.CANCELADO)) {
				pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
				pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());
			}
			writer.setPageEvent(pdfFactura.getPieDePagina());

			pdfFactura.getDocument().open();
			if (factura.getEstatus().equals(Estatus.TIMBRADO))
				pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
						factura.getEstatus(), factura.getComentarios(), factura.getDatosExtra());
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
				pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
						factura.getEstatus(), factura.getSelloCancelacion(), factura.getFechaCancelacion(),
						factura.getComentarios(), factura.getDatosExtra());

				pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
			}
			pdfFactura.getDocument().close();
			return writer;
			// pdfFactura.getDocument().close();
		} else {
			return null;
		}
	}

	@Override
	public void enviarEmail(String email, String uuid, HttpSession sesion) {
		EmailSender mailero = null;
		FacturaVTT factura = facturaVTTDAO.consultar(uuid);
		Comprobante cfdi = Util.unmarshallCFDI33XML(factura.getCfdiXML());

		UsoDeCFDI usoCFDIHB = usoDeCFDIDAO.consultarPorId(cfdi.getReceptor().getUsoCFDI().getValor());
		RegimenFiscal regimenFiscal = regimenFiscalDAO.consultarPorId(cfdi.getEmisor().getRegimenFiscal().getValor());
		FormaDePago formaDePago = formaDePagoDAO.consultar(cfdi.getFormaPago().getValor());
		TipoDeComprobante tipoDeComprobante = tipoDeComprobanteDAO.consultar(cfdi.getTipoDeComprobante().getValor());
		if (usoCFDIHB != null && regimenFiscal != null && formaDePago != null && tipoDeComprobante != null) {
			mailero = new EmailSender(usoCFDIHB.getDescripcion(), regimenFiscal.getDescripcion(),
					formaDePago.getDescripcion(), tipoDeComprobante.getDescripcion());
		} else {
			mailero = new EmailSender("", "", "", "");
		}

		Imagen imagen = imagenDAO.get(factura.getRfcEmisor());

		mailero.enviaFactura(email, factura, "", imagen, cfdi);
		String evento = "Se envió  la factura con id: " + factura.getUuid() + " al correo: " + email;
		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
		bitacoradao.addReg(registroBitacora);
	}

	private void crearReporteRenglon(FacturaVTT factura, C_MetodoDePago metodoPago, String tipo) {
		
		switch(tipo){
		case "P":{
			ComplementoRenglon reporterenglon= new ComplementoRenglon(factura);
			complementoDAO.guardar(reporterenglon);
			break;
		}
		default:{
			ReporteRenglon reporteRenglon = new ReporteRenglon(factura);
			if(metodoPago.getValor().compareTo("PPD")==0){
				reporteRenglon.setTieneComplementoPago(true);
			}
			repRenglonDAO.guardar(reporteRenglon);
			break;
		}
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

	private void redondearCantidades(Comprobante comprobante) {
		List<Concepto> listaConceptos = comprobante.getConceptos().getConcepto();
		for (Concepto concepto : listaConceptos) {
			double valorUnitario = concepto.getValorUnitario().doubleValue();
			int cantidadDecimales = Util.obtenerDecimales(valorUnitario);
			if (cantidadDecimales > 6) {
				concepto.setValorUnitario(Util.redondearBigD(concepto.getValorUnitario(), 6));
			} else if (cantidadDecimales == 1) {
				concepto.setValorUnitario(Util.redondearBigD(concepto.getValorUnitario(), 2));
			} else {
				concepto.setValorUnitario(Util.redondearBigD(concepto.getValorUnitario(), cantidadDecimales));
			}
			
			cantidadDecimales = Util.obtenerDecimales(concepto.getImporte().doubleValue());
			if (cantidadDecimales > 6) {
				concepto.setImporte(Util.redondearBigD(concepto.getImporte(), 6));
			} 
//			else if (cantidadDecimales == 1) {
//				concepto.setImporte(Util.redondearBigD(concepto.getImporte(), 2));
//			} else {
//				concepto.setImporte(Util.redondearBigD(concepto.getImporte(), cantidadDecimales));
//			}

			// Agregar ceros después del punto decimal en los impuestos
			if (concepto.getImpuestos() != null) {
				List<Traslado> listaTraslado = concepto.getImpuestos().getTraslados().getTraslado();
				for (Traslado traslado : listaTraslado) {
					traslado.setTasaOCuota(Util.redondearBigD(traslado.getTasaOCuota(), 6));
				}
			}
		}
	}

	private void agregarCerosATasaOCuota(Impuestos impuestosGlobales) {
		if(impuestosGlobales!=null){
			List<com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado> listaT = impuestosGlobales
					.getTraslados().getTraslado();
			for (com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado traslado : listaT) {
				traslado.setTasaOCuota(Util.redondearBigD(traslado.getTasaOCuota(), 6));
			}
		}
	}

	private RespuestaWebServicePersonalizada timbrar(Comprobante comprobante, String comentarios, String email,
			boolean auto, FacturaVTT.DatosExtra extra) {
		this.redondearCantidades(comprobante);
		this.agregarCerosATasaOCuota(comprobante.getImpuestos());
		if (!auto) {
			Serial s = serialDAO.consultar(comprobante.getEmisor().getRfc(), comprobante.getSerie());
			comprobante.setFolio(s.getFolio() + "");
		}
		String xmlCFDI = Util.marshallComprobante33(comprobante, false);

		TimbraCFDIResponse timbraCFDIResponse = webServiceClient33.getTimbraCFDIResponse(xmlCFDI);
		List<Object> respuestaWB = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
		RespuestaWebServicePersonalizada respPersonalizada = null;
		int codigoRespuesta = -1;
		String textoCodigoRespuesta = null;
		if (respuestaWB.get(6) instanceof Integer) {
			codigoRespuesta = (int) respuestaWB.get(6);

			if (codigoRespuesta == 0) {
				String xmlCFDITimbrado = (String) respuestaWB.get(3);
				Comprobante cfdiTimbrado = Util.unmarshallCFDI33XML(xmlCFDITimbrado);
				this.incrementarFolio(cfdiTimbrado.getEmisor().getRfc(), cfdiTimbrado.getSerie());
				byte[] bytesQRCode = (byte[]) respuestaWB.get(4);
				String selloDigital = (String) respuestaWB.get(5);

				TimbreFiscalDigital timbreFD = null;
				List<Object> listaComplemento = cfdiTimbrado.getComplemento().get(0).getAny();
				for (Object objComplemento : listaComplemento) {
					if (objComplemento instanceof TimbreFiscalDigital) {
						timbreFD = (TimbreFiscalDigital) objComplemento;
						break;
					}
				}

				Date fechaCertificacion = Util.xmlGregorianAFecha(timbreFD.getFechaTimbrado());
				FacturaVTT facturaTimbrada = new FacturaVTT(timbreFD.getUUID(), xmlCFDITimbrado,
						cfdiTimbrado.getEmisor().getRfc(), cfdiTimbrado.getReceptor().getNombre(), fechaCertificacion,
						selloDigital, bytesQRCode);
				
				if(auto){
					facturaTimbrada.setDatosExtra(extra);
				}
				
				facturaTimbrada.setComentarios(comentarios);
				facturaVTTDAO.guardar(facturaTimbrada);
				this.crearReporteRenglon(facturaTimbrada, comprobante.getMetodoPago(), comprobante.getTipoDeComprobante().getValor());

				UsoDeCFDI usoCFDIHB = usoDeCFDIDAO.consultarPorId(cfdiTimbrado.getReceptor().getUsoCFDI().getValor());
				RegimenFiscal regimenFiscal = regimenFiscalDAO.consultarPorId(cfdiTimbrado.getEmisor().getRegimenFiscal().getValor());
				FormaDePago formaDePago = formaDePagoDAO.consultar(cfdiTimbrado.getFormaPago().getValor());
				TipoDeComprobante tipoDeComprobante = tipoDeComprobanteDAO.consultar(cfdiTimbrado.getTipoDeComprobante().getValor());
				
				EmailSender mailero = new EmailSender(usoCFDIHB.getDescripcion(), regimenFiscal.getDescripcion(), formaDePago.getDescripcion(), tipoDeComprobante.getDescripcion());
				Imagen imagen = imagenDAO.get(cfdiTimbrado.getEmisor().getRfc());
				if (email != null) {
					mailero.enviaFactura(email, facturaTimbrada, "", imagen, cfdiTimbrado);
				}
				respPersonalizada = new RespuestaWebServicePersonalizada();
				respPersonalizada.setMensajeRespuesta("¡La factura se timbró con éxito!");
				respPersonalizada.setUuidFactura(timbreFD.getUUID());
				return respPersonalizada;
			} // FIN TIMBRADO EXITOSO

			// CASO DE ERROR EN EL TIMBRADO
			else {
				RegistroBitacora r= new RegistroBitacora();
				r.setEvento("Fallo en factura xml: "+ xmlCFDI);
				r.setFecha(new Date());
				r.setTipo("Operativo");
				r.setUsuario("Backend");
				bitacoradao.addReg(r);
				return construirMensajeError(respuestaWB);
			}
		} else {
			RegistroBitacora r= new RegistroBitacora();
			r.setEvento("Fallo en factura xml: "+ xmlCFDI);
			r.setFecha(new Date());
			r.setTipo("Operativo");
			r.setUsuario("Backend");
			bitacoradao.addReg(r);
			textoCodigoRespuesta = (String) respuestaWB.get(1);
			return construirMensajeError(respuestaWB);
			
		}
	}

	private RespuestaWebServicePersonalizada construirMensajeError(List<Object> respuestaWB) {
		StringBuilder respuestaError = new StringBuilder("Excepción en caso de error: ");
		respuestaError.append(respuestaWB.get(0) + "\r\n");
		respuestaError.append("Código de error: " + respuestaWB.get(1) + "\r\n");
		respuestaError.append("Mensaje de respuesta: " + respuestaWB.get(2) + "\r\n");
		respuestaError.append(respuestaWB.get(6) + "\r\n");
		respuestaError.append(respuestaWB.get(7) + "\r\n");
		respuestaError.append(respuestaWB.get(8) + "\r\n");

		RespuestaWebServicePersonalizada respPersonalizada = new RespuestaWebServicePersonalizada();
		respPersonalizada.setMensajeRespuesta(respuestaError.toString());
		return respPersonalizada;
	}
	
	private RespuestaWebServicePersonalizada construirMensaje(List<Object> respuestaWS) {
		StringBuilder respuesta = new StringBuilder("Mensaje de respuesta: ");
		respuesta.append(respuestaWS.get(0)+ "\r\n");
		respuesta.append("Código de error: " + respuestaWS.get(1) + "\r\n");
		respuesta.append("Mensaje de respuesta: " + respuestaWS.get(2) + "\r\n");
		respuesta.append("XML : " + respuestaWS.get(3) + "\r\n");
		respuesta.append("QRCode: " + respuestaWS.get(4) + "\r\n");
		respuesta.append("Sello: " + respuestaWS.get(5) + "\r\n");
		respuesta.append( respuestaWS.get(8) + "\r\n");
		
		RespuestaWebServicePersonalizada respPersonalizada = new RespuestaWebServicePersonalizada();
		respPersonalizada.setMensajeRespuesta(respuesta.toString());
		return respPersonalizada;
	}

}
