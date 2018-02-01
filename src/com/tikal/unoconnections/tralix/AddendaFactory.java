package com.tikal.unoconnections.tralix;

import java.math.BigInteger;
import java.sql.SQLException;

import com.google.cloud.sql.jdbc.internal.Util;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;

import mx.com.fact.schema.pepsico.ObjectFactory;
import mx.com.fact.schema.pepsico.RequestCFD;
import mx.com.fact.schema.pepsico.RequestCFD.Documento;
import mx.com.fact.schema.pepsico.RequestCFD.Proveedor;
import mx.com.fact.schema.pepsico.RequestCFD.Recepciones;
import mx.com.fact.schema.pepsico.RequestCFD.Recepciones.Recepcion;

public class AddendaFactory {
	public static Object getAdenda(String rfc, Datos d, Comprobante c){
		
		switch(rfc){
		case "SAB730510K44":{
			ObjectFactory of= new ObjectFactory();
			RequestCFD re=of.createRequestCFD();
			
			re.setVersion("2.0");
			re.setIdPedido(d.getsPedido().split("~")[0]);
			re.setTipo("AddendaPCO");
			
			Documento doc=of.createRequestCFDDocumento();
			doc.setTipoDoc(BigInteger.valueOf(1));
			re.setDocumento(doc);
			
			Proveedor prov= of.createRequestCFDProveedor();
			prov.setIdProveedor("1000001177");
			
			re.setProveedor(prov);
			
			Recepciones receps= of.createRequestCFDRecepciones();
			Recepcion recep= of.createRequestCFDRecepcionesRecepcion();
			recep.setIdRecepcion(d.getsPedido().split("~")[1]);
			
			for(Concepto co: c.getConceptos().getConcepto()){
				mx.com.fact.schema.pepsico.RequestCFD.Recepciones.Recepcion.Concepto con=of.createRequestCFDRecepcionesRecepcionConcepto();
				con.setCantidad(co.getCantidad());
				con.setDescripcion(co.getDescripcion().split(" ")[0]);
				try {
					con.setImporte(Util.createBigDecimalWithScale(co.getImporte(), 2));
					con.setValorUnitario(Util.createBigDecimalWithScale(co.getValorUnitario(),2));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				con.setUnidad(co.getUnidad());
				recep.getConcepto().add(con);
			}
			receps.getRecepcion().add(recep);
			re.setRecepciones(receps);
			
			return re;
		}
		}
		
		return null;
	}
	
	
}
