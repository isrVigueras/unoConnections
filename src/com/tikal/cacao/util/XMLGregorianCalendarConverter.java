package com.tikal.cacao.util;

import java.lang.reflect.Type;
import java.util.Date;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tikal.cacao.factura.FormatoFecha;

/**
 * @author Tikal
 *
 */
public class XMLGregorianCalendarConverter {

	public static class Serializer implements JsonSerializer {

        public Serializer() {
            super();
        }
        @Override
        public JsonElement serialize(Object t, Type type,
                JsonSerializationContext jsonSerializationContext) {
            XMLGregorianCalendar xgcal = (XMLGregorianCalendar) t;
            return new JsonPrimitive(xgcal.toXMLFormat());
        }

    }
    public static class Deserializer implements JsonDeserializer {

        @Override
        public Object deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) {
            try {
            	if (jsonElement.isJsonObject()) {
	                JsonObject obj  = jsonElement.getAsJsonObject();
	                XMLGregorianCalendar xmlGregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(obj.get("year").getAsInt(), 
	                                                                     obj.get("month").getAsInt(), 
	                                                                     obj.get("day").getAsInt(), 
	                                                                     obj.get("hour").getAsInt(), 
	                                                                     obj.get("minute").getAsInt(),obj.get("second").getAsInt(),
	                                                                     0, obj.get("timezone").getAsInt());
	                return xmlGregCalendar;
            	} else {
            		JsonPrimitive obj = jsonElement.getAsJsonPrimitive();
            		if (obj.isString()) {
            			String strFecha = obj.getAsString();
            			Date fecha = Util.obtenerFecha(strFecha);
            			XMLGregorianCalendar xmlGregCalendar = Util.getXMLDate(fecha, FormatoFecha.COMPROBANTE);
            			return xmlGregCalendar;
            		} else 
            			return null;
            	}
            } catch (Exception e) {
                return null;
            }
        }

    }
}
