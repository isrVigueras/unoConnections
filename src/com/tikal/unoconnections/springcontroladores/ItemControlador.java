package com.tikal.unoconnections.springcontroladores;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.tikal.unoconnections.dao.ItemDao;
import com.tikal.unoconnections.model.Item;
import com.tikal.unoconnections.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/items"})
public class ItemControlador {
	
	@Autowired
	ItemDao nuevoItemDao;
	
	@RequestMapping(value="/insertar", method = RequestMethod.POST, consumes="Application/json")
	public void insertarItem(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
		Item nuevo = (Item) JsonConvertidor.fromJson(json, Item.class);
		nuevoItemDao.insertarItem(nuevo);
	}
	
	@RequestMapping(value="/actualizar", method = RequestMethod.POST, consumes="Application/json")
	public void actualizarItem(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
		Item nuevo = (Item) JsonConvertidor.fromJson(json, Item.class);
		nuevoItemDao.actualizarItem(nuevo);
	}
	
	@RequestMapping(value={"/consultar/{id}"}, method = RequestMethod.GET, produces="application/json")
	public void consultarItem(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException{
		Item i= nuevoItemDao.consultarItem(Long.parseLong(id));
		response.getWriter().println(JsonConvertidor.toJson(i));
	}

	@RequestMapping(value="/consultarTodos", method = RequestMethod.GET, produces="application/json")
	public void consultarItems(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Item> lista=nuevoItemDao.consultarTodos();
		response.getWriter().println(JsonConvertidor.toJson(lista));
	}
	
	@RequestMapping(value={"/consultarPorEstatus/{estatus}"}, method = RequestMethod.GET, produces = "application/json")
	public void consultarPorEstatus(HttpServletRequest request, HttpServletResponse response, @RequestParam String estatus) throws IOException{
		List<Item> lista=nuevoItemDao.consultarPorEstatus(estatus);
		response.getWriter().println(JsonConvertidor.toJson(lista));
		
	}

}
