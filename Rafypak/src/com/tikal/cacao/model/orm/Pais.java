package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
@AttributeOverride(name = "id", column = @Column(name = "id_pais"))
public class Pais extends EntidadCatalogo {

//	@Override
//	//@Id
//	//@Column(name = "id_pais")
//	public String getId() {
//		return super.getId();
//	}

}
