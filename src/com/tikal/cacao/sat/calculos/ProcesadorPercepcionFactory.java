/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tikal.cacao.util.Switcher;

/**
 * @author Tikal
 *
 */
@Component
public class ProcesadorPercepcionFactory {
	// antes static
	private Switcher<String, ProcesadorPercepcion> switcher;
	
	@Autowired
	@Qualifier("procesadorPSueldos")
	private ProcesadorPercepcion procesadorPSueldos;
	
	@Resource(name="procesadorPAguinaldo")
	private ProcesadorPercepcion procesadorPAguinaldo;
	
	@Resource(name="procesadorPGastosMDH")
	private ProcesadorPercepcion procesadorPGastosMDH;
	
	@Resource(name="procesadorPContribucionesPagadasPatron")
	private ProcesadorPercepcion procesadorPContribucionesPagadasPatron;
	
	@Resource(name="procesadorPPremiosPuntualidad")
	private ProcesadorPercepcion procesadorPPremiosPuntualidad;
	
	@Resource(name="procesadorPHorasExtra")
	private ProcesadorPercepcion procesadorPHorasExtra;
	
	@Resource(name="procesadorPSeguroVida")
	private ProcesadorPercepcion procesadorPPrimaSeguroVida;
	
	@Resource(name="procesadorPSeguroGMM")
	private ProcesadorPercepcion procesadorPSeguroGMM;
	
	@Resource(name="procesadorPCuotaSindical")
	private ProcesadorPercepcion procesadorPCuotaSindical;
	
	@Resource(name="procesadorPBecas")
	private ProcesadorPercepcion procesadorPBecas;
	
	@Resource(name="procesadorPPrimaDominical")
	private ProcesadorPercepcion procesadorPPrimaDominical;
	
	@Resource(name="procesadorPPrimaVacacional")
	private ProcesadorPercepcion procesadorPPrimaVacacional;
	
	@Resource(name="procesadorPReembolsoFuneral")
	private ProcesadorPercepcion procesadorPReembolsoFuneral;
	
	@Resource(name="procesadorPCuotasIMSSPagadasPatron")
	private ProcesadorPercepcion procesadorPCuotasIMSSPagadasPatron;
	
	@Resource(name="procesadorPComisiones")
	private ProcesadorPercepcion procesadorPComisiones;
	
	@Resource(name="procesadorPPremiosAsistencia")
	private ProcesadorPercepcion procesadorPremiosAsistencia;
	
	@Resource(name="procesadorPIngresosAsimilados")
	private ProcesadorPercepcion procesadorPIngresosAsimilados;
	
//	ProcesadorPercepcionFactory() {
//		initSwitcher();
//	}
	
	// antes static
	@PostConstruct
	private void initSwitcher() {
		switcher = new Switcher<String, ProcesadorPercepcion>();
		switcher.addCaseCommand("001", procesadorPSueldos);
		switcher.addCaseCommand("002", procesadorPAguinaldo);
		switcher.addCaseCommand("004", procesadorPGastosMDH);
		switcher.addCaseCommand("009", procesadorPContribucionesPagadasPatron);
		switcher.addCaseCommand("010", procesadorPPremiosPuntualidad);
		switcher.addCaseCommand("011", procesadorPPrimaSeguroVida);
		switcher.addCaseCommand("012", procesadorPSeguroGMM);
		switcher.addCaseCommand("013", procesadorPCuotaSindical);
		switcher.addCaseCommand("015", procesadorPBecas);
		switcher.addCaseCommand("019", procesadorPHorasExtra);
		switcher.addCaseCommand("020", procesadorPPrimaDominical);
		switcher.addCaseCommand("026", procesadorPReembolsoFuneral);
		switcher.addCaseCommand("027", procesadorPCuotasIMSSPagadasPatron);
		switcher.addCaseCommand("028", procesadorPComisiones);
		switcher.addCaseCommand("046", procesadorPIngresosAsimilados);
		switcher.addCaseCommand("049", procesadorPremiosAsistencia);
	}
	
	// antes static
	public ProcesadorPercepcion getProcesador(String tipo) {
		return switcher.getCase(tipo);
	}
	
//	public static void agregarProcesador(String tipo, ProcesadorPercepcion nuevoProcesador) {
//		switcher.addCaseCommand(tipo, nuevoProcesador);
//	}
}
