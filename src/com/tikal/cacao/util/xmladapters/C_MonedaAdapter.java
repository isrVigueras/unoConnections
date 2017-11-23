package com.tikal.cacao.util.xmladapters;

import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.util.CatalogoCFDI33Adapter;

public class C_MonedaAdapter extends CatalogoCFDI33Adapter<C_Moneda> {

	public C_MonedaAdapter() {
		this.boundedType = C_Moneda.class;
	}
}
