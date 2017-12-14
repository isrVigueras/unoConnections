package com.tikal.cacao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tikal.cacao.dao.ConceptosDAO;
import com.tikal.cacao.dao.ListaDeClavesDeClasesDAO;
import com.tikal.cacao.dao.sql.ProductoServicioDAO;
import com.tikal.cacao.model.Concepto;
import com.tikal.cacao.model.ListaDeClasesDeProdServ;
import com.tikal.cacao.model.orm.ProductoOServicio;
import com.tikal.cacao.service.ConceptoSATService;

@Service
public class ConceptosSATServiceImpl implements ConceptoSATService {

	@Autowired
	private ConceptosDAO conceptosDAO;
	
	@Autowired
	@Qualifier(value = "productoServicioDAOH")
	private ProductoServicioDAO productoOServicioDAO;
	
	@Autowired
	ListaDeClavesDeClasesDAO listaDeClavesDeClasesDAO;
	
	@Override
	public void generarConceptos(String rfc, Object[][] listaPS) {
		if (listaPS != null) {
			Concepto conceptoSAT = null;
			List<Concepto> listaC = new ArrayList<>();
			String claveProdServ;
			String descripcionProdServ;
			String claveClase = "";
			//String claveClaseAux = "";
			ListaDeClasesDeProdServ lista = listaDeClavesDeClasesDAO.obtener(rfc);
			ListaDeClasesDeProdServ listaDeClasesDeProdServ = new ListaDeClasesDeProdServ();
			listaDeClasesDeProdServ.setIdRFC(rfc);
			if (lista != null) {
				listaDeClasesDeProdServ.getListaClavesClases().addAll(lista.getListaClavesClases());
			}
			
			
			for (Object[] object : listaPS) {
				claveProdServ = (String) object[0];
				descripcionProdServ = (String) object[1];
				if (claveClase.equals("") ) {
					claveClase = claveProdServ.substring(0, 6);
				} 
				
				if (!listaDeClasesDeProdServ.getListaClavesClases().contains(claveClase)) {
					listaDeClasesDeProdServ.getListaClavesClases().add(claveClase);
				}
				
				conceptoSAT = new Concepto();
				conceptoSAT.setNoIdentificacion(claveProdServ);
				conceptoSAT.setDescripcion(descripcionProdServ);
				conceptoSAT.setClaveProdServ(claveProdServ);
				conceptoSAT.setDescripcionSAT(descripcionProdServ);
				listaC.add(conceptoSAT);
				
				
			}
			conceptosDAO.add(rfc, listaC);
			listaDeClavesDeClasesDAO.guardar(listaDeClasesDeProdServ);
		}
	}

	@Override
	public List<ProductoOServicio> cargarProdServ(String rfc) {
//		Conceptos conceptos = conceptosDAO.consultar(rfc);
//		List<Concepto> listaC = conceptos.getConceptos();
		// listaPD = new ArrayList<>();
		ListaDeClasesDeProdServ listaClaveClases = listaDeClavesDeClasesDAO.obtener(rfc);
		List<ProductoOServicio> listaPD = productoOServicioDAO.consultaPorClases(listaClaveClases.getListaClavesClases());
		return listaPD;
	}

}
