package com.tikal.cacao.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;

public interface PagosFacturaVTTService {

	String agregar(ComprobanteConComplementoPagosVO comprobanteConComplementoPagos, Comprobante comprobante, String uuidRelacionado);
	
	RespuestaWebServicePersonalizada timbrar(ComprobanteConComplementoPagosVO comprobanteConComplementoPagos, Comprobante comprobante, String uuidRelacionado);
	
	List<PagosFacturaVTT> consultarPorUuidRelacionado(String uuidRelacionado);
	
	PdfWriter obtenerPDF(PagosFacturaVTT factura, OutputStream os) throws MalformedURLException, DocumentException, IOException;
	
	String cancelarAck(String uuid, String rfcEmisor, HttpSession sesion);
}
