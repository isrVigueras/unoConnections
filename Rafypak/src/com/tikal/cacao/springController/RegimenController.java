package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
/**
 * 
 * @author Tikal 
 *
 *
 */
@Controller
@RequestMapping(value={"/regimen"})
public class RegimenController {
	
	@Autowired
	@Qualifier("empresasdao")
	EmpresasDAO empresasdao;
	
	@Autowired
	@Qualifier("regimenesdao")
	RegimenesDAO regimenesdao;
	
	/**
	 * Create a new Regimen in storage
	 * 
	 * @param response
	 * @param request
	 * @param json
	 * @throws IOException
	 */
	
	
	@RequestMapping(value={"/add"},method= RequestMethod.POST,produces="application/json",consumes="application/json")
	public void addRegimen(HttpServletResponse response,HttpServletRequest request,@RequestBody String json) throws IOException{
		AsignadorDeCharset.asignar(request, response);
		Regimen r= (Regimen) JsonConvertidor.fromJson(json, Empresa.class);
		regimenesdao.crear(r);
		response.getWriter().println(JsonConvertidor.toJson(r));
	}
	
	
	/**
	 * Find regimen by id
	 * @param response
	 * @param request
	 * @param json
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value={"/find/{id}"},method= RequestMethod.GET,produces="application/json")
	public void find(HttpServletResponse response,HttpServletRequest request,@RequestBody String json,@PathVariable String id) throws IOException{
//		Empresa e= (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
//		empresasdao.crear(e);
		AsignadorDeCharset.asignar(request, response);
		response.getWriter().println(JsonConvertidor.toJson(regimenesdao.consultar(Long.parseLong(id))));
	}
	
	/**
	 * Update regimen
	 * @param response
	 * @param request
	 * @param json
	 * @throws IOException
	 */
	@RequestMapping(value={"/update"},method= RequestMethod.GET,produces="application/json",consumes="application/json")
	public void update(HttpServletResponse response,HttpServletRequest request,@RequestBody String json) throws IOException{
		AsignadorDeCharset.asignar(request, response);
		Regimen e= (Regimen) JsonConvertidor.fromJson(json, Regimen.class);
		regimenesdao.actualizar(e);
		response.getWriter().println(JsonConvertidor.toJson(e));
	}
	
	/**
	 * Remove regimen from storage
	 * @param response
	 * @param request
	 * @param json
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value={"/delete/{id}"},method= RequestMethod.GET,produces="application/json",consumes="application/json")
	public void delete(HttpServletResponse response,HttpServletRequest request,@RequestBody String json,@PathVariable String id) throws IOException{
		regimenesdao.eliminar(regimenesdao.consultar(Long.parseLong(id)));
	}

}
