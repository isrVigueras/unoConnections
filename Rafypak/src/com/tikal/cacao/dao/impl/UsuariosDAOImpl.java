/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.UsuariosDAO;
import com.tikal.cacao.model.Usuario;

/**
 * @author Tikal
 *
 */
@Repository
public class UsuariosDAOImpl implements UsuariosDAO {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.UsuariosDAO#crear(com.tikal.cacao.model.Usuario)
	 */
	@Override
	public void crear(Usuario usuario) {
		ofy().save().entity(usuario);

	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.UsuariosDAO#actualizar(com.tikal.cacao.model.Usuario)
	 */
	@Override
	public void actualizar(Usuario usuario) {
		ofy().save().entity(usuario);

	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.UsuariosDAO#consultar(java.lang.String)
	 */
	@Override
	public Usuario consultar(String id) {
		return ofy().load().type(Usuario.class).id(id).now();
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.UsuariosDAO#eliminar(com.tikal.cacao.model.Usuario)
	 */
	@Override
	public void eliminar(Usuario usuario) {
		ofy().delete().entity(usuario);

	}

}
