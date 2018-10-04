package com.tikal.cacao.sat.cfd.catalogos.dyn.comext;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_Colonia", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_Colonia extends CatalogoCFDI33 {

	public C_Colonia() {}

	public C_Colonia(String valor) {
		super(valor);
	}

}
