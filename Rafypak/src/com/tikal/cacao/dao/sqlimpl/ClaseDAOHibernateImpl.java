package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.ClaseDAO;
import com.tikal.cacao.model.orm.Clase;

public class ClaseDAOHibernateImpl extends AbstractDAOHibernate implements ClaseDAO {

	@Override
	public void guardar(Clase entity) {
		this.initSessionTx();
		sesion.persist(entity);
		this.cleanSessionTx();

	}

	@Override
	public Clase consultarPorId(String id) {
		sesion = this.sessionFactory.openSession();
		Clase c = (Clase) sesion.get(Clase.class, id);
		if (sesion.isConnected()) {
			sesion.close();
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Clase> consultaComboBox(String parameter) {
		sesion = this.sessionFactory.openSession();
		String hql = "select c.clave, c.descripcion from Clase c where c.grupo.clave = :claveGrupo";
		List<Clase> listaC = sesion.createQuery(hql)
				.setParameter("claveGrupo", parameter)
				.list();
		sesion.close();
		return listaC;
	}

	@Override
	public List<Clase> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
