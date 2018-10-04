package com.tikal.cacao.springController;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.tikal.cacao.dao.CatalogosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.IndicadoresNominaDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.dao.TarifasDAO;
import com.tikal.cacao.dao.impl.SerialDAOImp;
import com.tikal.cacao.dao.impl.TipoDeduccionDAOImpl;
import com.tikal.cacao.dao.impl.TipoPercepcionDAOImpl;
import com.tikal.cacao.dao.impl.TipoRegimenContratacionDAOImpl;
import com.tikal.cacao.model.CatalogosContabilidadPropia;
import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;

import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.model.IndicadorNomina;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.sat.calculos.CalculadoraNomina;
import com.tikal.cacao.sat.calculos.Nomina;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.ExcelDataExtractor;
import com.tikal.cacao.util.JsonConvertidor;


@Controller
@RequestMapping(value={"/prueba"})
public class PruebaController {
	
	@Autowired
	@Qualifier(value="tarifasdao")
	TarifasDAO tarifasDAO;
	
	@Autowired
	@Qualifier(value="tipoDeducciondao")
	TipoDeduccionDAOImpl tDeduccionesDAO;
	
	@Autowired
	@Qualifier(value="tipoPercepciondao")
	TipoPercepcionDAOImpl tPercepcionesDAO;
	
	@Autowired
	@Qualifier(value="tipoRegimenCdao")
	TipoRegimenContratacionDAOImpl tRegimenesContrDAO;
	
	@Resource(name="cpTipoPercepciondao")
	CatalogosDAO<ContabilidadPropiaTipoPercepcion, Long, String> cpTipoPercepcionDAO;
	
	@Resource(name="cpTipoDeducciondao")
	CatalogosDAO<ContabilidadPropiaTipoDeduccion, Long, String> cpTipoDeduccionDAO;
	
//	@Resource(name="empresasdao")
//	EmpresasDAO empresasDAO;
	
	@Resource(name="")
	RegimenesDAO regimendao;
	
	//Solo ejecutar una vez
	@RequestMapping(value = {"/crearSeriePrueba" }, method = RequestMethod.GET)
	public void crearSeriePruebaWS(HttpServletResponse response) throws IOException {
		SerialDAO serialDao = new SerialDAOImp();
		Serial serial = new Serial();
		serial.setRfc("AAA010101AAA");
		serial.setSerie("B");
		serial.setFolio(1);
		serialDao.guardar(serial);
		response.getWriter().println("La serie " + serial.getSerie() + "de prueba ha sido guardada correctamente");
	}
	
	@RequestMapping(value = {"/corregirInconsistencia" }, method = RequestMethod.GET)
	public void corregirInconsistenia(HttpServletResponse response) throws IOException {
		Regimen regimen = regimendao.consultar(5150112464502784L); // remplaza el rfc
		regimen.getIdEmpleados().remove(6);
		regimendao.actualizar(regimen);
		//Ref<Regimen> r = empresa.getRefRegimenes().remove(10); // reemplaza la posición
		//empresasDAO.actualizar(empresa);
		response.getWriter().println("Esquema actualizada con éxito! " + regimen.getNombre());
		//id empleado a eliminar 5754843859779584
		
	}
	
	@RequestMapping(value = {"/eliminarSATDeducciones" }, method = RequestMethod.GET)
	public void eliminarCatalogoSATDeducciones(HttpServletResponse response) throws IOException {
		List<EntTipoDeduccion> listSATDed = tDeduccionesDAO.consultarTodos(EntTipoDeduccion.class);
		List<Long> listIdsSATDed = new ArrayList<Long>();
		long id = 0L;
		for (EntTipoDeduccion satTipoDed : listSATDed) {
			id = satTipoDed.getId();
			listIdsSATDed.add(id);
		}
		tDeduccionesDAO.eliminar(EntTipoDeduccion.class, listIdsSATDed);
		response.getWriter().println("Deducciones eliminadas");
	}
	
	
	@RequestMapping(value = {"/guardarContabilidadPer" }, method = RequestMethod.GET)
	public void crearCatalogoPercepcionesCP(HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<EntTipoPercepcion> listSATPer = tPercepcionesDAO.consultarTodos(EntTipoPercepcion.class);
		List<ContabilidadPropiaTipoPercepcion> listContPropiaPer = new ArrayList<>();
		CatalogosContabilidadPropia<EntTipoPercepcion> cpTipoPer = null;
		for (EntTipoPercepcion satTipoPercepcion : listSATPer) {
			cpTipoPer = new ContabilidadPropiaTipoPercepcion();
			cpTipoPer.setClave(satTipoPercepcion.getClave());
			cpTipoPer.setDescripcion(satTipoPercepcion.getDescripcion());
			cpTipoPer.setLlaveAgrupadora(Key.create(satTipoPercepcion));
			cpTipoPer.setRfcEmpresa("COFE811212ABC");
			//cpTipoPer.setRfcEmpresa("TIKA070401AZ2");
			listContPropiaPer.add((ContabilidadPropiaTipoPercepcion) cpTipoPer);
		}
		cpTipoPercepcionDAO.crear(listContPropiaPer);
		response.getWriter().println("Las percepciones de la contabilidad de la empresa se guardaron exitosamente");
	}
	
