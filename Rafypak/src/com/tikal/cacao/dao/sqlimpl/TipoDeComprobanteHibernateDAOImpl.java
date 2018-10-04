package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.model.orm.TipoDeComprobante;

public class TipoDeComprobanteHibernateDAOImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<TipoDeComprobante> {

	@Override
	public void guardar(TipoDeComprobante entity) {
		this.initSessionTx();
		sesion.persist(entity);
		this.cleanSessionTx();
	}

	@Override
	public synchronized TipoDeComprobante consultar(String id) {
		super.sesion = super.sessionFactory.openSession();
		TipoDeComprobante tipoDeComprobante = (TipoDeComprobante) super.sesion.get(TipoDeComprobante.class, id);
		if (super.sesion.isConnected()) {
			super.sesion.close();
		}
		return tipoDeComprobante;
	}

	@Override
	public List<TipoDeComprobante> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "from TipoDeComprobante tc";
		List<TipoDeComprobante> listaResultado= this.sesion.createQuery(hql).list();
		this.sesion.close();
		//List<FormaDePago> listaFormasPago = Util.convertirArrayObjectToHBEntity(listaResultado, FormaDePago.class);
		return listaResultado;
	}

}
