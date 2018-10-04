package com.tikal.cacao.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tikal
 *
 */
public class Periodo {
	private Date fechaInicial;
	
	private Date fechaFinal;
	
    public Periodo(){}
	
	public Periodo(Date fechaIn, Date fechaFin) {
		this.fechaInicial = fechaIn;
		this.fechaFinal = fechaFin;
	}
	
	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strFormat = "Del " + formatter.format(this.fechaInicial) + " al " +
				formatter.format(this.fechaFinal);
		return strFormat;
	}
	
	public void setFechaFinal(Date fecha) {
		this.fechaFinal = fecha;
	}
	
	public void setFechaInicial(Date fecha) {
		this.fechaInicial = fecha;
	}
	
	public Date getFechaFinal() {
		return fechaFinal;
	}
	
	public Date getFechaInicial() {
		return fechaInicial;
	}
}
