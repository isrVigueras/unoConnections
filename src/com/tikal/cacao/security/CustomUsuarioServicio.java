package com.tikal.cacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tikal.cacao.model.Usuario;

@Service
public class CustomUsuarioServicio implements UserDetailsService  {
	
	@Autowired
    private UsuarioDAOImp userDao;
    
    
   public Usuario loadUserByUsername(String usuario) throws UsernameNotFoundException {
       return userDao.consultarUsuario(usuario);
   }

}
