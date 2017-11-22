/**
 * 
 */
package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Key;
import com.tikal.cacao.dao.CatalogosDAO;
import com.tikal.cacao.model.CatalogosContabilidadPropia;
import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.springController.viewObjects.CPTipoDeduccionVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

/**
 * @author Tikal
 *
 */
@Controller
@RequestMapping(value = {"cPropiaDeducciones"})
public class ContabilidadPropiaTipoDeduccionController implements CatalogosContabilidadPropiaController {

	
	@Resource(name="cpTipoDeducciondao")
	private CatalogosDAO<ContabilidadPropiaTipoDeduccion, Long, String> cpTipoDeduccionDAO; 
	
	@Resource(name="tipoDeducciondao")
	private CatalogosDAO<EntTipoDeduccion, Long, String> satTipoDeduccionDAO;
	
	@Override
	@RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json")
	public void crear(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		CPTipoDeduccionVO cpTipoDedVO = (CPTipoDeduccionVO) JsonConvertidor.fromJson(json, CPTipoDeduccionVO.class);
		EntTipoDeduccion satTipoDed = satTipoDeduccionDAO.consultarPorIndice(cpTipoDedVO.getIdTipoSAT(), EntTipoDeduccion.class);
		CatalogosContabilidadPropia<EntTipoDeduccion> cpTipoPer = cpTipoDedVO.getCpTipoDeduccion();
		cpTipoPer.setLlaveAgrupadora(Key.create(satTipoDed));
		cpTipoDeduccionDAO.crear((ContabilidadPropiaTipoDeduccion)cpTipoPer);

	}

	
	@Override
	@RequestMapping(value = {"/update" }, method = RequestMethod.POST, consumes = "application/json")
	public void modificar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		CPTipoDeduccionVO cpTipoDedVO = (CPTipoDeduccionVO) JsonConvertidor.fromJson(json, CPTipoDeduccionVO.class);
		EntTipoDeduccion satTipoDed = satTipoDeduccionDAO.consultar(Long.parseLong(cpTipoDedVO.getIdTipoSAT()), EntTipoDeduccion.class);
		CatalogosContabilidadPropia<EntTipoDeduccion> cpTipoPer = cpTipoDedVO.getCpTipoDeduccion();
		cpTipoPer.setLlaveAgrupadora(Key.create(satTipoDed));
		cpTipoDeduccionDAO.crear((ContabilidadPropiaTipoDeduccion)cpTipoPer);

	}

	
	@Override
	@RequestMapping(value = { "/findAll/{rfcEmpresa}" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarTodos(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfcEmpresa) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<ContabilidadPropiaTipoDeduccion> lista = cpTipoDeduccionDAO.consultarTodosPorIndice(ContabilidadPropiaTipoDeduccion.class, rfcEmpresa);
		response.getWriter().println(JsonConvertidor.toJson(lista));

	}

}
