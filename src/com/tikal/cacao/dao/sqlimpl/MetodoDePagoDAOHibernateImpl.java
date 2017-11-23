package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.model.orm.MetodoDePago;

public class MetodoDePagoDAOHibernateImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<MetodoDePago> {

	@Override
	public void guardar(MetodoDePago entity) {
		this.initSessionTx();
		this.sesion.persist(entity);
		this.cleanSessionTx();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetodoDePago> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "select mp.id, mp.descripcion from MetodoDePago mp";
		List<MetodoDePago> listaMetodosPago = this.sesion.createQuery(hql).list();
		this.sesion.close();
		return listaMetodosPago;
	}

	@Override
	public MetodoDePago consultar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
