package com.tikal.cacao.factura.ws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

//import java.lang.reflect.Field;

import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.tikal.cacao.util.Util;

import localhost.CancelaCFDI;
import localhost.CancelaCFDIAck;
import localhost.CancelaCFDIAckResponse;
import localhost.CancelaCFDIResponse;
import localhost.EncodeBase64;
import localhost.ObjectFactory;
import localhost.ObtieneCFDI;
import localhost.ObtieneCFDIResponse;
import localhost.ObtieneTimbresDisponibles;
import localhost.ObtieneTimbresDisponiblesResponse;
import localhost.RegistraEmisor;
import localhost.RegistraEmisorResponse;
import localhost.TimbraCFDI;
import localhost.TimbraCFDIResponse;

/**
 * @author Tikal
 *
 */
@Service
public class GenericClient extends WSClient {

	private ObjectFactory of = new ObjectFactory();
	private EncodeBase64 base64 = new EncodeBase64();
	private String uri;
	private String usuarioIntegrador;
	
	public GenericClient() {
		if (Util.detectarAmbienteProductivo()) {
			uri = "https://www.timbracfdi.mx/serviciointegracion/Timbrado.asmx";
			usuarioIntegrador = "SSaC3HanfgtTGP+gChvWNg==";
		} else {
			uri = "https://www.timbracfdipruebas.mx/serviciointegracionpruebas/Timbrado.asmx";
			usuarioIntegrador = "mvpNUXmQfK8=";
		}
	}

	
	/* (non-Javadoc)
	 * @see com.tikal.cacao.factura.ws.WSClient#getRegistraEmisorResponse()
	 */
	@Override
	public RegistraEmisorResponse getRegistraEmisorResponse(String rfcEmisor, String pass, ByteArrayInputStream cer, InputStream key) {
		RegistraEmisor request = of.createRegistraEmisor();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setBase64Cer(getByteArrayBase64(cer));
		request.setBase64Key(getByteArrayBase64(key));
		request.setContrasena(pass);
		
		RegistraEmisorResponse response = (RegistraEmisorResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://localhost/RegistraEmisor"));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.factura.ws.WSClient#getTimbraCFDIResponse(java.lang.String)
	 */
	@Override
	public TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante) {
		this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		TimbraCFDI request = of.createTimbraCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setXmlComprobanteBase64(getBase64CFDI(xmlComprobante));
		request.setIdComprobante(getidComprobante());
		
		TimbraCFDIResponse response = (TimbraCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://localhost/TimbraCFDI"));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.factura.ws.WSClient#getCancelaCFDIResponse()
	 */
	@Override
	public CancelaCFDIResponse getCancelaCFDIResponse(String uuid, String rfcEmisor) {
		this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		CancelaCFDI request = of.createCancelaCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		CancelaCFDIResponse response = (CancelaCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/CancelaCFDI"));
		return response;
	}
	
	@Override
	public CancelaCFDIAckResponse getCancelaCFDIAckResponse(String uuid, String rfcEmisor) {
		this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		CancelaCFDIAck request = of.createCancelaCFDIAck();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		CancelaCFDIAckResponse response =  (CancelaCFDIAckResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/CancelaCFDIAck"));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.factura.ws.WSClient#getObtieneCFDIResponse()
	 */
	@Override
	public ObtieneCFDIResponse getObtieneCFDIResponse(String uuid, String rfcEmisor) {
		ObtieneCFDI request = of.createObtieneCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		ObtieneCFDIResponse response = (ObtieneCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/ObtieneCFDI"));
		return response;
	}
	
	@Override
	public ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponse() {
		ObtieneTimbresDisponibles request = new ObtieneTimbresDisponibles();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		ObtieneTimbresDisponiblesResponse response = (ObtieneTimbresDisponiblesResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/ObtieneTimbresDisponibles"));
		return response;
	}
	
	/*private <T,S> T getResponse(String uri, String soapAction, Class<S> reqClass, String... fieldValues) {

		try {
			S request = reqClass.newInstance();
		
		Field[] fields = reqClass.getDeclaredFields();
		for (Field field : fields) {
			if ( field.getType().equals(String.class) ) {
				for(String fieldValue : fieldValues) {
					if (!field.isAccessible())
						field.setAccessible(true);
					
					
					//TODO establecer los valores de los campos 
				}
			}
				
		}
		
		T response = (T) getWebServiceTemplate().marshalSendAndReceive(
				uri, request, new SoapActionCallback(soapAction));
		
		return response;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	private HttpComponentsMessageSender crearMessageSender() {
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setConnectionTimeout(60000);
		messageSender.setReadTimeout(60000);
		return messageSender;
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
		return this.usuarioIntegrador;
	}
	

	private String getRfcEmisor(){
		//Esto es Fijo para el ambito de pruebas AAA010101AAA
		
		return "TTI160614777";
	}
	
	private String getBase64(String fileLocat)  {
		return base64.encode(fileLocat);
	}
	
	private String getFileBase64(File f) {
		return base64.encodeFile(f);
	}
	
	private String getBase64CFDI(String xmlCFDI) {
		return base64.encodeString(xmlCFDI);
	}
	
	private String getByteArrayBase64(InputStream inputStream) {
		return base64.encodeByteArrayIS(inputStream);
	}
	
	private String getContrasena() {
		return "12345678a";
	}

}
