package com.tikal.unoconnections.implementacionesdao;

import com.tikal.unoconnections.dao.ItemDao;
import com.tikal.unoconnections.model.Item;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class ItemDaoImp implements ItemDao {

	@Override
	public boolean insertarItem(Item item) {
		ofy().save().entities(item).now();
		return false;
	}

	@Override
	public boolean eliminarItem(String id) {
		ofy().delete().entities(this.consultarItem(id)).now();
		return false;
	}

	@Override
	public boolean actualizarItem(Item item) {
		ofy().save().entities(item).now();
		return false;
	}

	@Override
	public Item consultarItem(String id) {
		return ofy().load().type(Item.class).filter("idInterno",id).list().get(0);
	}




}
