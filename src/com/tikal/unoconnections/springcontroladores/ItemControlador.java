package com.tikal.unoconnections.springcontroladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.unoconnections.dao.ItemDao;
import com.tikal.unoconnections.model.Item;
import com.tikal.unoconnections.util.JsonConvertidor;

@Controller
@RequestMapping(value = {"/items"})
public class ItemControlador {
	
	@Autowired
	ItemDao nuevoItemDao;
	
	@RequestMapping(value="/insertar", method= RequestMethod.POST, consumes="Application/json")
	public void insertarItem(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
		Item nuevo = (Item) JsonConvertidor.fromJson(json, Item.class);
		nuevoItemDao.insertarItem(nuevo);
	}

}
