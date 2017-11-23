package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Key;
import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.CatalogosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.PagosDAO;
import com.tikal.cacao.dao.impl.TipoDeduccionDAOImpl;
import com.tikal.cacao.dao.impl.TipoPercepcionDAOImpl;
import com.tikal.cacao.model.CatalogosContabilidadPropia;
import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.model.Perfil;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.ConceptoSATService;
import com.tikal.cacao.springController.requestObject.EmpresaConProductoOServicioRO;
import com.tikal.cacao.springController.viewObjects.AlertaPagosEmpresasVO;
import com.tikal.cacao.springController.viewObjects.AlertaPagosRegimenVO;
import com.tikal.cacao.springController.viewObjects.EmpresaVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

@Controller
@RequestMapping(value = { "/empresas" })
public class EmpresasController {

	@Autowired
	@Qualifier(value = "tipoPercepciondao")
	TipoPercepcionDAOImpl tPercepcionesDAO;

	@Autowired
	@Qualifier(value = "empresasdao")
	EmpresasDAO empresasdao;

	@Autowired
	UsuarioDAO usuariodao;

	@Autowired
	PerfilDAO perfildao;

	@Autowired
	BitacoraDAO bitacoradao;

	@Resource(name = "cpTipoPercepciondao")
	CatalogosDAO<ContabilidadPropiaTipoPercepcion, Long, String> cpTipoPercepcionDAO;

	@Resource(name = "cpTipoDeducciondao")
	CatalogosDAO<ContabilidadPropiaTipoDeduccion, Long, String> cpTipoDeduccionDAO;

	@Autowired
	@Qualifier(value = "tipoDeducciondao")
	TipoDeduccionDAOImpl tDeduccionesDAO;
	
	@Resource(name= "pagosdao")
	PagosDAO pagosdao;
	
	@Autowired
	ConceptoSATService conceptosSATService;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void addEmpresa(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		if (ServicioSesion.verificarPermiso(request, usuariodao, perfildao, 0)) {
			AsignadorDeCharset.asignar(request, response);
			//Empresa e = (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
			EmpresaConProductoOServicioRO requestObject = (EmpresaConProductoOServicioRO) JsonConvertidor.fromJson(json, EmpresaConProductoOServicioRO.class);
			Empresa e = requestObject.getEmpresa();
			// e.getDireccion().setEstado(JsonConvertidor.estadoFromJson(json));
			empresasdao.crear(e);
			response.getWriter().println(JsonConvertidor.toJson(empresasdao.consultar(e.getRFC())));

			Object[][] arregloProdServ = requestObject.getProductoOServicio();
			conceptosSATService.generarConceptos(e.getRFC(), arregloProdServ);
			

			String evento = "Se dio de alta a la empresa " + e.getNombre() + " con Raz&oacute;n Social: "
					+ e.getRazonSocial();
			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(request.getSession(), "Operacional", evento);
			bitacoradao.addReg(registroBitacora);
		} else {
			response.sendError(403);
		}
	}

