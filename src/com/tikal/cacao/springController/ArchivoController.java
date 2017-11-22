package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.NombrePersona;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.springController.viewObjects.EmpleadoVO;
import com.tikal.cacao.util.AsignadorDeCharset;

@Controller
@RequestMapping(value={"/file"})
public class ArchivoController {
	
	@Autowired
	RegimenesDAO regdao;
	
	@Autowired
	EmpleadosDAO empdao;
	
	
	@RequestMapping(value={"certs"},method= RequestMethod.POST)
	public void certificados(HttpServletRequest req, HttpServletResponse res, @RequestParam String password ,@RequestBody String r) throws IOException{
		Enumeration<String> i= req.getAttributeNames();
		
		while(i.hasMoreElements()){
			String s=i.nextElement();
			System.out.println(s);
		}
		
		res.getWriter().println("ok");
	}
	
	@RequestMapping(value={"prueba"}, method= RequestMethod.GET, produces="application/json")
	public void get(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.getWriter().print("HOLA");
	}
	
//	public Object saveUserDataAndFile( @RequestParam(value = "user") String userInfo,
//	        @RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {
	//MultipartHttpServletRequest request,
	  @RequestMapping(value="upload", method = RequestMethod.POST)
	    public void UploadFile(HttpServletRequest request, HttpServletResponse response,@RequestBody String l) throws IOException {
		  AsignadorDeCharset.asignar(request, response);
		  String[] rengs= l.split("\n");
		  List<EmpleadoVO> lista= this.llenar(rengs);
		  PrintWriter pw=response.getWriter();
		  
		  for(EmpleadoVO em:lista){
			  Regimen r=regdao.consultar(em.getNombreRegimen());
			  if(r==null){
				  pw.println("No se pudo encontrar el regimen con el nombre: "+em.getNombreRegimen()+" no se creó "+em.getEmpleado().getNombre().toString());
			  }else{
				  Empleado e=empdao.crear(em.getEmpleado());
				  r.addEmpleado(e.getNumEmpleado());
				  regdao.actualizar(r);
				  pw.println("Se creó a "+e.getNombre().toString()+" en el régimen "+r.getNombre()+"</br>");
			  }
		  }
//	        Iterator<String> itr=request.getFileNames();
//	        int length= Integer.parseInt(request.getParameter("length"));
//	        while(itr.hasNext()){
//	        	System.out.println(itr.next());
//	        }
//	        MultipartFile file=request.getFile(itr.next());
//
//	        String fileName=file.getOriginalFilename();
//	        System.out.println(l);
	    }
	  
	  private List<EmpleadoVO> llenar(String[] arr){
		  List<EmpleadoVO> lista= new ArrayList<EmpleadoVO>();
		  for(int i =1; i< arr.length;i++){
			  String[] empData= arr[i].split(",");
			  EmpleadoVO e= new EmpleadoVO();
			  e.setNombreRegimen(empData[0]);
			  
			  //Datos del empleado
			  Empleado em= new Empleado();
			  em.setNumSeguroSocial(empData[1]);
			  NombrePersona nombre= new NombrePersona();
			  nombre.setApellidoPaterno(empData[2]);
			  nombre.setApellidoMaterno(empData[3]);
			  nombre.setNombresDePila(empData[4]);
			  
			  em.setNombre(nombre);
			  em.setCurp(empData[5]);
			  em.setRFC(empData[6]);
			  em.setEmail(empData[7]);
			  em.setTelefono(empData[8]);
			  em.setPuesto(empData[9]);
			  em.setDepartamento(empData[10]);
			  
			  //Direccion
			  Direccion d= new Direccion();
			  d.setEstado(empData[11]);
			  d.setLocalidad(empData[12]);
			  d.setCodigoPostal(empData[13]);
			  d.setColonia(empData[14]);
			  d.setCalle(empData[15]);
//			  d.setNumExterior(empData[16]);
//			  d.setNumInterior(empData[17]);
			  
			  em.setDireccion(d);
			  
			  em.setFechaDeContratacion(empData[18]);
			  e.setEmpleado(em);
			  lista.add(e);
		  }
		  return  lista;
	  }
}
