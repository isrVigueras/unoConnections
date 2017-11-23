package com.tikal.cacao.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.TimbraCFDIResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.FacturaVttDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.service.PagosFacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;
import com.tikal.cacao.util.EmailSender;
import com.tikal.cacao.util.Util;

import mx.gob.sat.pagos.Pagos;
import mx.gob.sat.pagos.Pagos.Pago;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

@Service
public class PagosFacturaVTTServiceImpl implements PagosFacturaVTTService {

	@Autowired
	private FacturaVttDAO pagosFacturaVttDAO;
	
	@Autowired
	private WSClientCfdi33 wsClienteCFDI33;
	
	@Autowired
	private ImagenDAO imagenDAO;
	
	@Autowired
	private SerialDAO serialDAO;
	
	@Override
	public String agregar(ComprobanteConComplementoPagosVO comprobanteConComplementoPagos, String uuidRelacionado) {
		Comprobante comprobante = comprobanteConComplementoPagos.getComprobante();
		Pagos complementoPagos = comprobanteConComplementoPagos.getComplementoPagos();
		
		ObjectFactoryComprobante33 of = new ObjectFactoryComprobante33();
		Complemento complemento = of.createComprobanteComplemento();
		complemento.getAny().add(complementoPagos);
		comprobante.getComplemento().add(complemento);
		
		String xmlCFDIPago = Util.marshallComprobanteConPagos(comprobante);
		
		Pago pago = complementoPagos.getPago().get(0);
		PagosFacturaVTT pagosFacturaVTT = new PagosFacturaVTT(
				Util.randomString(14), xmlCFDIPago, comprobante.getEmisor().getRfc(),
				comprobante.getReceptor().getNombre(), Util.xmlGregorianAFecha( comprobante.getFecha() ),
				null, null, uuidRelacionado, Util.xmlGregorianAFecha( pago.getFechaPago() ).toString(),
				pago.getFormaDePagoP().getValor(), pago.getMonedaP().getValor(), pago.getMonto().toPlainString());
		
		pagosFacturaVttDAO.guardar(pagosFacturaVTT);
		return "El CFDI con complemento de pago ha sido generado";
	}

	@Override
	public RespuestaWebServicePersonalizada timbrar(ComprobanteConComplementoPagosVO comprobanteConComplementoPagos, String uuidRelacionado) {
		Comprobante comprobante = comprobanteConComplementoPagos.getComprobante();
		Pagos complementoPagos = comprobanteConComplementoPagos.getComplementoPagos();
		String email = comprobanteConComplementoPagos.getEmail();
		
		ObjectFactoryComprobante33 of = new ObjectFactoryComprobante33();
		Complemento complemento = of.createComprobanteComplemento();
		complemento.getAny().add(complementoPagos);
		comprobante.getComplemento().add(complemento);
		
		String xmlCFDIPago = Util.marshallComprobanteConPagos(comprobante);
		TimbraCFDIResponse timbraCFDIResponse = wsClienteCFDI33.getTimbraCFDIResponse(xmlCFDIPago);
		List<Object> respuestaWB = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
		RespuestaWebServicePersonalizada respPersonalizada = null;
		int codigoRespuesta = -1;
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
				
				Pago pago = complementoPagos.getPago().get(0);
				Date fechaCertificacion = Util.xmlGregorianAFecha(timbreFD.getFechaTimbrado());
				PagosFacturaVTT facturaTimbrada = new PagosFacturaVTT(timbreFD.getUUID(), xmlCFDITimbrado,
						cfdiTimbrado.getEmisor().getRfc(), cfdiTimbrado.getReceptor().getNombre(),
						fechaCertificacion, selloDigital, bytesQRCode, 
						uuidRelacionado, Util.xmlGregorianAFecha( pago.getFechaPago() ).toString(),
						pago.getFormaDePagoP().getValor(), pago.getMonedaP().getValor(), pago.getMonto().toPlainString());
				//facturaTimbrada.setComentarios(comentarios);
				pagosFacturaVttDAO.guardar(facturaTimbrada);
				
				
				EmailSender mailero = new EmailSender();
				Imagen imagen = imagenDAO.get(cfdiTimbrado.getEmisor().getRfc());
				if(email!=null){
					mailero.enviaFactura(email, facturaTimbrada, "", imagen, cfdiTimbrado);
				}
				respPersonalizada = new RespuestaWebServicePersonalizada();
				respPersonalizada.setMensajeRespuesta("¡La factura se timbró con éxito!");
				respPersonalizada.setUuidFactura(timbreFD.getUUID());
				return respPersonalizada;
			} // FIN TIMBRADO EXITOSO
			
			// CASO DE ERROR EN EL TIMBRADO
			else {
				return Util.construirMensajeError(respuestaWB);
			}
		} else {
			return Util.construirMensajeError(respuestaWB);
		}
		//return null;
	}

	@Override
	public List<PagosFacturaVTT> consultarPorUuidRelacionado(String uuidRelacionado) {
		List<PagosFacturaVTT> pagosRelacionados = pagosFacturaVttDAO.consultarPorUuidRelacionado(uuidRelacionado);
		for (PagosFacturaVTT pagosFacturaVTT : pagosRelacionados) {
			pagosFacturaVTT.setCfdiXML(null);
			pagosFacturaVTT.setSelloDigital(null);
			pagosFacturaVTT.setCodigoQR(null);
		}
		return pagosRelacionados;
	}

	@Override
	public PdfWriter obtenerPDF(PagosFacturaVTT factura, OutputStream os)
			throws MalformedURLException, DocumentException, IOException {
		// TODO Auto-generated method stub
		return null;
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
	
}
