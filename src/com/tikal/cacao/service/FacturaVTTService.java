package com.tikal.cacao.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.servlet.http.HttpSession;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComentarioVO;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.PDFFacturaV33;

public interface FacturaVTTService {
	
	
	String registrarEmisor(String cadenaCer, String cadenaKey, String pwd, String rfc, HttpSession sesion);
	
	/**
	 * <p>Genera un CFDI 3.3 sin timbrar.</p>
	 * 
	 * @param comprobanteConComentario un objeto con los campos necesarios para generar
	 *  un <strong>CFDI 3.3 {@link com.tikal.cacao.model.FacturaVTT } sin timbrar</strong>
	 * @param sesion la sesi&oacute;n que se utiliza para obtener el nombre del usuario que realizo el evento
	 * @return un texto informativo del resultado 
	 */
	String generar(ComprobanteConComentarioVO comprobanteConComentario, HttpSession sesion);
	
	
	String actualizar(ComprobanteConComentarioVO comprobanteConComentario, String uuid, HttpSession sesion);
	
	/**
	 * <p>Timbra un CFDI 3.3 previamente generado</p>
	 * 
	 * @param uuid el uuid del comprobante previamente generado
	 * @param email la direcci&oacute;n email a la cual se enviará el archivo XML y PDF del CFDI.
	 * @param sesion la sesi&oacute;n que se utiliza para obtener el nombre del usuario que realizo el evento
	 * @return un texto con el resultado de la operaci&oacute;n de Timbrado del
	 * CFDI 3.3
	 */
	String timbrarCFDIGenerado(String uuid, String email, HttpSession sesion);
	
	/**
	 * <p>Timbra un CFDI 3.3 a partir de un objeto {@code ComprobanteVO} con la información de un CFDI 3.3, comentarios
	 * para la representaci&oacute;n impresa y, una direcci&oacute;n email para enviar 
	 * los archivos XML y PDF del CFDI.</p>
	 * 
	 * @param comprobanteVO el objeto con los campos para generar y timbrar un CFDI 3.3
	 * @param sesion la sesi&oacute;n que se utiliza para obtener el nombre del usuario que realizo el evento
	 * @return un texto con el resultado de la operaci&oacute;n de Timbrado del
	 * CFDI 3.3
	 */
	String timbrar(ComprobanteVO comprobanteVO, HttpSession sesion, boolean auto, FacturaVTT.DatosExtra extra);
	
	/**
	 * <p>Timbra un CFDI 3.3 que ya ha sido guardado como un objeto 
	 * {@link com.tikal.cacao.model.FacturaVTT} cuyo campo {@link com.tikal.cacao.factura.Estatus}
	 * es GENERADO.</p>
	 * 
	 * @param json una cadena en formato JSON
	 * @param uuid el UUID (generado por el sistema) del CFDI 3.3 a timbrar
	 * @param sesion la sesi&oacute;n que se utiliza para obtener el nombre del usuario que realizo el evento
	 * @return un texto con el resultado de la operaci&oacute;n de Timbrado del
	 * CFDI 3.3
	 */
	String timbrar(String json, String uuid, HttpSession sesion);
	
	String cancelarAck(String uuid, String rfcEmisor, HttpSession sesion);
	
	FacturaVTT consultar(String uuid);
	
	int obtenerNumeroPaginas(String rfcEmisor);
	
	PdfWriter obtenerPDF(FacturaVTT factura, OutputStream os) throws MalformedURLException, DocumentException, IOException;

	void enviarEmail(String email, String uuid, HttpSession sesion);
}
