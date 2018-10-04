package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.UsoDeCFDIDAO;
import com.tikal.cacao.model.orm.UsoDeCFDI;

public class UsoDeCFDIDAOHibernateImpl extends AbstractDAOHibernate implements UsoDeCFDIDAO {

	@Override
	public void guardar(UsoDeCFDI entity) {
		super.initSessionTx();
		super.sesion.persist(entity);
		super.cleanSessionTx();
	}

	@Override
	public synchronized UsoDeCFDI consultarPorId(String id) {
		super.sesion = super.sessionFactory.openSession();
		UsoDeCFDI usoDeCFDI = (UsoDeCFDI) super.sesion.get(UsoDeCFDI.class, id);
		if (super.sesion.isConnected()) {
			super.sesion.close();
		}
		return usoDeCFDI;
	}

	@Override
	public List<UsoDeCFDI> consultaComboBox(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsoDeCFDI> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
