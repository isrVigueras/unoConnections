package com.tikal.cacao.springController.orm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.sql.UnidadDeMedidaDAO;
import com.tikal.cacao.model.orm.UnidadDeMedida;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/unidad_de_medida"})
public class UnidadDeMedidaController {
	
	@Autowired
	@Qualifier(value = "unidadDeMedidaDAOH")
	UnidadDeMedidaDAO unidadDeMedidaDAO;
	
	@RequestMapping(value = {"/crear/{num}"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req, @PathVariable String num) {
		try {
			File f = new File("WEB-INF/catProdServ/clavesUnidadesDeMedida"+num+".txt");

	        BufferedReader b = new BufferedReader(new InputStreamReader(
	                new FileInputStream(f), "UTF8"));

	        PrintWriter writer = resp.getWriter();
	        String readLine = "";
	        UnidadDeMedida unidadDeMedida;
	        while ((readLine = b.readLine()) != null) {
	        	String[] renglon = readLine.split("#");
	        	unidadDeMedida = new UnidadDeMedida();
	        	unidadDeMedida.setId(renglon[0]);
	        	unidadDeMedida.setNombre(renglon[1]);
	        	if (renglon.length >= 3) {
	        		unidadDeMedida.setDescripcion(renglon[2]);
	        	}
	        	
	        	unidadDeMedidaDAO.guardar(unidadDeMedida);
	        	writer.println(JsonConvertidor.toJson(unidadDeMedida));
	        }
	        b.close();
		} catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value = {"/getLista/{nombreUM}"}, method = RequestMethod.GET, produces = "application/json")
	public void getListaUM(HttpServletResponse resp, HttpServletRequest req, @PathVariable String nombreUM) {
		try {
			AsignadorDeCharset.asignar(req, resp);
			List<UnidadDeMedida> listaUnidadDeMedida= unidadDeMedidaDAO.consultarPorNombre(nombreUM);
			resp.getWriter().println(JsonConvertidor.toJson(listaUnidadDeMedida));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
