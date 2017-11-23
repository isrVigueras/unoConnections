package com.tikal.cacao.springController.orm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import com.tikal.cacao.dao.sql.TipoDAO;
import com.tikal.cacao.model.orm.Division;
import com.tikal.cacao.model.orm.Tipo;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/division"})
public class DivisionController {

	@Autowired
	@Qualifier(value = "divisionDAOH")
	private DivisionDAO divisionDAO;
	
	@Autowired
	@Qualifier("tipoDAOH")
	private TipoDAO tipoDAO;
	
	//@RequestMapping(value = {"/carga"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req) {
		try {

			// cambiar por cuando deploye en instancias productivas
			//File f = new File("WEB-INF/catProdServ/division.txt");
            File f = new File("WEB-INF/datosDivision.txt");

            BufferedReader b = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF8"));

            String readLine = "";

            Division division;
            Tipo tipo ;
            
            PrintWriter writer = resp.getWriter();

            while ((readLine = b.readLine()) != null) {
                String[] renglon = readLine.split("#");
                division = new Division();
                division.setClave(renglon[0]);
                division.setDescripcion(renglon[1]);
                
                String idTipo = renglon[2];
                tipo = tipoDAO.consultarPorId(idTipo);
                division.setTipo(tipo);
                divisionDAO.guardar(division);
                writer.println(JsonConvertidor.toJson(division));
            }
            b.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = {"/getLista/{idTipo}"}, method = RequestMethod.GET, produces = "application/json")
	public void getLista(HttpServletResponse resp, HttpServletRequest req, @PathVariable String idTipo) {
		try {
			AsignadorDeCharset.asignar(req, resp);
			List<Division> listaDivision = divisionDAO.consultaComboBox(idTipo);
			resp.getWriter().println(JsonConvertidor.toJson(listaDivision));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
}
