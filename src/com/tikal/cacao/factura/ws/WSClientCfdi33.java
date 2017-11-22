package com.tikal.cacao.factura.ws;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tempuri.CancelaCFDIResponse;
import org.tempuri.ObtieneCFDIResponse;
import org.tempuri.TimbraCFDIResponse;

import org.tempuri.RegistraEmisorResponse;



public abstract class WSClientCfdi33 extends WebServiceGatewaySupport {
	
	public abstract RegistraEmisorResponse getRegistraEmisorResponse();

	public abstract TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante);
	
	public abstract CancelaCFDIResponse getCancelaCFDIResponse(String uuid);
	
	public abstract ObtieneCFDIResponse getObtieneCFDIResponse(String uuid);
}
