package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.PagosFacturaVTT;

public interface PagosFacturaVttDAO {

	List<PagosFacturaVTT> consultarPorUuidRelacionado(String uuidRelacionado);
	
	PagosFacturaVTT consultarUUID(String uuid);
	
	List<PagosFacturaVTT> cosultarPorCliente(String rfcCliente);
}
