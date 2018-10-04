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

import com.tikal.cacao.dao.sql.DivisionDAO;
import com.tikal.cacao.dao.sql.GrupoDAO;
import com.tikal.cacao.model.orm.Division;
import com.tikal.cacao.model.orm.Grupo;
import com.tikal.cacao.model.orm.Tipo;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/grupo"})
public class GrupoController {

	@Autowired
	@Qualifier(value = "grupoDAOH")
	private GrupoDAO grupoDAO;
	
	@Autowired
	@Qualifier(value = "divisionDAOH")
	private DivisionDAO divisionDAO;
	
	//@RequestMapping(value = {"/crear"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req) {
		try {

			
			File f = new File("WEB-INF/catProdServ/grupos.txt");

            BufferedReader b = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF8"));

            String readLine = "";

            Grupo grupo;
            Division division = null;
          
            PrintWriter writer = resp.getWriter();

            while ((readLine = b.readLine()) != null) {
                String[] renglon = readLine.split("#");
                grupo = new Grupo();
                grupo.setClave(renglon[0]);
                grupo.setDescripcion(renglon[1]);
                
                String claveDivision = renglon[2];
                
                division = divisionDAO.consultarPorId(claveDivision);
                division.getTipo();
                grupo.setDivision(division);
                grupoDAO.guardar(grupo);
              
                writer.println(JsonConvertidor.toJson(grupo));
            }
            b.close();
			
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value = {"/getLista/{idDivision}"}, method = RequestMethod.GET, produces = "application/json")
	public void getLista(HttpServletResponse resp, HttpServletRequest req, @PathVariable String idDivision) {
		try {
			AsignadorDeCharset.asignar(req, resp);
			List<Grupo> listaGrupo = grupoDAO.consultaComboBox(idDivision);
			resp.getWriter().println(JsonConvertidor.toJson(listaGrupo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
