package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.PagosFacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/recepcionPagos"})
public class PagosFacturaVTTController {
	
	@Autowired
	private PagosFacturaVTTService pagosFacturaVTTService;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired 
	private PerfilDAO perfilDAO;

	@RequestMapping(value = "/agregar/{uuidRelacionado}", method = RequestMethod.POST, consumes = "application/json")
	public void agregarPago(HttpServletRequest req, HttpServletResponse res, @RequestBody String json, @PathVariable String uuidRelacionado) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				ComprobanteConComplementoPagosVO comprobanteConComplementoPagosVO = (ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
//				String mensajeResultado = pagosFacturaVTTService.agregar(comprobanteConComplementoPagosVO, uuidRelacionado);
//				res.getWriter().println(mensajeResultado);
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/timbrar/{uuidRelacionado}", method = RequestMethod.POST, consumes = "application/json")
	public void timbrarPago(HttpServletRequest req, HttpServletResponse res, @RequestBody String json, @PathVariable String uuidRelacionado) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				ComprobanteConComplementoPagosVO comprobanteConComplementoPagosVO = (ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
//				RespuestaWebServicePersonalizada respuestaWS = pagosFacturaVTTService.timbrar(comprobanteConComplementoPagosVO, uuidRelacionado);
//				res.getWriter().println(respuestaWS.getMensajeRespuesta());
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/consultarPorUuidRelacionado/{uuidRelacionado}", method = RequestMethod.GET, produces = "application/json")
	public void consultarPorUuidRelacionado(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuidRelacionado) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				List<PagosFacturaVTT> pagosRelacionados = pagosFacturaVTTService.consultarPorUuidRelacionado(uuidRelacionado);
				res.getWriter().println(JsonConvertidor.toJson(pagosRelacionados));
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void obtenerPDF(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		
	}

	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET, produces = "text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		
	}
}
