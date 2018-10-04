/**
 * 
 */
package com.tikal.cacao.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tikal
 *
 */
public enum Bancos {
	BANAMEX("002"),
	BANCOMEXT("006"),
	BANOBRAS("009"),
	@SerializedName("BBVA BANCOMER")
	BBVA_BANCOMER("012"),
	SANTANDER("014"),
	BANJERCITO("019"),
	HSBC("021"),
	BAJIO("030"),
	IXE("032"),
	INBURSA("036"),
	INTERACCIONES("037"),
	MIFEL("042"),
	SCOTIABANK("044"),
	BANREGIO("059"),
	INVEX("059"),
	BANSI("060"),
	AFIRME("062"),
	BANORTE("072"),
	@SerializedName("THE ROYAL BANK")
	THE_ROYAL_BANK("102"),
	@SerializedName("AMERICAN EXPRESS")
	AMERICAN_EXPRESS("103"),
	BAMSA("106"),
	TOKYO("108"),
	@SerializedName("JP MORGAN")
	JP_MORGAN("110"),
	BMONEX("112"),
	@SerializedName("VE PORMAS")
	VE_POR_MAS("113"),
	ING("116"),
	DEUTSCHE("124"),
	@SerializedName("CREDIT SUISSE")
	CREDIT_SUISSE("126"),
	AZTECA("127"),
	AUTOFIN("128"),
	BARCLAYS("129"),
	COMPARTAMOS("130"),
	@SerializedName("BANCO_FAMSA")
	BANCO_FAMSA("131"),
	BMULTIVA("132"),
	ACTINVER("133"),
	@SerializedName("WAL-MART")
	WAL_MART("134"),
	NAFIN("135"),
	INTERBANCO("136"),
	BANCOPEL("137"),
	@SerializedName("ABC_CAPITAL")
	ABC_CAPITAL("138"),
	@SerializedName("UBS BANK")
	UBS_BANK("139"),
	CONSUBANCO("140"),
	VOLKSWAGEN("141"),
	CIBANCO("143"),
	BBASE("145"),
	BANSEFI("166"),
	@SerializedName("HIPOTECARIA FEDERAL")
	HIPOTECARIA_FEDERAL("168"),
	MONEXCB("600"),
	GMB("601"),
	MASARI("602"),
	VALUE("605"),
	ESTRUCTURADORES("606"),
	TIBER("607"),
	VECTOR("608"),
	@SerializedName("B&B")
	ByB("610"),
	ACCIVAL("614"),
	@SerializedName("MERRILL LYNCH")
	MERRILL_LYNCH("615"),
	FINAMEX("616"),
	VALMEX("617"),
	UNICA("618"),
	MAPFRE("619"),
	PROFUTURO("620"),
	@SerializedName("JP MORGAN")
	CB_ACTINVER("621"),
	OACTIN("622"),
	SKANDIA_VIDA("623"), // Skandia Vida
	CBDEUTSCHE("626"),
	ZURICH("627"),
	ZURICHVI("628"),
	@SerializedName("SU CASITA")
	SU_CASITA("629"),
	@SerializedName("CB INTERCAM")
	CB_INTERCAM("630"),
	@SerializedName("CI BOLSA")	
	CI_BOLSA("631"),
	@SerializedName("BULLTICK CB")
	BULLTICK_CB("632"),
	STERLING("633"),
	FINCOMUN("634"),
	@SerializedName("HDI SEGUROS")
	HDI_SEGUROS("636"),
	ORDER("637"),
	AKALA("638"),
	@SerializedName("CB JPMORGAN")
	CB_JPMORGAN("640"),
	REFORMA("642"),
	STP("646"),
	TELECOMM("647"),
	EVERCORE("648"),
	SKANDIA("649"), // Skandia Operadora de Seguros
	SEGMTY("651"),
	ASEA("652"),
	KUSPIT("653"),
	SOFIEXPRESS("655"),
	UNAGRA("656"),
	@SerializedName("OPCIONES EMPRESARIALES DEL NOROESTE")
	OPCIONES_EMPRESARIALES_DEL_NOROESTE("659"),
	CLS("901"),
	INDEVAL("902"),
	LIBERTAD("670");
	
	private String clave;
	
	private Bancos(String clave) {
		this.clave = clave;
	}
	
	public String getClave() {
		return clave;
	}
	
	
}
