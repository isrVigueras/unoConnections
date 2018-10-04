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

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.sql.ClaseDAO;
import com.tikal.cacao.dao.sql.ProductoServicioDAO;
import com.tikal.cacao.model.orm.Clase;
import com.tikal.cacao.model.orm.Grupo;
import com.tikal.cacao.model.orm.ProductoOServicio;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/producto_servicio"})
public class ProductoOServicioController {
	
	@Autowired
	@Qualifier(value = "claseDAOH")
	private ClaseDAO claseDAO;
	
	@Autowired
	@Qualifier(value = "productoServicioDAOH")
	private ProductoServicioDAO productoServicioDAO;
	

	//@RequestMapping(value = {"/crear/{numFile}"}, method = RequestMethod.GET, produces = "application/json")
	public void crear(HttpServletResponse resp, HttpServletRequest req, @PathVariable String numFile) {
		try {
			File f = null;
			if (numFile.equalsIgnoreCase("faltantes")) {
				f = new File("WEB-INF/catProdServ/faltantes.txt");
			} else {
				f = new File("WEB-INF/catProdServ/ps/pd" + numFile + ".txt");
			}

            BufferedReader b = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF8"));

            String readLine = "";

            ProductoOServicio productoOServicio;
            Clase clase= null;
          
            PrintWriter writer = resp.getWriter();
            //int i = 48791;
            while ((readLine = b.readLine()) != null) {
                if (readLine.length() > 0) {
                	String[] renglon = readLine.split("#");
                    productoOServicio = new ProductoOServicio();
                    productoOServicio.setClave(renglon[0]);
                    productoOServicio.setDescripcion(renglon[1]);
                    
                    String claveClase = renglon[2];
                    
                    clase = claseDAO.consultarPorId(claveClase);
                    productoOServicio.setClase(clase);
                    productoServicioDAO.guardar(productoOServicio);
                  
                    writer.println(JsonConvertidor.toJson(productoOServicio));
                }
            	
            }
            b.close();
		
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = {"/getLista/{idClase}"}, method = RequestMethod.GET, produces = "application/json")
	public void getLista(HttpServletResponse resp, HttpServletRequest req, @PathVariable String idClase) {
		try {
			AsignadorDeCharset.asignar(req, resp);
			List<ProductoOServicio> listaProductoServicio = productoServicioDAO.consultaComboBox(idClase);
			resp.getWriter().println(JsonConvertidor.toJson(listaProductoServicio));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
