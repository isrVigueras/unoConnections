package com.tikal.cacao.springController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.DomicilioCEDAO;
import com.tikal.cacao.model.DomicilioCE;
import com.tikal.cacao.sat.cfd.catalogos.C_Estado;
import com.tikal.cacao.sat.cfd.catalogos.C_Pais;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_CodigoPostal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_Colonia;
import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_Localidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_Municipio;

@Controller
@RequestMapping(value= {"/domicilioCE"})
public class DomicilioCEController {

	@Autowired
	DomicilioCEDAO domdao;
	
	@RequestMapping(value={"/setup"}, method = RequestMethod.GET)
	public void setup(HttpServletResponse res){
		DomicilioCE d= new DomicilioCE();
		d.setCalle("AV JUAN MONROY");
		d.setCodigoPostal(new C_CodigoPostal("50450"));
		d.setColonia(new C_Colonia("0528"));
		d.setEstado(C_Estado.MEX);
		d.setPais(C_Pais.MEX);
		d.setMunicipio(new C_Municipio("014"));
		d.setNumeroExterior("S/N");
		//d.setLocalidad(new C_Localidad("Atlacomulco"));
		d.setRfc("AAA010101AAA");
		
		domdao.guardar(d);
	}
}
