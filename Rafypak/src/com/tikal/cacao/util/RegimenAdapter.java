/**
 * 
 */
package com.tikal.cacao.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionAusentismo;
import com.tikal.cacao.model.DeduccionIncapacidad;
import com.tikal.cacao.model.DeduccionInfonavit;
import com.tikal.cacao.model.DeduccionPensionAlimenticia;
import com.tikal.cacao.model.ModalidadDescuento;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionCuotaSindical;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.PercepcionPrimaDominical;
import com.tikal.cacao.model.PercepcionPrimaSeguroDeVida;
import com.tikal.cacao.model.PercepcionSubsidiosIncapacidad;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.RegimenContratacion;
import com.tikal.cacao.model.RiesgoPuesto;
import com.tikal.cacao.model.TipoDeduccion;
import com.tikal.cacao.model.TipoIncapacidad;
import com.tikal.cacao.model.TipoPercepcion;
import com.tikal.cacao.model.clausulasEnum.BeneficiariosSeguroDeVida;
import com.tikal.cacao.model.clausulasEnum.ContratanteSeguroDeVida;
import com.tikal.cacao.model.clausulasEnum.RiesgosAmparadosPorSeguroDeVida;
import com.tikal.cacao.springController.viewObjects.RegimenVO;

/**
 * @author Ismael
 *
 */
public class RegimenAdapter extends TypeAdapter<RegimenVO> {
	Map<String, TipoPercepcion> nameToConstantP = new HashMap<String, TipoPercepcion>();
	Map<String, TipoDeduccion> nameToConstantD = new HashMap<String, TipoDeduccion>();
	Map<String, ModalidadDescuento> nameToConstantM = new HashMap<String, ModalidadDescuento>();
	Map<String, BeneficiariosSeguroDeVida> mapaBeneficiariosSeguroDeVida = new HashMap<String, BeneficiariosSeguroDeVida>();
	Map<String, ContratanteSeguroDeVida> mapaContratanteSeguroDeVida = new HashMap<String, ContratanteSeguroDeVida>();
	Map<String, RiesgosAmparadosPorSeguroDeVida> mapaRiesgosSeguroDeVida = new HashMap<String, RiesgosAmparadosPorSeguroDeVida>();
	Map<String, TipoIncapacidad> mapaTipoIncapacidad = new HashMap<String, TipoIncapacidad>();
	
	Map<String, RegimenContratacion> mapaRegimenContratacion = new HashMap<String, RegimenContratacion>();
	Map<String, RiesgoPuesto> mapaRiesgoPuesto = new HashMap<String, RiesgoPuesto>();
	
