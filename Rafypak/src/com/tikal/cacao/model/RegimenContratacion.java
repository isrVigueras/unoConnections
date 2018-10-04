/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum RegimenContratacion {
	
	//cura temporal para compatibilidad con viejos registros
	SUELDOS_Y_SALARIOS("02","Sueldos y salarios"),
	
	@SerializedName("Sueldos")
	SUELDOS("02","Sueldos"),
	
	@SerializedName("Jubilados")
	JUBILADOS("03","Jubilados"),
	
	@SerializedName("Pensionados")
	PENSIONADOS("04","Pensionados"), 
	
	@SerializedName("Asimilados Miembros Sociedades Cooperativas de Producción")
	ASIMILADOS_MIEMBROS_SOCIEDADES_COOPERATIVAS_DE_PRODUCCION("05",
					"Asimilados Miembros Sociedades Cooperativas de Producción"), 
	
	@SerializedName("Asimilados Integrantes Sociedades Asociaciones Civiles")
	ASIMILADOS_INTEGRANTES_SOCIEDADES_ASOCIACIONES_CIVILES("06",
					"Asimilados Integrantes Sociedades Asociaciones Civiles"),
	
	@SerializedName("Asimilados Miembros consejos")
	ASIMILADOS_MIEMBROS_CONSEJOS("07",
					"Asimilados Miembros consejos"), 
	
	@SerializedName("Asimilados comisionistas")
	ASIMILADOS_COMISIONISTAS("08",
					"Asimilados comisionistas"), 
	
	@SerializedName("Asimilados Honorarios")
	ASIMILADOS_HONORARIOS("09",
				 	"Asimilados Honorarios"), 
	
	@SerializedName("Asimilados acciones")
	ASIMILADOS_ACCIONES("10",
				    "Asimilados acciones"),

	@SerializedName("Asimilados otros")
	ASIMILADOS_OTROS("11","Asimilados otros"),
	
	@SerializedName("Otro Regimen")
	OTRO_REGIMEN("99","Otro Regimen");
	
	private String brandname;
	
	private String clave;

	private RegimenContratacion(String clave,String brand) {
		this.brandname = brand;
		this.clave = clave;
	}

	@Override
	public String toString() {
		return brandname;
	}
	
	public String getClave() {
		return this.clave;
	}

}
