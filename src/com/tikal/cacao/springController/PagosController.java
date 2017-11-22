package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.PagosDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.dao.TarifasDAO;
import com.tikal.cacao.exception.DescuentoSalarioException;
import com.tikal.cacao.factura.FacturaNominaFactory;
import com.tikal.cacao.factura.FacturaNominaFactory33;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.sat.calculos.Infonavit;
import com.tikal.cacao.sat.calculos.Nomina;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;
import com.tikal.cacao.springController.viewObjects.PagoVO;
import com.tikal.cacao.springController.viewObjects.PeriodoVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

import localhost.TimbraCFDIResponse;

@Controller
@RequestMapping(value = { "/pagos" })
public class PagosController {

	@Autowired
	@Qualifier(value = "tarifasdao")
	TarifasDAO tarifasDAO;

	@Autowired
	RegimenesDAO regdao;

	@Autowired
	EmpleadosDAO empdao;

	@Resource(name = "empresasdao")
	EmpresasDAO empresasdao;

	@Autowired
	@Qualifier(value = "pagosdao")
	PagosDAO pagosdao;

	@Autowired
	Nomina nomina;

	@Autowired
	BitacoraDAO bitacoradao;

	@Autowired
	UsuarioDAO usuariodao;

	@Autowired
	PerfilDAO perfildao;

	@Autowired
	private WSClient client;

	@Autowired
	FacturaNominaFactory33 facturaNominaFactory33;

	@RequestMapping(value = { "/calcularPagos/{id}/{rfcEmpresa}" }, method = RequestMethod.GET)
	public void calcularPagos(HttpServletResponse re, HttpServletRequest req, @PathVariable String id,
			@PathVariable String rfcEmpresa) throws IOException {
		if (ServicioSesion.verificarPermiso(req, usuariodao, perfildao, 6)) {
			AsignadorDeCharset.asignar(req, re);
			PrintWriter writer = re.getWriter();
			Regimen regimen = regdao.consultar(Long.parseLong(id));
			List<PagoVO> pagosvo = new ArrayList<PagoVO>();

			List<Pago> pagosRealizados = pagosdao.consultarPagosPorRegimen(regimen.getId(), new Date());
			if (pagosRealizados == null) {
				pagosvo = calcularPagos(regimen, id, rfcEmpresa);
				writer.println(JsonConvertidor.toJson(pagosvo));
				// return;
			} else if (pagosRealizados.size() != regimen.getIdEmpleados().size()) {
				Regimen regAux = regimen;
				List<Long> idEmpleadosSinPago = new ArrayList<Long>();
				for (Long idEmpleado : regAux.getIdEmpleados()) {
					boolean flag = false;
					for (Pago pago : pagosRealizados) {
						if (pago.getIdEmpleado().compareTo(idEmpleado) == 0) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						idEmpleadosSinPago.add(idEmpleado);
					}
				}
				regAux.setIdEmpleados(idEmpleadosSinPago);
				pagosvo = calcularPagos(regAux, id, rfcEmpresa);
				writer.println(JsonConvertidor.toJson(pagosvo));
				return;
			} else if (pagosRealizados.size() == regimen.getIdEmpleados().size()) {
				// List<Pago> pagos = nomina.calcularPagos(regimen);
				// //nomina.i++; // campo de prueba para algoritmo se cambio en
				// SBC
				// //List<Pago> pagos = Nomina.calcularPagos(regimen, null);
				// //tarifasDAO.queryT(regimen.getSueldo(),
				// Util.obtenerClaseTarifaSubsidio(regimen.getFormaPago())));
				//
				// List<PagoVO> pagosvo= new ArrayList<PagoVO>();
				// List<Empleado> emps=
				// empdao.consultaPorRegimen(Long.parseLong(id));
				// Map<Long,Empleado> mapa= new HashMap<Long,Empleado>();
				// for(Empleado e:emps){
				// mapa.put(e.getNumEmpleado(), e);
				// }
				//
				// Empresa empresa = empresasdao.consultar(rfcEmpresa);
				//
				// for(Pago p:pagos){
				// p.setRfcEmpresa(empresa.getRFC());
				// p.setRazonSocial(empresa.getRazonSocial());
				// pagosvo.add(new PagoVO(p,mapa.get(p.getIdEmpleado())));
				// }
				// pagosvo = calcularPagos(regimen, id, rfcEmpresa);

				writer.println(JsonConvertidor.toJson(pagosvo));
			}
		} else {
			re.sendError(403);
		}

	}

