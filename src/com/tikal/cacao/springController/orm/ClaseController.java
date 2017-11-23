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

import com.tikal.cacao.dao.sql.ClaseDAO;
import com.tikal.cacao.dao.sql.GrupoDAO;
import com.tikal.cacao.model.orm.Clase;
import com.tikal.cacao.model.orm.Grupo;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping("/clase")
public class ClaseController {
	
	@Autowired
	@Qualifier(value = "grupoDAOH")
	private GrupoDAO grupoDAO;
	
	@Autowired
	@Qualifier(value = "claseDAOH")
	private ClaseDAO claseDAO;
	
	//@RequestMapping(value = {"/crear"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req) {
		try {

			File f = new File("WEB-INF/catProdServ/clases3.txt");

            BufferedReader b = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF8"));

            String readLine = "";

            Clase clase;
            Grupo grupo= null;
          
            PrintWriter writer = resp.getWriter();

            while ((readLine = b.readLine()) != null) {
                String[] renglon = readLine.split("#");
                clase = new Clase();
                clase.setClave(renglon[0]);
                clase.setDescripcion(renglon[1]);
                
                String claseGrupo = renglon[2];
                
                grupo = grupoDAO.consultarPorId(claseGrupo);
                clase.setGrupo(grupo);
                claseDAO.guardar(clase);
              
                writer.println(JsonConvertidor.toJson(clase));
            }
            b.close();
			
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value = {"/getLista/{idGrupo}"}, method = RequestMethod.GET, produces = "application/json")
	public void getLista(HttpServletResponse resp, HttpServletRequest req, @PathVariable String idGrupo) {
		try {
			AsignadorDeCharset.asignar(req, resp);
			List<Clase> listaClase = claseDAO.consultaComboBox(idGrupo);
			resp.getWriter().println(JsonConvertidor.toJson(listaClase));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
