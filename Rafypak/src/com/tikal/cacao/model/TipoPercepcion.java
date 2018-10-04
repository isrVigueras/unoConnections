/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum TipoPercepcion {
	@SerializedName("Sueldos, Salarios Rayas y Jornales")
	SUELDOS_SALARIOS_RAYAS_JORNALES("Sueldos Salarios Rayas Jornales", true),
	
	@SerializedName("Gratificaci�n Anual")
	GRATIFICACION_ANUAL_AGUINALDO("Gratificacion Anual Aguinaldo", true),
	
	@SerializedName("Participaci�n de los trabajadores en las Utilidades PTU")
	PARTICIPACION_DE_LOS_TRABAJADORES_EN_LAS_UTILIDADES_PTU("Participacion de los Trabajadores en las Utilidades PTU", true), 
	
	@SerializedName("Fondo de Ahorro")
	REEMBOLSO_GASTOS_MEDICOS_DENTALES_Y_HOSPITALARIOS("Reembolso Gastos Medicos Dentales y Hospitalarios", false),
	
	@SerializedName("Caja de Ahorro")
	FONDO_DE_AHORRO("Fondo de Ahorro", false),
	
	@SerializedName("Caja de Ahorro")
	CAJA_DE_AHORRO("Caja de Ahorro", false),
	
	@SerializedName("Contribuciones a Cargo del Trabajador Pagadas por el Patr�n")
	CONTRIBUCIONES_A_CARGO_DEL_TRABAJADOR_PAGADAS_POR_EL_PATRON("Contribuciones a Cargo del Trabajador Pagadas por el Patron", false),
	
	@SerializedName("Premios por puntualidad")
	PREMIOS_POR_PUNTUALIDAD("Premios por puntualidad", true),
	
	@SerializedName("Prima de Seguro de vida")
	PRIMA_DE_SEGURO_DE_VIDA("Prima de Seguro de vida", true),
	
	@SerializedName("Seguro de Gastos M�dicos Mayores")
	SEGURO_DE_GASTOS_MEDICOS_MAYORES("Seguro de Gastos Medicos Mayores", false),
	
	@SerializedName("Cuotas Sindicales Pagadas por el Patr�n")
	CUOTAS_SINDICALES_PAGADAS_POR_EL_PATRON("Cuotas Sindicales Pagadas por el Patron", false),
	
	@SerializedName("Subsidios por incapacidad")
	SUBSIDIOS_POR_INCAPACIDAD("Subsidios por incapacidad", false),
	
	@SerializedName("Becas para Trabajadore y/o hijos")
	BECAS_PARA_TRABAJADORES_Y_O_HIJOS("Becas para trabajadores y o hijos", false),
	
	@SerializedName("Otros")
	OTROS("Otros", false), // para este valor no esta especificado si grava o exenta al ISR
	
	@SerializedName("Subsidios para el empleo")
	SUBSIDIOS_PARA_EL_EMPLEO("Subsidios para el empleo", false),
	
	@SerializedName("Horas extra")
	HORAS_EXTRA("Horas extra", true),
	
	@SerializedName("Prima Dominical")
	PRIMA_DOMINICAL("Prima dominical", true),
	
	@SerializedName("Prima Vacacional")
	PRIMA_VACACIONAL("Prima vacacional", true),
	
	@SerializedName("Prima por antig�edad")
	PRIMA_POR_ANTIGUEDAD("Prima por antiguedad", true),
	
	@SerializedName("Pagos por Separaci�n")
	PAGOS_POR_SEPARACION("Pagos por separacion", true),
	
	@SerializedName("Seguro de Retiro")
	SEGURO_DE_RETIRO("Seguro de retiro", true),
	
	@SerializedName("Indemnizaciones")
	INDEMNIZACIONES("Indemnizaciones", false),
	
	@SerializedName("Reembolso por funeral")
	REEMBOLSO_POR_FUNERAL("Reembolso por funeral", false),
	
	@SerializedName("Cuotas de seguridad social pagadas por el patr�n")
	CUOTAS_DE_SEGURIDAD_SOCIAL_PAGADAS_POR_EL_PATRON("Cuotas de seguridad social pagadas por el patron", false),
	
	@SerializedName("Comisiones")
	COMISIONES("Comisiones", true),
	
	@SerializedName("Vales de despensa")
	VALES_DE_DESPENSA("Vales de despensa", true),
	
	@SerializedName("Vales de restaurante")
	VALES_DE_RESTAURANTE("Vales de restaurante", true),
	
	@SerializedName("Vales de gasolina")
	VALES_DE_GASOLINA("Vales_de_gasolina", false), // esta exento s�lo si est�n a nombre de la dependencia
	
	@SerializedName("Vales de Ropa")
	VALES_DE_ROPA("Vales_de_ropa", true),
	
	@SerializedName("Ayuda para renta")
	AYUDA_PARA_RENTA("Ayuda para renta", true),
	
	@SerializedName("Ayuda para art�culos escolares")
	AYUDA_PARA_ARTICULOS_ESCOLARES("Ayuda para articulos escolares", true),
	
	@SerializedName("Ayuda para anteojos")
	AYUDA_PARA_ANTEOJOS("Ayuda para anteojos", true),
	
	@SerializedName("Ayuda para transporte")
	AYUDA_PARA_TRANSPORTE("Ayuda para transporte", true), // si es veh�culo de la empresa se exenta
	
	@SerializedName("Ayuda para gastos de funeral")
	AYUDA_PARA_GASTOS_DE_FUNERAL("Ayuda para gastos de funeral", false),
	@
	SerializedName("Otros ingresos por salarios")
	OTROS_INGRESOS_POR_SALARIOS("Otros ingresos por salarios", false), // para este valor no esta especificado si grava o exenta al ISR
	
	@SerializedName("Jubilaciones, pensiones o haberes de retiro")
	JUBILACIONES_PENSIONES_O_HABERES_DE_RETIRO("Jubilaciones pensiones o haberes de retiro", true),
	
	@SerializedName("Jubilaciones, pensiones o haberes de retiro en parcialidades")
	JUBILACIONES_PENSIONES_O_HABERES_DE_RETIRO_PARCIALIDADES("Jubilaciones, pensiones o haberes de retiro en parcialidades", true),
	
	@SerializedName("Ingresos en acciones o t�tulos valor que representan bienes")
	INGRESOS_ACCIONES_TITULOS_VALOR_QUE_REPRESENTAN_BIENES("Ingresos en acciones o t�tulos valor que representan bienes", false),
	
	@SerializedName("Ingresos asimilados a salarios")
	INGRESOS_ASIMILADOS_A_SALARIOS("Ingresos asimilados a salarios", false),
	
	@SerializedName("Alimentaci�n")
	ALIMENTACION("Alimentaci�n", false),
	
	@SerializedName("Habitaci�n")
	HABITACION("Habitaci�n", false),
	
	@SerializedName("Premios por asistencia")
	PREMIOS_POR_ASISTENCIA("Premios por asistencia", true),
	
	// en la versi�n para CDFI 3.3 para 2017 ya no se incluyen los tipos de percepciones siguientes
	// para los valores siguientes a�n no se determina si gravan o no para ISR
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_PROPIOS(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos propios", false),
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_FEDERALES(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos federales", false),
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_PROPIOS_Y_FEDERALES(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos propios y federales", false);
	
	
	private String brandname;
	
	/**
	 * Este valor indica si este <tt>TipoPercepcion</tt>
	 * grava para ISR
	 */
	private boolean gravable;

	/**
	 * @param brandname
	 */
	private TipoPercepcion(String brandname, boolean gravable) {
		this.brandname = brandname;
		this.gravable = gravable;
	}
	
		
	/**
	 * @return the gravable
	 */
	public boolean isGravable() {
		return gravable;
	}


	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return brandname;
	} 
	
	
}
