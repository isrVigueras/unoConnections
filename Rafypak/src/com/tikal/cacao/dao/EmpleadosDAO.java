/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.springController.viewObjects.EmpleadoVO;

/**
 * @author Tikal
 *
 */
public interface EmpleadosDAO {
	
	/**
	 * 
	 * @param empleado
	 */
	public Empleado crear(Empleado empleado);
	
	/**
	 * 
	 * @param empleado
	 */
	public void actualizar(Empleado empleado);
	
	/**
	 * 
	 * @param numEmpleado
	 * @return
	 */
	public Empleado consultar(long numEmpleado);
	
	/**
	 * 
	 * @param empleado
	 */
	public void eliminar(Empleado empleado);
	
	/**
	 * 
	 * @param rfc the RFC of the Empresa
	 * @return a list of employees working in the Empresa
	 *         whose <tt>rfc</tt> is the specified
	 */
	public List<EmpleadoVO> consultaPorEmpresa(String rfc);
	
	/**
	 * 
	 * @param id the id of the <code>Regimen</code> 
	 * @return a list of employees with they payment ruled by the 
	 *         Regimen with the specified <tt>id</tt>
	 */
	public List<Empleado> consultaPorRegimen(Long id);
	
	/**
	 * 
	 * @param id the id of the <code>Regimen</code> 
	 * @return a list of <code>EmpleadoVO</code> with they payment ruled by the 
	 *         Regimen with the specified <tt>id</tt>
	 */
	public List<EmpleadoVO> consultaVOPorRegimen(Long id);

}
