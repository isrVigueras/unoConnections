package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.Serial;

public interface SerialDAO {
	public void guardar(Serial s);
	public Serial consultar(String rfc, String serie);
	public List<Serial> consultar(String rfc);
}
