package com.tikal.cacao.util;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.springController.requestObject.FacturaRO;
import com.tikal.cacao.springController.viewObjects.ComprobanteConComentarioVO;
import com.tikal.cacao.springController.viewObjects.ComprobanteVO;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;
import com.tikal.cacao.springController.viewObjects.RegimenVO;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;

public class JsonConvertidor {
	public static Object fromJson(String json, Class<?> clase){
		Gson g;
		if (clase.equals(RegimenVO.class)) {
			g = getGsonWithSpecificTypeAdapter(clase, new RegimenAdapter());
		} else if (clase.equals(ListaPagosVO.class)) {
			g = getGsonWithSpecificTypeAdapter(clase, new ListaPagosVOAdapter());
		} else if (clase.equals(Comprobante.class) || clase.equals(com.tikal.cacao.sat.cfd.Comprobante.class)
				|| clase.equals(FacturaRO.class) || clase.equals(ComprobanteVO.class)
				|| clase.equals(ComprobanteConComentarioVO.class)
				|| clase.equals(com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO.class)
				|| clase.equals(com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComentarioVO.class)
				|| clase.equals(ComprobanteConComplementoPagosVO.class)) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarConverter.Deserializer());
			g = gsonBuilder.create();
		} else {
			g= new Gson();
		}
		
		return g.fromJson(json,clase);
	}
	
	public static String toJson(Object o){
		Gson g= new Gson();
		return g.toJson(o);
	}
	
	public static String estadoFromJson(String json) {
		int indexIni = json.indexOf("estado")+9;
		int indexFin = json.indexOf("localidad")-3;
		String estado = json.substring(indexIni, indexFin);
		return estado;
		 
	}
	
	static Gson getGsonWithSpecificTypeAdapter(Class<?> clase, TypeAdapter<?> adapter) {
		GsonBuilder gBuilder = new GsonBuilder();
		gBuilder.registerTypeAdapter(clase, adapter.nullSafe());
		return gBuilder.create();
	}
	
	public static String toJsonComprobantes(Object o){
		Gson g;
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarConverter.Serializer());
			g = gsonBuilder.create();
			return g.toJson(o);
			
	}
	
}
