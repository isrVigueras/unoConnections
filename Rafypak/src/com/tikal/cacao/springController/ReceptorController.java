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

import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Receptor;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

@Controller
@RequestMapping(value = { "/receptor" })
public class ReceptorController {

	@Autowired
	EmisorDAO emisordao;

	@Autowired
	UsuarioDAO usuariodao;

	@Autowired
	PerfilDAO perfildao;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException {

		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 13)) {
			AsignadorDeCharset.asignar(re, rs);
			Emisor e = (Emisor) JsonConvertidor.fromJson(json, Emisor.class);
			Receptor r = e.getReceptores().get(0);
			Util.validarDomicilioReceptor(r.getDomicilio());
			emisordao.addReceptor(e.getRfc(), r);
		}else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/edit/{indice}" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void edit(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json, @PathVariable int indice)
			throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 13)) {
			AsignadorDeCharset.asignar(re, rs);
			Emisor e = (Emisor) JsonConvertidor.fromJson(json, Emisor.class);
			Receptor r = e.getReceptores().get(0);
			Util.validarDomicilioReceptor(r.getDomicilio());
			emisordao.updateReceptor(e.getRfc(), r, indice);
		}else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/delete/{ind}" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void deleteReceptor(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json,
			@PathVariable int ind) throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 13)) {
			AsignadorDeCharset.asignar(re, rs);
			Emisor e = (Emisor) JsonConvertidor.fromJson(json, Emisor.class);
			emisordao.deleteReceptor(e.getRfc(), ind);
		}else {
			rs.sendError(403);
		}
	}

}
