package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/serial" })
public class SerialController {

	@Autowired
	SerialDAO serialdao;
	
	@Autowired
	UsuarioDAO usuariodao;
	
	@Autowired
	PerfilDAO perfildao;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void add(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException {
		if (ServicioSesion.verificarPermiso(req, usuariodao, perfildao, 14)) {
			AsignadorDeCharset.asignar(req, res);
			Serial s = (Serial) JsonConvertidor.fromJson(json, Serial.class);
			serialdao.guardar(s);
		}else {
			res.sendError(403);
		}
	}

	@RequestMapping(value = { "getAll/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void get(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		if (ServicioSesion.verificarPermiso(req, usuariodao, perfildao, 14)||ServicioSesion.verificarPermiso(req, usuariodao, perfildao, 11)) {
			AsignadorDeCharset.asignar(req, res);
			res.getWriter().println(JsonConvertidor.toJson(serialdao.consultar(rfc)));
		}else {
			res.sendError(403);
		}
	}

	@RequestMapping(value = { "getAll/{rfc}/{serie}" }, method = RequestMethod.GET, produces = "application/json")
	public void getSerial(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,
			@PathVariable String serie) throws IOException {
		if (ServicioSesion.verificarPermiso(req, usuariodao, perfildao, 14)) {
			AsignadorDeCharset.asignar(req, res);
			res.getWriter().println(JsonConvertidor.toJson(serialdao.consultar(rfc, serie)));
		}else {
			res.sendError(403);
		}
	}
}
