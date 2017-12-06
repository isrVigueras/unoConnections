package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_de_comprobante")
@AttributeOverride(name = "id", column = @Column(name = "id_tipo_de_comprobante"))
public class TipoDeComprobante extends EntidadCatalogo {

}
