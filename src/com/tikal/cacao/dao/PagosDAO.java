/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.Date;
import java.util.List;

import com.tikal.cacao.model.Pago;

/**
 * @author Tikal
 *
 */
public interface PagosDAO {
	
	/**
	 * Guarda un objeto {@code Pago} 
	 * @param pago el pago a guardar
	 */
	public void crear(Pago pago);
	
	/**
	 * Guarda todos los elementos {@code Pago} que estan en la lista
	 * @param pagos una lista de {@code Pago} a guardar
	 */
	public void crear(List<Pago> pagos);
	
	/**
	 * Regresa el {@code Pago} cuyo identificador es el especificado
	 * @param id el identificador del {@code Pago}
	 * @return el {@code Pago} cuyo identificador es el especificado
	 */
	public Pago consultar(Long id);
	
	/**
	 * &Eacute;ste m&eacute;todo regresa el <strong>salario base de cotizaci&oacute;n (SBC)</strong>
	 * con el que se registro el &uacute;ltimo {@link com.tikal.cacao.model.Pago} del empleado con el 
	 * identificador especificado
	 * @param idEmpleado identificador del empleado de quien se requiere conocer su <strong>SBC</strong>
	 * @param fecha la fecha a partir de la cual se consultar&aacute; el &uacute;ltimo pago
	 * @return el &uacute;ltimo <strong>SBC</strong> registrado en el pago del empleado cuyo identificador
	 * es el especificado
	 */
	public double consultarSBC(long idEmpleado, Date fecha);
	
	public List<Pago> consultarPagosPorRegimen(long idRegimen, Date fecha);
		
	public List<Pago> consultarPagosDesde(long idRegimen, int meses);
	
}
