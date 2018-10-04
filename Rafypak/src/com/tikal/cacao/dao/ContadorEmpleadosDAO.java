/**
 * 
 */
package com.tikal.cacao.dao;

import com.tikal.cacao.model.ContadorEmpleados;

/**
 * @author Tikal
 *
 */
public interface ContadorEmpleadosDAO {
	
	ContadorEmpleados consultar();
	
	void incrementar(ContadorEmpleados contador);
}
