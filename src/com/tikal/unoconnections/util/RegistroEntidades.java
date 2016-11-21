package com.tikal.unoconnections.util;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.unoconnections.model.Item;

public class RegistroEntidades {
	
	public RegistroEntidades(){
		ObjectifyService.register(Item.class);
	}

}
