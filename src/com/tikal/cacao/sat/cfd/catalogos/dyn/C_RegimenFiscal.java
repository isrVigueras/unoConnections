package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_RegimenFiscal", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_RegimenFiscal extends CatalogoCFDI33 {

	public C_RegimenFiscal() {
		
	}

	public C_RegimenFiscal(String valor) {
		super(valor);
		
	}

}
