package com.tikal.cacao.dao;

import com.tikal.cacao.model.DomicilioCE;

public interface DomicilioCEDAO {

	public void guardar(DomicilioCE d);
	
	public DomicilioCE get(String rfc);
}
