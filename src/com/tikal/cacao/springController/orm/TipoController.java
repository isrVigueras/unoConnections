package com.tikal.cacao.springController.orm;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.sql.TipoDAO;
import com.tikal.cacao.model.orm.Tipo;
import com.tikal.cacao.util.JsonConvertidor;

@Controller()
@RequestMapping(value = {"/tipo"})
public class TipoController {

	@Autowired
	@Qualifier("tipoDAOH")
	private TipoDAO tipoDAO;
	
	@RequestMapping(value = {"/getList"}, method = RequestMethod.GET, produces = "application/json")
	public void getLista(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		List<Tipo> listaT = tipoDAO.consultarTodos();
		resp.getWriter().println(JsonConvertidor.toJson(listaT));
	}
	
	// Método TEMPORAL
	//@RequestMapping(value = {"/pruebaSQL"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		Tipo tipoP = new Tipo();
		tipoP.setId("P");
		tipoP.setNombre("Producto");
		
		Tipo tipoS = new Tipo();
		tipoS.setId("S");
		tipoS.setNombre("Servicio");
		
		tipoDAO.guardar(tipoP);
		tipoDAO.guardar(tipoS);
		
		List<Tipo> listaT = tipoDAO.consultarTodos();
		resp.getWriter().println(JsonConvertidor.toJson(listaT));
		
	}
}
