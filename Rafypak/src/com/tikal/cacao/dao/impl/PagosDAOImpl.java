package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.PagosDAO;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.util.Util;

/**
 * 
 * @author Tikal
 */
@Repository
public class PagosDAOImpl implements PagosDAO {

	@Override
	public void crear(Pago pago) {
		ofy().save().entity(pago).now();

	}

	@Override
	public void crear(List<Pago> pagos) {
		ofy().save().entities(pagos).now();

	}

	@Override
	public Pago consultar(Long id) {
		return ofy().load().type(Pago.class).id(id).now();
	}

	@Override
	public double consultarSBC(long idEmpleado, Date fecha) {
		List<Pago> pagos = ofy().load().type(Pago.class).filter("idEmpleado", idEmpleado).
				filter("fechaDePago >=", fecha).list();
		if (pagos.size() == 0) {
			return 0.0; // la lista esta vacía, por lo tanto no hay un SBC registrado
		}
		else if (pagos.size() == 1) {
			return pagos.get(0).getSalarioDiarioIntegrado();
		} else {
			Collections.sort(pagos, new Comparator<Pago>() {
				@Override
				public int compare(Pago o1, Pago o2) {
					// ordenar la lista de pagos en forma descendente con respecto a la fecha del pago
					return o2.getFechaDePago().compareTo(o1.getFechaDePago());
				}
			});
			return pagos.get(0).getSalarioDiarioIntegrado(); // cambiar o probar detenidamente el método 'sort'
		}
	}

	@Override
	public List<Pago> consultarPagosPorRegimen(long idRegimen, Date fecha) {
		return consultarPagos(idRegimen, fecha);
	}
	
	@Override
	public List<Pago> consultarPagosDesde(long idRegimen, int meses) {
		Date fechaMesesAntes = Util.obtenerFechaMesesAntes(meses);
		return consultarPagos(idRegimen, fechaMesesAntes);	}
	
	private List<Pago> consultarPagos(long idRegimen, Date fecha) {
		List<Pago> lista = ofy().load().type(Pago.class).filter("idRegimen", idRegimen).filter("fechaDePagoEsquema >", fecha).list();
		if(lista.size()==0)
			return null;
		return lista;
	}

}
