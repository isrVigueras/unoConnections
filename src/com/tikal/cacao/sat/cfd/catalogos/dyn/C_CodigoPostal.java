package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_CodigoPostal", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_CodigoPostal extends CatalogoCFDI33 {

	public C_CodigoPostal() {
		super();
	}

	public C_CodigoPostal(String valor) {
		super(valor);
	}
	
	

}
