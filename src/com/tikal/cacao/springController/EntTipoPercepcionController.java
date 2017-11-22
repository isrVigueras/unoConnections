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
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

/**
 * @author Tikal
 *
 */
@Controller
@RequestMapping(value = {"/percepciones"})
public class EntTipoPercepcionController implements CatalogosController {

	@Resource(name="tipoPercepciondao")
	private CatalogosDAO<EntTipoPercepcion, Long, String> entTipoPercepcionDAO;
	
	@Override
	@RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json")
	public void crear(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		EntTipoPercepcion concepto = (EntTipoPercepcion) JsonConvertidor.fromJson(json, EntTipoPercepcion.class);
		entTipoPercepcionDAO.crear(concepto);
		
	}

	@Override
	@RequestMapping(value = {"/update" }, method = RequestMethod.POST, consumes = "application/json")
	public void modificar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) {
		this.crear(response, request, json);
		
	}

	@Override
	@RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarTodos(HttpServletResponse response, HttpServletRequest request) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		List<EntTipoPercepcion> lista = entTipoPercepcionDAO.consultarTodos(EntTipoPercepcion.class);
		response.getWriter().println(JsonConvertidor.toJson(lista));
	}

}
