package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.springController.viewObjects.FechasVO;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/bitacora"})
public class BitacoraController {

	@Autowired
	BitacoraDAO dao;
	
	@RequestMapping(value={"get/{tipo}"}, method= RequestMethod.GET, produces="application/json")
	public void get(HttpServletRequest req, HttpServletResponse res, @PathVariable String tipo) throws IOException{
		List<RegistroBitacora> lista= dao.getBitacora(tipo); 
		res.getWriter().print(JsonConvertidor.toJson(lista));
	}
	
	@RequestMapping(value={"getFechas/{tipo}"}, method= RequestMethod.POST, produces="application/json")
	public void getFecha(HttpServletRequest req, HttpServletResponse res,@PathVariable String tipo, @RequestBody String  json) throws IOException{
		FechasVO f= (FechasVO) JsonConvertidor.fromJson(json, FechasVO.class);
		List<RegistroBitacora> lista= dao.getBitacora(tipo);
		
		res.getWriter().print(JsonConvertidor.toJson(lista));
	}
	
	@RequestMapping(value={"getUsuario/{tipo}/{usuario}"}, method= RequestMethod.GET, produces="application/json")
	public void getusuario(HttpServletRequest req, HttpServletResponse res,@PathVariable String tipo, @PathVariable String  usuario) throws IOException{
		
		List<RegistroBitacora> lista= dao.getBitacora(tipo, usuario);
		res.getWriter().print(JsonConvertidor.toJson(lista));
	}
}
