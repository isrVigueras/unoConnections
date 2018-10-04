package com.tikal.cacao.dao.sqlimpl;

import java.util.ArrayList;
import java.util.List;

import com.tikal.cacao.dao.sql.UnidadDeMedidaDAO;
import com.tikal.cacao.model.orm.UnidadDeMedida;

public class UnidadDeMedidaDAOHibernateImpl extends AbstractDAOHibernate implements UnidadDeMedidaDAO {

	@Override
	public void guardar(UnidadDeMedida unidadM) {
		this.initSessionTx();
		sesion.persist(unidadM);
		this.cleanSessionTx();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadDeMedida> consultarPorNombre(String nombre) {
		sesion = this.sessionFactory.openSession();
		List<UnidadDeMedida> listaUM = new ArrayList<>();
		String hql = "select um.id, um.nombre from UnidadDeMedida um where um.nombre like :nombreParcial";
		listaUM = sesion.createQuery(hql)
				.setParameter("nombreParcial","%" + nombre + "%")
				.list();
		sesion.close();
		return listaUM;
	}

}