	@RequestMapping(value = { "/find/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void findEmpresa(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfc)
			throws IOException {
		if (ServicioSesion.verificarPermiso(request, usuariodao, perfildao, 0)) {
		AsignadorDeCharset.asignar(request, response);
		Empresa e = empresasdao.consultar(rfc);
		List<Regimen> lista = e.getRegimenes();
		List<AlertaPagosRegimenVO> listaAlertas = new ArrayList<>();
		AlertaPagosRegimenVO alertaPRVO = null;
 		for (Regimen regimen : lista) {
 			String mensaje = null;
 			List<Pago> pagosRealizados = pagosdao.consultarPagosPorRegimen(regimen.getId(), new Date());
 			if (pagosRealizados != null) {
	 			if (pagosRealizados.size() == regimen.getIdEmpleados().size()) {
	 				mensaje = "";
	 			} else {
	 				mensaje = Util.alertarPagoNomina(regimen);
	 			}
 			} else 
 				mensaje = Util.alertarPagoNomina(regimen);
			alertaPRVO = new AlertaPagosRegimenVO();
			alertaPRVO.setRegimen(regimen);
			alertaPRVO.setAlerta(mensaje);
			listaAlertas.add(alertaPRVO);
		}
		Object[] res = new Object[2];
		res[0] = new EmpresaVO(e);
		res[1] = listaAlertas;
		response.getWriter().println(JsonConvertidor.toJson(res));
		}
	}

	@RequestMapping(value = {
			"/update" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void updateEmpresa(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {
		if (ServicioSesion.verificarPermiso(request, usuariodao, perfildao, 1)) {
			AsignadorDeCharset.asignar(request, response);
			EmpresaConProductoOServicioRO requestObject = (EmpresaConProductoOServicioRO) JsonConvertidor.fromJson(json, EmpresaConProductoOServicioRO.class);
			//Empresa e = (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
			Empresa e = requestObject.getEmpresa();
			empresasdao.actualizar(e);
			
			Object[][] arregloProdServ = requestObject.getProductoOServicio();
			conceptosSATService.generarConceptos(e.getRFC(), arregloProdServ);
			response.getWriter().println(JsonConvertidor.toJson(empresasdao.consultar(e.getRFC())));
		} else {
			response.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/delete/{rfc}" }, method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public void deleteEmpresa(HttpServletResponse response, HttpServletRequest request, @RequestBody String json,
			@PathVariable String rfc) throws IOException {
		empresasdao.eliminar(empresasdao.consultar(rfc));
	}

	@RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
	public void findAll(HttpServletResponse response, HttpServletRequest request) throws IOException {
		// if(ASDJ.checaSession(request, usuariodao,0);
		AsignadorDeCharset.asignar(request, response);
			List<Empresa> lista = empresasdao.consultarTodos();
			if (lista == null) {
				lista = new ArrayList<Empresa>();
			}
			List<AlertaPagosEmpresasVO> listaAlertaEmpr = new ArrayList<>();
			for (Empresa empresa : lista) {
				List<Regimen> regs = empresa.getRegimenes();
				String mensaje = "";
				for (Regimen regimen : regs) {
					mensaje = Util.alertarPagoNomina(regimen);
					if (!mensaje.equals("")) {
						mensaje = "Hay pagos pendientes";
						break;
					}
					else
						mensaje = "";
				}
				AlertaPagosEmpresasVO alertaPagoEmpresa = new AlertaPagosEmpresasVO();
				alertaPagoEmpresa.setEmpresa(empresa);
				alertaPagoEmpresa.setAlerta(mensaje);
				listaAlertaEmpr.add(alertaPagoEmpresa);	
			}
			response.getWriter().println(JsonConvertidor.toJson(listaAlertaEmpr));
		

		//
		//
		// AsignadorDeCharset.asignar(request, response);
		//
		// HttpSession s = request.getSession();
		// String n = (String) s.getAttribute("userName");
		// if (n == null) {
		// response.sendError(403);
		// } else {
		// Usuario usuario = usuariodao.consultarUsuario(n);
		// Perfil perfil = perfildao.consultarPerfil(usuario.getPerfil());
		// if (perfil.getPermisos()[0] == false) {
		// List<Empresa> lista = empresasdao.consultarTodos();
		// if (lista == null) {
		// lista = new ArrayList<Empresa>();
		// }
		// response.getWriter().println(JsonConvertidor.toJson(lista));
		// }
		// else{
		// response.sendError(403);
		// }
		// }
	}
	
	@RequestMapping(value = { "/getEmails/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void getEmails(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfc) {
		String[] emails = empresasdao.consultarEmails(rfc);
		try {
			response.getWriter().println(JsonConvertidor.toJson(emails));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*private void llenarCatálogos(String rfc) {
		List<EntTipoPercepcion> listSATPer = tPercepcionesDAO.consultarTodos(EntTipoPercepcion.class);
		List<ContabilidadPropiaTipoPercepcion> listContPropiaPer = new ArrayList<>();
		CatalogosContabilidadPropia<EntTipoPercepcion> cpTipoPer = null;
		for (EntTipoPercepcion satTipoPercepcion : listSATPer) {
			cpTipoPer = new ContabilidadPropiaTipoPercepcion();
			cpTipoPer.setClave(satTipoPercepcion.getClave());
			cpTipoPer.setDescripcion(satTipoPercepcion.getDescripcion());
			cpTipoPer.setLlaveAgrupadora(Key.create(satTipoPercepcion));
			cpTipoPer.setRfcEmpresa(rfc);
			listContPropiaPer.add((ContabilidadPropiaTipoPercepcion) cpTipoPer);
		}
		cpTipoPercepcionDAO.crear(listContPropiaPer);

		List<EntTipoDeduccion> listSATDed = tDeduccionesDAO.consultarTodos(EntTipoDeduccion.class);
		List<ContabilidadPropiaTipoDeduccion> listContPropiaDed = new ArrayList<>();
		CatalogosContabilidadPropia<EntTipoDeduccion> cpTipoDed = null;
		for (EntTipoDeduccion satTipoDeduccion : listSATDed) {
			cpTipoDed = new ContabilidadPropiaTipoDeduccion();
			cpTipoDed.setClave(satTipoDeduccion.getClave());
			cpTipoDed.setDescripcion(satTipoDeduccion.getDescripcion());
			cpTipoDed.setLlaveAgrupadora(Key.create(satTipoDeduccion));
			cpTipoDed.setRfcEmpresa(rfc);
			listContPropiaDed.add((ContabilidadPropiaTipoDeduccion) cpTipoDed);
		}
		cpTipoDeduccionDAO.crear(listContPropiaDed);
	}*/
}
