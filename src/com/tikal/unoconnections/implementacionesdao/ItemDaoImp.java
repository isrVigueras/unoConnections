package com.tikal.unoconnections.implementacionesdao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
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
		List<Item> lista = ofy().load().type(Item.class).list();
		List<Item> nuevaLista = new ArrayList<Item>();
		for(int i = 0; i < lista.size(); i++){
			if(lista.get(i).getEstatus().compareTo(estatus) == 0){
				nuevaLista.add(lista.get(i));
			}
		}
		return nuevaLista;
	}


}
