/**
 * 
 */
package com.tikal.cacao.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.cacao.model.IndicadorNomina;

/**
 * @author Tikal
 *
 */
public abstract class IndicadoresNominaDAO {
	
	public static void guardar(IndicadorNomina indicador) {
		ofy().save().entity(indicador).now();
	}
	
	public static IndicadorNomina consultar(String nombre) {
		return ofy().load().type(IndicadorNomina.class).id(nombre).now();
	}
	
	public static List<IndicadorNomina> consultarTodos() {
		return ofy().load().type(IndicadorNomina.class).list();
	}

}
