/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.TarifasDAO;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;

/**
 * @author Tikal
 *
 */
@Repository
public class TarifasDAOImpl implements TarifasDAO {
	
	@Override
	public <T extends TarifaSubsidio> T queryT(Double monto, Class<T> t) {
		List<? extends TarifaSubsidio> list = 
				ofy().load().type(t).filter("limiteInferior2 <", monto).list();
		for (TarifaSubsidio tarifa : list) {
			if (tarifa.getLimiteSuperior()> monto) {
				return (T) tarifa;
			}
		}
		return (T) list.get(0);
	}

	@Override
	public void actualizar(TarifaSubsidio tarifa) {
		ofy().save().entities(tarifa).now();
		
	}

	


}