	public RegimenAdapter() {
		try {
			for(TipoPercepcion tipo : TipoPercepcion.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = TipoPercepcion.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				nameToConstantP.put(tipoName, tipo);
			}
			
			for(TipoDeduccion tipo : TipoDeduccion.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = TipoDeduccion.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				nameToConstantD.put(tipoName, tipo);
			}
			
			for(ModalidadDescuento tipo : ModalidadDescuento.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = ModalidadDescuento.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				nameToConstantM.put(tipoName, tipo);
			}
			
			for(BeneficiariosSeguroDeVida tipo : BeneficiariosSeguroDeVida.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = BeneficiariosSeguroDeVida.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaBeneficiariosSeguroDeVida.put(tipoName, tipo);
			}
			
			for(ContratanteSeguroDeVida tipo : ContratanteSeguroDeVida.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = ContratanteSeguroDeVida.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaContratanteSeguroDeVida.put(tipoName, tipo);
			}
			
			for(RiesgosAmparadosPorSeguroDeVida tipo : RiesgosAmparadosPorSeguroDeVida.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = RiesgosAmparadosPorSeguroDeVida.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaRiesgosSeguroDeVida.put(tipoName, tipo);
			}
			
			for(TipoIncapacidad tipo : TipoIncapacidad.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = TipoIncapacidad.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaTipoIncapacidad.put(tipoName, tipo);
			}
			
			for(RegimenContratacion tipo : RegimenContratacion.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = RegimenContratacion.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaRegimenContratacion.put(tipoName, tipo);
			}
			
			for(RiesgoPuesto tipo : RiesgoPuesto.values()) {
				String tipoName = tipo.name();
				SerializedName annotation = RiesgoPuesto.class.getField(tipoName).getAnnotation(SerializedName.class);
				if (annotation != null) {
					tipoName = annotation.value();
				}
				mapaRiesgoPuesto.put(tipoName, tipo);
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public RegimenVO read(JsonReader reader) throws IOException {
		//String tipo = null;
		RegimenContratacion tipoRegimen = null;
		RiesgoPuesto riesgoPuesto = null;
		Long id = null;
		String nombre = null;
		List<Percepcion> listaP = new ArrayList<Percepcion>();
		List<Deduccion> listaD = new ArrayList<Deduccion>();
		List<Long> listaL = new ArrayList<Long>();
		boolean activo = false;
		String formaPago = null;
		int[] diasDePago = null;
		double sueldo = 0.0;
		reader.beginObject();
 		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("tipo")) {
				tipoRegimen = mapaRegimenContratacion.get(reader.nextString());
				//tipo = reader.nextString();
			}else if (name.equals("id")) {
				id = reader.nextLong();
			} else if (name.equals("nombre")) {
				nombre = reader.nextString();
			} else if (name.equals("percepciones")) {
				reader.beginArray();
				while (reader.hasNext()) {
					listaP.add(readPercepcion(reader));
				}
				reader.endArray();
			} else if (name.equals("deducciones")) {
				reader.beginArray();
				while (reader.hasNext()) {
					listaD.add(readDeduccion(reader));
				}
				reader.endArray();
			} else if (name.equals("idEmpleados")) {
				listaL = readLongsArray(reader);
			} else if (name.equals("activo")) {
				activo = reader.nextBoolean();
			} else if (name.equals("formaPago")) {
				formaPago = reader.nextString();
			} else if (name.equals("diasDePago")) {
				diasDePago = readIntsArray(reader);
			} else if (name.equals("sueldo")) {
				sueldo = reader.nextDouble();
			} else if (name.equals("riesgoPuesto")) {
				riesgoPuesto = mapaRiesgoPuesto.get(reader.nextString());
			}
		}
		reader.endObject();
		Regimen r = new Regimen(id, nombre, listaP, listaD, listaL, activo, formaPago, diasDePago, sueldo);
		r.setTipoRegimenContratacion(tipoRegimen);
		RegimenVO rVO = new RegimenVO(r);
		if (riesgoPuesto != null)
			rVO.setRiesgoPuesto(riesgoPuesto);
		return rVO;
	}
	
	public Percepcion readPercepcion(JsonReader reader) throws IOException {
		
		String tipo = null;
		String clave = null;
		String descripcion = null;
		
		double cantidad = 0.0;
		double importeGravado = 0.0;
		double importeExento = 0.0;
		int[] horasExtra = null;
		int horasDobles = 0;
		int horasTriples = 0;
		double montoHorasTriples = 0.0;
		double porcentajeCuota = 0.0;
		int domingosLaborados = 0;
		
		//TipoPercepcion descripcion = null;
		BeneficiariosSeguroDeVida beneficiariosSDV = null;
		ContratanteSeguroDeVida contratante = null;
		RiesgosAmparadosPorSeguroDeVida riesgos = null;
		TipoIncapacidad tipoIncapacidad = null;
		
		Percepcion p = null;
		PercepcionHorasExtra pHorasExtra = null;
		PercepcionPrimaSeguroDeVida pPrimaSV = null;
		PercepcionSubsidiosIncapacidad pSubsidiosIncapacidad = null;
		PercepcionCuotaSindical pCuotaSindical = null;
		PercepcionPrimaDominical pPrimaDominical = null;
		
		boolean esSubclaseHoraExtra = false;
		boolean esSubclasePrimaSeguroVida = false;
		boolean esSubclaseSubsidiosIncapacidad = false;
		boolean esSubclaseCuotaSindical = false;
		boolean esSubclasePrimaDominical = false;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("clave")) {
				clave = reader.nextString();
			} else if (name.equals("descripcion")) {
				//descripcion = nameToConstantP.get(reader.nextString());
				descripcion = reader.nextString();
			} else if (name.equals("cantidad")) {
				cantidad = reader.nextDouble();
			} else if (name.equals("importeGravado")) {
				importeGravado = reader.nextDouble();
			} else if (name.equals("importeExento")) {
				importeExento = reader.nextDouble();
			} else if (name.equals("horasExtra")) {
				esSubclaseHoraExtra = true;
				horasExtra = readIntsArray(reader);
			} else if (name.equals("horasDobles")) {
				horasDobles = reader.nextInt();
			} else if (name.equals("horasTriples")) {
				horasTriples = reader.nextInt();
			} else if (name.equals("montoHorasTriples")) {
				montoHorasTriples = reader.nextDouble();
			} else if (name.equals("tipo")) {
				tipo = reader.nextString();
			} else if (name.equals("beneficiarios")) {
				esSubclasePrimaSeguroVida = true;
				beneficiariosSDV = mapaBeneficiariosSeguroDeVida.get(reader.nextString());
			} else if (name.equals("contratante")) {
				contratante = mapaContratanteSeguroDeVida.get(reader.nextString());
			} else if (name.equals("riesgosAmparados")) {
				riesgos = mapaRiesgosSeguroDeVida.get(reader.nextString());
			} else if (name.equals("tipoIncapacidad")) {
				esSubclaseSubsidiosIncapacidad = true;
				tipoIncapacidad = mapaTipoIncapacidad.get(reader.nextString());
			} else if (name.equals("porcentajeCuota")) {
				esSubclaseCuotaSindical = true;
				porcentajeCuota = reader.nextDouble();
			} else if (name.equals("domingosLaborados")) {
				esSubclasePrimaDominical = true;
				domingosLaborados = reader.nextInt();
			}
		}
		reader.endObject();
		if (esSubclaseHoraExtra) {
			pHorasExtra = new PercepcionHorasExtra(tipo, clave, descripcion);
			pHorasExtra.setCantidad(cantidad);
			pHorasExtra.setHorasExtra(horasExtra);
			pHorasExtra.setHorasDobles(horasDobles);
			pHorasExtra.setHorasTriples(horasTriples);
			pHorasExtra.setMontoHorasTriples(montoHorasTriples);
			p = pHorasExtra;
		} else if (esSubclasePrimaSeguroVida) {
			pPrimaSV = new PercepcionPrimaSeguroDeVida(tipo, clave, descripcion);
			pPrimaSV.setCantidad(cantidad);
			pPrimaSV.setBeneficiarios(beneficiariosSDV);
			pPrimaSV.setContratante(contratante);
			pPrimaSV.setRiesgosAmparados(riesgos);
			p = pPrimaSV;
		} else if (esSubclaseSubsidiosIncapacidad) {
			pSubsidiosIncapacidad = new PercepcionSubsidiosIncapacidad(tipo, clave, descripcion);
			pSubsidiosIncapacidad.setTipoIncapacidad(tipoIncapacidad);
			p = pSubsidiosIncapacidad;
		} else if (esSubclaseCuotaSindical) {
			pCuotaSindical = new PercepcionCuotaSindical(tipo, clave, descripcion);
			pCuotaSindical.setPorcentajeCuota(porcentajeCuota);
			p = pCuotaSindical;
		} else if (esSubclasePrimaDominical) {
			pPrimaDominical = new PercepcionPrimaDominical(tipo, clave, descripcion);
			pPrimaDominical.setDomingosLaborados(domingosLaborados);
			p = pPrimaDominical;
		}
		
