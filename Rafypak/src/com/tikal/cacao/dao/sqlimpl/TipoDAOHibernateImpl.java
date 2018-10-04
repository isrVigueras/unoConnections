package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tikal.cacao.dao.sql.TipoDAO;
import com.tikal.cacao.model.orm.Tipo;

public class TipoDAOHibernateImpl implements TipoDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public void guardar(Tipo t) {
		Session sesion = this.sessionFactory.openSession();
		Transaction tx = sesion.beginTransaction();
		sesion.persist(t);
		tx.commit();
		sesion.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo> consultarTodos() {
		Session session = this.sessionFactory.openSession();
		List<Tipo> tipos = session.createQuery("from Tipo").list();
		session.close();
		return tipos;
	}

	@Override
	public Tipo consultarPorId(String id) {
		Session session = this.sessionFactory.openSession();
		Tipo t = (Tipo)session.get(Tipo.class, id);
		session.close();
		return t;
	}

}
