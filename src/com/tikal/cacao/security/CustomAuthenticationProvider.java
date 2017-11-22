package com.tikal.cacao.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.tikal.cacao.model.Usuario;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UsuarioDAO userService;

	public CustomAuthenticationProvider(){
	}
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("****************ENTRO EN EL CUSTOMAUTHENTICATIONPROVIDER*******************");
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		Usuario user = userService.consultarUsuario(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
