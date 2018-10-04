package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.Pais;

public class PaisDAOHibernateImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<Pais> {

	@Override
	public void guardar(Pais entity) {
		this.initSessionTx();
		this.sesion.persist(entity);
		this.cleanSessionTx();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pais> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "select p.id, p.descripcion from Pais p";
		List<Pais> listaPaises = this.sesion.createQuery(hql).list();
		this.sesion.close();
		return listaPaises;
	}

	@Override
	public Pais consultar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
