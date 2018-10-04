/**
 * 
 */
package com.tikal.cacao.util;

import java.util.Comparator;

import com.tikal.cacao.model.Deduccion;

/**
 * @author Tikal
 *
 */
public class DeduccionDescendigComparator implements Comparator<Deduccion> {

	@Override
	public int compare(Deduccion o1, Deduccion o2) {
		return o2.getTipo().compareTo(o1.getTipo());
	}

}
