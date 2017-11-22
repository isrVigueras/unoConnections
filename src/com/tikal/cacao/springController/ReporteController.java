package com.tikal.cacao.springController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.reporte.Reporte;
import com.tikal.cacao.reporte.ReporteRenglon;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/reportes" })
public class ReporteController {

	@Autowired
	FacturaDAO facturaDAO;
	
	@Autowired
	ReporteRenglonDAO repRenglonDAO;

	@RequestMapping(value = "/fechas/{rfc}/{finicio}/{ffinal}", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public void buscar(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,
			@PathVariable String finicio, @PathVariable String ffinal) {
		try {
			AsignadorDeCharset.asignar(req, res);
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date datei = formatter.parse(finicio);
			Date datef = formatter.parse(ffinal);
			Calendar c = Calendar.getInstance();
			c.setTime(datef);
			c.add(Calendar.DATE, 1);
			datef = c.getTime();
			
//			List<Factura> lista = facturaDAO.buscar(datei, datef, rfc);
//			Reporte rep = new Reporte(lista);
			List<ReporteRenglon> repRenglones = repRenglonDAO.consultar(rfc, null, datei, datef);
			Reporte rep = new Reporte();
			rep.setRenglones(repRenglones);
			HSSFWorkbook reporte = rep.getReporte();
			reporte.write(res.getOutputStream());

			// res.getWriter().println(JsonConvertidor.toJson(listaVO));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	//SOLO PARA NOSOTROS
		@RequestMapping(value = "/dump/{rfcEmisor}", method = RequestMethod.GET)
		public void replicarFacturaAReporteRenglon(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfcEmisor) {
			List<Factura> facturas = facturaDAO.consutarTodas(rfcEmisor);
			List<ReporteRenglon> listaRepRenglon = new ArrayList<>();
			ReporteRenglon reporteRenglon = null;
			for (Factura factura : facturas) {
				if (factura.getFechaCertificacion() != null) {
					reporteRenglon = new ReporteRenglon(factura);
					listaRepRenglon.add(reporteRenglon);
				}
				
			}
			repRenglonDAO.guardar(listaRepRenglon);
			try {
				res.getWriter().println(JsonConvertidor.toJson(listaRepRenglon));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
