package com.tikal.unoconnections.dao;

import java.util.List;

import com.tikal.unoconnections.model.Item;

public interface ItemDao {
	
	public boolean insertarItem(Item item);
	public boolean eliminarItem(String id);
	public boolean actualizarItem(Item item);
	public Item consultarItem(long id);
	public List<Item> consultarTodos();
	public List<Item> consultarPorEstatus(String estatus);

}
