/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Subclass;

/**
 * @author Tikal
 *
 */
@Subclass(index = true)
public class EntTipoDeduccion extends Catalogos {

	@Override
	public boolean equals(Object obj) {
		EntTipoDeduccion etp = (EntTipoDeduccion) obj;
		if ( this.getId().equals(etp.getId()) &&
			 this.getClave().equals(etp.getClave()) &&
			 this.getDescripcion().equals(etp.getDescripcion()) ) {
			return true;
		}
		return super.equals(obj);
	}
}
