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

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.springController.viewObjects.RegimenEmpresa;
import com.tikal.cacao.springController.viewObjects.RegimenVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

@Controller
@RequestMapping(value = { "/esquemas" })
public class EsquemasController {

	@Autowired
	EmpresasDAO empresasdao;

	@Autowired
	RegimenesDAO regimenesdao;
	
	@Autowired
	BitacoraDAO bitacoradao;

	@Autowired
	UsuarioDAO usuariodao;
	
	@Autowired
	PerfilDAO perfildao;
	
	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void addEmpresa(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		if(ServicioSesion.verificarPermiso(request, usuariodao, perfildao, 2)){
			System.out.println(json);
			RegimenEmpresa e = (RegimenEmpresa) JsonConvertidor.fromJson(json, RegimenEmpresa.class);
			e.getRegimen().setTipoRegimen(e.getTipo());
			Empresa empresa = empresasdao.consultar( e.getEmpresa() );
			empresasdao.aplicarUnRegimen(e.getRegimen(), empresa);

			String evento = "Se creo el r&eacute;gimen (esquema de pago): " + e.getRegimen().getNombre() + 
					"en la empresa: " + empresa.getRazonSocial();
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(request.getSession(), "Operacional", evento);
			bitacoradao.addReg(registroBitacora);
		}else{
			response.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/update" }, method = RequestMethod.POST, consumes = "application/json")
	public void update(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		if(ServicioSesion.verificarPermiso(request, usuariodao, perfildao, 3)){
		RegimenVO es= (RegimenVO) JsonConvertidor.fromJson(json, RegimenVO.class);
		Regimen r= es.getReg();
		regimenesdao.actualizar(r);
		}else{
			response.sendError(403);
		}
		
	}

	@RequestMapping(value = { "/find/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void find(HttpServletResponse response, HttpServletRequest request, @PathVariable String id)
			throws IOException {
		// Empresa e= (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
		// empresasdao.crear(e);
		AsignadorDeCharset.asignar(request, response);
		RegimenVO r= new RegimenVO(regimenesdao.consultar(Long.parseLong(id)));
		response.getWriter().println(JsonConvertidor.toJson(r));
	}

}
