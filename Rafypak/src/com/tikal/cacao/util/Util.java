/**
 * 
 */
package com.tikal.cacao.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import java.util.GregorianCalendar;

import com.google.appengine.api.utils.SystemProperty;
import com.tikal.cacao.factura.FormatoFecha;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.orm.EntidadCatalogo;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.TUbicacion;
import com.tikal.cacao.sat.cfd.catalogos.C_Estado;
import com.tikal.cacao.sat.cfd.nomina.C_PeriodicidadPago;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;

import mx.gob.sat.cancelacfd.Acuse;
import mx.gob.sat.comercioexterior11.ComercioExterior;
import mx.gob.sat.implocal.ImpuestosLocales;
import mx.gob.sat.pagos.Pagos;

/**
 * @author Tikal
 *
 */
public class Util {

	/**
	 * Convierte la fecha en el formato "<tt>yyyy-MM-dd'T'HH:mm:ss.SSSZ</tt>" a su representaci&oacute;n en {@code java.util.Date}
	 * @param cadenaFecha fecha con formato "<tt>yyyy-MM-dd'T'HH:mm:ss.SSSZ</tt>"
	 * @return la fecha convertida en un objeto {@code java.util.Date} 
	 */
	public static Date obtenerFecha(String cadenaFecha) {
		DateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		//TODO suposición de que en App Engine esta línea causa error formato.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		Date fechaIngreso = new Date();
		try {
			fechaIngreso = formato.parse(cadenaFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaIngreso;
	}
	
	/**
	 * Convierte y regresa la cadena especificada en un objeto {@code java.util.Date} utilizando el formato
	 * especificado
	 * @param cadenaFecha la representaci&oacute;n en {@code String} de un objeto {@code java.util.Date}
	 * @param formato el formato que se utiliza para analizar gramaticalmente la cadena especificada
	 * @return la cadena especificada convertida en un objeto {@code java.util.Date}
	 */
	public static Date obtenerFecha(String cadenaFecha, DateFormat formato) {
		formato.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		Date fecha = null;
		cadenaFecha = cambiarNombreDelMes(cadenaFecha);
		try {
			fecha = formato.parse(cadenaFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	}
	
	/**
	 * 
	 * @param fecha Fecha a la que se le dará el formato
	 * @return una representaci&oacute;n del la fecha en objeto String
	 */
	public static String regresaFechaConFormato(Date fecha) {
		String cadenaFecha = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		cadenaFecha = df.format(fecha);
		return cadenaFecha;
	}
	
	public static String cambiarNombreDelMes(String strFecha) {
		String mes = strFecha.substring(0, 3);
		switch (mes) {
		case "Jan":
			strFecha = strFecha.replace(mes, "Ene");
			break;
		case "Apr":
			strFecha = strFecha.replace(mes, "Abr");
			break;
		case "Aug":
			strFecha = strFecha.replace(mes, "Ago");
			break;
		case "Dec":
			strFecha = strFecha.replace(mes, "Dic");
			break;
		}
	    return strFecha;
	}
	
	
	/**
	 * Regresa una subclase especifica de la superclase {@code TarifaSubsidio} que corresponde
	 * con la <tt>formaDePago</tt> especificada. Los valores de la <tt>formaDePago</tt> pueden ser:
	 * <ul>
	 * 	<li>"SEMANAL"</li>
	 *  <li>"DECENAL"</li>
	 *  <li>"QUINCENAL"</li>
	 *  <li>"MENSUAL"</li>
	 * </ul>
	 * Los valores {@code String} mencionados arriba pueden estar en cualquier combinaci&oacute;n de 
	 * may&uacute;sculas y min&uacute;sculas
	 * @param formaDePago la periodicidad del pago de un trabajador
	 * @return la subclase de la clase {@code TarifaSubsidio} a la que corresponde la <tt>formaDePago</tt>
	 */
	public static Class<? extends TarifaSubsidio> obtenerClaseTarifaSubsidio(String formaDePago) {
		PeriodosDePago periodo = PeriodosDePago.valueOf(formaDePago.toUpperCase());
		return obtenerClaseTarifaSubsidio(periodo);
	
	}
	
	/**
	 * Regresa una subclase especifica de la superclase {@code TarifaSubsidio}
	 * @param periodo el periodo de pago de un trabajador
	 * @return la subclase de la clase {@code TarifaSubsidio} a la que corresponde el <tt>periodo</tt>
	 * 
	 */
	public static Class<? extends TarifaSubsidio> obtenerClaseTarifaSubsidio(PeriodosDePago periodo) {
		Class<? extends TarifaSubsidio> c;
		switch(periodo) {
			case MENSUAL :
				c = TarifaMensual.class;
				break;
			case QUINCENAL :
				c = TarifaQuincenal.class;
				break;
			case DECENAL :
				c = TarifaDecenal.class;
				break;
			case SEMANAL :
				c = TarifaSemanal.class;
				break;
			default :
				c = TarifaTrabajoRealizado.class;
		}
		return c;
	}
	
	public static BigDecimal redondearBigD(BigDecimal bigDecimal, int decimales) {
		return bigDecimal.setScale(decimales, RoundingMode.HALF_UP);
	}
	
	public static int obtenerDecimales(double valor) {
		String strValor = String.valueOf(valor);
		String[] split = strValor.split("\\.");
		String strDecimales = split[1];
		return strDecimales.length();
	}
	
	/**
	 * Este m&eacute;todo regresa la cantidad de d&iacute;as entre la fecha
	 * especificada y la fecha actual
	 * @param fecha 
	 * @return la cantidad de d&iacute;as entre la fecha indicada y la fecha actual
	 */
	// agregar parametro 'int i' para efect
	public static int obtenerDiasEntre(Date fecha) { //, int i) 
	
		Calendar presente = Calendar.getInstance();
//		if (i >= 2) {
//			presente = new GregorianCalendar();
//			//presente.setTimeInMillis(1_492_268_400_000L); // 15-abril-2017
//			presente.setTimeInMillis(1_493_564_400_000L);
//		} else {
//			presente = Calendar.getInstance();
//		}
		//Calendar presente = new GregorianCalendar(2017, 4, 15);
	    
		Calendar pasado = Calendar.getInstance();
	    pasado.setTime(fecha);

	    int dias = 0;

	    while (pasado.before(presente)) {
	        pasado.add(Calendar.DATE, 1);
	        if (pasado.before(presente)) {
	            dias++;
	        }
	    } return dias;
	}
	
	public static int obtenerDiasEntre(Date fechaIn, Date fechaFin) {
		Calendar cFechaFin = Calendar.getInstance();
		Calendar cFechaIn = Calendar.getInstance();
	    cFechaIn.setTime(fechaIn);
	    cFechaFin.setTime(fechaFin);

	    int dias = 0;

	    while (cFechaIn.before(cFechaFin)) {
	        cFechaIn.add(Calendar.DATE, 1);
	        if (cFechaIn.before(cFechaFin)) {
	            dias++;
	        }
	    } return dias;
	}
	
	public static int obtenerMesesEntre(Date fechaIn, Date fechaFin) {
		Calendar cFechaFin = Calendar.getInstance();
		Calendar cFechaIn = Calendar.getInstance();
	    cFechaIn.setTime(fechaIn);
	    cFechaFin.setTime(fechaFin);
	    
	    int meses = 0;
	    while (cFechaIn.before(cFechaFin)) {
	    	cFechaIn.add(Calendar.MONTH, 1);
	    	if (cFechaIn.before(cFechaFin)) {
	    		meses++;
	    	}
	    }
		return meses;
	}
	
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	public static String randomString( int len ){
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
		}
	
	public static BigDecimal redondearBigD(double cantidad) {
		BigDecimal bigD = BigDecimal.valueOf(cantidad).setScale(2, RoundingMode.HALF_UP);
		return bigD;
	}
	
	/**
	 * &Eacute;ste m&eacute;todo regresa el n&uacute;mero decimal especificado con un redondeo a 
	 * dos valores a la derecha del punto decimal.
	 * @param cantidad la cantidad a redonear
	 * @return la cantidad con solo dos valores a la derecha del punto decimal
	 */
	public static double redondear(double cantidad) {
		return redondearBigD(cantidad).doubleValue();
	}
	
	public static float redondear(float cantidad) {
		return BigDecimal.valueOf(cantidad).setScale(2, RoundingMode.HALF_UP).floatValue();
	}
	
	public static double truncar(double cantidad) {
		BigDecimal bigD = BigDecimal.valueOf(cantidad).setScale(0, RoundingMode.DOWN);
		return bigD.doubleValue();
	}
	
	/**
	 * &Eacute;ste m&eacute;todo crea un objeto {@link RegistroBitacora } con el <em>tipo</em> y <em>evento</em> 
	 * especificados. El par&aacute;metro <em>sesion</em> debe tener un atributo que indique el 
	 * nombre del usuario de la sesi&oacute;n.
	 * @param sesion la sesi&oacute;n que se utiliza para obtener el nombre del usuario que realizo el evento
	 * @param tipo inidica si el objeto {@code RegistroBitacora} es de tipo Operacional o Administrativo
	 * @param evento texto que especifica el evento que se desea registrar
	 * @return un objeto {@code RegistroBitacora} listo para persistirse
	 */
	public static RegistroBitacora crearRegistroBitacora(HttpSession sesion, String tipo, String evento) {
		RegistroBitacora registroBitacora = new RegistroBitacora();
		registroBitacora.setUsuario((String) sesion.getAttribute("userName"));
		registroBitacora.setTipo(tipo);
		registroBitacora.setEvento(evento);
		registroBitacora.setFecha(new Date());
		return registroBitacora;
	}
	
	public static String alertarPagoNomina(Regimen reg) {
		String mensaje = "";
		Calendar presente = Calendar.getInstance();
		int[] diasDePago = reg.getDiasDePago();
		if (diasDePago == null) 
			return mensaje;
		//periodo = PeriodosDePago.valueOf(reg.getFormaPago());
		int hoy = presente.get(Calendar.DAY_OF_MONTH);
		boolean avisar = false;
		int i = 0;
		while (!avisar && i < diasDePago.length ) {
			if (hoy == diasDePago[i] || hoy == diasDePago[i] - 1 || hoy == diasDePago[i] - 2 ) {
				mensaje = "Éste esquema tiene pagos pendientes ";
				avisar = true;
			} else {
				i++;
			}
		}
		return mensaje;
	}
	
	public static Date regresarFecha(int dia){
        int fechin = dia;
        String nueva = "";
        Date fechaPago = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String fecha = df.format(now).toString();
        String febrero = fecha.substring(3,5);
        if(fechin > 28 && febrero.equals("02")){
            nueva = fecha.replace(fecha.substring(0,2),Integer.toString(28));
        }else {
            nueva = fecha.replace(fecha.substring(0, 2), Integer.toString(fechin));
        }
        try {
            fechaPago = df.parse(nueva);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaPago;
    }
	
	public static int obtenerDiaDePago(int[] diasDePago, int diaDeHoy, PeriodosDePago periodicidad) {
		int dia = 0;
		switch (periodicidad) {
			case MENSUAL:
				dia = diasDePago[0];
				break;
			case QUINCENAL:
				//break;
			case DECENAL:
				//break;
			case SEMANAL:
				for (int i = 0; i < diasDePago.length; i++) {
					if (diaDeHoy <= diasDePago[i]) {
						dia = diasDePago[i];
						break;
					}
				}
			default:
				break;
		}
		return dia;
	}
	
	/**
	 * &Eacute;ste m&eacute;todo regresa un objeto {@code Date} representando
	 * el momento actual del mes que resulte de restar a &eacute;ste, el n&uacute;mero de meses
	 * especificado.
	 * @param meses n&uacute;mero de meses que se restar&aacute;n de la fecha actual
	 * @return un objeto {@code Date} que resulte de restar al momento actual el n&uacute;mero de meses
	 */
	public static Date obtenerFechaMesesAntes(int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -meses);
		return calendar.getTime();
	}
	
	
	public static XMLGregorianCalendar getXMLDate(Date date, FormatoFecha formato) {
    	GregorianCalendar c = new GregorianCalendar(TimeZone.getTimeZone("America/Mexico_City"));
    	Date dateMexico = c.getTime();
    	System.out.println(dateMexico);
    	if (!date.after(dateMexico)) {
    		c.setTime(date);
    	}
    	XMLGregorianCalendar date2 = null;
    	try {
	    	switch (formato) {
			case COMPROBANTE:
				date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(
						c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH),
						c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND),
						DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
				break;
			case NOMINA:
				date2 = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
						c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
				break;
			default:
				break;
			}
    	} catch (DatatypeConfigurationException exception) {
    		return date2;
    	}
    	 
    	return date2;
    }
	
	public static Date xmlGregorianAFecha(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static C_PeriodicidadPago convertir(PeriodosDePago periodosDePago) {
		C_PeriodicidadPago periodicidadPago = null;
		switch (periodosDePago) {
		case BIMESTRAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_6;
			break;
		case MENSUAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_5;
			break;
		case QUINCENAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_4;
			break;
		case DECENAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_10;
			break;
		case SEMANAL:
			periodicidadPago = C_PeriodicidadPago.VALUE_2;
			break;
		default:
			break;
		}
		return periodicidadPago;
	}
	
	
	public static C_Estado convertir(String estado) {
		switch (estado) {
		case "":
			break;
		}
		return null;
	}
	
	public static com.tikal.cacao.sat.cfd33.Comprobante unmarshallCFDI33XML(String cadenaXML) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital.class, NominaElement.class, ComercioExterior.class, Pagos.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringBuffer xmlStr = new StringBuffer(cadenaXML);
			com.tikal.cacao.sat.cfd33.Comprobante comprobante = 
					(com.tikal.cacao.sat.cfd33.Comprobante) unmarshaller.unmarshal(new StreamSource( new StringReader(xmlStr.toString() ) ) );
			return comprobante;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Comprobante unmarshallXML(String cadenaXML) {
		try {
			//TODO en el caso de nomina agregar al método newInstance la clase NominaElement.class
			JAXBContext jaxbContext = JAXBContext.newInstance(Comprobante.class, TimbreFiscalDigital.class, ImpuestosLocales.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringBuffer xmlStr = new StringBuffer(cadenaXML);
			Comprobante comprobante = (Comprobante) unmarshaller.unmarshal(new StreamSource( new StringReader(xmlStr.toString() ) ) );
			return comprobante;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Convierte una cadena xml que representa el acuse de cancelaci&oacute;n a un objeto {@code Acuse}
	 * @param acuseXML cadena xml del acuse de cancelaci&oacute;n
	 * @return el acuse de cancelaci&oacute;n
	 */
	public static Acuse unmarshallAcuseXML(String acuseXML) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Acuse.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringBuffer xmlStr = new StringBuffer(acuseXML);
			Acuse acuse = (Acuse) unmarshaller.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ) );
			return acuse;
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String marshallComprobante33(com.tikal.cacao.sat.cfd33.Comprobante c) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
				"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
		
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(c, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String marshallComprobante33(com.tikal.cacao.sat.cfd33.Comprobante c, boolean esNomina) {
		try {
			JAXBContext jaxbContext;
			Marshaller jaxbMarshaller;
			if (esNomina) {
				jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, NominaElement.class);
				jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
						"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd " +
								"http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
			} else {
				if(c.getReceptor().getRfc().compareTo("XEXX010101000")==0){
					jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, ComercioExterior.class);
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
							"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd"+
					" http://www.sat.gob.mx/ComercioExterior11 http://www.sat.gob.mx/sitio_internet/cfd/ComercioExterior11/ComercioExterior11.xsd");
				}else{
					if(c.getComplemento()!=null){
						if(c.getReceptor().getRfc().compareTo("SAB730510K44")==0){
							jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, mx.gob.sat.pagos.Pagos.class, mx.com.fact.schema.pepsico.RequestCFD.class);
						}else{
							jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, mx.gob.sat.pagos.Pagos.class);
						}
						jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
								"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd"+" http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
					}else{
						jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, mx.com.fact.schema.pepsico.RequestCFD.class);
						jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
								"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
					}
				}
			}
		
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(c, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String marshallComprobanteConPagos(com.tikal.cacao.sat.cfd33.Comprobante c) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class, Pagos.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
				"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd" +
					"http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
		
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(c, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static TimbreFiscalDigital obtenerTimbreFiscalDigital(Comprobante comprobante) {
		List<Object> complemento = comprobante.getComplemento().getAny();
		TimbreFiscalDigital timbreFD = null;
		if (complemento.size() > 0) {
			for (Object object : complemento) {
				if (object instanceof TimbreFiscalDigital) {
					timbreFD = (TimbreFiscalDigital) object;
					break;
				}
			}
			return timbreFD;
		} else {
			return timbreFD;
		}
		
	}
	
	public static ComercioExterior obtenerComplComercioExterior(com.tikal.cacao.sat.cfd33.Comprobante comprobante) {
		List<Complemento> listaComplemento = comprobante.getComplemento();
		if (listaComplemento.size() > 0) {
			Complemento complemento = listaComplemento.get(0);
			List<Object> any = complemento.getAny();
			ComercioExterior complementoComExt = null;
			if (any.size() > 0) {
				for (Object object : any) {
					if (object instanceof ComercioExterior) {
						complementoComExt = (ComercioExterior) object;
						break;
					}
				}
				return complementoComExt;
			} else {
				return complementoComExt;
			}
		} else {
			return null;
		}
		
	}
	
	public static ImpuestosLocales obtenerImpuestosLocales(Comprobante comprobante) {
		List<Object> complemento = comprobante.getComplemento().getAny();
		ImpuestosLocales impLocales = null;
		if (complemento.size() > 0) {
			for (Object object : complemento) {
				if (object instanceof ImpuestosLocales) {
					impLocales = (ImpuestosLocales) object;
					break;
				}
			}
			return impLocales;
		} else {
			return impLocales;
		}
	}
	
	public static String marshallComprobante(com.tikal.cacao.sat.cfd.Comprobante c, boolean esNomina) {
		try {
			JAXBContext jaxbContext;
			Marshaller jaxbMarshaller;
			if (esNomina) {
				jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd.Comprobante.class, NominaElement.class);
				jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
						"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd " 
						+ "http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
			} else {
				if (c.getComplemento()!= null) {
					jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd.Comprobante.class, ImpuestosLocales.class);
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
							"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd "
							+ "http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");
				} else {
					jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd.Comprobante.class);
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
							"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd ");
				}
			}
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(c, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	public static String marshallComprobante33(com.tikal.cacao.sat.cfd33.Comprobante c) {
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(com.tikal.cacao.sat.cfd33.Comprobante.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//		
//			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
//				"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
//		
//			StringWriter sw = new StringWriter();
//			jaxbMarshaller.marshal(c, sw);
//			return sw.toString();
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static String regresaTextoOCadenaVacia(String texto) {
		if (texto == null) {
			texto = "";
		}	
		return texto;
	}
	

	public static void validarDomicilioReceptor(TUbicacion domicilio) {
		if (domicilio.getCalle() != null)
			if (domicilio.getCalle().equals(""))
				domicilio.setCalle(null);
		
		if (domicilio.getNoExterior() != null)
			if (domicilio.getNoExterior().equals(""))
				domicilio.setNoExterior(null);
		
		if (domicilio.getNoInterior() != null)
			if (domicilio.getNoInterior().equals(""))
				domicilio.setNoInterior(null);
		
		if (domicilio.getColonia() != null)
			if (domicilio.getColonia().equals(""))
				domicilio.setColonia(null);
		
		if (domicilio.getLocalidad() != null)
			if (domicilio.getLocalidad().equals(""))
				domicilio.setLocalidad(null);
		
		if (domicilio.getReferencia() != null)
			if (domicilio.getReferencia().equals(""))
				domicilio.setReferencia(null);
		
		if (domicilio.getMunicipio() != null)
			if (domicilio.getMunicipio().equals(""))
				domicilio.setMunicipio(null);
		
		if (domicilio.getEstado() != null)
			if (domicilio.getEstado().equals(""))
				domicilio.setEstado(null);
		
		if (domicilio.getCodigoPostal() != null)
			if (domicilio.getCodigoPostal().equals(""))
				domicilio.setCodigoPostal(null);

	}
	
	
	public static boolean verificarPagoRealizado(List<Pago> pagosRealizados) {
		Date fechaActual = new Date();
		boolean seRealizoElPago = false;
		for (Pago pago : pagosRealizados) {
			if (pago.getFechaDePagoEsquema().after(fechaActual)) {
				seRealizoElPago = false;
			} else {
				seRealizoElPago = true;
			}
		}
		
		return seRealizoElPago;
	}
	
	public static boolean detectarAmbienteProductivo() {
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			// Production   
			return true;
		} else {
		  // Local development server
		  // which is: SystemProperty.Environment.Value.Development
			 return false;
		}
	}
	
	public static RespuestaWebServicePersonalizada construirMensajeError(List<Object> respuestaWB) {
		StringBuilder respuestaError = new StringBuilder("Excepción en caso de error: ");
		respuestaError.append(respuestaWB.get(0)+ "\r\n");
		respuestaError.append("Código de error: " + respuestaWB.get(1) + "\r\n");
		respuestaError.append("Mensaje de respuesta: " + respuestaWB.get(2) + "\r\n");
		respuestaError.append( respuestaWB.get(6) + "\r\n");
		respuestaError.append( respuestaWB.get(7) + "\r\n");
		respuestaError.append( respuestaWB.get(8) + "\r\n");
		
		RespuestaWebServicePersonalizada respPersonalizada = new RespuestaWebServicePersonalizada();
		respPersonalizada.setMensajeRespuesta(respuestaError.toString());
		return respPersonalizada;
	}
	
	public static <T extends EntidadCatalogo> List<T> convertirArrayObjectToHBEntity(List<Object> listaHBObjects, Class<T> claseHB) {
		List<T> lista = new ArrayList<>();
		for (Object object : listaHBObjects) {
			//try {
				T entidadHB = (T) object;
//				entidadHB.setId((String) object[0]);
//				entidadHB.setDescripcion((String) object[1]);
				lista.add(entidadHB);
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		return lista;
	}
	
}
