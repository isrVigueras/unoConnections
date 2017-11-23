package com.tikal.cacao.dao.sqlimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tikal.cacao.dao.sql.ProductoServicioDAO;
import com.tikal.cacao.model.orm.ProductoOServicio;

public class ProductoOServicioDAOHibernateImpl implements ProductoServicioDAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public void guardar(ProductoOServicio entity) {
		Session sesion = this.sessionFactory.openSession();
		Transaction tx = sesion.beginTransaction();
		sesion.persist(entity);
		tx.commit();
		sesion.close();

	}

	@Override
	public ProductoOServicio consultarPorId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoOServicio> consultaComboBox(String parameter) {
		Session sesion = this.sessionFactory.openSession();
		String hql = "select ps.clave, ps.descripcion from ProductoOServicio ps where ps.clase.clave = :claveClase";
		List<ProductoOServicio> listaPS = sesion.createQuery(hql)
				.setParameter("claveClase", parameter)
				.list();
		sesion.close();
		return listaPS;
	}

	@Override
	public List<ProductoOServicio> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoOServicio> consultaPorClases(List<String> clavesClases) {
		Session sesion = this.sessionFactory.openSession();
		List<ProductoOServicio> listaPS = new ArrayList<>();
		String hql = "select ps.clave, ps.descripcion from ProductoOServicio ps where ps.clase.clave = :claveClase";
		for (String claveClase : clavesClases) {
			List<ProductoOServicio> lista = sesion.createQuery(hql)
					.setParameter("claveClase", claveClase)
					.list();
			listaPS.addAll(lista);
		}
		sesion.close();
		return listaPS;
	}

}
