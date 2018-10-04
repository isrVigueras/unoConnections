package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tikal.cacao.dao.sql.DivisionDAO;
import com.tikal.cacao.model.orm.Division;

public class DivisionDAOHibernateImpl implements DivisionDAO {
	
	private SessionFactory sessionFactory;
	private Session sesion;
	private Transaction tx;

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	public void guardar(Division d) {
		this.initSessionTx();
		sesion.persist(d);
		this.cleanSessionTx();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Division> consultarTodos() {
		Session session = this.sessionFactory.openSession();
		List<Division> divisiones = session.createQuery("from Division").list();
		session.close();
		return divisiones;
	}

	
	private void initSessionTx() {
		sesion = sessionFactory.openSession();
		tx = sesion.beginTransaction();
	}
	
	private void cleanSessionTx() {
		tx.commit();
		sesion.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Division> consultaComboBox(String parameter) {
		Session session = this.sessionFactory.openSession();
		String hql = "select d.clave, d.descripcion from Division d where d.tipo.id = :idtipo";
		List<Division> listaD = session.createQuery(hql)
				.setParameter("idtipo", parameter)
				.list();
		session.close();
		return listaD;
		/*Type tipoString = new StringType();
		SQLQuery query = session.createSQLQuery(
				"SELECT d.id_division, d.descripcion FROM division d JOIN tipo t ON d.fk_tipo = t.id_t AND d.fk_tipo = :fktipo")
				.addScalar("d.id_division", tipoString)
				.addScalar("d.descripcion", tipoString);
		
		List<Division> listaD = query.setString("fktipo", parameter).list();
		for (Division division : listaD) {
			
		}
		
		return null;
		*/
		
		
	}

	@Override
	public Division consultarPorId(String id) {
		Session session = this.sessionFactory.openSession();
		Division t = (Division)session.get(Division.class, id);
		session.close();
		return t;
	}
}
