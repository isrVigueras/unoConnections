package com.tikal.cacao.factura.ws;

import java.io.InputStream;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tempuri.AsignaTimbresEmisorResponse;
import org.tempuri.CancelaCFDIAckResponse;
import org.tempuri.CancelaCFDIResponse;
import org.tempuri.ObtieneCFDIResponse;
import org.tempuri.ObtieneTimbresDisponiblesResponse;
import org.tempuri.TimbraCFDIResponse;

import org.tempuri.RegistraEmisorResponse;



public abstract class WSClientCfdi33 extends WebServiceGatewaySupport {
	
	public abstract RegistraEmisorResponse getRegistraEmisorResponse(String rfcEmisor, String pass, InputStream cer, InputStream ker);

	public abstract TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante);
	
	public abstract CancelaCFDIResponse getCancelaCFDIResponse(String uuid, String rfcEmisor);
	
	public abstract CancelaCFDIAckResponse getCancelaCFDIAckResponse(String uuid, String rfcEmisor); 
	
	public abstract ObtieneCFDIResponse getObtieneCFDIResponse(String uuid, String rfcEmisor);
	
	public abstract ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponsePorEmisor(String rfcEmisor);
	
	public abstract AsignaTimbresEmisorResponse getAsignaTimbresEmisorResponse(String rfcEmisor, int numTimbres);
}