	@RequestMapping(value = { "/calcularPagosPorRegimen/{idRegimen}" }, method = RequestMethod.GET)
	public void calcularPagosPorRegimen(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String idRegimen) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		long regimen = Long.parseLong(idRegimen);
		List<Pago> lista = pagosdao.consultarPagosPorRegimen(regimen, new Date());
		if (lista == null) {
			lista = new ArrayList<Pago>();
			response.getWriter().print(JsonConvertidor.toJson(lista));
			return;
		}
		List<PagoVO> listaPVO = new ArrayList<PagoVO>();
		List<Empleado> emps = empdao.consultaPorRegimen(Long.parseLong(idRegimen));
		Map<Long, Empleado> mapa = new HashMap<Long, Empleado>();
		for (Empleado e : emps) {
			mapa.put(e.getNumEmpleado(), e);
		}

		for (Pago p : lista) {
			listaPVO.add(new PagoVO(p, mapa.get(p.getIdEmpleado())));
		}
		response.getWriter().print(JsonConvertidor.toJson(listaPVO));

	}

	@RequestMapping(value = { "/recalcularPago" }, method = RequestMethod.POST, consumes = "application/json")
	public void recalcularPago(HttpServletResponse re, HttpServletRequest req, @RequestBody String json)
			throws IOException {
		try {
			AsignadorDeCharset.asignar(req, re);
			ListaPagosVO listaPagosVO = (ListaPagosVO) JsonConvertidor.fromJson(json, ListaPagosVO.class);
			listaPagosVO.setListaPago(nomina.calcularPagoConAjuste(listaPagosVO.getListaPago()));
			re.getWriter().println(JsonConvertidor.toJson(listaPagosVO.getLista()));

		} catch (DescuentoSalarioException e) {
			re.getWriter().println(e.getMessage());
		}
	}

	@RequestMapping(value = { "/guardarPago" }, method = RequestMethod.POST, consumes = "application/json")
	public void guardarPagos(HttpServletResponse re, HttpServletRequest req, @RequestBody String json)
			throws UnsupportedEncodingException {
		AsignadorDeCharset.asignar(req, re);
		boolean error = false;
		ListaPagosVO listaPagosVO = (ListaPagosVO) JsonConvertidor.fromJson(json, ListaPagosVO.class);
		List<PagoVO> list = listaPagosVO.getLista();
		FacturaNominaFactory facturaNominaFactory = new FacturaNominaFactory();
		Comprobante cfdi = null;
		String cadenaComprobante = null;
		String cadenaComprobanteTimbrado = null;
		TimbraCFDIResponse timbraCFDIResponse = null;
		String mensaje="";
		for (PagoVO pagoVO : list) {
			cfdi = facturaNominaFactory.generarFactura(pagoVO);
			cadenaComprobante = Util.marshallComprobante(cfdi, true);
			timbraCFDIResponse = client.getTimbraCFDIResponse(cadenaComprobante);
			List<Object> respuesta = timbraCFDIResponse.getTimbraCFDIResult().getAnyType();
			Integer codigoError = (Integer) respuesta.get(1);
			if (codigoError == 0) {
				cadenaComprobanteTimbrado = (String) respuesta.get(3);
				if (cadenaComprobanteTimbrado != null)
					pagoVO.setCadenaComprobante(cadenaComprobanteTimbrado);
				else
					pagoVO.setCadenaComprobante("");
			} else {
				try {
					error = true;
//					PrintWriter writer = re.getWriter();
//					writer.println(respuesta.get(2));
//					re.sendError(400);
					mensaje+="| "+respuesta.get(2).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!error) {
			pagosdao.crear(listaPagosVO.getListaPago());

			String evento = "Se genero la nómina para (Nombre del Regimen) de la fecha (fecha)";
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(req.getSession(), "Operacional", evento);
			bitacoradao.addReg(registroBitacora);
		}else{
			try {
				re.sendError(466, mensaje);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = {
			"/calcularAportacionesInfonavit/{rfcEmpresa}" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void calcularAportacionesInfonavit(HttpServletResponse re, HttpServletRequest req,
			@PathVariable String rfcEmpresa, @RequestBody String periodoJson) throws IOException {
		PeriodoVO periodoVO = (PeriodoVO) JsonConvertidor.fromJson(periodoJson, PeriodoVO.class);
		Empresa empresa = empresasdao.consultar(rfcEmpresa);
		List<Regimen> regs = empresa.getRegimenes();
		double aportacionInfonavit = 0;
		for (Regimen regimen : regs) {
			// TODO persistir las fechas de pago mensuales y bimestrales para
			// las cuotas al IMSS
			// y las aportaciones al INFONAVIT. Consultar los pagos realizados
			// por los
			// periodos mensuales y bimestrales correspondientes y realizar los
			// cálculos
			List<Pago> pagos = pagosdao.consultarPagosPorRegimen(regimen.getId(), new Date(1_483_250_400_000L)); // hardcode
																													// 01-01-2017
			if (pagos == null)
				continue;
			for (Pago pago : pagos) {
				if (pago.isTrabajadorAsegurado()) {
					aportacionInfonavit += Infonavit.calcularAportaciones(pago.getSalarioDiarioIntegrado(),
							periodoVO.getPeriodo(), Integer.parseInt(pago.getDiasPagados()));
				}
			}
		}
		re.getWriter().println(aportacionInfonavit);
	}

	@RequestMapping(value = { "/generarFactura/{idPago}" }, method = RequestMethod.GET)
	public void generarFactura(HttpServletResponse re, HttpServletRequest req, @PathVariable String idPago) {
		Pago pago = pagosdao.consultar(Long.parseLong(idPago));
		String cfdiTimbrado = pago.getCadenaComprobante();
		try {
			PrintWriter writer = re.getWriter();
			re.setContentType("text/xml");
			writer.println(cfdiTimbrado);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Regimen regimen = regdao.consultar(pago.getIdRegimen());
		/*
		 * Empleado empleado = empdao.consultar(pago.getIdEmpleado()); PagoVO
		 * pagoVO = new PagoVO(pago, empleado);
		 * //com.tikal.cacao.sat.cfd33.Comprobante cfdi33 =
		 * facturaNominaFactory33.generarFactura(pagoVO); FacturaNominaFactory
		 * facturaNominaFactory = new FacturaNominaFactory(); Comprobante cfdi =
		 * facturaNominaFactory.generarFactura(pagoVO);
		 * 
		 * 
		 * String cadenaComprobante = Util.marshallComprobante(cfdi, true);
		 * TimbraCFDIResponse timbraCFDIResponse =
		 * client.getTimbraCFDIResponse(cadenaComprobante);
		 * 
		 * try { PrintWriter writer = re.getWriter(); List<Object> respuesta =
		 * timbraCFDIResponse.getTimbraCFDIResult().getAnyType(); Integer
		 * codigoError = (Integer) respuesta.get(1); if (codigoError == 0) {
		 * re.setContentType("text/xml"); writer.println(respuesta.get(3)); }
		 * else { writer.println("Excepción en caso de error: " +
		 * respuesta.get(0)); writer.println("Código de error: " +
		 * respuesta.get(1)); writer.println("Mensaje de respuesta: " +
		 * respuesta.get(2)); writer.println("XML certificado o timbrado: " +
		 * respuesta.get(3)); //TODO descargar el archivo xml writer.println(
		 * "QRCode: " + respuesta.get(4)); //TODO transformar el arreglo de
		 * bytes para obtener la imagen del QRCode writer.println(
		 * "Sello digital del comprobante: " + respuesta.get(5)); }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */

	}

	private List<PagoVO> calcularPagos(Regimen regimen, String id, String rfcEmpresa) {
		List<Pago> pagos = nomina.calcularPagos(regimen);
		// nomina.i++; // campo de prueba para algoritmo se cambio en SBC
		// List<Pago> pagos = Nomina.calcularPagos(regimen, null);
		// tarifasDAO.queryT(regimen.getSueldo(),
		// Util.obtenerClaseTarifaSubsidio(regimen.getFormaPago())));

		List<PagoVO> pagosvo = new ArrayList<PagoVO>();
		List<Empleado> emps = empdao.consultaPorRegimen(Long.parseLong(id));
		Map<Long, Empleado> mapa = new HashMap<Long, Empleado>();
		for (Empleado e : emps) {
			mapa.put(e.getNumEmpleado(), e);
		}

		Empresa empresa = empresasdao.consultar(rfcEmpresa);

		for (Pago p : pagos) {
			p.setRfcEmpresa(empresa.getRFC());
			p.setRazonSocial(empresa.getRazonSocial());
			p.setRegistroPatronal(empresa.getRegistroPatronal());
			p.setCpLugarExpedicion(empresa.getDireccion().getCodigoPostal());
			pagosvo.add(new PagoVO(p, mapa.get(p.getIdEmpleado())));
		}

		return pagosvo;

	}

}
