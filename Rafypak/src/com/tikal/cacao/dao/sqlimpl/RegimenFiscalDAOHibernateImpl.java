package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.RegimenFiscalDAO;
import com.tikal.cacao.model.orm.RegimenFiscal;

public class RegimenFiscalDAOHibernateImpl extends AbstractDAOHibernate implements RegimenFiscalDAO {

	@Override
	public void guardar(RegimenFiscal entity) {
		super.initSessionTx();
		super.sesion.persist(entity);
		super.cleanSessionTx();

	}

	@Override
	public synchronized RegimenFiscal consultarPorId(String id) {
		super.sesion = super.sessionFactory.openSession();
		RegimenFiscal regimenFiscal = (RegimenFiscal) super.sesion.get(RegimenFiscal.class, id);
		if (super.sesion.isConnected()) {
			super.sesion.close();
		}
		return regimenFiscal;
	}

	@Override
	public List<RegimenFiscal> consultaComboBox(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegimenFiscal> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
