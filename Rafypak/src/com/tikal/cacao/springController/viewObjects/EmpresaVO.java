package com.tikal.cacao.springController.viewObjects;

import java.util.ArrayList;
import java.util.List;

import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

public class EmpresaVO extends Empresa{

	private List<RegimenVO> regimenesVO;
		
	public EmpresaVO(){
		regimenesVO = new ArrayList<RegimenVO>();
	}
	
	public EmpresaVO(Empresa e){
		regimenesVO = new ArrayList<RegimenVO>();
		this.setActivo(e.isActivo());
		this.setDireccion(e.getDireccion());
		this.setNombre(e.getNombre());
		this.setRazonSocial(e.getRazonSocial());
		this.setEmails(e.getEmails());
		for(Regimen r: e.getRegimenes()){
			this.regimenesVO.add(new RegimenVO(r));
		}
		this.setRFC(e.getRFC());
	}
}
