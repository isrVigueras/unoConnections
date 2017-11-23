package com.tikal.cacao.util.xmladapters;

import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Impuesto;
import com.tikal.cacao.util.CatalogoCFDI33Adapter;

public class C_ImpuestoAdapter extends CatalogoCFDI33Adapter<C_Impuesto> {

	public C_ImpuestoAdapter() {
		this.boundedType = C_Impuesto.class;
	}
}
