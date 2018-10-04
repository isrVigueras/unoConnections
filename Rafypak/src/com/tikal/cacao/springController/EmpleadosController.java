package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.springController.viewObjects.EmpleadoVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

@Controller
@RequestMapping(value = { "/employee" })
public class EmpleadosController {

	@Autowired
	@Qualifier("employeedao")
	EmpleadosDAO employeedao;
	
	@Autowired
	@Qualifier("regimenesdao")
	RegimenesDAO regimenesdao;
	
	@Autowired
	BitacoraDAO bitacoradao;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void add(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		EmpleadoVO evo = (EmpleadoVO) JsonConvertidor.fromJson(json, EmpleadoVO.class);
		Empleado e= evo.getEmpleado();
		employeedao.crear(e);
		Regimen r = regimenesdao.consultar(Long.parseLong(evo.getIdEmpresa()));
		r.getIdEmpleados().add(e.getNumEmpleado());
		regimenesdao.actualizar(r);
		response.getWriter().println(JsonConvertidor.toJson(e));
		
		String evento = "Se dio de alta el empleado: " + e.getNombre().getNombresDePila() + " " + e.getNombre().getApellidoPaterno() +
				" en el régimen: " + evo.getNombreRegimen();
		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(request.getSession(), "Operacional", evento);
		bitacoradao.addReg(registroBitacora);
	}

	/**
	 * Find Employee by id
	 * 
	 * @param response
	 * @param request
	 * @param json
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value = { "/find/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void find(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String id) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		response.getWriter().println(JsonConvertidor.toJson(employeedao.consultar(Long.parseLong(id))));
	}

	@RequestMapping(value = { "/getByRegimen/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void findByRegimen(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String id) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<EmpleadoVO> empleados= employeedao.consultaVOPorRegimen(Long.parseLong(id));
		response.getWriter().println(JsonConvertidor.toJson(empleados));
	}
	
	@RequestMapping(value = { "/getByEmpresa/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void findByEmpresa(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String id) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<EmpleadoVO> empleados= employeedao.consultaPorEmpresa(id);
		response.getWriter().println(JsonConvertidor.toJson(empleados));
	}
	
	/**
	 * Update Employee
	 * 
	 * @param response
	 * @param request
	 * @param json
	 * @throws IOException
	 */
	@RequestMapping(value = {
			"/update" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void update(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		EmpleadoVO evo = (EmpleadoVO) JsonConvertidor.fromJson(json, EmpleadoVO.class);
		Empleado e= evo.getEmpleado();
		employeedao.actualizar(e);
		response.getWriter().println(JsonConvertidor.toJson(e));
	}

	/**
	 * Remove Employee from storage
	 * 
	 * @param response
	 * @param request
	 * @param json
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value = {
			"/delete/{id}/{idReg}" }, method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public void delete(HttpServletResponse response, HttpServletRequest request, @PathVariable String idReg,
			@PathVariable String id) throws IOException {
		Regimen r = regimenesdao.consultar(Long.parseLong(idReg));
		employeedao.eliminar(employeedao.consultar(Long.parseLong(id))); // curado se quito parámetro Regimen
		// cambie el parámetro 'json' a 'idReg' para deshacer la relación entre el 
		// empleado a eliminar y el regimen al que éste pertenecía
	}

}
