/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
public interface RegimenesDAO {
	
	/**
	 * 
	 * @param regimen
	 */
	public void crear(Regimen regimen);
	
	/**
	 * 
	 * @param regimen
	 */
	public void actualizar(Regimen regimen);
	
	/**
	 * 
	 * @param id
	 */
	public Regimen consultar(long id);
	
	/**
	 * 
	 * @param nombre
	 */
	public Regimen consultar(String nombre);
	
	/**
	 * 
	 * @param regimen
	 */
	public void eliminar(Regimen regimen);
	
	/**
	 * 
	 * @param empresa
	 * @return
	 */
	public List<Regimen> consultaPorEmpresa(Empresa empresa);

}
