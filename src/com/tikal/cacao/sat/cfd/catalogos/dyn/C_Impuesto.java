package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_Impuesto", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_Impuesto extends CatalogoCFDI33 {

	public C_Impuesto() {
	}

	public C_Impuesto(String valor) {
		super(valor);
	}

}
