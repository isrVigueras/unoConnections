package com.tikal.cacao.springController;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.springController.viewObjects.DatosFacturaVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/emisor" })
public class EmisorController {

	@Autowired
	EmisorDAO emisordao;
	
	@Resource(name = "empresasdao")
	EmpresasDAO empresasdao;
	
	@RequestMapping(value = {
			"/consultar/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void get(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc) throws IOException {
		
		AsignadorDeCharset.asignar(re, rs);
		Emisor emisor = emisordao.consultar(rfc);
		Empresa empresa = empresasdao.consultar(rfc);
		
		Direccion direccion = empresa.getDireccion();
		String lugarDeExpedicion = "";
		if(direccion.getLocalidad()!=null){
			lugarDeExpedicion+=direccion.getLocalidad()+", ";
		}
		if(direccion.getMunicipio()!=null){
			lugarDeExpedicion+=direccion.getMunicipio()+", ";
		}
		lugarDeExpedicion +=direccion.getEstado();
				
		DatosFacturaVO datosFacturaVO = new DatosFacturaVO(lugarDeExpedicion, empresa.getRazonSocial(), emisor, empresa.getEmails());
		datosFacturaVO.construirDomicilio(direccion, "MÉXICO");
		rs.getWriter().println(JsonConvertidor.toJson(datosFacturaVO));
	}
	
	@RequestMapping(value = {
	"/consultar33/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void get33(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc) throws IOException {
	
		AsignadorDeCharset.asignar(re, rs);
		Emisor emisor = emisordao.consultar(rfc);
		Empresa empresa = empresasdao.consultar(rfc);
		
		Direccion direccion = empresa.getDireccion();
		String lugarDeExpedicion = null;
		if(direccion.getCodigoPostal() != null) {
			lugarDeExpedicion = direccion.getCodigoPostal();
		}
				
		DatosFacturaVO datosFacturaVO = new DatosFacturaVO(lugarDeExpedicion, empresa.getRazonSocial(), emisor, empresa.getEmails());
		//datosFacturaVO.construirDomicilio(direccion, "MÉXICO");
		rs.getWriter().println(JsonConvertidor.toJson(datosFacturaVO));
	}

	@RequestMapping(value = { "/consultar2/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void getReceptores(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc) throws IOException {

		AsignadorDeCharset.asignar(re, rs);
		rs.getWriter().println(JsonConvertidor.toJson(emisordao.consultar(rfc).getReceptores()));
	}
}
