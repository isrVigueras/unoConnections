package com.tikal.cacao.util.xmladapters;

import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_Localidad;
import com.tikal.cacao.util.CatalogoCFDI33Adapter;

public class C_LocalidadAdapter extends CatalogoCFDI33Adapter<C_Localidad> {

	public C_LocalidadAdapter() {
		super.boundedType = C_Localidad.class;
	}

}
