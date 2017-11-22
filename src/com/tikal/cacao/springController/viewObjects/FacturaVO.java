package com.tikal.cacao.springController.viewObjects;

import java.util.Date;

import com.tikal.cacao.factura.Estatus;

/**
 * @author Tikal
 *
 */
public class FacturaVO {
	
	private String uuid;

	private String rfcReceptor;
	
	private Date fechaCertificacion;
	
	private String total;
	
	private String folio;
	
	private Estatus estatus;

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @return the rfcReceptor
	 */
	public String getRfcReceptor() {
		return rfcReceptor;
	}

	/**
	 * @param rfcReceptor the rfcReceptor to set
	 */
	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	/**
	 * @return the fechaCertificacion
	 */
	public Date getFechaCertificacion() {
		return fechaCertificacion;
	}

	/**
	 * @param fechaCertificacion the fechaCertificacion to set
	 */
	public void setFechaCertificacion(Date fechaCertificacion) {
		this.fechaCertificacion = fechaCertificacion;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the estatus
	 */
	public Estatus getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}
