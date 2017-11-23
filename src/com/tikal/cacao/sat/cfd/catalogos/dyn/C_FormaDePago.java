package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_FormaPago", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_FormaDePago extends CatalogoCFDI33 {

	public C_FormaDePago() {
	}

	public C_FormaDePago(String valor) {
		super(valor);
	}

}
