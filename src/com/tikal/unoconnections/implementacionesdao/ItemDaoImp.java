package com.tikal.unoconnections.implementacionesdao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.unoconnections.dao.ItemDao;
import com.tikal.unoconnections.model.Item;

public class ItemDaoImp implements ItemDao {

	@Override
	public boolean insertarItem(Item item) {
		ofy().save().entities(item).now();
		return false;
	}

	@Override
	public boolean eliminarItem(String id) {
		ofy().delete().entities(this.consultarItem(Long.parseLong(id))).now();
		return false;
	}

	@Override
	public boolean actualizarItem(Item item) {
		ofy().save().entities(item).now();
		return false;
	}

	@Override
	public Item consultarItem(long id) {
		return ofy().load().type(Item.class).id(id).now();
				
	}

	public List<Item> consultarTodos(){
		return ofy().load().type(Item.class).list();
	}

	@Override
	public List<Item> consultarPorEstatus(String estatus) {
		return ofy().load().type(Item.class).filter(estatus, Item.class).list();
	}


}
