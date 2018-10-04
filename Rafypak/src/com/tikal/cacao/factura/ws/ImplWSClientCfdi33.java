package com.tikal.cacao.factura.ws;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.tempuri.AsignaTimbresEmisor;
import org.tempuri.AsignaTimbresEmisorResponse;
import org.tempuri.CancelaCFDI;
import org.tempuri.CancelaCFDIAck;
import org.tempuri.CancelaCFDIAckResponse;
import org.tempuri.CancelaCFDIResponse;
import org.tempuri.ObjectFactory;
import org.tempuri.ObtieneCFDI;
import org.tempuri.ObtieneCFDIResponse;
import org.tempuri.ObtieneTimbresDisponibles;
import org.tempuri.ObtieneTimbresDisponiblesResponse;
import org.tempuri.TimbraCFDI;
import org.tempuri.TimbraCFDIResponse;

import com.tikal.cacao.util.Util;

import localhost.EncodeBase64;
import org.tempuri.RegistraEmisor;
import org.tempuri.RegistraEmisorResponse;

@Service
public class ImplWSClientCfdi33 extends WSClientCfdi33 {
	
	private ObjectFactory of = new ObjectFactory();
	private EncodeBase64 base64 = new EncodeBase64();
	private String uri;
	private String usuarioIntegrador;
	
	public ImplWSClientCfdi33() {
		if(Util.detectarAmbienteProductivo()) {
			uri = "https://timbracfdi33.mx:1443/Timbrado.asmx";
			usuarioIntegrador = "SSaC3HanfgtTGP+gChvWNg==";
			this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		} else {
			uri = "https://cfdi33-pruebas.buzoncfdi.mx:1443/Timbrado.asmx";
			usuarioIntegrador = "mvpNUXmQfK8=";
		}
	}

	@Override
	public RegistraEmisorResponse getRegistraEmisorResponse(String rfcEmisor, String pass, InputStream cer, InputStream key) {
		RegistraEmisor request = of.createRegistraEmisor();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setBase64Cer(getByteArrayBase64(cer));
		request.setBase64Key(getByteArrayBase64(key));
		request.setContrasena(pass);
		
		RegistraEmisorResponse response = (RegistraEmisorResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://tempuri.org/RegistraEmisor"));
		return response;
	}

	@Override
	public TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante) {
		if (Util.detectarAmbienteProductivo()) {
			this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		} else {
			this.getWebServiceTemplate().setMessageSenders(this.configurarMessageSenders());
		}
		TimbraCFDI request = of.createTimbraCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setXmlComprobanteBase64(getBase64CFDI(xmlComprobante));
		
		// l�nea de prueba hard code
		//request.setXmlComprobanteBase64(base64.encode("WEB-INF/cdfi33BaseParaPrueba.xml"));
		
		request.setIdComprobante(getidComprobante());
		
		TimbraCFDIResponse response = (TimbraCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request, 
						new SoapActionCallback("http://tempuri.org/TimbraCFDI"));
		return response;
	}

	@Override
	public CancelaCFDIResponse getCancelaCFDIResponse(String uuid, String rfcEmisor) {
		CancelaCFDI request = of.createCancelaCFDI();
		request.setFolioUUID(uuid);
		request.setRfcEmisor(rfcEmisor);
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		CancelaCFDIResponse response = 
				(CancelaCFDIResponse) getWebServiceResponse(uri, request, "http://tempuri.org/CancelaCFDI");
		return response;
	}
	
	@Override
	public CancelaCFDIAckResponse getCancelaCFDIAckResponse(String uuid, String rfcEmisor) {
		//this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		this.getWebServiceTemplate().setMessageSenders(this.configurarMessageSenders());
		CancelaCFDIAck request = of.createCancelaCFDIAck();
		request.setFolioUUID(uuid);
		request.setRfcEmisor(rfcEmisor);
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		CancelaCFDIAckResponse response = (CancelaCFDIAckResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://tempuri.org/CancelaCFDIAck"));
		return response;
	}

	@Override
	public ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponsePorEmisor(String rfcEmisor) {
		ObtieneTimbresDisponibles request = of.createObtieneTimbresDisponibles();
		request.setRfcEmisor(rfcEmisor);
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		ObtieneTimbresDisponiblesResponse response = (ObtieneTimbresDisponiblesResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://tempuri.org/ObtieneTimbresDisponibles"));
		return response;
	}

	@Override
	public AsignaTimbresEmisorResponse getAsignaTimbresEmisorResponse(String rfcEmisor, int numTimbres) {
		AsignaTimbresEmisor request = of.createAsignaTimbresEmisor();
		request.setNoTimbres(numTimbres);
		request.setRfcEmisor(rfcEmisor);
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		AsignaTimbresEmisorResponse response = 
				(AsignaTimbresEmisorResponse) getWebServiceResponse(uri, request, "http://tempuri.org/AsignaTimbresEmisor");
		return response;
	}

	@Override
	public ObtieneCFDIResponse getObtieneCFDIResponse(String uuid, String rfcEmisor) {
		ObtieneCFDI request = of.createObtieneCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid);
		
		ObtieneCFDIResponse response = 
				(ObtieneCFDIResponse) getWebServiceResponse(uri, request, "http://tempuri.org/ObtieneCFDI");
		return response;
	}
	
	private Object getWebServiceResponse(String uri, Object request, String soapAction) {
		return this.getWebServiceTemplate().marshalSendAndReceive(uri, request, new SoapActionCallback(soapAction));
	}
	
	
	private WebServiceMessageSender[] configurarMessageSenders () {
		List<WebServiceMessageSender> messageSenders = new ArrayList<>();
		WebServiceMessageSender httpUrlConnectionMS = this.getWebServiceTemplate().getMessageSenders()[0];
		WebServiceMessageSender httpComponetsMS = this.crearMessageSender();
		messageSenders.add(httpUrlConnectionMS);
		messageSenders.add(httpComponetsMS);
		
		WebServiceMessageSender[] arregloMessageSenders;
		arregloMessageSenders = messageSenders.toArray(new WebServiceMessageSender[2]);
		return arregloMessageSenders;
	}
	
	private HttpComponentsMessageSender crearMessageSender() {
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setConnectionTimeout(70000);
		messageSender.setReadTimeout(70000);
		//messageSender.
		return messageSender;
	}
	
	private String getByteArrayBase64(InputStream inputStream) {
		return base64.encodeByteArrayIS(inputStream);
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
		//return "mvpNUXmQfK8=";
		return usuarioIntegrador;
//		return "SSaC3HanfgtTGP+gChvWNg==";
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
	
	private String getContrasena() {
		return "12345678a";
	}

}