		else {
			p = new Percepcion(tipo, clave, descripcion);
			p.setCantidad(cantidad);
		}
		p.setImporteExento(importeExento);
		p.setImporteGravado(importeGravado);
		return p;
	}
	
	public Deduccion readDeduccion(JsonReader reader) throws IOException {
		String clave = null;
		String tipo = null;
		//TipoDeduccion descripcion = null;
		String descripcion = null;
		TipoIncapacidad tipoIncapacidad = null;
		double descuento = 0.0;
		ModalidadDescuento modalidad = null;
		double valorDeCredito = 0.0;
		int diasIncapacidad = 0;
		int diasAusente = 0;
		boolean trabajaSeisDias = false;
		double porcentajePension = 0.0;
		
		Deduccion d = null;
		DeduccionInfonavit dInfonavit = null;
		DeduccionIncapacidad dIncapacidad = null;
		DeduccionAusentismo dAusentismo = null;
		DeduccionPensionAlimenticia dPensionAlimenticia = null;
		
		boolean esSubclaseInfonavit = false;
		boolean esSubclaseIncapacidad = false;
		boolean esSubclaseAusentismo = false;
		boolean esSubclasePensionAlimenticia = false;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("clave")) {
				clave = reader.nextString();
			} else if (name.equals("tipo")) {
				tipo = reader.nextString();
			} else if (name.equals("descripcion")) {
				descripcion = reader.nextString();
			} else if (name.equals("descuento")) {
				descuento = reader.nextDouble();
			} else if (name.equals("modalidad")) {
				//dInfonavit = new DeduccionInfonavit();
				esSubclaseInfonavit = true;
				modalidad = nameToConstantM.get(reader.nextString());
			} else if (name.equals("valorDeCredito")) {
				valorDeCredito = reader.nextDouble();
			} else if (name.equals("incapacidad")) {
				esSubclaseIncapacidad = true;
				tipoIncapacidad = mapaTipoIncapacidad.get(reader.nextString());
			} else if (name.equals("diasIncapacidad")) {
				diasIncapacidad = reader.nextInt();
			} else if (name.equals("diasAusente")) {
				esSubclaseAusentismo = true;
				diasAusente = reader.nextInt();
			} else if (name.equals("trabajaSeisDias")) {
				trabajaSeisDias = reader.nextBoolean();
			} else if (name.equals("porcentajePension")) {
				esSubclasePensionAlimenticia = true;
				porcentajePension = reader.nextDouble();
			}
		}
		reader.endObject();
		if (esSubclaseInfonavit) {
			dInfonavit = new DeduccionInfonavit(tipo, clave, descripcion);
			dInfonavit.setModalidad(modalidad);
			dInfonavit.setValorDeCredito(valorDeCredito);
			d = dInfonavit;
		} else if (esSubclaseIncapacidad) {
			dIncapacidad = new DeduccionIncapacidad(tipo, clave, descripcion);
			dIncapacidad.setDiasIncapacidad(diasIncapacidad);
			dIncapacidad.setIncapacidad(tipoIncapacidad);
			d = dIncapacidad;
		} else if (esSubclaseAusentismo) {
			dAusentismo = new DeduccionAusentismo(tipo, clave, descripcion);
			dAusentismo.setDiasAusente(diasAusente);
			dAusentismo.setTrabajaSeisDias(trabajaSeisDias);
			d = dAusentismo;
		} else if (esSubclasePensionAlimenticia) {
			dPensionAlimenticia = new DeduccionPensionAlimenticia(tipo, clave, descripcion);
			dPensionAlimenticia.setPorcentajePension(porcentajePension);
			d = dPensionAlimenticia;
		} else {
			d = new Deduccion(tipo, clave, descripcion);
			d.setDescuento(descuento);
		}
		return d;
	}
	
	public List<Long> readLongsArray(JsonReader reader) throws IOException {
		List<Long> listaIdEmp = new ArrayList<Long>();
		reader.beginArray();
		while (reader.hasNext()) {
			listaIdEmp.add(reader.nextLong());
		}
		reader.endArray();
		return listaIdEmp;
	}
	
	public int[] readIntsArray(JsonReader reader) throws IOException {
		int[] unDia = new int[1];
		int[] dosDias = new int[2];
		int[] tresDias = new int[3];
		int[] cuatroDias = new int[4];
		int i = 0;
		reader.beginArray();
		while (reader.hasNext()) {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				continue;
			}
			i++;
			int dia = reader.nextInt();
			switch (i) {
				case 1:
					unDia[0] = dia;
					dosDias[0] = dia;
					tresDias[0] = dia;
					cuatroDias[0] = dia;
					break;
				case 2:
					dosDias[1] = dia;
					tresDias[1] = dia;
					cuatroDias[1] = dia;
					break;
				case 3:
					tresDias[2] = dia;
					cuatroDias[2] = dia;
					break;
				case 4:
					cuatroDias[3] = dia;
					break;
			} 
		}
		reader.endArray();
		switch (i) {
			case 1:
				return unDia;
			case 2:
				return dosDias;
			case 3:
				return tresDias;
			case 4:
				return cuatroDias;
			default:
				return null;
		}
		
	}

	@Override
	public void write(JsonWriter writer, RegimenVO regimen) throws IOException {
		//TODO
		
	}

}
