package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class DatosEmisor {

	@Id
	private String rfc;
	
	private String pswd;
	
	public String getRfc() {
		return rfc;
	}
	
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	public String getPswd() {
		return pswd;
	}
}
