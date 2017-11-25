package com.tikal.unoconnections.tralix;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.model.Direccion;

@Entity
public class Datos {
	
	@Id
	private Long id;
	
	@Index
	private String rfcEmisor;
	//#01
	private String idCFD;
	private String serie;
	private String folio;
	private String fecha_hora;
	private float subtotal;
	private float total;
	private float impTrasladados; //total
	private float impRetenidos; //totalImpuestos
	private String totalLetra;
	private String moneda;
	private float tipoCambio;
	private String referencia;
	private String repVentas; //representante_ventas
	private String viaEmbarque;
	private String nPedido; //nuestro_pedido
	private String sPedido; //su_pedido
	
	//#01A
	private String noCtaPago;
	
	//#02
	private String condPago;
	private String metodoPago;
	private String formaPago;
	
	//#03 //#04
	//private String idUnicoRec; //IdentificadorÚnicoReceptor
	private String RFC;
	Direccion direccion;
	
	//#06
	private String impuesto;
	private float tasa;
	private float imp; //importe
	
	//#09
	//private String idIntReceptor; //IdentificadorInternoReceptor
	private String email;
	private String asunto = "null";
	private String mensaje = "null";
	private String adjunto;
	
	//#10
	private float version = 0;
	private int tipoOpe;
	private String clavePedimento;
	private String certOrigen = "null"; //certificadoOrigen
	private String numCertOrigen = "";
	private String numExportConfiable = "null"; //NumeroDeExportadorConfiable
	private String incoterm = "null";
	private String subdiv = "null"; 
	private String observaciones = "null";
	private String tipoCambioUSD = "null";
	private String totalUSD;
	
	//#12
	private String CURP = "null";
	private String numRegIdTrib;
	
	//#14
	private String pais;
	
	//#99
	private String numLineas;
	
	public Datos (String info){
		String[] values= info.split("\\|");
		
		this.idCFD = values[0];
		this.serie = values[1];
		this.folio = values[2];
		this.fecha_hora = values[3];
		this.subtotal = values[4];
		this.total = values[5];
		this.impTrasladados = values[6];
		this.impRetenidos = values[7];
		this.totalLetra = values[8];
		this.moneda = values[9];
		this.tipoCambio = values[10];
		this.referencia = values[11];
		this.repVentas = values[12];
		this.viaEmbarque = values[13];
		this.nPedido = values[14];
		this.sPedido =values[15];
		this.noCtaPago = values[16];
		this.condPago = values[17];
		this.metodoPago = values[18];
		this.formaPago = values[19];
		this.RFC = values[20];
		//this.direccion = values[21];
		this.cantidad = values[22];
		this.descripcion = descripcion;
		this.valorUnit = valorUnit;
		this.importe = importe;
		this.unidadMed = unidadMed;
		this.categoria = categoria;
		this.fraccionArancelaria = fraccionArancelaria;
		this.impuesto = impuesto;
		this.tasa = tasa;
		this.imp = imp;
		this.email = email;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.adjunto = adjunto;
		this.version = version;
		this.tipoOpe = tipoOpe;
		this.clavePedimento = clavePedimento;
		this.certOrigen = certOrigen;
		this.numCertOrigen = numCertOrigen;
		this.numExportConfiable = numExportConfiable;
		this.incoterm = incoterm;
		this.subdiv = subdiv;
		this.observaciones = observaciones;
		this.tipoCambioUSD = tipoCambioUSD;
		this.totalUSD = totalUSD;
		this.CURP = CURP;
		this.numRegIdTrib = numRegIdTrib;
		this.pais = pais;
		this.numLineas = numLineas;
	}

	public String getIdCFD() {
		return idCFD;
	}

	public void setIdCFD(String idCFD) {
		this.idCFD = idCFD;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getFecha_Hora() {
		return fecha_hora;
	}

	public void setFecha_Hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getImpTrasladados() {
		return impTrasladados;
	}

	public void setImpTrasladados(float impTrasladados) {
		this.impTrasladados = impTrasladados;
	}

	public float getImpRetenidos() {
		return impRetenidos;
	}

	public void setImpRetenidos(float impRetenidos) {
		this.impRetenidos = impRetenidos;
	}

	public String getTotalLetra() {
		return totalLetra;
	}

	public void setTotalLetra(String totalLetra) {
		this.totalLetra = totalLetra;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public float getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(float tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getRepVentas() {
		return repVentas;
	}

	public void setRepVentas(String repVentas) {
		this.repVentas = repVentas;
	}

	public String getViaEmbarque() {
		return viaEmbarque;
	}

	public void setViaEmbarque(String viaEmbarque) {
		this.viaEmbarque = viaEmbarque;
	}

	public String getnPedido() {
		return nPedido;
	}

	public void setnPedido(String nPedido) {
		this.nPedido = nPedido;
	}

	public String getsPedido() {
		return sPedido;
	}

	public void setsPedido(String sPedido) {
		this.sPedido = sPedido;
	}

	public String getNoCtaPago() {
		return noCtaPago;
	}

	public void setNoCtaPago(String noCtaPago) {
		this.noCtaPago = noCtaPago;
	}

	public String getCondPago() {
		return condPago;
	}

	public void setCondPago(String condPago) {
		this.condPago = condPago;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getRFC() {
		return RFC;
	}

	public void setRFC(String rFC) {
		RFC = rFC;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public float getTasa() {
		return tasa;
	}

	public void setTasa(float tasa) {
		this.tasa = tasa;
	}

	public float getImp() {
		return imp;
	}

	public void setImp(float imp) {
		this.imp = imp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public int getTipoOpe() {
		return tipoOpe;
	}

	public void setTipoOpe(int tipoOpe) {
		this.tipoOpe = tipoOpe;
	}

	public String getClavePedimento() {
		return clavePedimento;
	}

	public void setClavePedimento(String clavePedimento) {
		this.clavePedimento = clavePedimento;
	}

	public String getCertOrigen() {
		return certOrigen;
	}

	public void setCertOrigen(String certOrigen) {
		this.certOrigen = certOrigen;
	}

	public String getNumCertOrigen() {
		return numCertOrigen;
	}

	public void setNumCertOrigen(String numCertOrigen) {
		this.numCertOrigen = numCertOrigen;
	}

	public String getNumExportConfiable() {
		return numExportConfiable;
	}

	public void setNumExportConfiable(String numExportConfiable) {
		this.numExportConfiable = numExportConfiable;
	}

	public String getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}

	public String getSubdiv() {
		return subdiv;
	}

	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipoCambioUSD() {
		return tipoCambioUSD;
	}

	public void setTipoCambioUSD(String tipoCambioUSD) {
		this.tipoCambioUSD = tipoCambioUSD;
	}

	public String getTotalUSD() {
		return totalUSD;
	}

	public void setTotalUSD(String totalUSD) {
		this.totalUSD = totalUSD;
	}

	public String getCURP() {
		return CURP;
	}

	public void setCURP(String cURP) {
		CURP = cURP;
	}

	public String getNumRegIdTrib() {
		return numRegIdTrib;
	}

	public void setNumRegIdTrib(String numRegIdTrib) {
		this.numRegIdTrib = numRegIdTrib;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNumLineas() {
		return numLineas;
	}

	public void setNumLineas(String numLineas) {
		this.numLineas = numLineas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}	
	
	
}
