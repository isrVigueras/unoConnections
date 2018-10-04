package com.tikal.cacao.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.tikal.cacao.sat.cfd.catalogos.CatalogoCFDI33;

public abstract class CatalogoCFDI33Adapter<T extends CatalogoCFDI33> extends XmlAdapter<String, T> {
	
	protected Class<T> boundedType;
	
	@Override
	public T unmarshal(String v) throws Exception {
		T instanceBounded = boundedType.newInstance();
		instanceBounded.setValor(v);
		return instanceBounded;
	}

	@Override
	public String marshal(T v) throws Exception {
		String valor = null;
		if (v != null) {
			valor = v.getValor();
		}
		return valor;
	}

}
