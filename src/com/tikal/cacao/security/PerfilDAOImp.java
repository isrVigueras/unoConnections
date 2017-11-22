package com.tikal.cacao.security;


import static com.googlecode.objectify.ObjectifyService.ofy;
import java.util.List;
import com.tikal.cacao.model.Perfil;

public class PerfilDAOImp implements PerfilDAO{

	@Override
	public boolean crearPerfil(Perfil perfil) {
		ofy().save().entity(perfil).now();
		return true;
	}

	@Override
	public boolean eliminarPerfil(String perfil) {
		// cargar el perfil y asignarle el esatus de mostrar o no mostrar
		return false;
	}

	@Override
	public List<Perfil> consultarPerfiles() {
		// agregar el filter para guardar en la lista los que no se deban mostrar "Eliminados"
		return ofy().load().type(Perfil.class).list();
	}

	@Override
	public Perfil consultarPerfil(String perfil) {
		List<Perfil> lista = ofy().load().type(Perfil.class).list();
		for(int i = 0; i < lista.size(); i++){
			if(lista.get(i).getTipo().compareTo(perfil)==0){
				//System.out.println("el tipo encontrado es:" + lista.get(i).getTipo());
				return lista.get(i);
			}
		}
		return null;
		//return ofy().load().type(Perfil.class).filter("tipo", perfil).first().now();
	}

	@Override
	public boolean actulizaPerfil(Perfil perfil) {
		ofy().save().entity(perfil).now();
		return true;
	}

	@Override
	public Perfil consultarPerfilPorId(long id) {
		return ofy().load().type(Perfil.class).id(id).now();
	}

}
