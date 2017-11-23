package com.tikal.cacao.sat.cfd.catalogos.dyn.pagos;

import javax.xml.bind.annotation.XmlType;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

@XmlType(name = "c_TipoCadenaPago", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Pagos")
public class C_TipoCadenaPago extends CatalogoCFDI33 {
	
	public C_TipoCadenaPago() {}
	
	public C_TipoCadenaPago(String valor) {
		super(valor);
	}

}
