package com.tikal.cacao.dao;

import java.util.Date;
import java.util.List;

import com.tikal.cacao.model.Factura;

/**
 * @author Tikal
 *
 */
public interface FacturaDAO {

	/**
	 * <p>&Eacute;ste m&eacutetodo almacena una factura en un almac&eacute;n de persistencia
	 * que depender&aacute de la implementaci&oacute;n.
	 * @param f la factura timbrada a almacenar 
	 */
	void guardar(Factura f);
	
	/**
	 * <p>&Eacute;ste m&eacutetodo regresa una factura {@code Factura} timbrada 
	 * la cual se identifica por el <em>N&uacute;mero del folio fiscal (UUID)</em> </p>
	 * 
	 * @param uuid N&uacute;mero de folio fiscal de la factura
	 * @return la factura con el <em>uuid</em> especificado.
	 */
	Factura consultar(String uuid);
	
	/**
	 * <p>&Eacute;ste m&eacutetodo regresa un objeto {@code List} con las facturas {@code Factura}
	 * que se han timbrado hasta el momento y han sido emitidos por el emisor cuyo <em>RFC</em>
	 * es el especificado.</p>
	 * 
	 * <p>Si no se encuentran facturas timbradas, el objeto que se debe regresar es un objeto 
	 * {@code List} vac&iacuteo.</p>
	 * 
	 * @param rfcEmisor el RFC del emisor de las facturas
	 * @return una lista con las facturas timbradas hasta el momento
	 */
	List<Factura> consutarTodas(String rfcEmisor);
	

	/**
	 * <p>&Eacute;ste m&eacutetodo regresa un objeto {@code List} con las facturas {@code Factura}
	 * que se han timbrado hasta el momento y han sido emitidos por el emisor cuyo <em>RFC</em>
	 * es el especificado.</p>
	 * 
	 * <p>Si no se encuentran facturas timbradas, el objeto que se debe regresar es un objeto 
	 * {@code List} vac&iacuteo.</p>
	 * 
	 * @param rfcEmisor el RFC del emisor de las facturas
	 * @param page pagina de facturas
	 * @return una lista con las facturas timbradas hasta el momento
	 */
	List<Factura> consutarTodas(String rfcEmisor, int page);
	
	
	
	/**
	 * <p>&Eacute;ste m&eacutetodo elimina el objeto {@code Factura} <em>f</em> especificado del almac&eacute;n de persistencia</p>
	 * <p>La factura <em>f</em> debe tener en su atributo <em>estatus</em> el valor de {@code Estatus.GENERADO} para que pueda
	 * ser eliminado del almac&eacute;n.</p>
	 * @param f la factura sin timbrar que se va a eliminar del almac&eacute;n de persistencia
	 * @return {@code true} si se elimin&oacute; la factura, {@code false} en caso contrario
	 */
	boolean eliminar(Factura f);
	
	void eliminar(String uuid);
	
	int getPaginas(String rfc);
		
	
	
	List<Factura> buscar(Date fi, Date ff,String rfc);
}
