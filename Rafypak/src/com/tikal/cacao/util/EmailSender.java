package com.tikal.cacao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.appengine.api.search.query.QueryParser.primitive_return;
import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.sat.cfd.Comprobante;

public class EmailSender {
	
	private String descripcionUsoDeCFDI;
	private String descripcionRegimenFiscal;
	private String descripcionFormaDePago;
	private String descripcionTipoDeComprobante;
	
	public EmailSender() {
	}
	
	public EmailSender(String descripcionUsoDeCFDI, String descripcionRegimenFiscal, String descripcionFormaDePago, String descripcionTipoDeComprobante) {
		this.descripcionUsoDeCFDI = descripcionUsoDeCFDI;
		this.descripcionRegimenFiscal = descripcionRegimenFiscal;
		this.descripcionFormaDePago = descripcionFormaDePago;
		this.descripcionTipoDeComprobante = descripcionTipoDeComprobante;
	}

	public void enviaEmail(String emailReceptor, String nombreReceptor, String pass) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String mensaje = "Su nueva contraseña es: " + pass;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Password Reset"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, nombreReceptor));
			msg.setSubject("Contraseña Nueva");
			msg.setText(mensaje);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	
	public void enviaFactura(String emailReceptor, Factura factura,String text, String filename, Imagen urlImg){
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(session);
			
			//append PDF
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent("<h1>Factura timbrada</h1>","text/html");
			mp.addBodyPart(mbp);
			ByteArrayOutputStream os= new ByteArrayOutputStream();
			Comprobante cfdi = Util.unmarshallXML(factura.getCfdiXML());
			PDFFactura pdfFactura = new PDFFactura();
			PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), os);
			pdfFactura.getDocument().open();
			if (factura.getEstatus().equals(Estatus.TIMBRADO)){
				pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(),urlImg, factura.getEstatus(), factura.getComentarios());
			} 
			else{ 
				if (factura.getEstatus().equals(Estatus.GENERADO)){
					pdfFactura.construirPdf(cfdi, urlImg, factura.getEstatus(), factura.getComentarios());
				}
			}
			pdfFactura.getDocument().close();
			byte[] datap= os.toByteArray();
			MimeBodyPart attachmentp = new MimeBodyPart();
			InputStream attachmentDataStreamp = new ByteArrayInputStream(datap);
			attachmentp.setFileName(cfdi.getSerie()+cfdi.getFolio()+".pdf");
			attachmentp.setContent(attachmentDataStreamp, "application/pdf");
			mp.addBodyPart(attachmentp);
			
			//append XML file
			MimeBodyPart attachmentx= new MimeBodyPart();
			InputStream attachmentDataStreamx= new ByteArrayInputStream(factura.getCfdiXML().getBytes());
			attachmentx.setFileName(cfdi.getSerie()+cfdi.getFolio()+".xml");
			attachmentx.setContent(attachmentDataStreamx,"text/xml");
			mp.addBodyPart(attachmentx);
//			DataHandler handler;
//			mbp.setDataHandler(handler);
//			DataSource s= new DataSource();
			
//			
//			msg.setFrom(new InternetAddress("facturacion@tikal.mx", "Factura "+filename));
//			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, ""));
//			msg.setSubject("Factura "+filename);
////			
//			msg.setText("correo de prueba");
			
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Facturación"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
			msg.setSubject("Factura "+factura.getUuid());
//			msg.setText("Prueba de correo 2");
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(OverQuotaException e){
			System.out.println("Se alcanzó");
		}
	}
	
	public void enviaFactura(String emailReceptor, FacturaVTT factura, String text, Imagen urlImg, com.tikal.cacao.sat.cfd33.Comprobante cfdi) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(session);
			
			//append PDF
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent("<h1>Factura timbrada</h1>","text/html");
			mp.addBodyPart(mbp);
			ByteArrayOutputStream os= new ByteArrayOutputStream();
			//com.tikal.cacao.sat.cfd33.Comprobante cfdi = Util.unmarshallCFDI33XML(factura.getCfdiXML());
			
			PDFFacturaV33 pdfFactura = new PDFFacturaV33(this.descripcionUsoDeCFDI, this.descripcionRegimenFiscal, this.descripcionFormaDePago, this.descripcionTipoDeComprobante);
			PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), os);
			pdfFactura.getPieDePagina().setUuid(factura.getUuid());
			
			if (factura.getEstatus().equals(Estatus.CANCELADO)) {
				pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
				pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());
			}
			writer.setPageEvent(pdfFactura.getPieDePagina());
			pdfFactura.getDocument().open();
			
			if (factura.getEstatus().equals(Estatus.TIMBRADO)){
				pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(),urlImg, factura.getEstatus(), factura.getComentarios(), factura.getDatosExtra());
			} 
			else if (factura.getEstatus().equals(Estatus.GENERADO)){
				pdfFactura.construirPdf(cfdi, urlImg, factura.getEstatus(), factura.getComentarios());
			}
			else if (factura.getEstatus().equals(Estatus.CANCELADO)) {
				pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), urlImg, factura.getEstatus(),
						factura.getSelloCancelacion(), factura.getFechaCancelacion(), factura.getComentarios(), factura.getDatosExtra());
				pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
			}
			pdfFactura.getDocument().close();
			byte[] datap= os.toByteArray();
			MimeBodyPart attachmentp = new MimeBodyPart();
			InputStream attachmentDataStreamp = new ByteArrayInputStream(datap);
			attachmentp.setFileName(cfdi.getSerie()+cfdi.getFolio()+".pdf");
			attachmentp.setContent(attachmentDataStreamp, "application/pdf");
			mp.addBodyPart(attachmentp);
			
			//append XML file
			MimeBodyPart attachmentx= new MimeBodyPart();
			InputStream attachmentDataStreamx= new ByteArrayInputStream(factura.getCfdiXML().getBytes());
			attachmentx.setFileName(cfdi.getSerie()+cfdi.getFolio()+".xml");
			attachmentx.setContent(attachmentDataStreamx,"text/xml");
			mp.addBodyPart(attachmentx);
			
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Facturación"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
			msg.setSubject("Factura "+factura.getUuid());
//			msg.setText("Prueba de correo 2");
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(OverQuotaException e){
			System.out.println("Se alcanzó");
		}
	}
}
