package com.tikal.unoconnections.tralix;

import java.util.ArrayList;
import java.util.List;

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
	// #01
	private String idCFD;
	private String serie;
	private String folio;
	private String fecha_hora;
	private float subtotal;
	private float total;
	private float impTrasladados; // total
	private float impRetenidos; // totalImpuestos
	private String totalLetra;
	private String moneda;
	private float tipoCambio;
	private String referencia;
	private String repVentas; // representante_ventas
	private String viaEmbarque;
	private String nPedido; // nuestro_pedido
	private String sPedido; // su_pedido

	// #01A
	private String noCtaPago;

	// #02
	private String condPago;
	private String metodoPago;
	private String formaPago;

	// #03 	
	//#04
	// private String idUnicoRec; //IdentificadorÚnicoReceptor
	private String RFC;
	Direccion direccion;
	private String nombreReceptor;

	// #06
	private String impuesto;
	private float tasa;
	private float imp; // importe

	// #09
	// private String idIntReceptor; //IdentificadorInternoReceptor
	private String email;
	private String asunto;
	private String mensaje;
	private String adjunto;

	// #10
	private float version = 0;
	private int tipoOpe;
	private String clavePedimento;
	private String certOrigen; // certificadoOrigen
	private String numCertOrigen;
	private String numExportConfiable; // NumeroDeExportadorConfiable
	private String incoterm;
	private String subdiv;
	private String observaciones;
	private String tipoCambioUSD;
	private String totalUSD;

	// #12
	private String CURP;
	private String numRegIdTrib;

	// #14
	private String pais;

	// #99
	private int numLineas;

	// Conceptos
	private List<DatosConcepto> conceptos;
	
	public Datos(){
		
	}

	public Datos(String info) {
		this.setConceptos(new ArrayList<DatosConcepto>());

		String[] rengs = info.split("\n");

		for (String reng : rengs) {
			String head = reng.substring(0, reng.indexOf("|"));
			switch (head) {
			case "01": {
				this.parsea01(reng);
				break;
			}
			case "01A": {
				this.parsea01A(reng);
				break;
			}
			case "02": {
				this.parsea02(reng);
				break;
			}
			case "03": {
				this.parsea03(reng);
				break;
			}
			case "05": {
				this.parsea05(reng);
				break;
			}
			case "06": {
				this.parsea06(reng);
				break;
			}
			case "09": {
				this.parsea09(reng);
				break;
			}
			case "10":{
				this.parsea10(reng);
				break;
			}
			case "12": {
				this.parsea12(reng);
				break;
			}
			case "14": {
				this.parsea14(reng);
				break;
			}
			case "99": {
				String[] values = reng.split("\\|");
				this.trimear(values);
				this.numLineas = Integer.parseInt(values[1]);
			}
			}

		}
	}

	private void parsea01(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.idCFD = values[1];
		this.serie = values[2];
		this.folio = values[3];
		this.fecha_hora = values[4];
		this.subtotal = Float.parseFloat(values[5]);
		this.total = Float.parseFloat(values[6]);
		this.impTrasladados = Float.parseFloat(values[7]);
		this.impRetenidos = Float.parseFloat(values[8]);
		this.totalLetra = values[9];
		this.moneda = values[10];
		if (!values[11].isEmpty()) {
			this.tipoCambio = Float.parseFloat(values[11]);
		}
	}

	private void parsea01A(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.noCtaPago = values[1];
	}

	private void parsea02(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.condPago = values[1];
		this.metodoPago = values[2];
		this.formaPago = values[3];
	}

	private void parsea03(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.RFC = values[2];
		this.setNombreReceptor(values[3]);
		this.direccion= new Direccion();
		direccion.setCalle(values[5]);
		direccion.setCodigoPostal(values[10]);
		direccion.setColonia(values[8]);
		direccion.setLocalidad(values[9]);
		direccion.setNumExterior(values[6]);
		direccion.setEstado(values[9].split(" ")[1]);
		
	}

	private void parsea05(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		String id = values[1];
		if (id.compareTo("0") != 0) {
			DatosConcepto d = new DatosConcepto();
			d.setClave(values[1]);
			d.setCantidad(Integer.parseInt(values[2]));
			d.setDescripcion(values[3]);
			d.setFraccionArancelaria(values[8]);
			d.setImporte(Float.parseFloat(values[5]));
			d.setUnidadMed(values[7]);
			d.setValorUnit(Float.parseFloat(values[4]));
			this.conceptos.add(d);
		} else {
			DatosConcepto d = this.conceptos.get(this.conceptos.size() - 1);
			String desc = d.getDescripcion();
			desc += " " + values[3];
			d.setDescripcion(desc);
		}

	}

	private void parsea06(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.impuesto = values[1];
		this.tasa = Float.parseFloat(values[2]);
		this.imp = Float.parseFloat(values[3]);
	}

	private void parsea09(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.email = values[2];
		this.asunto = values[3];
		this.mensaje = values[4];
		this.adjunto = values[5];
	}
	
	private void parsea10(String reng){
		String[] values = reng.split("\\|");
		this.trimear(values);
		if(!values[2].isEmpty()){
			this.tipoOpe=Integer.parseInt(values[2]);
		}
		if(!values[3].isEmpty()){
			this.clavePedimento=values[3];
		}
		
		if(!values[3].isEmpty()){
			this.clavePedimento=values[3];
		}
		if(!values[4].isEmpty()){
			this.certOrigen=values[4];
		}
		if(!values[5].isEmpty()){
			this.numCertOrigen=values[5];
		}
		if(!values[6].isEmpty()){
			this.numExportConfiable=values[6];
		}
		if(!values[7].isEmpty()){
			this.incoterm=values[7];
		}
		if(!values[8].isEmpty()){
			this.subdiv=values[8];
		}
		if(!values[9].isEmpty()){
			this.observaciones=values[9];
		}
		if(!values[10].isEmpty()){
			this.tipoCambioUSD=values[10];
		}else{
			this.tipoCambioUSD=tipoCambio+"";
		}
		if(!values[11].isEmpty()){
			this.totalUSD=values[11];
		}
	}

	private void parsea12(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.CURP = values[1];
		this.numRegIdTrib = values[2];
	}

	private void parsea14(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.direccion = new Direccion();
		this.direccion.setCalle(values[1]);
		this.direccion.setNumExterior(values[2]);
		this.direccion.setNumInterior(values[3]);
		this.direccion.setColonia(values[4]);
		this.direccion.setLocalidad(values[5]);
		this.direccion.setMunicipio(values[6]);
		this.direccion.setEstado(values[7]);
		this.setPais(values[9]);
//		this.direccion.setCodigoPostal(values[10]);
	}
	
	private void trimear(String[] values){
		for(int i= 0; i<values.length; i++){
			values[i]= values[i].trim();
		}
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

	public int getNumLineas() {
		return numLineas;
	}

	public void setNumLineas(int numLineas) {
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

	public List<DatosConcepto> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<DatosConcepto> conceptos) {
		this.conceptos = conceptos;
	}

	public String getNombreReceptor() {
		return nombreReceptor;
	}

	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}

}
