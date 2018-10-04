/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.ContadorEmpleadosDAO;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.ContadorEmpleados;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.springController.viewObjects.EmpleadoVO;
import com.tikal.cacao.util.Encrypter;
import com.tikal.cacao.util.TDESEncrypter;

/**
 * @author Tikal
 *
 */
@Repository
public class EmpleadosDAOImpl implements EmpleadosDAO {

	@Autowired
	@Qualifier("regimenesdao")
	private RegimenesDAO regDAO;

	@Autowired
	@Qualifier(value = "empresasdao")
	private EmpresasDAO empresasDAO;
	
	@Autowired
	private ContadorEmpleadosDAO contadorDAO;
	
	@Autowired
	private Encrypter encrypter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.EmpleadosDAO#crear(com.tikal.cacao.model.Empleado)
	 */
	@Override
	public Empleado crear(Empleado empleado) {
		ContadorEmpleados contador = contadorDAO.consultar();
		if (contador == null) {
			contador = new ContadorEmpleados();
			contador.setCuenta(1L);
		}
		empleado.setNumEmpleado(contador.getCuenta());
		cifrarDatos(empleado);
		ofy().save().entities(empleado).now();
		contadorDAO.incrementar(contador);
		return empleado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.EmpleadosDAO#actualizar(com.tikal.cacao.model.
	 * Empleado)
	 */
	@Override
	public void actualizar(Empleado empleado) {
		cifrarDatos(empleado);
		ofy().save().entities(empleado).now();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tikal.cacao.dao.EmpleadosDAO#consultar(long)
	 */
	@Override
	public Empleado consultar(long numEmpleado) {
		Empleado empleado = ofy().load().type(Empleado.class).id(numEmpleado).now();
		descifrarDatos(empleado);
		return empleado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tikal.cacao.dao.EmpleadosDAO#eliminar(com.tikal.cacao.model.Empleado)
	 */
	@Override
	public void eliminar(Empleado empleado) {
		empleado.setActivo(false);
		actualizar(empleado);

	}

	@Override
	public List<EmpleadoVO> consultaPorEmpresa(String rfc) {
		Empresa empresa = empresasDAO.consultar(rfc);
		List<Regimen> regimenes = empresa.getRegimenes();
		List<EmpleadoVO> empleados = new ArrayList<EmpleadoVO>();
		for (Regimen regimen : regimenes) {
			empleados.addAll(consultaVOPorRegimen(regimen.getId()));
		}
		return empleados;
	}

	@Override
	public List<Empleado> consultaPorRegimen(Long id) {
		Regimen regimen = regDAO.consultar(id);
		List<Long> ids = regimen.getIdEmpleados();
		List<Long> temp = new ArrayList<Long>();
		for (Long idt : ids) {
			if (idt != null) {
				temp.add(idt);
			}
		}
		regimen.setIdEmpleados(temp);

		if (regimen.getIdEmpleados().size() > 0) {
			Collection<Empleado> colecionEmpl = ofy().load().type(Empleado.class).ids(regimen.getIdEmpleados())
					.values();
			List<Empleado> lista =  new ArrayList<Empleado>(colecionEmpl);
			for (Empleado empleado : lista) {
				descifrarDatos(empleado);
			}
			return lista;
		}
		return  new ArrayList<Empleado>();
	}
	
	@Override
	public List<EmpleadoVO> consultaVOPorRegimen(Long id) {
		Regimen regimen = regDAO.consultar(id);
		List<EmpleadoVO> listEmplVO = new ArrayList<EmpleadoVO>();
		EmpleadoVO emplVO = null;
		String nombreRegimen = regimen.getNombre();
		Collection<Empleado> colecionEmpl = ofy().load().type(Empleado.class).ids(regimen.getIdEmpleados())
				.values();
		List<Empleado> listEmpl = new ArrayList<Empleado>(colecionEmpl);
		for (Empleado empleado : listEmpl) {
			descifrarDatos(empleado);
			emplVO = new EmpleadoVO();
			emplVO.setEmpleado(empleado);
			emplVO.setNombreRegimen(nombreRegimen);
			listEmplVO.add(emplVO);
		}
		return listEmplVO;
	}
		
	private void cifrarDatos(Empleado empleado) {
		String sk = TDESEncrypter.SK;
		
		String numSeguroSocial = empleado.getNumSeguroSocial();
		if (numSeguroSocial != null)
			empleado.setNumSeguroSocial(encrypter.encrypt(numSeguroSocial, sk));
		
		empleado.setCurp(encrypter.encrypt(empleado.getCurp(), sk));
		empleado.setRFC(encrypter.encrypt(empleado.getRFC(), sk));
		
		String telefono = empleado.getTelefono();
		if (telefono != null)
			empleado.setTelefono(encrypter.encrypt(telefono, sk));
		//empleado.setNombreBanco(encrypter.encrypt(empleado.getNombreBanco(), TDESEncrypter.SK));
		
		String claveBanco = empleado.getClaveBanco();
		if (claveBanco != null)
			empleado.setClaveBanco(encrypter.encrypt(claveBanco, sk));
		
		String nombreBanco = empleado.getNombreBanco();
		if (nombreBanco != null)
			empleado.setNombreBanco(encrypter.encrypt(nombreBanco, sk));
		
		String email = empleado.getEmail();
		if (email != null)
			empleado.setEmail(encrypter.encrypt(email, sk));
		
		Direccion dirEncrypt = empleado.getDireccion();
		String estado = dirEncrypt.getEstado();
		if (estado != null)
			if ( !estado.equals(""))
				dirEncrypt.setEstado(encrypter.encrypt(estado, sk));
		
		String localidad = dirEncrypt.getLocalidad();
		dirEncrypt.setLocalidad(encrypter.encrypt(localidad, sk));
		
		String colonia = dirEncrypt.getColonia();
		dirEncrypt.setColonia(encrypter.encrypt(colonia, sk));
		
		String codigoPostal = dirEncrypt.getCodigoPostal();
		dirEncrypt.setCodigoPostal(encrypter.encrypt(codigoPostal, sk));
		
		String calle = dirEncrypt.getCalle();
		if (calle != null)
			if ( !calle.equals(""))
				dirEncrypt.setCalle(encrypter.encrypt(calle, sk));
		
		String numeroExt = dirEncrypt.getNumExterior();
		dirEncrypt.setNumExterior(encrypter.encrypt(numeroExt, sk));
		
		String numeroInt = dirEncrypt.getNumInterior();
		if (numeroInt != null)
			dirEncrypt.setNumInterior(encrypter.encrypt(numeroInt, sk));
		//Probar en Esquema Vendedores en empresa FEMSA
	}
	
	private void descifrarDatos(Empleado empleado) {
		String sk = TDESEncrypter.SK;
		
		String numSeguroSocial = empleado.getNumSeguroSocial();
		if (numSeguroSocial != null)
			empleado.setNumSeguroSocial(encrypter.decrypt(numSeguroSocial, sk));
		
		empleado.setCurp(encrypter.decrypt(empleado.getCurp(), sk));
		empleado.setRFC(encrypter.decrypt(empleado.getRFC(), sk));
		
		String telefono = empleado.getTelefono();
		if (telefono != null)
			empleado.setTelefono(encrypter.decrypt(telefono, sk));
		//empleado.setNombreBanco(encrypter.encrypt(empleado.getNombreBanco(), TDESEncrypter.SK));
		
		String claveBanco = empleado.getClaveBanco();
		if (claveBanco != null)
			empleado.setClaveBanco(encrypter.decrypt(claveBanco, sk));
		
		String nombreBanco = empleado.getNombreBanco();
		if (nombreBanco != null)
			empleado.setNombreBanco(encrypter.decrypt(nombreBanco, sk));
		
		String email = empleado.getEmail();
		if (email != null)
			empleado.setEmail(encrypter.decrypt(email, sk));
		
		Direccion dirEncrypt = empleado.getDireccion();
		String estado = dirEncrypt.getEstado();
		if (estado != null)
			if ( !estado.equals(""))
				dirEncrypt.setEstado(encrypter.decrypt(estado, sk));
		
		String localidad = dirEncrypt.getLocalidad();
		dirEncrypt.setLocalidad(encrypter.decrypt(localidad, sk));
		
		String colonia = dirEncrypt.getColonia();
		dirEncrypt.setColonia(encrypter.decrypt(colonia, sk));
		
		String codigoPostal = dirEncrypt.getCodigoPostal();
		dirEncrypt.setCodigoPostal(encrypter.decrypt(codigoPostal, sk));
		
		String calle = dirEncrypt.getCalle();
		if (calle != null)
			if ( !calle.equals(""))
				dirEncrypt.setCalle(encrypter.decrypt(calle, sk));
		
		String numeroExt = dirEncrypt.getNumExterior();
		dirEncrypt.setNumExterior(encrypter.decrypt(numeroExt, sk));
		
		String numeroInt = dirEncrypt.getNumInterior();
		if (numeroInt != null)
			dirEncrypt.setNumInterior(encrypter.decrypt(numeroInt, sk));

	}

}
