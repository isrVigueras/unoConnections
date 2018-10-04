package com.tikal.cacao.springController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.ConceptosDAO;
import com.tikal.cacao.model.Concepto;
import com.tikal.cacao.model.Conceptos;
import com.tikal.cacao.model.orm.ProductoOServicio;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.ConceptoSATService;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/conceptos" })
public class ConceptoController {

	@Autowired
	ConceptosDAO conceptosdao;

	@Autowired
	UsuarioDAO usuariodao;

	@Autowired
	PerfilDAO perfildao;

	@Autowired
	ConceptoSATService conceptosSATService;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 12)) {
			AsignadorDeCharset.asignar(re, rs);
			Conceptos e = (Conceptos) JsonConvertidor.fromJson(json, Conceptos.class);
			conceptosdao.add(e.getRfc(), e.getConceptos().get(0));
		} else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/addMultiple/{rfc}" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void addMultiple(HttpServletRequest re, HttpServletResponse rs, @RequestBody String cadena,
			@PathVariable String rfc) throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 12)) {
			AsignadorDeCharset.asignar(re, rs);
			String[] conceptos = cadena.split("\n");
			List<Concepto> lista = new ArrayList<Concepto>();

			for (int i = 1; i < conceptos.length; i++) {
				String c = conceptos[i];
				String[] values = c.split("\t");
				// String[] values =
				// c.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				// Concepto con = new Concepto();
				// con.setNoIdentificacion(values[1]);
				// con.setDescripcion(values[3].replace("|", ",").replace("\"",
				// ""));
				// con.setUnidad(values[4]);
				// if (values.length > 5) {
				// values[5] = values[5].replace("$", "").trim();
				// values[5] = values[5].replace("\"", "").trim();
				// values[5] = values[5].replace(",", "").trim();
				// if (values[5].length() > 0) {
				// con.setValorUnitario(Float.parseFloat(values[5]));
				// }
				// }
				Concepto con = new Concepto();
				con.setClaveProdServ(values[4]);
				con.setClaveUnidad(values[2]);
				con.setDescripcion(values[6]);
				con.setDescripcionSAT(values[5]);
				con.setDescripcionUnidadSAT(values[3]);
				con.setNoIdentificacion(values[0]);
				con.setUnidad(values[3]);
				if (values.length > 8) {
					if (!values[8].isEmpty()) {
						con.setUnidadAduana(values[8]);
					}
				}
				lista.add(con);
			}
			conceptosdao.add(rfc, lista);
			rs.getWriter().print(JsonConvertidor.toJson(lista));
		} else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/delete/{ind}" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void remove(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json, @PathVariable int ind)
			throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 12)) {
			AsignadorDeCharset.asignar(re, rs);
			Conceptos e = (Conceptos) JsonConvertidor.fromJson(json, Conceptos.class);
			conceptosdao.eliminar(e.getRfc(), ind);
		} else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = {
			"/edit/{indice}" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void edit(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json, @PathVariable int indice)
			throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 12)) {
			AsignadorDeCharset.asignar(re, rs);
			Conceptos e = (Conceptos) JsonConvertidor.fromJson(json, Conceptos.class);
			conceptosdao.actualizar(e.getRfc(), indice, e.getConceptos().get(0));
		} else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = { "/cargar/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void cargar(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc) throws IOException {
		if (ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 12)
				|| ServicioSesion.verificarPermiso(re, usuariodao, perfildao, 11)) {
			AsignadorDeCharset.asignar(re, rs);
			Conceptos c = conceptosdao.consultar(rfc);
			rs.getWriter().println(JsonConvertidor.toJson(c));
		} else {
			rs.sendError(403);
		}
	}

	@RequestMapping(value = { "/alv/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void alv(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc) throws IOException {
		AsignadorDeCharset.asignar(re, rs);
		conceptosdao.alv(rfc);
	}

	@RequestMapping(value = { "/getProdServ/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
	public void getProductosServiciosSAT(HttpServletRequest re, HttpServletResponse rs, @PathVariable String rfc)
			throws IOException {
		AsignadorDeCharset.asignar(re, rs);
		List<ProductoOServicio> listaPD = conceptosSATService.cargarProdServ(rfc);
		rs.getWriter().println(JsonConvertidor.toJson(listaPD));
	}
}
