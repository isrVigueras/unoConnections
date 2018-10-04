package com.tikal.unoconnections.tralix;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.exception.DatosTxtException;

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
	private double subtotal;
	private double total;
	private double impTrasladados; // total
	private double impRetenidos; // totalImpuestos
	private String totalLetra;
	private String moneda;
	private double tipoCambio;
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
	// private String idUnicoRec; //IdentificadorÚnicoReceptor
	private String RFC;
	Direccion direccion;
	private String nombreReceptor;
	
	// #04
	private String idShip;
	private String shipNombre;
	private String shipPais;
	private String shipCalle;
	private String shipColonia;
	private String shipEstado;

	// #06
	private String impuesto;
	private double tasa;
	private double imp; // importe

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
	private String usoCFDI;
	private String uuidRelacionado;
	private String descuento;
	
	private String error;
	
	@Index
	private boolean pausada;

	// Conceptos
	private List<DatosConcepto> conceptos;

	public Datos() {

	}

	public Datos(String info) throws DatosTxtException {
		this.setConceptos(new ArrayList<DatosConcepto>());
		this.pausada=false;
		String[] rengs = info.split("\n");

		for (String reng : rengs) {
			if(reng.contains("|")){
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
				case "04":{
					this.parsea04(reng);
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
				case "10": {
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
					if (values.length >= 3) {
						this.usoCFDI = values[2].split("-")[0].trim();
						if (this.usoCFDI.length() < 3) {
							throw new DatosTxtException("¡Advertencia! No se indica el Uso de CFDI. "
									+ "Debe eliminar este registro y volver a cargar el archivo con el Uso de CFDI. "
									+ "Factura: " + this.getSerie() + this.getFolio());
						}
						if(values.length>=4){
							this.uuidRelacionado = values[3].trim();
						}
					} else {
						throw new DatosTxtException("¡Advertencia! No se indica el Uso de CFDI. "
								+ "Debe eliminar este registro y volver a cargar el archivo con el Uso de CFDI. "
								+ "Factura: " + this.getSerie() + this.getFolio());
					}
					
				}
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
		
		//cambiar
		this.subtotal = Double.parseDouble(values[5].replaceAll("," , ""));
		
		//cambiar
		this.total = Double.parseDouble(values[6].replaceAll("," , ""));
		
		//cambiar
		this.impTrasladados = Double.parseDouble(values[7].replaceAll("," , ""));
		
		//cambiar
		this.impRetenidos = Double.parseDouble(values[8].replaceAll("," , ""));
		
		this.totalLetra = values[9];
		this.moneda = values[10];
		if (!values[11].isEmpty()) {
		//cambiar
			this.tipoCambio = Double.parseDouble(values[11].replaceAll("," , ""));
		}
		this.repVentas= values[13];
		this.nPedido = values[15];
		this.sPedido = values[16];
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
		if (this.metodoPago.toUpperCase().contains("TRANSFERENCIA")) {
			this.metodoPago = "TRANSFERENCIA ELECTRÓNICA DE FONDOS";
		} else if (this.metodoPago.toUpperCase().contains("OTROS")) {
			this.metodoPago = "POR DEFINIR";
		}
		this.formaPago = values[3];
	}

	private void parsea03(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.RFC = values[2];
		this.setNombreReceptor(values[3]);
		this.direccion = new Direccion();
		if (!values[5].isEmpty()) {
			direccion.setCalle(values[5]);
		}
		if (!values[5].isEmpty()) {
			direccion.setCodigoPostal(values[10]);
		}
		if (!values[8].isEmpty()) {
			direccion.setColonia(values[8]);
		}
		if (!values[9].isEmpty()) {
			direccion.setLocalidad(values[9]);
		}
		if (!values[6].isEmpty()) {
			direccion.setNumExterior(values[6]);
		}
		if (!values[9].isEmpty()) {
			direccion.setEstado(values[9].split(" ")[1]);
			if (direccion.getEstado().length() > 3) {
				if (values[9].contains(",")) {
					direccion.setEstado(values[9].split(",")[1].trim().split(" ")[0]); // El txt tiene que modificarse para agregar una coma entre la ciudad y el estado
				}
			}
				
		}

	}

	private void parsea04(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.setIdShip(values[1]);
		this.shipNombre= values[2];
		this.shipPais= values[3];
		this.shipCalle= values[4];
		this.shipColonia= values[5];
		this.shipEstado= values[8];
	
	}
	
	private void parsea05(String reng) throws DatosTxtException {
		String[] values = reng.split("\\|");
		this.trimear(values);
		String id = values[1];
		if (id.compareTo("0") != 0) {
			DatosConcepto d = new DatosConcepto();
			d.setClave(values[1]);
			
			//cambiar
			d.setCantidad(Double.parseDouble(values[2].replaceAll("," , "")));
			
			d.setDescripcion(values[3]);
			d.setDescripcion( d.getDescripcion().
					replace("LOS BIENES QUE AMPARA ESTA FACTURA SON PAGADOS EN UNA SOLA EXHIBICIÓN", ""));
			if (values[8] != null && !values[8].contentEquals("")) {
				d.setFraccionArancelaria(values[8]);
			} else if (this.RFC.contentEquals("XEXX010101000")) {
				throw new DatosTxtException("El concepto de exportación" + d.getClave() + " no tiene fracción arancelaria. " +
						"Factura : " + this.serie + this.folio + ".\n\r" + " Debe borrar el registro y volver a subir el archivo .txt");
			}
			
			
			//cambiar
			d.setImporte( Util.redondear( Double.parseDouble(values[5].replaceAll("," , "")) ) );
			
			d.setUnidadMed(values[6]);
			
			//cambiar
			d.setValorUnit(Double.parseDouble(values[4].replaceAll("," , "")));
			this.conceptos.add(d);
		} else {
			DatosConcepto d = this.conceptos.get(this.conceptos.size() - 1);
			if(d.getUnidadMed().compareTo("LY")==0){
				String aux= values[3];
				if(aux.contains("M2=")){
					String[] arr=aux.split("M2=");
					aux= arr[1].trim();
					aux=aux.replace(",", "");
					aux=aux.replace("'", "");
					arr= aux.split(" ");
					d.setUnidadAduana("04");
					double cantidadAduana= Double.parseDouble(arr[0]);
					d.setCantidadAduana(cantidadAduana);
					d.setValorUnitAduana(d.getImporte()/cantidadAduana);
				}
			}else{
				//meter aquí la condición de los clienteses
				if(this.getNombreReceptor().contains("TEXENE")||this.getNombreReceptor().contains("COVALENCE")){
					String aux= values[3];
					if(aux.contains("KG=")){
						String[] arr=aux.split("KG=");
						aux= arr[1].trim();
						aux=aux.replace(",", "");
						aux=aux.replace("'", "");
						arr= aux.split(" ");
						d.setUnidadAduana("01");
						double cantidadAduana= Double.parseDouble(arr[0]);
						d.setCantidadAduana(cantidadAduana);
						d.setValorUnitAduana(d.getImporte()/cantidadAduana);
					}
				}else if(this.getNombreReceptor().contains("COVALENCE")){
					String aux= values[3];
					if(aux.contains("M2=")){
						String[] arr=aux.split("M2=");
						aux= arr[1].trim();
						aux=aux.replace(",", "");
						aux=aux.replace("'", "");
						arr= aux.split(" ");
						d.setUnidadAduana("04");
						double cantidadAduana= Double.parseDouble(arr[0]);
						d.setCantidadAduana(cantidadAduana);
						d.setValorUnitAduana(d.getImporte()/cantidadAduana);
					}
				}
			}
			
			String desc = d.getDescripcion();
			desc += " " + values[3];
			d.setDescripcion(desc);
			
			if(d.getDescripcion().contains("PAGADOS EN UNA SOLA")){
				d.setDescripcion( d.getDescripcion().
						replace(" LOS BIENES QUE AMPARA ESTA FACTURA SON PAGADOS EN UNA SOLA EXHIBICIÓN", ""));
				if(d.getClave().contains("ALWCAS")&& this.conceptos.size()>1){
					
					this.descuento= d.getImporte()+"";
					this.conceptos.remove(d);
				}
			}
		}

	}

	private void parsea06(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.impuesto = values[1];
		
		//cambiar
		this.tasa = Double.parseDouble(values[2].replaceAll("," , ""));
		
		//cambiar
		this.imp = Double.parseDouble(values[3].replaceAll("," , ""));
	}

	private void parsea09(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		this.email = values[2];
		this.asunto = values[3];
		this.mensaje = values[4];
		this.adjunto = values[5];
	}

	private void parsea10(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		if (!values[2].isEmpty()) {
			this.tipoOpe = Integer.parseInt(values[2]);
		}
		if (!values[3].isEmpty()) {
			this.clavePedimento = values[3];
		}

		if (!values[3].isEmpty()) {
			this.clavePedimento = values[3];
		}
		if (!values[4].isEmpty()) {
			this.certOrigen = values[4];
		}
		if (!values[5].isEmpty()) {
			this.numCertOrigen = values[5];
		}
		if (!values[6].isEmpty()) {
			this.numExportConfiable = values[6];
		}
		if (!values[7].isEmpty()) {
			this.incoterm = values[7];
		}
		if (!values[8].isEmpty()) {
			this.subdiv = values[8];
		}
		if (!values[9].isEmpty()) {
			this.observaciones = values[9];
		}
		if (!values[10].isEmpty()) {
			this.tipoCambioUSD = values[10];
		} else {
			this.tipoCambioUSD = tipoCambio + "";
		}
		if (!values[11].isEmpty() && !values[11].contentEquals("03") && values[11].contentEquals(this.total+"")) {
			this.totalUSD = values[11];
		} else if (this.RFC.contentEquals("XEXX010101000")){
			this.totalUSD = String.valueOf(this.total);
		}
	}

	private void parsea12(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
		if (!values[1].isEmpty()) {
			this.CURP = values[1];
		}
		this.numRegIdTrib = values[2];
	}

	private void parsea14(String reng) {
		String[] values = reng.split("\\|");
		this.trimear(values);
//		this.direccion = new Direccion();
//		this.direccion.setCalle(values[1]);
//		this.direccion.setNumExterior(values[2]);
//		this.direccion.setNumInterior(values[3]);
//		this.direccion.setColonia(values[4]);
//		this.direccion.setLocalidad(values[5]);
//		this.direccion.setMunicipio(values[6]);
//		this.direccion.setEstado(values[7]);
		this.setPais(values[9]);
		// this.direccion.setCodigoPostal(values[10]);
	}

	private void trimear(String[] values) {
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
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

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getImpTrasladados() {
		return impTrasladados;
	}

	public void setImpTrasladados(double impTrasladados) {
		this.impTrasladados = impTrasladados;
	}

	public double getImpRetenidos() {
		return impRetenidos;
	}

	public void setImpRetenidos(double impRetenidos) {
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

	public double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(double tipoCambio) {
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

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
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

	public double getTasa() {
		return tasa;
	}

	public void setTasa(double tasa) {
		this.tasa = tasa;
	}

	public double getImp() {
		return imp;
	}

	public void setImp(double imp) {
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

	public String getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	public String getShipNombre() {
		return shipNombre;
	}

	public void setShipNombre(String shipNombre) {
		this.shipNombre = shipNombre;
	}

	public String getShipPais() {
		return shipPais;
	}

	public void setShipPais(String shipPais) {
		this.shipPais = shipPais;
	}

	public String getShipCalle() {
		return shipCalle;
	}

	public void setShipCalle(String shipCalle) {
		this.shipCalle = shipCalle;
	}

	public String getShipColonia() {
		return shipColonia;
	}

	public void setShipColonia(String shipColonia) {
		this.shipColonia = shipColonia;
	}

	public String getShipEstado() {
		return shipEstado;
	}

	public void setShipEstado(String shipEstado) {
		this.shipEstado = shipEstado;
	}

	public String getIdShip() {
		return idShip;
	}

	public void setIdShip(String idShip) {
		this.idShip = idShip;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isPausada() {
		return pausada;
	}

	public void setPausada(boolean pausada) {
		this.pausada = pausada;
	}

	public String getUuidRelacionado() {
		return uuidRelacionado;
	}

	public void setUuidRelacionado(String uuidRelacionado) {
		this.uuidRelacionado = uuidRelacionado;
	}

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	
	
}