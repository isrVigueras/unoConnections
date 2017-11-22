/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tikal.cacao.util.Switcher;

/**
 * @author Tikal
 *
 */
@Component
public class ProcesadorDeduccionFactory {
	private Switcher<String, ProcesadorDeduccion> switcher;
	
	@Resource(name="procesadorDSeguridadSocial")
	private ProcesadorDeduccion procesadorDSeguridadSocial;
	
	@Resource(name="procesadorDISR")
	private ProcesadorDeduccion procesadorDISR;
	
	@Resource(name="procesadorDOtros")
	private ProcesadorDeduccion procesadorDOtros;

	
	@Resource(name="procesadorDIncapacidad")
	private ProcesadorDeduccion procesadorDIncapacidad;
	
	@Resource(name="procesadorDAusentismo")
	private ProcesadorDeduccion procesadorDAusentismo;
		
	@PostConstruct
	private void initSwitcher() {
		switcher = new Switcher<String, ProcesadorDeduccion>();
		switcher.addCaseCommand("001", procesadorDSeguridadSocial);
		switcher.addCaseCommand("002", procesadorDISR);
		switcher.addCaseCommand("004", procesadorDOtros);
		switcher.addCaseCommand("006", procesadorDIncapacidad);
		switcher.addCaseCommand("020", procesadorDAusentismo);
	}
	
	public ProcesadorDeduccion getProcesador(String tipo) {
		return switcher.getCase(tipo);
	}
}
