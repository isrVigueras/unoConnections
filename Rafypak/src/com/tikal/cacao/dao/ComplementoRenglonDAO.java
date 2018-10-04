package com.tikal.cacao.dao;

import java.util.Date;
import java.util.List;

import com.tikal.cacao.reporte.ComplementoRenglon;

public interface ComplementoRenglonDAO {
	void guardar(ComplementoRenglon r);

	void guardar(List<ComplementoRenglon> lista);

	ComplementoRenglon consultar(String uuid);

	List<ComplementoRenglon> consultar(String rfcEmisor, String serie, Date fechaI, Date fechaF);

	List<ComplementoRenglon> consultar(String rfcEmisor, String rfcReceptor, String serie, Date fechaI, Date fechaF);

	List<ComplementoRenglon> consultarPag(String rfcEmisor, int page);

	List<ComplementoRenglon> consultarPag(String rfcEmisor, String serie, int page);

	List<ComplementoRenglon> consultarPagRec(String rfcEmisor, String receptor, int page);

	List<ComplementoRenglon> consultarids(String[] ids);

	int pags(String rfcEmisor);

	int pags(String rfc, String serie);

	int pagsRec(String rfc, String receptor);

	/**
	 * <h2>Atenci&oacute;n</h2>
	 * <p>
	 * El prop&oacute;sito de este m&eacute;todo es borrar los <em>entities<em>
	 * {@code ComplementoRenglon} cuyo atributo estatus tiene el valor de
	 * <strong>GENERADO</strong> y se creo otro <em>entity</em>
	 * {@code ComplementoRenglon} con un estatus de <strong>TIMBRADO</strong>
	 * </p>
	 * 
	 * @param uuid
	 */
	void eliminar(String uuid);

	void eliminar(List<ComplementoRenglon> lista);
}
