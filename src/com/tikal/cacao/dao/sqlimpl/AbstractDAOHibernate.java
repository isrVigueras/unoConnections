package com.tikal.cacao.dao.sqlimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDAOHibernate {

	protected SessionFactory sessionFactory;
	protected Session sesion;
	protected Transaction tx;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected void initSessionTx() {
		sesion = sessionFactory.openSession();
		tx = sesion.beginTransaction();
	}
	
	protected void cleanSessionTx() {
		tx.commit();
		sesion.close();
	}
}
