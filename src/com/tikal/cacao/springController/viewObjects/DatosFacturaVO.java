package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.sat.cfd.TUbicacionFiscal;

/**
 * @author Tikal
 *
 */
public class DatosFacturaVO {
	
	private String lugarDeExpedicion;
	
	private String nombreEmisor;
	
	private Emisor emisor;
	
	private TUbicacionFiscal domicilioFiscal;
	
	private String[] emails;

	public DatosFacturaVO(String lugarDeExpedicion, String nombreEmisor, Emisor emisor, String[] emails) {
		this.lugarDeExpedicion = lugarDeExpedicion;
		this.nombreEmisor = nombreEmisor;
		this.emisor = emisor;
		this.emails = emails;
	}
	
	/**
	 * @return the lugarDeExpedicion
	 */
	public String getLugarDeExpedicion() {
		return lugarDeExpedicion;
	}
	
	/**
	 * @return the nombreEmisor
	 */
	public String getNombreEmisor() {
		return nombreEmisor;
	}

	/**
	 * @param nombreEmisor the nombreEmisor to set
	 */
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}

	/**
	 * @param lugarDeExpedicion the lugarDeExpedicion to set
	 */
	public void setLugarDeExpedicion(String lugarDeExpedicion) {
		this.lugarDeExpedicion = lugarDeExpedicion;
	}

	/**
	 * @return the emisor
	 */
	public Emisor getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
	
	/**
	 * @return the emails
	 */
	public String[] getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public void construirDomicilio(Direccion d, String pais) {
		this.domicilioFiscal = new TUbicacionFiscal();
		this.domicilioFiscal.setCalle(d.getCalle());
		this.domicilioFiscal.setCodigoPostal(d.getCodigoPostal());
		this.domicilioFiscal.setColonia(d.getColonia());
		this.domicilioFiscal.setEstado(d.getEstado());
		this.domicilioFiscal.setLocalidad(d.getLocalidad());
		this.domicilioFiscal.setMunicipio(d.getMunicipio());
		this.domicilioFiscal.setNoExterior(d.getNumExterior());
		this.domicilioFiscal.setNoInterior(d.getNumInterior());
		this.domicilioFiscal.setPais(pais);
	}
	
	
}
