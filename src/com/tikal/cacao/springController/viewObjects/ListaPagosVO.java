/**
 * 
 */
package com.tikal.cacao.springController.viewObjects;

import java.util.ArrayList;
import java.util.List;

import com.tikal.cacao.model.Pago;

/**
 * @author Tikal
 *
 */
public class ListaPagosVO {
	private List<PagoVO> lista;

	/**
	 * @return the lista
	 */
	public List<PagoVO> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<PagoVO> lista) {
		this.lista = lista;
	}
	
	public List<Pago> getListaPago() {
		List<Pago> listaPago = new ArrayList<Pago>();
		for (PagoVO p: lista) {
			listaPago.add(p.getPago());
		}
		return listaPago;
	}
	
	public void setListaPago(List<Pago> listaPago) {
		int i = 0;
		for (PagoVO pVO : lista) {
			pVO.setPago(listaPago.get(i));
			i++;
		}
	}
}
