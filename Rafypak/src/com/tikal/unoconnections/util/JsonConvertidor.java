package com.tikal.unoconnections.util;

import com.google.appengine.repackaged.com.google.gson.Gson;

public class JsonConvertidor {
	public static Object fromJson(String json, Class clase){
		Gson g= new Gson();
		
		return g.fromJson(json,clase);
	}
	
	public static String toJson(Object o){
		Gson g= new Gson();
		return g.toJson(o);
	}
}
