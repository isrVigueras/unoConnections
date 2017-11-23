package com.tikal.cacao.dao.sql;

import java.util.List;

import com.tikal.cacao.model.orm.ProductoOServicio;

public interface ProductoServicioDAO extends HibernateDAO<ProductoOServicio> {

	public List<ProductoOServicio> consultaPorClases(List<String> clavesClases);
}
