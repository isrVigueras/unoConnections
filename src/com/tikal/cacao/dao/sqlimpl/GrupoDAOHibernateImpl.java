package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tikal.cacao.dao.sql.GrupoDAO;
import com.tikal.cacao.model.orm.Grupo;

public class GrupoDAOHibernateImpl implements GrupoDAO {
	
	private SessionFactory sessionFactory;
	private Session sesion;
	private Transaction tx;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public void guardar(Grupo entity) {
		this.initSessionTx();
		sesion.persist(entity);
		this.cleanSessionTx();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> consultarTodos() {
		Session session = this.sessionFactory.openSession();
		List<Grupo> grupos = session.createQuery("from Grupo").list();
		session.close();
		return grupos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> consultaComboBox(String parameter) {
		Session session = this.sessionFactory.openSession();
		String hql = "select g.clave, g.descripcion from Grupo g where g.division.clave = :claveDiv";
		List<Grupo> listaD = session.createQuery(hql)
				.setParameter("claveDiv", parameter)
				.list();
		session.close();
		return listaD;
	}

	@Override
	public Grupo consultarPorId(String id) {
		Session session = this.sessionFactory.openSession();
		Grupo t = (Grupo) session.get(Grupo.class, id);
		session.close();
		return t;
	}
	
	private void initSessionTx() {
		sesion = sessionFactory.openSession();
		tx = sesion.beginTransaction();
	}
	
	private void cleanSessionTx() {
		tx.commit();
		sesion.close();
	}

}
