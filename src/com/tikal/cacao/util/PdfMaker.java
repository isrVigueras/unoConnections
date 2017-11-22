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

public class PdfMaker {

	private Document document;
	private ListaPagosVO lista;

	public PdfMaker() {
		this.document = new Document();
		this.document.setPageSize(PageSize.A3);
		this.document.setMargins(40, 40, 40, 40); // Left Right Top Bottom
	}

	public Document imprimirPagos() throws ParseException {
		for (PagoVO p : this.lista.getLista()) {
			try {
				this.document = construirPdf(p);
				this.document = construirPdf(p);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return this.document;
	}

	public Document construirPdf(PagoVO p) throws DocumentException, ParseException {

		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100);
		headerTable.setWidths(new int[] { 20, 60, 15 });

		Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

		BaseColor azul = new BaseColor(204, 229, 255);

		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorderWidth(0);

		PdfPCell centralh1 = new PdfPCell(new Paragraph("TEXTO DE PRUEBA CENTRAL", font1));
		centralh1.setHorizontalAlignment(Element.ALIGN_CENTER);
		centralh1.setBorderWidth(0);
		
		PdfPCell righth1 = new PdfPCell(new Paragraph("Recibo", font3));
		righth1.setBackgroundColor(azul);
		righth1.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell centralh2 = new PdfPCell(new Paragraph("Régimen Fiscal:", font2));
		centralh2.setHorizontalAlignment(Element.ALIGN_CENTER);
		centralh2.setBorderWidth(0);

		PdfPCell righth2 = new PdfPCell(
				new Paragraph("Texto de Prueba", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.RED)));
		righth2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell centralh3 = new PdfPCell(new Paragraph("Lugar de Expedición:", font2));
		centralh3.setHorizontalAlignment(Element.ALIGN_CENTER);
		centralh3.setBorderWidth(0);

		PdfPCell righth3 = new PdfPCell(new Paragraph("Fecha Pago", font3));
		righth3.setBackgroundColor(azul);
		righth3.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell centralh4 = new PdfPCell(new Paragraph("Fecha y Hora de Emisión:", font2));
		centralh4.setHorizontalAlignment(Element.ALIGN_CENTER);
		centralh4.setBorderWidth(0);

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
		
		PdfPCell righth4 = new PdfPCell(new Paragraph(date, font3)); ///
		righth4.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell centralh5 = new PdfPCell(new Paragraph("Periodo de Pago:", font2));
		centralh5.setHorizontalAlignment(Element.ALIGN_CENTER);
		centralh5.setBorderWidth(0);

		headerTable.addCell(emptyCell);
		headerTable.addCell(centralh1);
		headerTable.addCell(righth1);
		headerTable.addCell(emptyCell);
		headerTable.addCell(centralh2);
		headerTable.addCell(righth2);
		headerTable.addCell(emptyCell);
		headerTable.addCell(centralh3);
		headerTable.addCell(righth3);
		headerTable.addCell(emptyCell);
		headerTable.addCell(centralh4);
		headerTable.addCell(righth4);
		headerTable.addCell(emptyCell);
		headerTable.addCell(centralh5);
		headerTable.addCell(emptyCell);

		document.add(headerTable);

		PdfPTable table1 = new PdfPTable(5);
		table1.setWidthPercentage(100);
		table1.setWidths(new int[] { 28, 17, 59, 21, 15 });

		PdfPCell cell1table1 = new PdfPCell(new Paragraph("No. Empleado", font3));
		cell1table1.setBackgroundColor(azul);
		cell1table1.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table1 = new PdfPCell(new Paragraph("Nombre", font3));
		cell2table1.setBackgroundColor(azul);
		cell2table1.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table1 = new PdfPCell(new Paragraph("CURP", font3));
		cell3table1.setBackgroundColor(azul);
		cell3table1.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell4table1 = new PdfPCell(new Paragraph("RFC", font3));
		cell4table1.setBackgroundColor(azul);
		cell4table1.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell5table1 = new PdfPCell(new Paragraph(p.getIdEmpleado() + "", font3));
		PdfPCell cell6table1 = new PdfPCell(new Paragraph(p.getNombre(), font3));
		PdfPCell cell7table1 = new PdfPCell(new Paragraph(p.getCurp(), font3));
		PdfPCell cell8table1 = new PdfPCell(new Paragraph(p.getRfc(), font3));

		table1.addCell(emptyCell);
		table1.addCell(cell1table1);
		table1.addCell(cell2table1);
		table1.addCell(cell3table1);
		table1.addCell(cell4table1);
		table1.addCell(emptyCell);
		table1.addCell(cell5table1);
		table1.addCell(cell6table1);
		table1.addCell(cell7table1);
		table1.addCell(cell8table1);

		document.add(table1);

		PdfPTable table2 = new PdfPTable(6);
		table2.setWidthPercentage(100);
		table2.setWidths(new int[] { 15, 15, 15, 25, 25, 20 });

		PdfPCell cell1table2 = new PdfPCell(new Paragraph("Régimen", font3));
		cell1table2.setBackgroundColor(azul);
		cell1table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table2 = new PdfPCell(new Paragraph("NSS", font3));
		cell2table2.setBackgroundColor(azul);
		cell2table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table2 = new PdfPCell(new Paragraph("Fecha de Ingreso", font3));
		cell3table2.setBackgroundColor(azul);
		cell3table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell4table2 = new PdfPCell(new Paragraph("Puesto", font3));
		cell4table2.setBackgroundColor(azul);
		cell4table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell5table2 = new PdfPCell(new Paragraph("Departamento", font3));
		cell5table2.setBackgroundColor(azul);
		cell5table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell6table2 = new PdfPCell(new Paragraph("S.D / S.D.I", font3));
		cell6table2.setBackgroundColor(azul);
		cell6table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell7table2 = new PdfPCell(new Paragraph(p.getTipoRegimen(), font3));
		PdfPCell cell8table2 = new PdfPCell(new Paragraph(p.getNss(), font3));
		PdfPCell cell9table2 = new PdfPCell(new Paragraph(fechaIngreso, font3));
		PdfPCell cell10table2 = new PdfPCell(new Paragraph(p.getPuesto(), font3));
		PdfPCell cell11table2 = new PdfPCell(new Paragraph(p.getDepartamento(), font3));
		PdfPCell cell12table2 = new PdfPCell(new Paragraph(p.getSalarioDiario()+" / "+ p.getSalarioDiarioIntegrado(), font3));

		table2.addCell(cell1table2);
		table2.addCell(cell2table2);
		table2.addCell(cell3table2);
		table2.addCell(cell4table2);
		table2.addCell(cell5table2);
		table2.addCell(cell6table2);
		table2.addCell(cell7table2);
		table2.addCell(cell8table2);
		table2.addCell(cell9table2);
		table2.addCell(cell10table2);
		table2.addCell(cell11table2);
		table2.addCell(cell12table2);

		document.add(table2);

		PdfPTable table3 = new PdfPTable(8);
		table3.setWidthPercentage(100);
		table3.setWidths(new int[] { 16, 16, 16, 10, 15, 20, 15, 15 });

		PdfPCell cell1table3 = new PdfPCell(new Paragraph("Registro Patronal", font3));
		cell1table3.setBackgroundColor(azul);
		cell1table3.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table3 = new PdfPCell(new Paragraph("Banco", font3));
		cell2table3.setBackgroundColor(azul);
		cell2table3.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table3 = new PdfPCell(new Paragraph("CLABE", font3));
		cell3table3.setBackgroundColor(azul);
		cell3table3.setHorizontalAlignment(Element.ALIGN_CENTER); ////el chino me lo puede dar
		PdfPCell cell4table3 = new PdfPCell(new Paragraph("Días Pag.", font3));
		cell4table3.setBackgroundColor(azul);
		cell4table3.setHorizontalAlignment(Element.ALIGN_CENTER); //este también me lo puede dar el chino 
		PdfPCell cell5table3 = new PdfPCell(new Paragraph("Periodicidad", font3));
		cell5table3.setBackgroundColor(azul);
		cell5table3.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell6table3 = new PdfPCell(new Paragraph("Contrato", font3));
		cell6table3.setBackgroundColor(azul);
		cell6table3.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell7table3 = new PdfPCell(new Paragraph("Jornada", font3));
		cell7table3.setBackgroundColor(azul);
		cell7table3.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell8table3 = new PdfPCell(new Paragraph("Riesgo Trabajo", font3));
		cell8table3.setBackgroundColor(azul);
		cell8table3.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell9table3 = new PdfPCell(new Paragraph("C675540716", font3));
		PdfPCell cell10table3 = new PdfPCell(new Paragraph(" ", font3));
		PdfPCell cell11table3 = new PdfPCell(new Paragraph(" ", font3));
		PdfPCell cell12table3 = new PdfPCell(new Paragraph(p.getDiasPagados(), font3));
		PdfPCell cell13table3 = new PdfPCell(new Paragraph(p.getFormaPago(), font3));
		PdfPCell cell14table3 = new PdfPCell(new Paragraph(" ", font3));
		PdfPCell cell15table3 = new PdfPCell(new Paragraph(" ", font3));
		PdfPCell cell16table3 = new PdfPCell(new Paragraph(" ", font3));

		table3.addCell(cell1table3);
		table3.addCell(cell2table3);
		table3.addCell(cell3table3);
		table3.addCell(cell4table3);
		table3.addCell(cell5table3);
		table3.addCell(cell6table3);
		table3.addCell(cell7table3);
		table3.addCell(cell8table3);
		table3.addCell(cell9table3);
		table3.addCell(cell10table3);
		table3.addCell(cell11table3);
		table3.addCell(cell12table3);
		table3.addCell(cell13table3);
		table3.addCell(cell14table3);
		table3.addCell(cell15table3);
		table3.addCell(cell16table3);

		document.add(table3);

		PdfPTable table4 = new PdfPTable(2);
		table4.setWidthPercentage(100);

		PdfPCell cell1table4 = new PdfPCell(new Paragraph("PERCEPCIONES", font3));
		cell1table4.setBackgroundColor(azul);
		cell1table4.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table4 = new PdfPCell(new Paragraph("DEDUCCIONES", font3));
		cell2table4.setBackgroundColor(azul);
		cell2table4.setHorizontalAlignment(Element.ALIGN_CENTER);

		table4.addCell(cell1table4);
		table4.addCell(cell2table4);

		document.add(table4);

		PdfPTable table5 = new PdfPTable(6);
		table5.setWidthPercentage(100);
		table5.setWidths(new int[] { 10, 30, 10, 10, 30, 10 });

		PdfPCell cell1table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell1table5.setBackgroundColor(azul);
		cell1table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell2table5.setBackgroundColor(azul);
		cell2table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table5 = new PdfPCell(new Paragraph("Importe", font3));
		cell3table5.setBackgroundColor(azul);
		cell3table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell4table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell4table5.setBackgroundColor(azul);
		cell4table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell5table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell5table5.setBackgroundColor(azul);
		cell5table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell6table5 = new PdfPCell(new Paragraph("Importe", font3));
		cell6table5.setBackgroundColor(azul);
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
		PdfPCell cell2table6 = new PdfPCell(new Paragraph(formatter.format(totalPercepciones), font2));
		PdfPCell cell3table6 = new PdfPCell(new Paragraph("Total: ", font2));
		PdfPCell cell4table6 = new PdfPCell(new Paragraph(formatter.format(totalDeducciones), font2));
		PdfPCell cell5table6 = new PdfPCell(new Paragraph("PAGAR: ", font2));
		PdfPCell cell6table6 = new PdfPCell(new Paragraph(formatter.format(p.getCantidadAPagar()), font2));

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
		document.add(new Chunk("\n\n\n\n\n"));

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
