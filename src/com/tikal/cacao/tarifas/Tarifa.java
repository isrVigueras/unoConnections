/**
 * 
 */
package com.tikal.cacao.tarifas;

/**
 * @author Tikal
 * Esta clase contiene los campos que son comunes a las Tarifas del numeral 6 del rubro B
 * y a los numerales 1 y 2 del rubro C del Anexo 8 de la Resoluci&oacute;n Miscel&aacute;nea Fiscal
 */
public abstract class Tarifa {
	
	double limiteInferior;
	
	double limiteSuperior;
	
	double cuotaFija;
	
	/**
	 * Por ciento para aplicarse sobre el excedente del limite inferior
	 */
	double porcentajeDelImpuesto;
}
