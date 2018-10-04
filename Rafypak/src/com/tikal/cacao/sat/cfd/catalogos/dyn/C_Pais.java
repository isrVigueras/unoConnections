package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_Pais", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_Pais extends CatalogoCFDI33 {

	public C_Pais() {
		
	}

	public C_Pais(String valor) {
		super(valor);
	}

}
