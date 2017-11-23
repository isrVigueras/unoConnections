package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.Moneda;

public class MonedaHibernateDAOImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<Moneda> {

	@Override
	public void guardar(Moneda entity) {
		this.initSessionTx();
		this.sesion.persist(entity);
		this.cleanSessionTx();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Moneda> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "select m from Moneda m"; //Esta forma de Hibernate Query Language regresa un objeto List
		List<Moneda> listaMonedas = this.sesion.createQuery(hql).list();
		this.sesion.close();
		return listaMonedas;
	}

	@Override
	public Moneda consultar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
