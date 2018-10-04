package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_TipoFactor", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_TipoFactor extends CatalogoCFDI33 {

	public C_TipoFactor() {
	}

	public C_TipoFactor(String valor) {
		super(valor);
	}

}
