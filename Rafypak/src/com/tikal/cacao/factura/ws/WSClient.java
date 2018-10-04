package com.tikal.cacao.factura.ws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import localhost.CancelaCFDIAckResponse;
import localhost.CancelaCFDIResponse;
import localhost.ObtieneCFDIResponse;
import localhost.ObtieneTimbresDisponiblesResponse;
import localhost.RegistraEmisorResponse;
import localhost.TimbraCFDIResponse;

/**
 * @author Tikal
 *
 */
public abstract class WSClient extends WebServiceGatewaySupport {

	public abstract RegistraEmisorResponse getRegistraEmisorResponse(String rfcEmisor, String pass, ByteArrayInputStream cer, InputStream ker);

	public abstract TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobanteBase64);
	
	public abstract CancelaCFDIResponse getCancelaCFDIResponse(String uuid, String rfcEmisor);
	
	public abstract CancelaCFDIAckResponse getCancelaCFDIAckResponse(String uuid, String rfcEmisor);
	
	public abstract ObtieneCFDIResponse getObtieneCFDIResponse(String uuid, String rfcEmisor);
	
	public abstract ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponse();
}
