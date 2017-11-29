package com.tikal.cacao.util.xmladapters;

import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_Municipio;
import com.tikal.cacao.util.CatalogoCFDI33Adapter;

public class C_MunicipioAdapter extends CatalogoCFDI33Adapter<C_Municipio> {

	public C_MunicipioAdapter() {
		super.boundedType = C_Municipio.class;
	}
}
