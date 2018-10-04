package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.util.JsonConvertidor;

/**
 * @author Tikal
 *
 */
@Controller
@RequestMapping(value = "/imagen")
public class ImagenController {

	@Autowired
	ImagenDAO imagenDAO;
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST, consumes = "application/json")
	public void guardar(HttpServletResponse resp, HttpServletRequest req, @RequestBody String json) {
		Imagen imagen = (Imagen) JsonConvertidor.fromJson(json, Imagen.class);
		imagenDAO.addImagen(imagen.getRfc(), imagen.getImage());
		try {
			resp.getWriter().println("La imagen se guardo con exito");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/obtenerNombre/{rfc}", method = RequestMethod.GET, produces = "application/json")
	public void obtener(HttpServletResponse resp, HttpServletRequest req, @PathVariable String rfc) {
		Imagen imagen = imagenDAO.get(rfc);
		String urlImg = imagen.getImage();
		String nombreImg = null;
		String regex= "[\\d]+";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(urlImg);
        if (m.find()) {
        	nombreImg = m.group();
        }
		try {
			resp.getWriter().print(nombreImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
