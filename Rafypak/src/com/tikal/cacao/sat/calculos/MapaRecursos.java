/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;

/**
 * @author Tikal
 *
 */
@Component
public class MapaRecursos {
	
	private Map<String,Double> mapa;
	
	private TarifaSubsidio tarifa;
	
	private boolean asegurado;
	
	private boolean trabajaDomingos;
	
	private int diasAusentismo;
	
	private int diasIncapacidad;
	
	//private boolean conceptosIntegranSBC;
	
	public MapaRecursos() {
		this.mapa = new HashMap<String,Double>();
		this.mapa.put("totalAPagar", 0.0);
		this.mapa.put("sueldo", 0.0);
		this.mapa.put("ingresoCotizable", 0.0);
		this.mapa.put("ingresoGravable", 0.0);
		this.mapa.put("sbc", 0.0);
		this.mapa.put("ultimoSBC", 0.0);
		this.mapa.put("montoPremioASBC", 0.0);
		this.mapa.put("tiempoExtraParaSBC", 0.0);
		this.mapa.put("previsionSocial", 0.0); // se usa para el ajuste anual
	}
	
	public void setSueldo(double sueldo) {
		this.mapa.put("sueldo", sueldo);
	}
	
	public double getSueldo() {
		return this.mapa.get("sueldo");
	}
	
	public void setTotalAPagar(double totalAPagar) {
		this.mapa.put("totalAPagar", totalAPagar);
	}
	
	public double getTotalAPagar() {
		return this.mapa.get("totalAPagar");
	}
	
	public void setIngresoGravable(double ingresoGravable) {
		this.mapa.put("ingresoGravable", ingresoGravable);
	}
	
	public double getIngresoGravable() {
		return this.mapa.get("ingresoGravable");
	}
	
	public void setIngresoCotizable(double ingresoGravable) {
		this.mapa.put("ingresoCotizable", ingresoGravable);
	}
	
	public double getIngresoCotizable() {
		return this.mapa.get("ingresoCotizable");
	}
	
	public void setSBC(double sbc) {
		this.mapa.put("sbc", sbc);
	}
	
	public double getSBC() {
		return this.mapa.get("sbc");
	}
	
	public void setUltimoSBC(double ultimoSBC) {
		this.mapa.put("ultimoSBC", ultimoSBC);
	}
	
	public double getUltimoSBC() {
		return this.mapa.get("ultimoSBC");
	}
	
	public void setMontoPremioASBC(double montoPremioASBC) {
		this.mapa.put("montoPremioASBC", montoPremioASBC);
	}
	
	public double getMontoPremioASBC() {
		return this.mapa.get("montoPremioASBC");
	}
	
	public double getTiempoExtraParaSBC() {
		return this.mapa.get("tiempoExtraParaSBC");
	}
	
	public void setTiempoExtraParaSBC(double tiempoExtraParaSBC) {
		this.mapa.put("tiempoExtraParaSBC", tiempoExtraParaSBC);
	}
	
	public double getPrevisionSocial() {
		return this.mapa.get("previsionSocial");
	}
	
	public void setPrevisionSocial(double previsionSocial) {
		this.mapa.put("previsionSocial", previsionSocial);
	}
	
	public TarifaSubsidio getTarifa() {
		return tarifa;
	}
	
	public void setTarifa(TarifaSubsidio tarifa) {
		this.tarifa = tarifa;
	}

	public boolean isAsegurado() {
		return asegurado;
	}

	public void setAsegurado(boolean esAsegurado) {
		this.asegurado = esAsegurado;
	}

	public boolean isTrabajaDomingos() {
		return trabajaDomingos;
	}

	public void setTrabajaDomingos(boolean trabajaDomingos) {
		this.trabajaDomingos = trabajaDomingos;
	}

	public int getDiasAusentismo() {
		return diasAusentismo;
	}

	public void setDiasAusentismo(int diasAusentismo) {
		this.diasAusentismo = diasAusentismo;
	}

	public int getDiasIncapacidad() {
		return diasIncapacidad;
	}

	public void setDiasIncapacidad(int diasIncapacidad) {
		this.diasIncapacidad = diasIncapacidad;
	}

    

}
