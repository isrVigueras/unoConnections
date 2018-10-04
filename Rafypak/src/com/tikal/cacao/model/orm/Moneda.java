package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moneda")
@AttributeOverride(name = "id", column = @Column(name = "id_moneda"))
public class Moneda extends EntidadCatalogo {
	
	private byte decimales;
	
//	@Override
//	//@Id
//	//@Column(name = "id_moneda")
//	public String getId() {
//		return super.getId();
//	}

	public byte getDecimales() {
		return decimales;
	}

	public void setDecimales(byte decimales) {
		this.decimales = decimales;
	}
	
	

}
