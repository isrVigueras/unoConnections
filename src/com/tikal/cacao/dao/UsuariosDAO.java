/**
 * 
 */
package com.tikal.cacao.dao;

import com.tikal.cacao.model.Usuario;

/**
 * @author Tikal
 *
 */
public interface UsuariosDAO {
	
	/**
	 * 
	 * @param usuario
	 */
	public void crear(Usuario usuario);
	
	/**
	 * 
	 * @param usuario
	 */
	public void actualizar(Usuario usuario);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Usuario consultar(String id);
	
	/**
	 * 
	 * @param usuario
	 */
	public void eliminar(Usuario usuario);
	

}
