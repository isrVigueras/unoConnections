package com.tikal.cacao.springController.requestObject;

import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.orm.ProductoOServicio;

public class EmpresaConProductoOServicioRO {

	private Empresa empresa;
	
	private Object[][] productoServicio;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Object[][] getProductoOServicio() {
		return productoServicio;
	}

	public void setProductoOServicio(Object[][] productoOServicio) {
		this.productoServicio = productoOServicio;
	}
	
	
}
