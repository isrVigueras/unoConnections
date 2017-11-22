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
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.springController.viewObjects.CPTipoPercepcionVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

/**
 * @author Tikal
 *
 */
@Controller
@RequestMapping(value = {"/cPropiaPercepciones"})
public class ContabilidadPropiaTipoPercepcionController implements CatalogosContabilidadPropiaController {

	@Resource(name="cpTipoPercepciondao")
	private CatalogosDAO<ContabilidadPropiaTipoPercepcion, Long, String> cpTipoPercepcionDAO; 
	
	@Resource(name="tipoPercepciondao")
	private CatalogosDAO<EntTipoPercepcion, Long, String> satTipoPercepcionDAO;
	
	@Override
	@RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json")
	public void crear(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		CPTipoPercepcionVO cpTipoPerVO = (CPTipoPercepcionVO) JsonConvertidor.fromJson(json, CPTipoPercepcionVO.class);
		EntTipoPercepcion satTipoPer = satTipoPercepcionDAO.consultarPorIndice(cpTipoPerVO.getIdTipoSAT(), EntTipoPercepcion.class);
		CatalogosContabilidadPropia<EntTipoPercepcion> cpTipoPer = cpTipoPerVO.getCpTipoPercepcion();
		cpTipoPer.setLlaveAgrupadora(Key.create(satTipoPer));
		cpTipoPercepcionDAO.crear((ContabilidadPropiaTipoPercepcion)cpTipoPer);
	}

	
	@Override
	@RequestMapping(value = {"/update" }, method = RequestMethod.POST, consumes = "application/json")
	public void modificar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		CPTipoPercepcionVO cpTipoPerVO = (CPTipoPercepcionVO) JsonConvertidor.fromJson(json, CPTipoPercepcionVO.class);
		EntTipoPercepcion satTipoPer = satTipoPercepcionDAO.consultar(Long.parseLong(cpTipoPerVO.getIdTipoSAT()), EntTipoPercepcion.class);
		CatalogosContabilidadPropia<EntTipoPercepcion> cpTipoPer = cpTipoPerVO.getCpTipoPercepcion();
		cpTipoPer.setLlaveAgrupadora(Key.create(satTipoPer));
		cpTipoPercepcionDAO.crear((ContabilidadPropiaTipoPercepcion)cpTipoPer);

	}

	
	@Override
	@RequestMapping(value = { "/findAll/{rfcEmpresa}" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarTodos(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfcEmpresa) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<ContabilidadPropiaTipoPercepcion> lista = cpTipoPercepcionDAO.consultarTodosPorIndice(ContabilidadPropiaTipoPercepcion.class, rfcEmpresa);
		response.getWriter().println(JsonConvertidor.toJson(lista));

	}

}
