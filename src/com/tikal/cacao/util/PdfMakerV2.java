package com.tikal.cacao.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;
import com.tikal.cacao.springController.viewObjects.PagoVO;

public class PdfMakerV2 {
	
	private Document document;
	private ListaPagosVO lista;
	
	
	public PdfMakerV2() {
		this.document = new Document();
		this.document.setPageSize(PageSize.A3);
		this.document.setMargins(40, 40, 40, 40); // Left Right Top Bottom
	}
	
	public Document imprimirPagos() throws ParseException {
		for (PagoVO p : this.lista.getLista()) {
			try {
				this.document = construirPdf(p);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		return this.document;
	}

	public Document construirPdf(PagoVO p) throws DocumentException, ParseException {
		
		Font font1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
		
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorderWidth(0);
		
		BaseColor gris = new BaseColor(153, 153, 102);
		BaseColor otroGris = new BaseColor(235, 235, 224);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(p.getFechaDePago());
		String date2=p.getFechaIngreso();
		Date datin;
		try{
			datin = sdf.parse(date2);
		}catch(ParseException exc){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date2=date2.substring(0,date2.indexOf("T"));
			datin =sdf.parse(date2);
		}
		
		String fechaIngreso = sdf.format(datin);
		
		
		PdfPTable tabla = new PdfPTable(2);
		tabla.setWidthPercentage(100);
		
		PdfPCell celda1tabla = new PdfPCell(new Paragraph("EMREB SA DE CV", font2));
		celda1tabla.setBorderWidth(0);
		celda1tabla.setBackgroundColor(otroGris);
		tabla.addCell(celda1tabla);
		
		
		PdfPCell celda2tabla = new PdfPCell(new Paragraph("Folio Fiscal", font2));
		celda2tabla.setBorderWidth(0);
		celda2tabla.setBackgroundColor(otroGris);
		tabla.addCell(celda2tabla);
		
		PdfPCell celda3tabla = new PdfPCell(new Paragraph("EMR140422RD0", font3));
		celda3tabla.setBorderWidth(0);
		tabla.addCell(celda3tabla);
		
		PdfPCell celda4tabla = new PdfPCell(new Paragraph(" ", font3)); ////////////////////////////Folio Fiscal
		celda4tabla.setBorderWidth(0);
		tabla.addCell(celda4tabla);
		
		tabla.addCell(emptyCell);
		
		PdfPCell celda5tabla = new PdfPCell(new Paragraph("No. de Serie del Certificado del CSD", font2));
		celda5tabla.setBorderWidth(0);
		celda5tabla.setBackgroundColor(otroGris);
		tabla.addCell(celda5tabla);
		
		tabla.addCell(emptyCell);
		
		PdfPCell celda6tabla = new PdfPCell(new Paragraph(" ", font3)); //////////////////////No. de Serie del Certificado del CSD
		celda6tabla.setBorderWidth(0);
		tabla.addCell(celda6tabla);
		
		
		PdfPCell celda7tabla = new PdfPCell(new Paragraph("Régimen Fiscal", font2));
		celda7tabla.setBorderWidth(0);
		celda7tabla.setBackgroundColor(otroGris);
		tabla.addCell(celda7tabla);
		
		
		PdfPCell celda8tabla = new PdfPCell(new Paragraph("Lugar, fecha y hora de emisión", font2));
		celda8tabla.setBorderWidth(0);
		celda8tabla.setBackgroundColor(otroGris);
		tabla.addCell(celda8tabla);
		
		PdfPCell celda9tabla = new PdfPCell(new Paragraph("601 General de Ley Personas Morales", font3)); 
		celda9tabla.setBorderWidth(0);
		tabla.addCell(celda9tabla);
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
		
		PdfPCell celda10tabla = new PdfPCell(new Paragraph(strDate, font3)); 
		celda10tabla.setBorderWidth(0);
		tabla.addCell(celda10tabla);
		
		tabla.addCell(emptyCell);
		tabla.addCell(emptyCell);
		
		document.add(tabla);
		
		
		PdfPTable tabla1= new PdfPTable(3);
		tabla1.setWidthPercentage(100);
		
		PdfPCell celdatabla1 = new PdfPCell(new Paragraph("ESTE DOCUMENTO ES UNA REPRESENTACIÓN IMPRESA DE UN CFDI", font2));
		celdatabla1.setBorderWidth(0);
		celdatabla1.setBackgroundColor(otroGris);
		celdatabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdatabla1.setColspan(3);
		tabla1.addCell(celdatabla1);
		
		PdfPCell celda1tabla1 = new PdfPCell(new Paragraph("RECIBO DE NOMINA", font1));
		celda1tabla1.setBorderWidth(0);
		celda1tabla1.setBackgroundColor(otroGris);
		celda1tabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda1tabla1.setColspan(3);
		tabla1.addCell(celda1tabla1);
		
		PdfPCell celda2tabla1 = new PdfPCell(new Paragraph("Nombre: "+ p.getNombre(), font3));
		celda2tabla1.setColspan(3);
		celda2tabla1.setBorderWidth(0);
		//celda2tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda2tabla1);
		
		
		PdfPCell celda3tabla1 = new PdfPCell(new Paragraph("CodigoNomina: ", font3));
		celda3tabla1.setBorderWidth(0);
		//celda3tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda3tabla1);
		
		
		PdfPCell celda4tabla1 = new PdfPCell(new Paragraph("RegistroPatronal: ", font3));
		celda4tabla1.setBorderWidth(0);
		//celda4tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda4tabla1);
		
		
		PdfPCell celda5tabla1 = new PdfPCell(new Paragraph("PeriocidadPago: ", font3));
		celda5tabla1.setBorderWidth(0);
		//celda5tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda5tabla1);
		
		PdfPCell celda6tabla1 = new PdfPCell(new Paragraph("DiasNomina: " + p.getDiasPagados(), font3));
		celda6tabla1.setBorderWidth(0);
		//celda6tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda6tabla1);
		
		PdfPCell celda7tabla1 = new PdfPCell(new Paragraph("RiesgoPuesto: ", font3));
		celda7tabla1.setBorderWidth(0);
		//celda7tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda7tabla1);
		
		PdfPCell celda8tabla1 = new PdfPCell(new Paragraph("FechaInicial: " , font3));
		celda8tabla1.setBorderWidth(0);
		//celda8tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda8tabla1);
		
		PdfPCell celda9tabla1 = new PdfPCell(new Paragraph("FechaFinal: " , font3));
		celda9tabla1.setBorderWidth(0);
		//celda9tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda9tabla1);
		
		PdfPCell celda10tabla1 = new PdfPCell(new Paragraph("CURP: " + p.getCurp(), font3));
		celda10tabla1.setBorderWidth(0);
		//celda10tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda10tabla1);
		
		PdfPCell celda11tabla1 = new PdfPCell(new Paragraph("NumEmpleado: " + p.getIdEmpleado(), font3));
		celda11tabla1.setBorderWidth(0);
		//celda11tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda11tabla1);
		
		PdfPCell celda12tabla1 = new PdfPCell(new Paragraph("TipoRegimen: " + p.getIdRegimen(), font3));
		celda12tabla1.setBorderWidth(0);
		//celda12tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda12tabla1);
		
		PdfPCell celda13tabla1 = new PdfPCell(new Paragraph("NumSegSocial: " + p.getNss(), font3));
		celda13tabla1.setBorderWidth(0);
		//celda13tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda13tabla1);
		
		PdfPCell celda14tabla1 = new PdfPCell(new Paragraph("Departamento: " + p.getDepartamento(), font3));
		celda14tabla1.setBorderWidth(0);
		//celda14tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda14tabla1);
		
		PdfPCell celda15tabla1 = new PdfPCell(new Paragraph("CLABE: ", font3));
		celda15tabla1.setBorderWidth(0);
		//celda15tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda15tabla1);
		
		PdfPCell celda16tabla1 = new PdfPCell(new Paragraph("Banco: ", font3));
		celda16tabla1.setBorderWidth(0);
		//celda16tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda16tabla1);
		
		PdfPCell celda17tabla1 = new PdfPCell(new Paragraph("FechaIniRelLab: " + fechaIngreso, font3));
		celda17tabla1.setBorderWidth(0);
		//celda17tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda17tabla1); 
		
		PdfPCell celda18tabla1 = new PdfPCell(new Paragraph("Puesto: " + p.getPuesto(), font3));
		celda18tabla1.setBorderWidth(0);
		//celda18tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda18tabla1);
		
		PdfPCell celda19tabla1 = new PdfPCell(new Paragraph("TipoContrato: ", font3));
		celda19tabla1.setBorderWidth(0);
		//celda19tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda19tabla1);
		
		PdfPCell celda20tabla1 = new PdfPCell(new Paragraph("TipoJornada: ", font3));
		celda20tabla1.setBorderWidth(0);
		//celda20tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda20tabla1);
		
		PdfPCell celda21tabla1 = new PdfPCell(new Paragraph("SDI IMSS: ", font3));
		celda21tabla1.setBorderWidth(0);
		//celda21tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda21tabla1);
		
		PdfPCell celda22tabla1 = new PdfPCell(new Paragraph("NumDiasPagados: " + p.getDiasPagados(), font3));
		celda22tabla1.setBorderWidth(0);
		//celda22tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda22tabla1);
		
		PdfPCell celda23tabla1 = new PdfPCell(new Paragraph("FechaPago: " + date, font3));
		celda23tabla1.setBorderWidth(0);
		//celda23tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda23tabla1);
		
		PdfPCell celda24tabla1 = new PdfPCell(new Paragraph("SDI Indemnización: ", font3));
		celda24tabla1.setBorderWidth(0);
		//celda24tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda24tabla1);
		
		PdfPCell celda25tabla1 = new PdfPCell(new Paragraph("CURP Emisor: ", font3));
		celda25tabla1.setBorderWidth(0);
		//celda25tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda25tabla1);
		
		PdfPCell celda26tabla1 = new PdfPCell(new Paragraph("Tipo: ", font3));
		celda26tabla1.setBorderWidth(0);
		//celda26tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda26tabla1);
		
		PdfPCell celda27tabla1 = new PdfPCell(new Paragraph("Imp Hrs Simples: ", font3));
		celda27tabla1.setBorderWidth(0);
		//celda27tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda27tabla1);
		
		PdfPCell celda28tabla1 = new PdfPCell(new Paragraph("Imp Hrs Dobles: ", font3));
		celda28tabla1.setBorderWidth(0);
		//celda28tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda28tabla1);
		
		PdfPCell celda29tabla1 = new PdfPCell(new Paragraph("Imp Hrs Triples: ", font3));
		celda29tabla1.setBorderWidth(0);
		//celda29tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda29tabla1);
		
		PdfPCell celda30tabla1 = new PdfPCell(new Paragraph("Antiguedad: ", font3));
		celda30tabla1.setBorderWidth(0);
		//celda30tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda30tabla1);
		
		PdfPCell celda31tabla1 = new PdfPCell(new Paragraph("Ent Fed: ", font3));
		celda31tabla1.setBorderWidth(0);
		//celda31tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda31tabla1);
		
		PdfPCell celdaVaciaTabla1 = new PdfPCell(new Paragraph(" "));
		celdaVaciaTabla1.setBorderWidth(0);
		//celdaVaciaTabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celdaVaciaTabla1);
		
		document.add(tabla1); /////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		PdfPTable table4 = new PdfPTable(2);
		table4.setWidthPercentage(100);

		PdfPCell cell1table4 = new PdfPCell(new Paragraph("PERCEPCIONES", font3));
		cell1table4.setBackgroundColor(otroGris);
		cell1table4.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table4 = new PdfPCell(new Paragraph("DEDUCCIONES", font3));
		cell2table4.setBackgroundColor(otroGris);
		cell2table4.setHorizontalAlignment(Element.ALIGN_CENTER);

		table4.addCell(cell1table4);
		table4.addCell(cell2table4);

		document.add(table4);

		PdfPTable table5 = new PdfPTable(6);
		table5.setWidthPercentage(100);
		table5.setWidths(new int[] { 10, 30, 10, 10, 30, 10 });

		PdfPCell cell1table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell1table5.setBackgroundColor(otroGris);
		cell1table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell2table5.setBackgroundColor(otroGris);
		cell2table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table5 = new PdfPCell(new Paragraph("Importe", font3));
		cell3table5.setBackgroundColor(otroGris);
		cell3table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell4table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell4table5.setBackgroundColor(otroGris);
		cell4table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell5table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell5table5.setBackgroundColor(otroGris);
		cell5table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell6table5 = new PdfPCell(new Paragraph("Importe", font3));
		cell6table5.setBackgroundColor(otroGris);
		cell6table5.setHorizontalAlignment(Element.ALIGN_CENTER);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		PdfPCell dumbCell = new PdfPCell(new Paragraph(" "));
		dumbCell.setBorderWidthTop(0);
		dumbCell.setBorderWidthBottom(0);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		table5.addCell(cell1table5);
		table5.addCell(cell2table5);
		table5.addCell(cell3table5);
		table5.addCell(cell4table5);
		table5.addCell(cell5table5);
		table5.addCell(cell6table5);

		document.add(table5);

		// for(int i = 0; i < 30; i ++){
		// table5.addCell(dumbCell);
		// }
		//
		// PdfPCell dumbCellTwo = new PdfPCell(new PdfPCell(new Paragraph("
		// ")));
		// dumbCellTwo.setBorderWidthTop(0);
		//
		// for(int i = 0; i < 6; i ++){
		// table5.addCell(dumbCellTwo);
		// }

		// Tabla de percepciones y de deducciones
		PdfPTable tablota = new PdfPTable(2);
		tablota.setWidthPercentage(100);

		// tabla de percepciones
		PdfPTable percepciones = new PdfPTable(3);
		percepciones.setWidths(new int[] { 10, 30, 10 });

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		

		double totalPercepciones = 0;
		for (Percepcion per : p.getPercepciones()) {
			PdfPCell tipoPerCell = new PdfPCell(new Paragraph(per.getClave(), font3));
			tipoPerCell.setBorderWidthTop(0);
			tipoPerCell.setBorderWidthBottom(0);
			percepciones.addCell(tipoPerCell);

			PdfPCell desPerCell = new PdfPCell(new Paragraph(per.getClave() + " - " + per.getDescripcion(), font3));
			desPerCell.setBorderWidthTop(0);
			desPerCell.setBorderWidthBottom(0);
			percepciones.addCell(desPerCell);

			PdfPCell impPerCell = new PdfPCell(new Paragraph(formatter.format(per.getCantidad()), font3));
			impPerCell.setBorderWidthTop(0);
			impPerCell.setBorderWidthBottom(0);
			percepciones.addCell(impPerCell);
			totalPercepciones += per.getCantidad();
		}

		PdfPCell emptyCellDos = new PdfPCell(new Paragraph(" "));
		emptyCellDos.setBorderWidthTop(0);
		emptyCellDos.setBorderWidthBottom(0);

		for (int i = 0; i < (30 - (p.getPercepciones().size() * 3)); i++) {
			percepciones.addCell(emptyCellDos);
		}

		PdfPTable deducciones = new PdfPTable(3);
		deducciones.setWidths(new int[] { 10, 30, 10 });

		double totalDeducciones = 0;
		for (Deduccion ded : p.getDeducciones()) {
			PdfPCell tipoPerCell = new PdfPCell(new Paragraph(ded.getClave(), font3));
			tipoPerCell.setBorderWidthTop(0);
			tipoPerCell.setBorderWidthBottom(0);
			deducciones.addCell(tipoPerCell);

			PdfPCell desPerCell = new PdfPCell(new Paragraph(ded.getClave() + " - " + ded.getDescripcion(), font3));
			desPerCell.setBorderWidthTop(0);
			desPerCell.setBorderWidthBottom(0);
			deducciones.addCell(desPerCell);

			PdfPCell impPerCell = new PdfPCell(new Paragraph(formatter.format(ded.getDescuento()), font3));
			impPerCell.setBorderWidthTop(0);
			impPerCell.setBorderWidthBottom(0);
			deducciones.addCell(impPerCell);

			totalDeducciones += ded.getDescuento();
		}

		for (int i = 0; i < (30 - (p.getDeducciones().size() * 3)); i++) {
			deducciones.addCell(emptyCellDos);
		}

		PdfPCell perCell = new PdfPCell(percepciones);
		perCell.setBorderWidthTop(0);
		perCell.setBorderWidthLeft(0);
		perCell.setBorderWidthRight(0);
		tablota.addCell(perCell);

		PdfPCell dedCell = new PdfPCell(deducciones);
		dedCell.setBorderWidthTop(0);
		dedCell.setBorderWidthLeft(0);
		dedCell.setBorderWidthRight(0);
		tablota.addCell(dedCell);

		document.add(tablota);

		PdfPTable table6 = new PdfPTable(6);
		table6.setWidthPercentage(100);
		table6.setWidths(new int[] { 34, 6, 10, 34, 6, 10 });

		PdfPCell cell1table6 = new PdfPCell(new Paragraph("Total: ", font2));
		//cell1table6.setBorderWidth(0);
		PdfPCell cell2table6 = new PdfPCell(new Paragraph(formatter.format(totalPercepciones), font2));
		//cell2table6.setBorderWidth(0);
		PdfPCell cell3table6 = new PdfPCell(new Paragraph("Total: ", font2));
		//cell3table6.setBorderWidth(0);
		PdfPCell cell4table6 = new PdfPCell(new Paragraph(formatter.format(totalDeducciones), font2));
		//cell4table6.setBorderWidth(0);
		PdfPCell cell5table6 = new PdfPCell(new Paragraph("PAGAR: ", font2));
		//cell5table6.setBorderWidth(0);
		PdfPCell cell6table6 = new PdfPCell(new Paragraph(formatter.format(p.getCantidadAPagar()), font2));
		//cell6table6.setBorderWidth(0);

		table6.addCell(emptyCell);
		table6.addCell(cell1table6);
		table6.addCell(cell2table6);
		table6.addCell(emptyCell);
		table6.addCell(cell3table6);
		table6.addCell(cell4table6);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(cell5table6);
		table6.addCell(cell6table6);

		document.add(table6);
		
		PdfPTable table7 = new PdfPTable(3);
		table7.setWidthPercentage(100);
		table7.setWidths(new int[] { 15, 66, 15 });

		PdfPCell cell1table7 = new PdfPCell();
		cell1table7.setBorderWidth(0);

		Phrase line1footer = new Phrase();
		Chunk line1part1 = new Chunk("Num. certificado emisor ", font2);
		Chunk line1part2 = new Chunk("00001000000307160885 ", font3);
		Chunk line1part3 = new Chunk("Num. certificado SAT ", font2);
		Chunk line1part4 = new Chunk("00001000000401477845", font3);
		line1footer.add(line1part1);
		line1footer.add(line1part2);
		line1footer.add(line1part3);
		line1footer.add(line1part4);

		PdfPCell cell2table7 = new PdfPCell(line1footer);
		cell2table7.setBorderWidth(0);

		Phrase line2footer = new Phrase();
		Chunk line2part1 = new Chunk("Folio Fiscal: ", font2);
		Chunk line2part2 = new Chunk("D8E99A7B-7047-4B9C-B32E-7FC8BF84C73F ", font3);
		Chunk line2part3 = new Chunk("Fecha de Certificación: ", font2);
		Chunk line2part4 = new Chunk("2016-10-17T16:51:07 \n\n", font3);
		line2footer.add(line2part1);
		line2footer.add(line2part2);
		line2footer.add(line2part3);
		line2footer.add(line2part4);

		PdfPCell cell3table7 = new PdfPCell(line2footer);
		cell3table7.setBorderWidth(0);

		Phrase line3footer = new Phrase();
		Chunk line3part1 = new Chunk("Sello del emisor ", font2);
		Chunk line3part2 = new Chunk(
				"tOSe+Ex/wvn33YlGwtfmrJwQ31Crd7lI9VcH63TGjHfxk5vfb3q9uSbDUGk9TXvo70ydOpikRVw+9B2Six0mbu3PjoPpO909oAYITrRyomdeUGJ4vmA2/12L86EJLWpU7vIt4cL8HpkEw7TOFhSdpzb/890+jP+C1adBsHU1VHc=",
				font3);
		line3footer.add(line3part1);
		line3footer.add(line3part2);
		
		PdfPCell cell4table7 = new PdfPCell(line3footer);
		cell4table7.setBorderWidth(0);
		
		Phrase line4footer = new Phrase();
		Chunk line4part1 = new Chunk("Sello del SAT ", font2);
		Chunk line4part2 = new Chunk(
				"tOSe+Ex/wvn33YlGwtfmrJwQ31Crd7lI9VcH63TGjHfxk5vfb3q9uSbDUGk9TXvo70ydOpikRVw+9B2Six0mbu3PjoPpO909oAYITrRyomdeUGJ4vmA2/12L86EJLWpU7vIt4cL8HpkEw7TOFhSdpzb/890+jP+C1adBsHU1VHc=tOSe+Ex/wvn33YlGwtfmrJwQ31Crd7lI9VcH63TGjHfxk5vfb3q9uSbDUGk9TXvo70ydOpikRVw+9B2Six0mbu3PjoPpO909oAYITrRyomdeUGJ4vmA2/12L86EJLWpU7vIt4cL8HpkEw7TOFhSdpzb/890+jP+C1adBsHU1VHc=",
				font3);
		line4footer.add(line4part1);
		line4footer.add(line4part2);
		
		PdfPCell cell5table7 = new PdfPCell(line4footer);
		cell5table7.setBorderWidth(0);
		
		Phrase line5footer = new Phrase();
		Chunk line5part1 = new Chunk("Cadena Original TFD", font2);
		Chunk line5part2 = new Chunk(
				"tOSe+Ex/wvn33YlGwtfmrJwQ31Crd7lI9VcH63TGjHfxk5vfb3q9uSbDUGk9TXvo70ydOpikRVw+9B2Six0mbu3PjoPpO909oAYITrRyomdeUGJ4vmA2/12L86EJLWpU7vIt4cL8HpkEw7TOFhSdpzb/890+jP+C1adBsHU1VHc=tOSe+Ex/wvn33YlGwtfmrJwQ31Crd7lI9VcH63TGjHfxk5vfb3q9uSbDUGk9TXvo70ydOpikRVw+9B2Six0mbu3PjoPpO909oAYITrRyomdeUGJ4vmA2/12L86EJLWpU7vIt4cL8HpkEw7TOFhSdpzb/890+jP+C1adBsHU1VHc= \n\n\n\n\n\n\n\n\n",
				font3);
		line5footer.add(line5part1);
		line5footer.add(line5part2);
		
		PdfPCell cell6table7 = new PdfPCell(line5footer);
		cell6table7.setBorderWidth(0);


		table7.addCell(cell1table7);
		table7.addCell(cell2table7);
		table7.addCell(cell1table7);

		table7.addCell(cell1table7);
		table7.addCell(cell3table7);
		table7.addCell(cell1table7);
	
		table7.addCell(cell1table7);
		table7.addCell(cell4table7);
		
		PdfPCell firmaCell = new PdfPCell(new Paragraph("Firma del Empleado", font3));
		firmaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		firmaCell.setBorderWidthBottom(0);
		firmaCell.setBorderWidthLeft(0);
		firmaCell.setBorderWidthRight(0);
		table7.addCell(firmaCell);
		
		table7.addCell(cell1table7);
		table7.addCell(cell5table7);
		table7.addCell(cell1table7);
		
		table7.addCell(cell1table7);
		table7.addCell(cell6table7);
		table7.addCell(cell1table7);

		document.add(table7);
	
		return document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public ListaPagosVO getLista() {
		return lista;
	}

	public void setLista(ListaPagosVO lista) {
		this.lista = lista;
	}

}
