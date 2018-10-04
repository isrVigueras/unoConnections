package com.tikal.cacao.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;
import com.tikal.cacao.springController.viewObjects.PagoVO;

public class ListaPagosVOAdapter extends TypeAdapter<ListaPagosVO> {
	
	private DateFormat formatoFechaDePago = new SimpleDateFormat("MMM d, yyyy h:mm:ss a");
	private RegimenAdapter regAdapter = new RegimenAdapter();
	
	@Override
	public void write(JsonWriter out, ListaPagosVO value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ListaPagosVO read(JsonReader in) throws IOException {
		ListaPagosVO listaPagosVO = new ListaPagosVO();
		List<PagoVO> lista = new ArrayList<>();
		in.beginObject();
		String name = in.nextName();
		if (name.equals("lista")) {
			in.beginArray();
			while (in.hasNext()) {
				lista.add(readPagoVO(in));
			}
			in.endArray();
		}
		in.endObject();
		listaPagosVO.setLista(lista);
		return listaPagosVO;
	}
	
	public PagoVO readPagoVO(JsonReader reader) throws IOException {
		PagoVO pVO = new PagoVO();
		List<Percepcion> listaP = new ArrayList<>();
		List<Deduccion> listaD = new ArrayList<>();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			switch (name) {
			case "idPago":
				pVO.setIdPago(reader.nextLong());
				break;
			case "Nombre":
				pVO.setNombre(reader.nextString());
				break;
			case "rfc":
				pVO.setRfc(reader.nextString());
				break;
			case "nss":
				pVO.setNss(reader.nextString());
				break;
			case "fechaIngreso":
				pVO.setFechaIngreso(reader.nextString());
				break;
			case "curp":
				pVO.setCurp(reader.nextString());
				break;
			case "cantidadAPagar":
				pVO.setCantidadAPagar(reader.nextDouble());
				break;
			case "montoPrevisionSocial":
				pVO.setMontoPrevisionSocial(reader.nextDouble());
				break;
			case "fechaDePago":
				pVO.setFechaDePago(Util.obtenerFecha(reader.nextString(), formatoFechaDePago));
				break;
			case "fechaDePagoEsquema":
				pVO.setFechaDePagoEsquema(Util.obtenerFecha(reader.nextString(), formatoFechaDePago));
				break;
			case "formaPago":
				pVO.setFormaPago(reader.nextString());
				break;
			case "diasPagados":
				pVO.setDiasPagados(reader.nextString());
				break;
			case "percepciones":
				reader.beginArray();
				while (reader.hasNext()) {
					listaP.add(regAdapter.readPercepcion(reader));
				}
				reader.endArray();
				pVO.setPercepciones(listaP);
				break;
			case "deducciones":
				reader.beginArray();
				while (reader.hasNext()) {
					listaD.add(regAdapter.readDeduccion(reader));
				}
				reader.endArray();
				pVO.setDeducciones(listaD);
				break;
			case "idEmpleado":
				pVO.setIdEmpleado(reader.nextLong());
				break;
			case "salarioDiario":
				pVO.setSalarioDiario(reader.nextDouble());
				break;
			case "salarioDiarioIntegrado":
				pVO.setSalarioDiarioIntegrado(reader.nextDouble());
				break;
			case "trabajadorAsegurado":
				pVO.setTrabajadorAsegurado(reader.nextBoolean());
				break;
			case "tipoRegimen":
				pVO.setTipoRegimen(reader.nextString());
				break;
			case "puesto":
				pVO.setPuesto(reader.nextString());
				break;
			case "departamento":
				pVO.setDepartamento(reader.nextString());
				break;
			case "rfcEmpresa":
				pVO.setRfcEmpresa(reader.nextString());
				break;
			case "razonSocial":
				pVO.setRazonSocial(reader.nextString());
				break;
			case "claveBanco":
				pVO.setClaveBanco(reader.nextString());
				break;
			case "cpLugarExpedicion":
				pVO.setCpLugarExpedicion(reader.nextString());
				break;
			case "idRegimen":
				pVO.setIdRegimen(reader.nextLong());
				break;
			case "cadenaComprobante":
				pVO.setCadenaComprobante(reader.nextString());
				break;
			}
		}
		reader.endObject();	
		return pVO;
	
	}

}
