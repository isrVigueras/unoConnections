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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.CatalogosDAO;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

/**
 * @author Tikal
 *
 */
@Controller
@RequestMapping(value = {"/deducciones"})
public class EntTipoDeduccionController implements CatalogosController {

	@Resource(name="tipoDeducciondao")
	private CatalogosDAO<EntTipoDeduccion, Long, String> entTipoDeduccionDAO;
	
	@Override
	@RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json")
	public void crear(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		EntTipoDeduccion concepto = (EntTipoDeduccion) JsonConvertidor.fromJson(json, EntTipoDeduccion.class);
		entTipoDeduccionDAO.crear(concepto);
	}

	@Override
	@RequestMapping(value = {"/update" }, method = RequestMethod.POST, consumes = "application/json")
	public void modificar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		crear(response, request, json);
		
	}

	@Override
	@RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarTodos(HttpServletResponse response, HttpServletRequest request) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<EntTipoDeduccion> lista = entTipoDeduccionDAO.consultarTodos(EntTipoDeduccion.class);
		response.getWriter().println(JsonConvertidor.toJson(lista));
	}

}
