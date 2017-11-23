package com.tikal.cacao.sat.cfd.catalogos.dyn;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_TipoDeComprobante", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
public class C_TipoDeComprobante extends CatalogoCFDI33 {

	public C_TipoDeComprobante() {
		super();
	}

	public C_TipoDeComprobante(String valor) {
		super(valor);
	}
	
	
}
