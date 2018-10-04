package com.tikal.cacao.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "regimen_fiscal")
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "id_regimen_fiscal")),
		@AttributeOverride(name = "aplicaPersonaFisica", column = @Column(name = "aplica_persona_fisica")),
		@AttributeOverride(name = "aplicaPersonaMoral", column = @Column(name = "aplica_persona_moral"))
})
public class RegimenFiscal extends EntidadCatalogo {

	private boolean aplicaPersonaFisica;
	
	private boolean aplicaPersonaMoral;

	public boolean isAplicaPersonaFisica() {
		return aplicaPersonaFisica;
	}

	public void setAplicaPersonaFisica(boolean aplicaPersonaFisica) {
		this.aplicaPersonaFisica = aplicaPersonaFisica;
	}

	public boolean isAplicaPersonaMoral() {
		return aplicaPersonaMoral;
	}

	public void setAplicaPersonaMoral(boolean aplicaPersonaMoral) {
		this.aplicaPersonaMoral = aplicaPersonaMoral;
	}
	
}
