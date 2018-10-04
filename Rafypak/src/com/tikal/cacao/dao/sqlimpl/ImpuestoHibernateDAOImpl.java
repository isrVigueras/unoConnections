package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.Impuesto;

public class ImpuestoHibernateDAOImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<Impuesto> {

	@Override
	public void guardar(Impuesto entity) {
		this.initSessionTx();
		this.sesion.persist(entity);
		this.cleanSessionTx();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Impuesto> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "select i.id, i.descripcion from Impuesto i";
		List<Impuesto> listaImpuestos = this.sesion.createQuery(hql).list();
		this.sesion.close();
		return listaImpuestos;
	}

	@Override
	public Impuesto consultar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
