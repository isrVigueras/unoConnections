/**
 * 
 */
package com.tikal.cacao.model;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.security.Rol;

/**
 * @author Tikal
 *
 */
@Entity
public class Usuario implements UserDetails{
	
	@Id Long id;
	@Index String usuario;
	private String pass;
	private List<Rol> authorities;
	@Index private String perfil;
	@Index private String email;


	@Override
	public List<Rol> getAuthorities() {
		return this.authorities;
	}
	
	public List<Rol> setAuthorities(List<Rol> authorities){
		return this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.pass;
	}
	public void resetPassword(){
		this.pass="";
	}

	
	public void setUsername(String username){
		this.usuario = username;
	}
	
	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(String rol){
		for(Rol r:this.getAuthorities()){
			if(r.getName().compareTo(rol)==0){
				return true;
			}
		}
		return false;
	}
	
	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
