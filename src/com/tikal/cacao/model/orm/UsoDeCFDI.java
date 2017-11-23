package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "uso_cfdi")
@AttributeOverride(name = "id", column = @Column(name = "id_uso_cfdi"))
public class UsoDeCFDI extends EntidadCatalogo {

}
