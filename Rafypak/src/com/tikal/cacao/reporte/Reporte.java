package com.tikal.cacao.reporte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.tikal.cacao.model.Factura;

public class Reporte {
	private List<ReporteRenglon> renglones;

	public Reporte() {}
	
	public Reporte(List<Factura> facturas) {
		this.renglones = new ArrayList<ReporteRenglon>();
		for (Factura f : facturas) {
			ReporteRenglon r = new ReporteRenglon(f);
			this.renglones.add(r);
		}
	}
	
	public void setRenglones(List<ReporteRenglon> renglones) {
		this.renglones = renglones;
	}
	
	public List<ReporteRenglon> getRenglones() {
		return renglones;
	}

	public HSSFWorkbook getReporte() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Hoja excel");

		String[] headers = new String[] { "Fecha", "Empresa Emisora", "Serie","Folio", "Receptor", "RFC Receptor",
				"Lugar de Expedición", "Folio Fiscal", "Subtotal", "IVA", "Total","Status"};
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);

        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        for(int i=0; i<this.renglones.size();i++){
        	ReporteRenglon reng= this.renglones.get(i);
    		reng.setStrFechaCertificacion(formatter.format(reng.getFecha()));
        	HSSFRow dataRow = sheet.createRow(i + 1);
        	reng.llenarRenglon(dataRow);
        	
        }
        sheet.setColumnWidth(0, 13*256);
        sheet.setColumnWidth(1, 35*256);
        sheet.setColumnWidth(2, 10*256);
        sheet.setColumnWidth(3, 15*256);
        sheet.setColumnWidth(4, 25*256);
        sheet.setColumnWidth(5, 20*256);
        sheet.setColumnWidth(6, 25*256);
        sheet.setColumnWidth(7, 40*256);
        sheet.setColumnWidth(8, 13*256);
        sheet.setColumnWidth(9, 13*256);
        sheet.setColumnWidth(10, 13*256);
        sheet.setColumnWidth(11, 20*256);
		return workbook;
	}
}
