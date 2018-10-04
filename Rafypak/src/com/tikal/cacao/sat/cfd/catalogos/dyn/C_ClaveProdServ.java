package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_ClaveProdServ", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_ClaveProdServ extends CatalogoCFDI33 {

	public C_ClaveProdServ() {
		super();
	}

	public C_ClaveProdServ(String valor) {
		super(valor);
	}
	
}
