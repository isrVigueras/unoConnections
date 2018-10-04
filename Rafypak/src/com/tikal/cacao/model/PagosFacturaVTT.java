package com.tikal.cacao.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index=true)
public class PagosFacturaVTT extends FacturaVTT {

	public PagosFacturaVTT() {}

	public PagosFacturaVTT(String uuid, String cfdi, String rfcEmisor, String nombreReceptor, Date fecha, String sello,
			byte[] codigoQR) {
		super(uuid, cfdi, rfcEmisor, nombreReceptor, fecha, sello, codigoQR);
		
	}
	
	public PagosFacturaVTT(String uuid, String cfdi, String rfcEmisor, String nombreReceptor, Date fecha, String sello,
			byte[] codigoQR, String uuidRelacionado, String fechaPago, String formaDePago, String moneda, String monto) {
		super(uuid, cfdi, rfcEmisor, nombreReceptor, fecha, sello, codigoQR);
		this.setUuidCFDIRelacionado(uuidRelacionado);
		this.setFechaPago(fechaPago);
		this.setFormaDePago(formaDePago);
		this.setMoneda(moneda);
		this.setMonto(monto);
		
	}
	
	@Index
	private String uuidCFDIRelacionado;
	
	private String fechaPago;
	
	private String formaDePago;
	
	private String moneda;
	
	private String monto;

	public String getUuidCFDIRelacionado() {
		return uuidCFDIRelacionado;
	}

	public void setUuidCFDIRelacionado(String uuidCFDIRelacionado) {
		this.uuidCFDIRelacionado = uuidCFDIRelacionado;
	}

	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}
	
}
