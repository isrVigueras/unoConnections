/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum Estado {
	@SerializedName("Aguascalientes")
	AGUASCALIENTES("Aguascalientes"),
	
	@SerializedName("Baja California")
	BAJA_CALIFORNIA("Baja California"),
	
	@SerializedName("Baja California Sur")
	BAJA_CALIFORNIA_SUR("Baja California Sur"),
	
	@SerializedName("Campeche")
	CAMPECHE("Campeche"),
	
	@SerializedName("Chiapas")
	CHIAPAS("Chiapas"),
	
	@SerializedName("Chihuahua")
	CHIHUAHUA("Chihuahua"),
	
	@SerializedName("Coahuila")
	COAHUILA("Coahuila"),
	
	@SerializedName("Colima")
	COLIMA("Colima"),
	
	@SerializedName("Ciudad de México")
	CIUDAD_DE_MEXICO("Ciudad de México"),
	
	@SerializedName("Durango")
	DURANGO("Durango"),
	
	@SerializedName("Estado de México")
	ESTADO_DE_MEXICO("Estado de México"),
	
	@SerializedName("Guanajuato")
	GUANAJUATO("Guanajuato"),
	
	@SerializedName("Guerrero")
	GUERRERO("Guerrero"),
	
	@SerializedName("Hidalgo")
	HIDALGO("Hidalgo"),
	
	@SerializedName("Jalisco")
	JALISCO("Jalisco"),
	
	@SerializedName("Michoacán")
	MICHOACAN("Michoacán"),
	
	@SerializedName("Morelos")
	MORELOS("Morelos"),
	
	@SerializedName("Nayarit")
	NAYARIT("Nayarit"),
	
	@SerializedName("Nuevo León")
	NUEVO_LEON("Nuevo León"),
	
	@SerializedName("Oaxaca")
	OAXACA("Oaxaca"),
	
	@SerializedName("Puebla")
	PUEBLA("Puebla"),
	
	@SerializedName("Querétaro")
	QUERETARO("Querétaro"),
	
	@SerializedName("Quintana Roo")
	QUINTANA_ROO("Quintana Roo"),
	
	@SerializedName("San Luis Potosí")
	SAN_LUIS_POTOSI("San Luis Potosí"),
	
	@SerializedName("Sinaloa")
	SINALOA("Sinaloa"),
	
	@SerializedName("Sonora")
	SONORA("Sonora"),
	
	@SerializedName("Tabasco")
	TABASCO("Tabasco"),
	
	@SerializedName("Tamaulipas")
	TAMAULIPAS("Tamaulipas"),
	
	@SerializedName("Tlaxcala")
	TLAXCALA("Tlaxcala"),
	
	@SerializedName("Veracruz")
	VERACRUZ("Veracruz"),
	
	@SerializedName("Yucatán")
	YUCATAN("Yucatán"),
	
	@SerializedName("Zacatecas")
	ZACATECAS("Zacatecas");
	
	private String brandname;
	
	private Estado(String brandname) {
		this.brandname = brandname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.brandname;
	}
	
	

}
