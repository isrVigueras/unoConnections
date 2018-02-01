/**
 * 
 */
package com.tikal.cacao.util;

import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.cacao.model.Catalogos;
import com.tikal.cacao.model.CatalogosContabilidadPropia;
import com.tikal.cacao.model.Conceptos;
import com.tikal.cacao.model.ContabilidadPropiaTipoDeduccion;
import com.tikal.cacao.model.ContabilidadPropiaTipoPercepcion;
import com.tikal.cacao.model.ContadorEmpleados;
import com.tikal.cacao.model.DatosEmisor;
import com.tikal.cacao.model.DeduccionAusentismo;
import com.tikal.cacao.model.DeduccionInfonavit;
import com.tikal.cacao.model.DomicilioCE;
import com.tikal.cacao.model.Emisor;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.EntTipoDeduccion;
import com.tikal.cacao.model.EntTipoPercepcion;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.model.IndicadorNomina;
import com.tikal.cacao.model.ListaDeClasesDeProdServ;
import com.tikal.cacao.model.Pago;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.model.PercepcionCuotaSindical;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.PercepcionPrimaDominical;
import com.tikal.cacao.model.PercepcionPrimaSeguroDeVida;
import com.tikal.cacao.model.PercepcionSubsidiosIncapacidad;
import com.tikal.cacao.model.Perfil;
import com.tikal.cacao.model.PrimaRiesgoDeTrabajo;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.model.TipoRegimenContratacion;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.reporte.ComplementoRenglon;
import com.tikal.cacao.reporte.ReporteRenglon;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;
import com.tikal.unoconnections.tralix.Datos;

/**
 * @author Tikal
 *
 */
@Component
public class EntityRegister {
	
	/**
	 * This constructor register all entities used in the application
	 */
	public EntityRegister() {
		ObjectifyService.register(Empleado.class);
		ObjectifyService.register(Empresa.class);
		ObjectifyService.register(Regimen.class);
		ObjectifyService.register(Factura.class);
		ObjectifyService.register(DeduccionInfonavit.class);
		registrar(DeduccionAusentismo.class);
		ObjectifyService.register(Pago.class);
		registrar(Emisor.class);
		registrar(DatosEmisor.class);
		registrar(Catalogos.class);
		registrar(EntTipoPercepcion.class);
		registrar(EntTipoDeduccion.class);
		registrar(TipoRegimenContratacion.class);
		registrar(CatalogosContabilidadPropia.class);
		registrar(ContabilidadPropiaTipoPercepcion.class);
		registrar(ContabilidadPropiaTipoDeduccion.class);
		registrar(IndicadorNomina.class);
		registrar(PercepcionHorasExtra.class);
		registrar(PercepcionPrimaSeguroDeVida.class);
		registrar(PercepcionCuotaSindical.class);
		registrar(PercepcionSubsidiosIncapacidad.class);
		registrar(PercepcionPrimaDominical.class);
		registrar(PrimaRiesgoDeTrabajo.class);
		ObjectifyService.register(Usuario.class);
		ObjectifyService.register(TarifaSubsidio.class);
		ObjectifyService.register(TarifaTrabajoRealizado.class);
		ObjectifyService.register(TarifaSemanal.class);
		ObjectifyService.register(TarifaDecenal.class);
		ObjectifyService.register(TarifaQuincenal.class);
		ObjectifyService.register(TarifaMensual.class);
		ObjectifyService.register(Perfil.class);
		ObjectifyService.register(RegistroBitacora.class);
		registrar(ContadorEmpleados.class);
		registrar(Conceptos.class);
		registrar(Serial.class);
		registrar(Imagen.class);
		registrar(ReporteRenglon.class);
		registrar(ComplementoRenglon.class);
		registrar(ListaDeClasesDeProdServ.class);
		registrar(FacturaVTT.class);
		registrar(Datos.class);
		registrar(DomicilioCE.class);
		registrar(PagosFacturaVTT.class);
	}
	
	private <T> void registrar(Class<T> clase) {
		ObjectifyService.register(clase);
	}

}
