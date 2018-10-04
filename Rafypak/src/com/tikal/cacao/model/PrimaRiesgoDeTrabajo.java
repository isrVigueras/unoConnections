/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class PrimaRiesgoDeTrabajo {
	
	@Id
	private long id;

	private final int v = 28;
	
	private double f;
	
	private double n;
	
	private int s;
	
	private double i;
	
	private int d;
	
	private final double m = 0.005;
	
	private boolean centroDeTrabajoAcreditado;
	
	private long idRegimen;

	public long getId() {
		return id;
	}
	
	/**
	 * @return el n&uacute;mero de trabajadores promedio expuestos al riesgo
	 */
	public double getN() {
		return n;
	}

	/**
	 * @param n el n&uacute;mero de trabajadores promedio expuestos al riesgo
	 */
	public void setN(double n) {
		this.n = n;
	}

	/**
	 * @return el total de los d&iacute;as subsidiados a causa de 
	 *  incapacidad temporal
	 */
	public int getS() {
		return s;
	}

	/**
	 * @param s el total de los d&iacute;as subsidiados a causa de 
	 *  incapacidad temporal
	 */
	public void setS(int s) {
		this.s = s;
	}

	/**
	 * @return la suma de los porcentajes de las incapacidades permanentes,
	 *  parciales y totales, divididos entre 100
	 */
	public double getI() {
		return i;
	}

	/**
	 * @param i la suma de los porcentajes de las incapacidades permanentes,
	 *  parciales y totales, divididos entre 100
	 */
	public void setI(double i) {
		this.i = i;
	}

	/**
	 * @return el n&uacute;mero de defuciones
	 */
	public int getD() {
		return d;
	}

	/**
	 * @param d el n&uacute;mero de defuciones
	 */
	public void setD(int d) {
		this.d = d;
	}

	/**
	 * Regresa {@code true} si el centro de trabajo cuenta con un sistema 
	 * de administraci&oacute;n y seguridad en el trabajo acreditado por
	 * la Secretar&iacute;a de Trabajo y Previsi&oacute;n Social. Regresa
	 * {@code false} en caso contrario
	 * @return 
	 */
	public boolean isCentroDeTrabajoAcreditado() {
		return centroDeTrabajoAcreditado;
	}

	/**
	 * @param centroDeTrabajoAcreditado the centroDeTrabajoAcreditado to set
	 */
	public void setCentroDeTrabajoAcreditado(boolean centroDeTrabajoAcreditado) {
		this.centroDeTrabajoAcreditado = centroDeTrabajoAcreditado;
	}

	/**
	 * @return la duraci&oacute;n promedio de vida activa de un individuo que no
	 * haya sido v&iacute;ctima de un accidente mortal o de incapacidad permanente total
	 */
	public int getV() {
		return v;
	}

	/**
	 * &Eacute;ste m&eacute;todo regresa el valor del factor de prima. Si isCentroDeTrabajoAcreditado() 
	 * regresa {@code true} el factor de prima tendr&aacute; un valor de 2.2, en caso contrario,
	 * el valor ser&aacute; de 2.3.
	 * 
	 * @return el factor de prima
	 */
	public double getF() {
		if (isCentroDeTrabajoAcreditado())
			f = 2.2;
		else 
			f = 2.3;
		return f;
	}

	/**
	 * @return la prima m&iacute;nima de riesgo
	 */
	public double getM() {
		return m;
	}

	/**
	 * &Eacute;ste m&eacute;todo regresa el identificador del {@link Regimen} al que
	 * corresponde esta instancia de {@link PrimaRiesgoDeTrabajo}
	 * @return el identificador del del objeto {@link Regimen}
	 */
	public long getIdRegimen() {
		return idRegimen;
	}

	/**
	 * &Eacute;ste m&eacute;todo establece el identificador del {@link Regimen} al que
	 * corresponde esta instancia de {@link PrimaRiesgoDeTrabajo}
	 * @param idRegimen el identificador del objeto {@link Regimen}
	 */
	public void setIdRegimen(long idRegimen) {
		this.idRegimen = idRegimen;
	}
	
	
}