	@RequestMapping(value = {"/guardarContabilidadDed" }, method = RequestMethod.GET)
	public void crearCatalogoDeduccionesCP(HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<EntTipoDeduccion> listSATDed = tDeduccionesDAO.consultarTodos(EntTipoDeduccion.class);
		List<ContabilidadPropiaTipoDeduccion> listContPropiaDed = new ArrayList<>();
		CatalogosContabilidadPropia<EntTipoDeduccion> cpTipoDed = null;
		for (EntTipoDeduccion satTipoDeduccion : listSATDed) {
			cpTipoDed = new ContabilidadPropiaTipoDeduccion();
			cpTipoDed.setClave(satTipoDeduccion.getClave());
			cpTipoDed.setDescripcion(satTipoDeduccion.getDescripcion());
			cpTipoDed.setLlaveAgrupadora(Key.create(satTipoDeduccion));
			cpTipoDed.setRfcEmpresa("COFE811212ABC");
			//cpTipoDed.setRfcEmpresa("TIKA070401AZ2");
			listContPropiaDed.add((ContabilidadPropiaTipoDeduccion) cpTipoDed);
		}
		cpTipoDeduccionDAO.crear(listContPropiaDed);
		response.getWriter().println("Las deducciones de la contabilidad de la empresa se guardaron exitosamente");
	}
	
	@RequestMapping(value = {"/cpPerdd/{idTipoSAT}" }, method = RequestMethod.GET)
	public void crearCPPercepcion(HttpServletResponse response, HttpServletRequest request, @PathVariable String idTipoSAT) throws IOException {
		EntTipoPercepcion satTipoPer = tPercepcionesDAO.consultarPorIndice(idTipoSAT, EntTipoPercepcion.class);
		ContabilidadPropiaTipoPercepcion cpTipoPer = new ContabilidadPropiaTipoPercepcion();
		cpTipoPer.setClave("101");
		cpTipoPer.setDescripcion("Sueldo");
		cpTipoPer.setLlaveAgrupadora( Key.create(satTipoPer) );
		cpTipoPercepcionDAO.crear(cpTipoPer);
		PrintWriter writer = response.getWriter();
		writer.println(JsonConvertidor.toJson(cpTipoPer));
	}
	
	@RequestMapping(value={"/guardarIndicadores" },method= RequestMethod.GET)
	public void guardarIndicadores(HttpServletResponse re) throws IOException {
		IndicadoresNominaDAO.guardar(new IndicadorNomina("Unidad de Medida y Actualización", 75.49));
		re.getWriter().println("Indicadores guardados");
	}
	
	@RequestMapping(value={"/cargarTipoPercepcion"},method= RequestMethod.GET)
	public void cargarCatalogos(HttpServletResponse re, HttpServletRequest req) throws IOException {
		AsignadorDeCharset.asignar(req, re);
		List<EntTipoPercepcion> listEntTipoP = tPercepcionesDAO.consultarTodos(EntTipoPercepcion.class);
		re.getWriter().println(JsonConvertidor.toJson(listEntTipoP));
	}
	
