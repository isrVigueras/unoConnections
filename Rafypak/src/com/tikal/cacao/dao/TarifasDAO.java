package com.tikal.cacao.dao;

import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;

/**
 * @author Tikal
 *
 */
public interface TarifasDAO {
	
	/**
	 * 
	 * @param tarifa The <tt>TarifaSubsidio</tt> subclass to update
	 */
	public void actualizar(TarifaSubsidio tarifa);
	
	
	/**
	 * 
	 * @param monto The base amount to apply <tt>t</tt> tax rate
	 * @param t indicates the <tt>Class</tt> of the tax rate
	 * @return a tax rate for the <tt>monto</tt>
	 */
	public <T extends TarifaSubsidio> T queryT(Double monto, Class<T> t);
}
