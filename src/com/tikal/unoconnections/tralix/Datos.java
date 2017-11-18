package com.tikal.unoconnections.tralix;
import com.tikal.cacao.model.Direccion;

public class Datos {
	//#01
	private String idCFD;
	private String serie;
	private String folio;
	//fecha_hora
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
	//private String idUnicoRec;
	private String RFC;
	Direccion direccion;

	//#05
	private String idUnicoInterno;
	private int cantidad;
	private String descripcion;
	private float valorUnit;
	private float importe;
	private String unidadMed;
	private int categoria; //categoria
	private String fraccionArancelaria; 
	
	//#06
	private String impuesto;
	private float tasa;
	private float imp; //importe
	
	//#09
	private String idIntReceptor;
	private String email;
	private String asunto = "null";
	private String mensaje = "null";
	private String adjunto;
	
	//#10
	private float version = 0;
	private int tipoOpe;
	private String clavePedimento;
	private String certOrigen = "null";
	private String numCertOrigen = "";
	private String numExportConfiable = "null";
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
	
	public Datos (String idCFD, String serie, String folio, float subtotal, float total, float impTrasladados,
			float impRetenidos, String totalLetra, String moneda, float tipoCambio, String referencia, String repVentas,
			String viaEmbarque, String nPedido, String sPedido, String noCtaPago, String condPago, String metodoPago,
			String formaPago, String RFC, Direccion direccion, String idUnicoInterno, int cantidad,
			String descripcion, float valorUnit, float importe, String unidadMed, int categoria, String fraccionArancelaria, 
			String impuesto, float tasa, float imp, String idIntReceptor, String email, String asunto, String mensaje,
			String adjunto, float version, int tipoOpe, String clavePedimento, String certOrigen, String numCertOrigen, 
			String numExportConfiable, String incoterm, String subdiv, String observaciones, String tipoCambioUSD, 
			String totalUSD, String CURP, String numRegIdTrib, String pais, String numLineas){
		this.idCFD = idCFD;
		this.serie = serie;
		this.folio = folio;
		this.subtotal = subtotal;
		this.total = total;
		this.impTrasladados = impTrasladados;
		this.impRetenidos = impRetenidos;
		this.totalLetra = totalLetra;
		this.moneda = moneda;
		this.tipoCambio = tipoCambio;
		this.referencia = referencia;
		this.repVentas = repVentas;
		this.viaEmbarque = viaEmbarque;
		this.nPedido = nPedido;
		this.sPedido = sPedido;
		this.noCtaPago = noCtaPago;
		this.condPago = condPago;
		this.metodoPago = metodoPago;
		this.formaPago = formaPago;
		this.RFC = RFC;
		this.direccion = direccion;
		this.idUnicoInterno = idUnicoInterno;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.valorUnit = valorUnit;
		this.importe = importe;
		this.unidadMed = unidadMed;
		this.categoria = categoria;
		this.fraccionArancelaria = fraccionArancelaria;
		this.impuesto = impuesto;
		this.tasa = tasa;
		this.imp = imp;
		this.idIntReceptor = idIntReceptor;
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

	public String getIdUnicoInterno() {
		return idUnicoInterno;
	}

	public void setIdUnicoInterno(String idUnicoInterno) {
		this.idUnicoInterno = idUnicoInterno;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValorUnit() {
		return valorUnit;
	}

	public void setValorUnit(float valorUnit) {
		this.valorUnit = valorUnit;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public String getUnidadMed() {
		return unidadMed;
	}

	public void setUnidadMed(String unidadMed) {
		this.unidadMed = unidadMed;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public String getFraccionArancelaria() {
		return fraccionArancelaria;
	}

	public void setFraccionArancelaria(String fraccionArancelaria) {
		this.fraccionArancelaria = fraccionArancelaria;
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

	public String getIdIntReceptor() {
		return idIntReceptor;
	}

	public void setIdIntReceptor(String idIntReceptor) {
		this.idIntReceptor = idIntReceptor;
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
	
}
	
	
	
	


