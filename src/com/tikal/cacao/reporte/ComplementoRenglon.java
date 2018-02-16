package com.tikal.cacao.reporte;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.factura.VersionCFDI;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.util.Util;

@Entity
public class ComplementoRenglon extends ReporteRenglon{

	@Index
	private List<String> uuidsReferenciados;
	
	public ComplementoRenglon(){
		
	}
	
	public ComplementoRenglon(FacturaVTT f){
		this.fechaCertificacion = f.getFechaCertificacion();
		com.tikal.cacao.sat.cfd33.Comprobante cfdi = Util.unmarshallCFDI33XML(f.getCfdiXML());
		this.rfcEmisor = cfdi.getEmisor().getRfc();
		this.emisor = cfdi.getEmisor().getNombre();
		this.serie = cfdi.getSerie();
		this.folio = cfdi.getFolio();
		this.nombreRec = cfdi.getReceptor().getNombre();
		this.rfcReceptor = cfdi.getReceptor().getRfc();
		this.lugar = cfdi.getLugarExpedicion().getValor();
		this.uuid = f.getUuid();
//		this.subtotal=cfdi.getSubTotal()+"";
//		this.iva=cfdi.getImpuestos().getTotalImpuestosTrasladados()+"";
//		this.total=cfdi.getTotal()+"";
		this.estatus="";
		if(f.getEstatus()!=null){
			this.estatus=f.getEstatus().name();
		}
		this.version = VersionCFDI.V3_3;
		
		this.tieneComplementoPago=false;
	}
	
	public List<String> getUuids(){
		if(this.uuidsReferenciados==null){
			this.uuidsReferenciados= new ArrayList<String>();
		}
		return this.uuidsReferenciados;
	}
	
}
