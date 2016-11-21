package com.tikal.unoconnections.dao;

import com.tikal.unoconnections.model.Item;

public interface ItemDao {
	
	public boolean insertarItem(Item item);
	public boolean eliminarItem(String id);
	public boolean actualizarItem(Item item);
	public Item consultarItem(String id);

}
