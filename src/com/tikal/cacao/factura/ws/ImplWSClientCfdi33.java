package com.tikal.cacao.factura.ws;

import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.tempuri.CancelaCFDIResponse;
import org.tempuri.ObjectFactory;
import org.tempuri.ObtieneCFDI;
import org.tempuri.ObtieneCFDIResponse;
import org.tempuri.TimbraCFDI;
import org.tempuri.TimbraCFDIResponse;

import localhost.EncodeBase64;
import org.tempuri.RegistraEmisor;
import org.tempuri.RegistraEmisorResponse;

@Service
public class ImplWSClientCfdi33 extends WSClientCfdi33 {
	
	private ObjectFactory of = new ObjectFactory();
	private EncodeBase64 base64 = new EncodeBase64();
	private final String uri = "https://cfdi33-pruebas.buzoncfdi.mx:1443/Timbrado.asmx";

	@Override
	public RegistraEmisorResponse getRegistraEmisorResponse() {
		RegistraEmisor request = of.createRegistraEmisor();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(getRfcEmisor());
		request.setBase64Cer(getBase64("WEB-INF/aaa010101aaa__csd_01.cer"));
		request.setBase64Key(getBase64("WEB-INF/aaa010101aaa__csd_01.key"));
		request.setContrasena(getContraseña());
		
		RegistraEmisorResponse response = (RegistraEmisorResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://tempuri.org/RegistraEmisor"));
		return response;
	}

	@Override
	public TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante) {
		TimbraCFDI request = of.createTimbraCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		//request.setXmlComprobanteBase64(getBase64CFDI(xmlComprobante));
		
		// línea de prueba hard code
		request.setXmlComprobanteBase64(base64.encode("WEB-INF/cdfi33BaseParaPrueba.xml"));
		
		request.setIdComprobante(getidComprobante());
		
		TimbraCFDIResponse response = (TimbraCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request, 
						new SoapActionCallback("http://tempuri.org/TimbraCFDI"));
		return response;
	}

	@Override
	public CancelaCFDIResponse getCancelaCFDIResponse(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObtieneCFDIResponse getObtieneCFDIResponse(String uuid) {
		ObtieneCFDI request = of.createObtieneCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(getRfcEmisor());
		request.setFolioUUID(uuid);
		
		ObtieneCFDIResponse response = (ObtieneCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://tempuri.org/ObtieneCFDI"));
		return response;
	}
	
	private String getFolioUUID() {
		// Es un atributo UUID del elemento  CDGI:Complemento de xmlTimbradoWS
		//return "4FFA5E77-2640-4C6B-85E8-40304872A60D";
		return "51F395B9-EB4B-48BF-B2E4-2F302FD03F04";
		
	}
	
	private String getidComprobante() {
		
		Integer i; 
		i=877;
		//i=(int) Math.floor(Math.random()*1000+1);		
		return i.toString();
	}

	private String getUsuarioIntegrador(){
		//Esto es Fijo para el ambito de pruebas
		return "mvpNUXmQfK8=";
	}
	

	private String getRfcEmisor(){
		//Esto es Fijo para el ambito de pruebas
		return "AAA010101AAA";
	}
	
	private String getBase64(String fileLocat)  {
		return base64.encode(fileLocat);
	}
	
	private String getBase64CFDI(String xmlCFDI) {
		return base64.encodeString(xmlCFDI);
	}
	
	private String getContraseña() {
		return "12345678a";
	}

}