	@RequestMapping(value={"/guardarCatatalogos"},method= RequestMethod.GET)
	public void guardarCatalogos(HttpServletResponse re) throws IOException {
		ExcelDataExtractor.guardarCatalogos();
		re.getWriter().println("Los Catálogos se guardaron exitosamente");
	}
	
//	@RequestMapping(value={"/calcularPagos"},method= RequestMethod.GET)
//	public void calcularPagos(HttpServletResponse re) throws IOException {
//		Regimen regimen = crearRegimen();
//		List<Long> listaEmpleados = new ArrayList<Long>();
//		listaEmpleados.add(4644337115725824L);
//		regimen.setIdEmpleados(listaEmpleados);
//		PrintWriter writer = re.getWriter();
//		try {
//			List<Pago> pagos = Nomina.calcularPagos(regimen, tarifasDAO.queryT(regimen.getSueldo(), Util.obtenerClaseTarifaSubsidio(regimen.getFormaPago())));
//			for (Pago pago : pagos) {
//				writer.println(JsonConvertidor.toJson(pago));
//			}
//		} catch (RuntimeException e) {
//			writer.println(JsonConvertidor.toJson(e.getMessage()));
//		}
//		
//	}
	
	
	@RequestMapping(value={"/calcularISR/{periodo}/{baseGravable}"},method= RequestMethod.GET)
	public void calculoISR(HttpServletResponse re, @PathVariable Double baseGravable, @PathVariable String periodo) throws IOException {
		Class<? extends TarifaSubsidio> c;
		switch(periodo.toUpperCase()) {
		case "MENSUAL" :
			c = TarifaMensual.class;
			break;
		case "QUINCENAL" :
			c = TarifaQuincenal.class;
			break;
		case "DECENAL" :
			c = TarifaDecenal.class;
			break;
		case "SEMANAL" :
			c = TarifaSemanal.class;
			break;
		default :
			c = TarifaTrabajoRealizado.class;
		}
		TarifaSubsidio tarifa = tarifasDAO.queryT(baseGravable, c);
		Double resultado = CalculadoraNomina.calcularISR(baseGravable, tarifa, false);
		re.getWriter().println(JsonConvertidor.toJson(resultado));
	}
	
//	@RequestMapping(value={"/calcularPagoAguinaldo/{sueldo}"}, method=RequestMethod.GET)
//	public void calcularAguinaldo(HttpServletResponse re, @PathVariable Double sueldo) throws IOException {
//		Double aguinaldoBruto = Nomina.calcularAguinaldo(sueldo, 15, PeriodosDePago.MENSUAL);
//		Double impuestoAguinaldo = Nomina.calcularISRAguinaldo(
//				aguinaldoBruto, sueldo, tarifasDAO.queryT(aguinaldoBruto+sueldo, TarifaMensual.class),tarifasDAO.queryT(sueldo, TarifaMensual.class));
//		Double aguinaldoNeto = aguinaldoBruto - impuestoAguinaldo;
//		PrintWriter writer = re.getWriter();
//		writer.println("Aguinaldo Bruto: " + JsonConvertidor.toJson(aguinaldoBruto));
//		writer.println("ISR al Aguinaldo : " + JsonConvertidor.toJson(impuestoAguinaldo));
//		writer.println("Aguinaldo Neto: " + JsonConvertidor.toJson(aguinaldoNeto));
//	}
	
//	@RequestMapping(value={"/calcularPrimaDominical"}, method=RequestMethod.GET,
//			params = {"sueldo", "periodoDePago", "numDomingosLaborados"})
//	public void calcularPrimaDominical(HttpServletResponse re, 
//			@RequestParam("sueldo") double sueldo,
//			@RequestParam("periodoDePago") String periodo,
//			@RequestParam("numDomingosLaborados") int numDomingosLaborados) throws IOException {
//		
//		double[] primaDominical = Nomina.calcularPrimaDominical(sueldo, PeriodosDePago.valueOf(periodo.toUpperCase()), numDomingosLaborados);
//		re.getWriter().println(JsonConvertidor.toJson(primaDominical));
//	}
	
//	@RequestMapping(value={"/calcularPrimaVacacional/{fechaIngreso}/{sueldo}"}, method=RequestMethod.GET)
//	public void calcularPrimaVacacional(HttpServletResponse re, @PathVariable String fechaIngreso, @PathVariable Double sueldo) throws IOException, ParseException {
//		Double primaVacacional = Nomina.calcularPrimaVacacional(sueldo, fechaIngreso);			
//		re.getWriter().println(JsonConvertidor.toJson(primaVacacional));
//	}
	
	
	@RequestMapping(value={"/getEmpleados"},method= RequestMethod.GET)
	public void getEmpleados(HttpServletResponse re) throws IOException {
		List<Empleado> empleados = ofy().load().type(Empleado.class).list();
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(empleados));
	}
	
	@RequestMapping(value={"/eliminarNull"},method= RequestMethod.GET)
	public void modificarRegimen() {
		Regimen regimen = ofy().load().type(Regimen.class).id(5629499534213120L).now();
		regimen.getIdEmpleados().remove(0);
		ofy().save().entity(regimen).now();
	}
	
	
	@RequestMapping(value={"/prueba"},method= RequestMethod.GET)
	public void prueba(HttpServletResponse re) throws IOException{
		re.getWriter().println("prueba exitosa");
	}
	
	
	private class Hilo extends Thread{
		@Override
		public void run(){
			String[] tarifas={"Semanal","Decenal","Quincenal","Mensual"};
			for(String t:tarifas){
				ExcelDataExtractor.guardarTarifa(t);
			}
		}
	}
	
	
	@RequestMapping(value={"/pruebaGuardarTarifa/{nombreTarifa}"},method= RequestMethod.GET)
	public void pruebaGuardarTarifa(HttpServletResponse re, @PathVariable String nombreTarifa) throws IOException {
		ExcelDataExtractor.guardarTarifa(nombreTarifa);
//		Hilo h = new Hilo();
//		h.start();
//		re.getWriter().println("Se está corriendo :D");
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaT/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaT(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaTrabajoRealizado tarifa = ExcelDataExtractor.recuperarT(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaS/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaS(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaSemanal tarifa = ExcelDataExtractor.recuperarS(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaD/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaD(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaDecenal tarifa = ExcelDataExtractor.recuperarD(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaQ/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaQ(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaQuincenal tarifa = ExcelDataExtractor.recuperarQ(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaM/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaM(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaMensual tarifa = ExcelDataExtractor.recuperarM(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
		
}
