package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_Moneda", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_Moneda extends CatalogoCFDI33 {

	public C_Moneda() {
		// TODO Auto-generated constructor stub
	}

	public C_Moneda(String valor) {
		super(valor);
		// TODO Auto-generated constructor stub
	}

}
