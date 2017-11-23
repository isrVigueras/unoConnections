package com.tikal.cacao.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.ExtendedColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;
import com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Retenciones;
import com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Retenciones.Retencion;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.util.PDFFactura.MyFooter;

import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

public class PDFFacturaV33 {

	private Document document;
	private static String IVA = "IVA";
	private static String IEPS = "IEPS";
	private static String ISR = "ISR";
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();
	private MyFooter pieDePagina = new MyFooter(null);
	
	private Font fontTituloSellos = new Font(Font.FontFamily.HELVETICA, 7.5F, Font.BOLD);
	private Font fontContenidoSellos = new Font(Font.FontFamily.COURIER, 7.5F, Font.NORMAL);
	private Font fontLeyendaFiscal = new Font(Font.FontFamily.HELVETICA, 7.5F, Font.NORMAL);
	private Font font2 = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.BOLD);
	private Font font3 = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.NORMAL);
	private Font fontSerieYFolio = new Font(Font.FontFamily.HELVETICA, 9.5F, Font.BOLD, BaseColor.RED);
	private Font fontHeadFactura = new Font(Font.FontFamily.HELVETICA, 9.5F, Font.BOLD);
	
	//private Font fontWhiteDecimals = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.NORMAL, BaseColor.WHITE);
	
	private Font fontHead = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.NORMAL);
	private Font fontHeadConceptos = new Font(Font.FontFamily.HELVETICA, 7.5F, Font.NORMAL);
	private Font fontConceptos = new Font(Font.FontFamily.HELVETICA, 7.5F, Font.NORMAL);
	private BaseColor tikalColor;
	//fontHead.setColor(BaseColor.WHITE);

	private PdfPCell emptyCell = new PdfPCell();
	private PdfPCell celdaEspacio = new PdfPCell();
	//emptyCell.setBorderWidth(0);
	
	//Campos obtenidos desde SQL Cloud;
	private String descripcionUsoDeCFDI;
	private String descripcionRegimenFiscal;
	private String descripcionFormaDePago;
	
	public PDFFacturaV33(String descripcionUsoDeCFDI, String descripcionRegimenFiscal, String descripcionFormaDePago) {
		fontHead.setColor(BaseColor.WHITE);
		fontHeadConceptos.setColor(BaseColor.WHITE);
		emptyCell.setBorderWidth(1);
		emptyCell.setBorderColor(BaseColor.GRAY);
		tikalColor = new CustomColor(ExtendedColor.TYPE_RGB, 142F / 255F, 0F / 255F, 32F / 255F);
		
		celdaEspacio.setBorder(PdfPCell.NO_BORDER);
		//celdaEspacio.addElement(Chunk.NEWLINE);
		
		this.document = new Document();
		this.document.setPageSize(PageSize.A4);
		this.document.setMargins(35, 35, 40, 40); // Left Right Top Bottom
	
		this.descripcionUsoDeCFDI = descripcionUsoDeCFDI;
		this.descripcionRegimenFiscal = descripcionRegimenFiscal;
		this.descripcionFormaDePago = descripcionFormaDePago;
	}
	
	public Document getDocument() {
		return document;
	}

	public MyFooter getPieDePagina() {
		return pieDePagina;
	}
	
	public void crearMarcaDeAgua(String contenido, PdfWriter writer) {
		PdfContentByte fondo = writer.getDirectContent();
		Font fuente = new Font(FontFamily.HELVETICA, 45);
		Phrase frase = new Phrase(contenido, fuente);
		fondo.saveState();
		PdfGState gs1 = new PdfGState();
		gs1.setFillOpacity(0.5f);
		fondo.setGState(gs1);
		ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 650, 45);
		fondo.restoreState();
	}
	
	/**
	 * Construye y regresa un objeto {@code Document} que se convertir&aacute;
	 * la representaci&oacute;n impresa de un CFDI versi&oacute;n 3.3.
	 * 
	 * @param comprobante
	 *            el CFDI con los datos con los que se construira el documento
	 * @param selloDigital
	 *            el sello digital del CFDI
	 * @param bytesQRCode
	 *            el arreglo de bytes que se convertir&aacute; en la imagen del
	 *            c&oacute;digo QR que se agregar&aacute; al documento
	 * @return un objeto {@code Document} que se convertir&aacute; la
	 *         representaci&oacute;n impresa de un CFDI
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Document construirPdf(Comprobante comprobante, String selloDigital, byte[] bytesQRCode, Imagen imagen, Estatus estatus, String comentarios) throws MalformedURLException, DocumentException, IOException {
		List<Object> complementoTFD = comprobante.getComplemento().get(0).getAny();
		TimbreFiscalDigital tfd = null;
		if (complementoTFD.size() > 0) {
			for (Object object : complementoTFD) {
				if (object instanceof TimbreFiscalDigital) {
					tfd = (TimbreFiscalDigital) object;
				}
			}
		}
		
		this.construirBoceto(comprobante, imagen, estatus, tfd, comentarios);
		this.construirTimbre(selloDigital, bytesQRCode, tfd);
		this.construirHechoPor();
		return document;
	}
	
	/**
	 * Construye y regresa un objeto {@code Document} que se convertir&aacute;
	 * la representaci&oacute;n impresa de un CFDI versi&oacute;n 3.3. <strong>sin timbar</strong>
	 * @param comprobante
	 * @param imagen
	 * @param estatus
	 * @param comentarios
	 * @return un objeto {@code Document} que se convertir&aacute; la
	 *         representaci&oacute;n impresa de un CFDI sin timbrar
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public Document construirPdf(Comprobante comprobante, Imagen imagen, Estatus estatus, String comentarios) throws MalformedURLException, DocumentException, IOException {
		construirBoceto(comprobante, imagen, estatus, null, comentarios);
		construirHechoPor();
		return document;
	}
	
	
	public Document construirPdfCancelado(Comprobante comprobante, String selloDigital, byte[] bytesQRCode, Imagen imagen,
			Estatus estatus, String selloCancelacion, Date fechaCancelacion, String comentarios) throws DocumentException, MalformedURLException, IOException {
		
		List<Object> complementoTFD = comprobante.getComplemento().get(0).getAny();
		TimbreFiscalDigital tfd = null;
		if (complementoTFD.size() > 0) {
			for (Object object : complementoTFD) {
				if (object instanceof TimbreFiscalDigital) {
					tfd = (TimbreFiscalDigital) object;
				}
			}
		}
		
		this.construirBoceto(comprobante, imagen, estatus, tfd, comentarios);
		this.construirTimbre(selloDigital, bytesQRCode, tfd);
		this.construirHechoPor();
		return document;
	}
	
	private void construirBoceto(Comprobante comprobante, Imagen imagen, Estatus estatus, TimbreFiscalDigital tfd, String comentarios) throws MalformedURLException, DocumentException, IOException {
		this.construirEncabezado(comprobante, imagen);
		this.construirInfoReceptorYLugarFecha(comprobante, estatus, tfd);
		this.construirUsoCFDIYDatosFiscales(comprobante, estatus, tfd);
		
		this.construirTablaIVA();
		this.construirTablaConceptos(comprobante);
		this.construirComentariosEImporteConLetra(comprobante, comentarios);
		
		this.construirLeyendaFiscalYTotal(comprobante, estatus);
	}
	
	
	private void construirTimbre(String selloDigital, byte[] bytesQRCode, TimbreFiscalDigital tfd) throws DocumentException, MalformedURLException, IOException {
		// QRCode Y SELLOS DIGITALES
		PdfPTable mainTable = new PdfPTable(2);
		mainTable.setWidthPercentage(100); //antes 100.0f
		mainTable.setWidths(new float[] { 25, 75 });

		PdfPCell primeraCeldaTabla = new PdfPCell();
//				primeraCeldaTabla.setBorderWidthTop(1);
//				primeraCeldaTabla.setBorderWidthBottom(1);
		
//				primeraCeldaTabla.setBorderWidthLeft(1);
//		primeraCeldaTabla.setBorderWidth(1);
//		primeraCeldaTabla.disableBorderSide(PdfPCell.RIGHT);
//		primeraCeldaTabla.setBorderColor(BaseColor.GRAY);
		
		primeraCeldaTabla.setBorder(PdfPCell.NO_BORDER);

		PdfPTable tablaQRCode = new PdfPTable(1);
		Image imgQRCode = Image.getInstance(bytesQRCode);
		//imgQRCode.setAlignment(Image.MIDDLE);
		// int dpi = imgQRCode.getDpiX();
		// imgQRCode.scalePercent(100 * 72 / dpi - 20);
		
		
		// el tercer parámetro en el constructor de Chunk (offsetY) controla el
		// tamaño de la imagen
		Chunk chunkQRCode = null;
		PdfPCell celdaQRCode = new PdfPCell();
		boolean selloEmisorCorto = false;
		if (tfd.getSelloCFD().length() < 340) {
			selloEmisorCorto = true;
			chunkQRCode = new Chunk(imgQRCode, 5.0F, -75F);
		} else {
			chunkQRCode = new Chunk(imgQRCode, 5.0F, -65F); // new Chunk(imgQRCode, 0.5F, -78F)
		}
		
		celdaQRCode.setBorder(PdfPCell.NO_BORDER);
		
		if (selloEmisorCorto) {
			Phrase fraseQRCode = new Phrase();
			fraseQRCode.add(Chunk.NEWLINE);
			fraseQRCode.add(chunkQRCode);
			fraseQRCode.add(Chunk.NEWLINE);
			celdaQRCode.addElement(fraseQRCode);
		} else {
			celdaQRCode.addElement(Chunk.NEWLINE);
			celdaQRCode.addElement(Chunk.NEWLINE); //TODO Probar
			celdaQRCode.addElement(chunkQRCode);
		}
		
		//celdaQRCode.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaQRCode.addCell(celdaQRCode);
		primeraCeldaTabla.addElement(tablaQRCode);
		mainTable.addCell(primeraCeldaTabla);

		PdfPCell segundaCeldaTabla = new PdfPCell();
//				segundaCeldaTabla.setBorderWidthTop(1);
//				segundaCeldaTabla.setBorderWidthBottom(1);
//				segundaCeldaTabla.setBorderWidthRight(1);
//				segundaCeldaTabla.setBorderWidthLeft(1);
//		segundaCeldaTabla.setBorderWidth(1);
//		segundaCeldaTabla.setBorderColor(BaseColor.GRAY);
		segundaCeldaTabla.setBorder(PdfPCell.NO_BORDER);

		PdfPTable tablaTimbre = new PdfPTable(1);
		tablaTimbre.setWidthPercentage(100);

		// celdaQRCode = new PdfPCell(Image.getInstance(bytesQRCode));
		// celdaQRCode.setBorderWidth(0);

		PdfPCell cell1table7 = new PdfPCell();
		cell1table7.setBorderWidth(0);

		Phrase line3footer = new Phrase();
		Chunk line3part1 = new Chunk("Sello digital del emisor ", fontTituloSellos);
		Chunk line3part2 = new Chunk(tfd.getSelloCFD(), fontContenidoSellos);
		
		if (selloEmisorCorto) {
			line3footer.add(Chunk.NEWLINE);
		}
		
		line3footer.add(line3part1);
		line3footer.add(Chunk.NEWLINE);
		line3footer.add(line3part2);

		PdfPCell cell4table7 = new PdfPCell(line3footer);
		cell4table7.setBorderWidth(0);
		// cell4table7.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

		Phrase line4footer = new Phrase();
		Chunk line4part1 = new Chunk("Sello digital del SAT ", fontTituloSellos);
		Chunk line4part2 = new Chunk(tfd.getSelloSAT(), fontContenidoSellos);
		line4footer.add(line4part1);
		line4footer.add(Chunk.NEWLINE);
		line4footer.add(line4part2);

		PdfPCell cell5table7 = new PdfPCell(line4footer);
		cell5table7.setBorderWidth(0);
		// cell5table7.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

		Phrase fraseCadenaOriginal = new Phrase();
		Chunk chunkCadenaOriginalEtq = new Chunk("Cadena original del complemento de certificación digital del SAT ",
				fontTituloSellos);
		Chunk chunkCadenaOriginalValor = new Chunk(selloDigital, fontContenidoSellos);
		fraseCadenaOriginal.add(chunkCadenaOriginalEtq);
		fraseCadenaOriginal.add(Chunk.NEWLINE);
		fraseCadenaOriginal.add(chunkCadenaOriginalValor);
		if (selloEmisorCorto) {
			fraseCadenaOriginal.add(Chunk.NEWLINE);
			fraseCadenaOriginal.add(Chunk.NEWLINE);
		}

		PdfPCell celdaCadenaOriginal = new PdfPCell(fraseCadenaOriginal);
		celdaCadenaOriginal.setBorderWidth(0);

		tablaTimbre.addCell(cell4table7);

		tablaTimbre.addCell(cell5table7);

		tablaTimbre.addCell(celdaCadenaOriginal);

		// tablaTimbre.addCell(cell6table7);

		segundaCeldaTabla.addElement(tablaTimbre);
		mainTable.addCell(segundaCeldaTabla);
		//mainTable.setSpacingBefore(2);

		document.add(mainTable);
	}
	
	
	private void construirHechoPor() throws DocumentException {
		Font ffont = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.NORMAL);
		Font ffontBold = new Font(Font.FontFamily.HELVETICA, 8.5F, Font.BOLD);
		
		Phrase footerTikal = new Phrase();
		Image imgTikal;
		Chunk chunkTikal;
		try {
			imgTikal = Image.getInstance("images/Logo-Tikal.png");
			imgTikal.setScaleToFitHeight(false);
			imgTikal.scaleToFit(25f, 7.45f);
			chunkTikal = new Chunk(imgTikal,0,0);
		} catch (BadElementException e) {
			chunkTikal = new Chunk();
			e.printStackTrace();
		} catch (MalformedURLException e) {
			chunkTikal = new Chunk();
			e.printStackTrace();
		} catch (IOException e) {
			chunkTikal = new Chunk();
			e.printStackTrace();
		}
		Chunk chunkHechoPor = new Chunk(" Hecho por ", ffont);
		
		Chunk chunkContacto = new Chunk(" Contacto: ", ffont);
		Chunk chunkEmail = new Chunk("info@tikal.mx ", ffontBold);
		Chunk chunkWeb = new Chunk(" /  www.tikal.mx", ffontBold);
		
		footerTikal.add(chunkHechoPor);
		footerTikal.add(chunkTikal);
		footerTikal.add(chunkContacto);
		footerTikal.add(chunkEmail);
		footerTikal.add(chunkWeb);
		
		PdfPCell celdaHechoPor = new PdfPCell(footerTikal);
		celdaHechoPor.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHechoPor.setBorder(PdfPCell.NO_BORDER);
		
		PdfPTable tablaHechoPor = new PdfPTable(1);
		tablaHechoPor.setWidthPercentage(100);
		tablaHechoPor.addCell(celdaHechoPor);
		tablaHechoPor.setSpacingBefore(5.0f);
		
		document.add(tablaHechoPor);
	}
	
	private void construirEncabezado(Comprobante comprobante, Imagen imagen) throws DocumentException, MalformedURLException, IOException {
		PdfPTable tablaEncabezado = new PdfPTable(3);
		tablaEncabezado.setWidthPercentage(100);
		tablaEncabezado.setWidths(new float[] { 30, 40, 30 });

		// ENCABEZADO DEL COMPROBANTE
		PdfPTable subTablaLogo = new PdfPTable(1);
		PdfPCell celdaLogo = new PdfPCell();
		celdaLogo.setBorder(PdfPCell.NO_BORDER);
		Image imgLogo;
		if (imagen != null) {
			imgLogo = Image.getInstance(new URL(imagen.getImage()));
			imgLogo.setScaleToFitHeight(false);
			imgLogo.scaleToFit(125F, 37.25F);
			Chunk chunkLogo = new Chunk(imgLogo, 0, -25);
			celdaLogo.addElement(chunkLogo);
		} //else {
//			imgLogo = Image.getInstance("images/Logo-Tikal.png");
//			imgLogo.setScaleToFitHeight(false);
//			imgLogo.scaleToFit(125F, 37.25F);
//		}
		
		subTablaLogo.addCell(celdaLogo);
		PdfPCell celdaTablaLogo = new PdfPCell();
		celdaTablaLogo.addElement(subTablaLogo);
		celdaTablaLogo.disableBorderSide(PdfPCell.RIGHT);
		celdaTablaLogo.setBorderColor(BaseColor.GRAY);
		celdaTablaLogo.setBorderWidth(1);
		tablaEncabezado.addCell(celdaTablaLogo);

		PdfPCell celdaDatosEmisor = new PdfPCell();
		Phrase fraseDatosEmisor = new Phrase();
		Chunk chunkNombreEmisor = new Chunk("", font2);
		if(comprobante.getEmisor().getNombre()!=null){
			chunkNombreEmisor = new Chunk(comprobante.getEmisor().getNombre(), font2);
		}
		Chunk chunkRFCEmisor = new Chunk("R.F.C. ".concat(comprobante.getEmisor().getRfc()), font3);
		//Chunk chunkDomicilioEmisor = new Chunk(comprobante.getEmisor().getDomicilioFiscal().toString(), font3);
		fraseDatosEmisor.add(chunkNombreEmisor);
		fraseDatosEmisor.add(Chunk.NEWLINE);
		fraseDatosEmisor.add(chunkRFCEmisor);
		fraseDatosEmisor.add(Chunk.NEWLINE);
		//fraseDatosEmisor.add(chunkDomicilioEmisor);
		celdaDatosEmisor.setBorderWidth(1);
		celdaDatosEmisor.disableBorderSide(PdfPCell.LEFT);
		celdaDatosEmisor.addElement(fraseDatosEmisor);
		celdaDatosEmisor.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaDatosEmisor.setBorderColor(BaseColor.GRAY);
		tablaEncabezado.addCell(celdaDatosEmisor);

		PdfPCell celdaSubTablaEncabezado = new PdfPCell();
		celdaSubTablaEncabezado.setBorderWidth(1);
		PdfPTable subTablaEncabezado = new PdfPTable(1);
		agregarCeldaSinBorde("FACTURA", fontHeadFactura, subTablaEncabezado, true);
		subTablaEncabezado.addCell(celdaEspacio);
		agregarCeldaSinBorde(getFolioYSerie(comprobante), fontSerieYFolio, subTablaEncabezado, true);
		celdaSubTablaEncabezado.addElement(subTablaEncabezado);
		celdaSubTablaEncabezado.setBorderColor(BaseColor.GRAY);
		tablaEncabezado.addCell(celdaSubTablaEncabezado);
		document.add(tablaEncabezado);
	}
	
	private void construirInfoReceptorYLugarFecha(Comprobante comprobante, Estatus estatus, TimbreFiscalDigital tfd) throws DocumentException {
		PdfPTable tablaReceptorYHoraCert = new PdfPTable(3);
		tablaReceptorYHoraCert.setWidthPercentage(100);
		tablaReceptorYHoraCert.setWidths(new float[] { 35, 20, 45 });

		agregarCeldaConFondo("Nombre o razón social del Cliente", fontHead, tablaReceptorYHoraCert, false);
		agregarCeldaConFondo("R.F.C.", fontHead, tablaReceptorYHoraCert, false);
		String etqLugarFechaHora = null;
		if (estatus.equals(Estatus.GENERADO)) {
			etqLugarFechaHora = "Lugar, fecha y hora de emisión";
		} else {
			etqLugarFechaHora = "Lugar, fecha y hora de emisión / fecha y hora de certificación";
		}
		agregarCeldaConFondo(etqLugarFechaHora, fontHead, tablaReceptorYHoraCert,
				false);

		agregarCelda(comprobante.getReceptor().getNombre(), font3, tablaReceptorYHoraCert, false);
		agregarCelda(comprobante.getReceptor().getRfc(), font3, tablaReceptorYHoraCert, false);
		
		String lugarFechaEmiHoraCert = "";
		if (estatus.equals(Estatus.TIMBRADO) || estatus.equals(Estatus.CANCELADO))
			lugarFechaEmiHoraCert = comprobante.getLugarExpedicion().getValor().concat(" a ").concat(comprobante.getFecha().toString().concat(" / ").concat(tfd.getFechaTimbrado().toString()));
		else if (estatus.equals(Estatus.GENERADO))
			lugarFechaEmiHoraCert = comprobante.getLugarExpedicion().getValor().concat(" a ").concat(comprobante.getFecha().toString());
		agregarCelda(lugarFechaEmiHoraCert, font3, tablaReceptorYHoraCert, false);

		document.add(tablaReceptorYHoraCert);
	}
	
	
	private void construirUsoCFDIYDatosFiscales(Comprobante comprobante, Estatus estatus, TimbreFiscalDigital tfd) throws DocumentException {
		PdfPTable tablaUsoCFDIDatosFis = new PdfPTable(2);
		tablaUsoCFDIDatosFis.setWidthPercentage(100);
		tablaUsoCFDIDatosFis.setWidths(new float[] { 40, 60 });

		agregarCeldaConFondo("Uso de CFDI (Receptor)", fontHead, tablaUsoCFDIDatosFis, false);
		agregarCeldaConFondo("Otros datos fiscales", fontHead, tablaUsoCFDIDatosFis, false);

		agregarCelda(comprobante.getReceptor().getUsoCFDI().getValor() + " " + this.descripcionUsoDeCFDI
				, font3, tablaUsoCFDIDatosFis, false);

		Phrase fraseDatosFiscales = new Phrase();
		if (estatus.equals(Estatus.TIMBRADO)) { //TODO agregar caso de cancelado
			 agregarChunkYNuevaLinea("Folio fiscal: ".concat(tfd.getUUID()),
			 font3, fraseDatosFiscales);
			 agregarChunkYNuevaLinea("Serie del certificado de emisor:".concat(comprobante.getNoCertificado()), font3, fraseDatosFiscales);
			 agregarChunkYNuevaLinea("Serie del certificado del SAT:".concat(tfd.getNoCertificadoSAT()), font3, fraseDatosFiscales);
		}
		
		agregarChunkYNuevaLinea(
				"Régimen fiscal: ".concat(comprobante.getEmisor().getRegimenFiscal().getValor())
				+ " " + this.descripcionRegimenFiscal, font3,
				fraseDatosFiscales);

		PdfPCell celdaDatosFiscales = new PdfPCell();
		celdaDatosFiscales.setMinimumHeight(45);
		celdaDatosFiscales.setPhrase(fraseDatosFiscales);
		celdaDatosFiscales.setBorderColor(BaseColor.GRAY);
		celdaDatosFiscales.setBorderWidth(1);
		tablaUsoCFDIDatosFis.addCell(celdaDatosFiscales);
		tablaUsoCFDIDatosFis.setSpacingAfter(5.0F);
		document.add(tablaUsoCFDIDatosFis);
	}
	
	
	private void construirTablaIVA() throws DocumentException {
		PdfPTable tablaIVA = new PdfPTable(3);
		tablaIVA.setWidthPercentage(100);
		tablaIVA.setWidths(new int[] {33, 33, 34});
		
		PdfPCell celdaInfoIva = new PdfPCell(new Paragraph("Impuesto trasladado a cada concepto del CFDI", fontHead));
		celdaInfoIva.setColspan(3);
		celdaInfoIva.setBackgroundColor(this.tikalColor);
		celdaInfoIva.setPadding(5F);
		celdaInfoIva.setBorderColor(BaseColor.GRAY);
		celdaInfoIva.setBorderWidth(1F);
		tablaIVA.addCell(celdaInfoIva);
		
		agregarCeldaConFondo("Impuesto", fontHeadConceptos, tablaIVA, true);
		agregarCeldaConFondo("Tipo factor", fontHeadConceptos, tablaIVA, true);
		agregarCeldaConFondo("Tipo tasa", fontHeadConceptos, tablaIVA, true);
		
		agregarCelda("002 IVA", fontConceptos, tablaIVA, true);
		agregarCelda("Tasa", fontConceptos, tablaIVA, true);
		agregarCelda("16%", fontConceptos, tablaIVA, true);
		document.add(tablaIVA);
	}
	
	private void construirTablaConceptos(Comprobante comprobante) throws DocumentException {
		PdfPTable tablaConceptos = new PdfPTable(9);
		tablaConceptos.setWidthPercentage(100);
		tablaConceptos.setWidths(new float[] { 9, 9, 8, 8, 8, 15, 15, 15, 13 });

		agregarCeldaConFondo("Clave ProdServ", fontHeadConceptos, tablaConceptos, true);
		
		agregarCeldaConFondo("No. Identificación", fontHeadConceptos, tablaConceptos, true);

		agregarCeldaConFondo("Cantidad", fontHeadConceptos, tablaConceptos, true);
		
		agregarCeldaConFondo("Clave Unidad", fontHeadConceptos, tablaConceptos, true);

		agregarCeldaConFondo("Unidad", fontHeadConceptos, tablaConceptos, true);

		agregarCeldaConFondo("Descripción", fontHeadConceptos, tablaConceptos, true);

		agregarCeldaConFondo("Valor unitario", fontHeadConceptos, tablaConceptos, true);

		agregarCeldaConFondo("Importe", fontHeadConceptos, tablaConceptos, true);
		
		agregarCeldaConFondo("Traslado IVA", fontHeadConceptos, tablaConceptos, true);

		List<Concepto> listaConceptos = comprobante.getConceptos().getConcepto();
		for (Concepto concepto : listaConceptos) {
			agregarCelda(concepto.getClaveProdServ().getValor(), fontConceptos, tablaConceptos, true);
			agregarCelda(concepto.getNoIdentificacion(), fontConceptos, tablaConceptos, true);
			agregarCelda(concepto.getCantidad().toString(), fontConceptos, tablaConceptos, true);
			agregarCelda(concepto.getClaveUnidad().getValor(), fontConceptos, tablaConceptos, true);
			agregarCelda(concepto.getUnidad(), fontConceptos, tablaConceptos, true);
			agregarCelda(concepto.getDescripcion(), fontConceptos, tablaConceptos, false);
			double valorUnitario = concepto.getValorUnitario().doubleValue();
			int decimalesValorUnitario = Util.obtenerDecimales(valorUnitario);
			if (decimalesValorUnitario > 2) {
				BigDecimal valorUnitarioBD = Util.redondearBigD(valorUnitario);
				agregarCelda(formatter.format(valorUnitarioBD), fontConceptos, tablaConceptos, true);
			} else {
				agregarCelda(formatter.format(concepto.getValorUnitario().doubleValue()), fontConceptos, tablaConceptos, true);
			}
			agregarCelda(formatter.format(concepto.getImporte().doubleValue()), fontConceptos, tablaConceptos, true);
		
			Traslado traslado = concepto.getImpuestos().getTraslados().getTraslado().get(0);
			if (traslado != null) {
				agregarCelda(formatter.format(traslado.getImporte()), fontConceptos, tablaConceptos, true);
			} else {
				agregarCelda("", fontConceptos, tablaConceptos, true);
			}
		}

		tablaConceptos.setSpacingBefore(2.5F);
		tablaConceptos.setSpacingAfter(5);
		document.add(tablaConceptos);

	}
	
	
	private void construirComentariosEImporteConLetra(Comprobante comprobante, String comentarios) throws DocumentException {
		//TABLA DE COMENTARIOS DE LA FACTURA
		if (comentarios != null) {
			if (!comentarios.contentEquals("")) {
				PdfPTable tablaComentarios = new PdfPTable(1);
				tablaComentarios.setWidthPercentage(100);
				agregarCeldaConFondo("Comentarios", fontHead, tablaComentarios, false);
				agregarCelda(comentarios, font3, tablaComentarios, false);//fontOkuee
				tablaComentarios.setSpacingAfter(5);
				document.add(tablaComentarios);
			}
		}

		// IMPORTE CON LETRA
		PdfPTable tablaImporteConLetra = new PdfPTable(3);
		tablaImporteConLetra.setWidthPercentage(100);
		tablaImporteConLetra.setWidths(new int[] { 20, 65, 15 });
		agregarCeldaConFondo("Importe con letra ", fontHead, tablaImporteConLetra, true);

		double importeTotal = Math.round(comprobante.getTotal().doubleValue() * 100.0) / 100.0;
		String importeConLetra = NumberToLetterConverter.convertNumberToLetter(importeTotal, comprobante.getMoneda().getValor());
		Chunk chunkImporteConLetra = new Chunk(importeConLetra, font3);
		Phrase fraseImporteConLetra = new Phrase(chunkImporteConLetra);
		PdfPCell celdaImporteConLetra = new PdfPCell();
		// celdaImporteConLetra.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaImporteConLetra.setBorder(PdfPCell.NO_BORDER);
		celdaImporteConLetra.setPhrase(fraseImporteConLetra);
		tablaImporteConLetra.addCell(celdaImporteConLetra);

		emptyCell.setBackgroundColor(tikalColor);
		tablaImporteConLetra.addCell(emptyCell);
		document.add(tablaImporteConLetra);
	}
	
	private void construirLeyendaFiscalYTotal(Comprobante comprobante, Estatus estatus) throws DocumentException {
		PdfPTable tablaLeyendaTotal = new PdfPTable(3);
		tablaLeyendaTotal.setWidthPercentage(100);
		tablaLeyendaTotal.setWidths(new float[] { 65, 20, 15 });

		Phrase fraseLeyenda = new Phrase();
		if ((comprobante.getCondicionesDePago() == null)
				&& !estatus.equals(Estatus.GENERADO)) {
			fraseLeyenda.add(Chunk.NEWLINE);
		}
		if (!estatus.equals(Estatus.GENERADO)) {
			Chunk chunkLeyenda = new Chunk("Este documento es una representación impresa de un CFDI", fontTituloSellos);
			//fraseLeyenda.add(Chunk.NEWLINE);
			fraseLeyenda.add(chunkLeyenda);
		}
		// fraseLeyenda.add(Chunk.NEWLINE);

		fraseLeyenda.add(Chunk.NEWLINE);
		String descripcionMetodoPago = "";
		if (comprobante.getMetodoPago().getValor().contentEquals("PUE")) {
			descripcionMetodoPago = " Pago en una sola exhibición";
		} else if (comprobante.getMetodoPago().getValor().contentEquals("PPD")) {
			descripcionMetodoPago = " Pago en parcialidades o diferido";
		}
		String strMetodoPago = "Método de pago: ".concat(comprobante.getMetodoPago().getValor()).concat(descripcionMetodoPago)
				.concat("                 Moneda: ").concat(comprobante.getMoneda().getValor());
		Chunk chunkMetodoDePago = new Chunk(strMetodoPago, fontLeyendaFiscal);
		fraseLeyenda.add(chunkMetodoDePago);

		// se agrega la leyenda 'Efectos fiscales al pago'
		String strFormaDePago = "Forma de pago: ".concat(comprobante.getFormaPago().getValor()).concat(" "+descripcionFormaDePago)
				.concat("                 Efectos fiscales al pago");
		fraseLeyenda.add(Chunk.NEWLINE);
		Chunk chunkFormaDePago = new Chunk(strFormaDePago, fontLeyendaFiscal);
		fraseLeyenda.add(chunkFormaDePago);
		fraseLeyenda.add(Chunk.NEWLINE);
		
		String condicionesDePago = comprobante.getCondicionesDePago();
		if (condicionesDePago != null) {
			if ( !condicionesDePago.contentEquals("") ) {
				String strCondicionesDePago = "Condiciones de pago: ".
						concat(condicionesDePago).concat("      ");
				Chunk chunkCondicionesDePago = new Chunk(strCondicionesDePago, fontLeyendaFiscal);
				fraseLeyenda.add(chunkCondicionesDePago);
				fraseLeyenda.add(Chunk.NEWLINE);
			}
		}
		
		//DESAPARECE EN CFDI 3.3
//		String numCtaPago = comprobante.getNumCtaPago();
//		if (numCtaPago != null) {
//			if ( !numCtaPago.contentEquals("") ) {
//				String strNumCtaPago = "Número de cuenta de pago: ".
//						concat(numCtaPago);
//				Chunk chunkCondicionesDePago = new Chunk(strNumCtaPago, fontLeyendaFiscal);
//				fraseLeyenda.add(chunkCondicionesDePago);
//				fraseLeyenda.add(Chunk.NEWLINE);
//			}
//		}
		
		PdfPCell celdaLeyenda = new PdfPCell(fraseLeyenda);
		celdaLeyenda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaLeyenda.setBorderColor(BaseColor.GRAY);
		celdaLeyenda.setBorderWidth(1);
		tablaLeyendaTotal.addCell(celdaLeyenda);

		PdfPTable subTablaEtqTotal = new PdfPTable(1);
		agregarCeldaConFondo("Subtotal", fontHead, subTablaEtqTotal, true);

		List<Comprobante.Impuestos.Traslados.Traslado> traslados = comprobante.getImpuestos().getTraslados().getTraslado();
		boolean existeIVATraslado = false;
		double importe = 0.0;
		if (traslados.size() > 0) {
			if (traslados.get(0).getImpuesto().getValor().contentEquals("002")) {
				existeIVATraslado = true;
				importe = traslados.get(0).getImporte().doubleValue();
				agregarCeldaConFondo("IVA 16%", fontHead, subTablaEtqTotal, true);
			}
		} else {
			subTablaEtqTotal.addCell(emptyCell);
		}
		
		boolean existeISR = agregarEtiquetaRetenciones(comprobante.getImpuestos().getRetenciones(), subTablaEtqTotal);

		agregarCeldaConFondo("Total", fontHead, subTablaEtqTotal, true);
		PdfPCell celdaTablaEtqTotal = new PdfPCell(subTablaEtqTotal);
		celdaTablaEtqTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaLeyendaTotal.addCell(celdaTablaEtqTotal);

		PdfPTable subTablaValoresTotal = new PdfPTable(1);
		agregarCelda(formatter.format(comprobante.getSubTotal().doubleValue()), font3, subTablaValoresTotal, true);

		if (existeIVATraslado) {
			agregarCelda(formatter.format(importe), font3, subTablaValoresTotal, true);
		} else {
			subTablaValoresTotal.addCell(emptyCell);
		}
		
		if (existeISR) {
			List<Retencion> listaRetencion = comprobante.getImpuestos().getRetenciones().getRetencion();
			double importeISR = 0.0;
			for (Retencion retencion : listaRetencion) {
				if (retencion.getImpuesto().equals(ISR)) {
					importeISR = retencion.getImporte().doubleValue();
					break;
				}
			}
			
			agregarCelda(formatter.format(importeISR), font3, subTablaValoresTotal, true);
		}

		agregarCelda(formatter.format(comprobante.getTotal().doubleValue()), font3, subTablaValoresTotal, true);
		PdfPCell celdaTablaValoresTotal = new PdfPCell(subTablaValoresTotal);
		celdaTablaValoresTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaLeyendaTotal.addCell(celdaTablaValoresTotal);

		document.add(tablaLeyendaTotal);
	}
	
	private void agregarCeldaSinBorde(String contenidoCelda, Font fuente, PdfPTable tabla, boolean centrado) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorder(PdfPCell.NO_BORDER);
		//celda.setBorderColor(BaseColor.GRAY);
		celda.setPadding(8);

		if (centrado) {
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		tabla.addCell(celda);
	}
	
	private void agregarCeldaConFondo(String contenidoCelda, Font fuente, PdfPTable tabla, boolean centrado) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorderWidth(1);
		celda.setBorderColor(BaseColor.GRAY);
		celda.setPadding(5);
		celda.setBackgroundColor(tikalColor);

		if (centrado) {
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		tabla.addCell(celda);
	}
	
	private void agregarCelda(String contenidoCelda, Font fuente, PdfPTable tabla, boolean centrado) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorderWidth(1);
		celda.setBorderColor(BaseColor.GRAY);
		celda.setPadding(5);

		if (centrado) {
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		tabla.addCell(celda);
	}
	
	private void agregarChunkYNuevaLinea(String contenido, Font fuente, Phrase frase) {
		Chunk chunk = new Chunk(contenido, fuente);
		frase.add(chunk);
		frase.add(Chunk.NEWLINE);
	}
	
	private String getFolioYSerie(Comprobante c) {
		String folio = c.getFolio();
		String serie = c.getSerie();
		StringBuilder folioYSerie = new StringBuilder();
		if (serie != null)
			folioYSerie.append(serie);
		if (folio != null)
			folioYSerie.append(folio);

		return folioYSerie.toString();
	}
	
	private boolean agregarEtiquetaRetenciones(Retenciones retenciones, PdfPTable subTablaEtqTotal) {
		boolean existeISR = false;
		if (retenciones != null) {
			List<Retencion> listaRetencion = retenciones.getRetencion();
			//double importe = 0.0;
			if (listaRetencion.size() > 0) {
				if (listaRetencion.get(0).getImpuesto().equals(ISR)) {
					existeISR = true;
					//importe = listaRetencion.get(0).getImporte().doubleValue();
					agregarCeldaConFondo("ISR Retenido", fontHead, subTablaEtqTotal, true);
				}
			} /*else {
				subTablaEtqTotal.addCell(emptyCell);
			}*/
		}
		return existeISR;
	}

	
	
	
	
	
	
	
	
	
	
	
}
