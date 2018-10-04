package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "metodo_pago")
@AttributeOverride(name = "id", column = @Column(name = "id_metodo_pago"))
public class MetodoDePago extends EntidadCatalogo {

//	@Override
//	//@Id
//	//@Column(name = "id_metodo_pago")
//	public String getId() {
//		return super.getId();
//	}

}
