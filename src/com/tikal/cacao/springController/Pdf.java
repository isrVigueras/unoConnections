package com.tikal.cacao.springController;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.PagosDAO;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;
import com.tikal.cacao.springController.viewObjects.PagoVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.PdfMakerV2;

@Controller
@RequestMapping(value = {"/pdf"})
public class Pdf {
	
	@Autowired
	PagosDAO pagosdao;
	
	@Autowired EmpleadosDAO empdao;
	
	@RequestMapping(value={"/recibo"}, method = RequestMethod.POST,produces="application/pdf")
	public void generaReporte(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException, ParseException{
		AsignadorDeCharset.asignar(request, response);
		response.setContentType("Application/Pdf");
		String json = request.getParameter("cadena");
		ListaPagosVO lista = (ListaPagosVO)JsonConvertidor.fromJson(json, ListaPagosVO.class);
		PdfMakerV2 nuevo = new PdfMakerV2();
		nuevo.setLista(lista);
		PdfWriter writer= PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPagos();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@RequestMapping(value={"/recibo/{id}"}, method = RequestMethod.GET,produces="application/pdf")
	public void generaReporteInd(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws DocumentException, IOException, ParseException{
		AsignadorDeCharset.asignar(request, response);
		response.setContentType("Application/Pdf");
		Pago p = pagosdao.consultar(Long.parseLong(id));
		Empleado e= empdao.consultar(p.getIdEmpleado());
		PagoVO pago = new PagoVO(p,e);
		
//		String json = request.getParameter("cadena");
//		ListaPagosVO lista = (ListaPagosVO)JsonConvertidor.fromJson(json, ListaPagosVO.class);
		PdfMakerV2 nuevo = new PdfMakerV2();
//		nuevo.setLista(lista);
		PdfWriter writer= PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.construirPdf(pago);
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}