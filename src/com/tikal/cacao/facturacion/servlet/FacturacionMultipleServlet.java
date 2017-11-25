package com.tikal.cacao.facturacion.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.service.FacturaVTTService;

public class FacturacionMultipleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private WSClient client;

	@Autowired
	private WSClientCfdi33 client33;

	@Autowired
	EmpresasDAO empresasDAO;

	@Autowired
	EmisorDAO emisorDAO;

	@Autowired
	SerialDAO serialDAO;

	@Autowired
	FacturaDAO facturaDAO;

	@Autowired
	BitacoraDAO bitacoradao;

	@Autowired
	ImagenDAO imagenDAO;

	@Autowired
	ReporteRenglonDAO repRenglonDAO;
	
	@Autowired
	FacturaVTTService servicioFact;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		List<FacturaRenglon> lista = facturarenglondao.todos();
//		for (FacturaRenglon fr : lista) {
//			String respuesta=this.timbrar(fr, request.getSession());
//			if(respuesta.compareTo("OK")==0){
//				facturarenglondao.eliminar(fr.getId());
//			}else{
//				fr.setError(respuesta);
//				fr.setFailed(true);
//				facturarenglondao.guardar(fr);
//			}
//			
//		}
//		response.getWriter().print("OK");
		
		
	}

//	private String timbrar(Datis f, HttpSession sesion) {
//		Comprobante c = new Comprobante();
//		Comprobante.Emisor emisor = new Comprobante.Emisor();
//		Empresa empresa = empresasDAO.consultar(f.getRfcEmisor());
//		emisor.setNombre(empresa.getNombre());
//		emisor.setRfc(empresa.getRFC());
//	
//		emisor.setRegimenFiscal(new C_RegimenFiscal(f.getRegimen().split("-")[0]));
////		emisor.getRegimenFiscal().add(reg);
//		c.setEmisor(emisor);
//		Comprobante.Receptor receptor = new Comprobante.Receptor();
//		List<Receptor> receptores = emisorDAO.consultar(f.getRfcEmisor()).getReceptores();
//		Receptor recept = null;
//		for (Receptor r : receptores) {
//			if (r.getRfc().compareToIgnoreCase(f.getRfcReceptor()) == 0) {
//				recept = r;
//				break;
//			}
//		}
//		if(recept==null){
//			return "No se encontró al receptor con RFC: "+f.getRfcReceptor();
//		}
//		receptor.setNombre(recept.getNombre());
//		receptor.setRfc(recept.getRfc());
//		receptor.setUsoCFDI(new C_UsoCFDI(f.getUsoCFDI().split("-")[0]));
////		receptor.setResidenciaFiscal(new C_Pais("MEX"));
//		
////		receptor.setDomicilio(recept.getDomicilio());
//		c.setReceptor(receptor);
//		c.setVersion("3.3");
//		c.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
//		
//		c.setLugarExpedicion(new C_CodigoPostal(empresa.getDireccion().getCodigoPostal()));
//		c.setTipoDeComprobante(new C_TipoDeComprobante(f.getTipoComprobante().split("-")[0]));
//		String[] formadepago=f.getFormaPago().split("-"); 
//		c.setFormaPago(new C_FormaDePago(formadepago[0]));
//		String[] metododepago= f.getMetodoPago().split("-");
//		c.setMetodoPago(new C_MetodoDePago(metododepago[0]));
//		c.setMoneda(new C_Moneda("MXN"));
//
//		Impuestos imps = new Impuestos();
//		Traslados t = new Traslados();
//		Traslado tras = new Traslado();
//		tras.setImpuesto(new C_Impuesto("IVA"));
//		tras.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
//		tras.setTipoFactor(new C_TipoFactor("Tasa"));
//		tras.setImpuesto(new C_Impuesto("002"));
//		
//		Conceptos conceptos = new Conceptos();
//		Comprobante.Conceptos.Concepto con = new Comprobante.Conceptos.Concepto();
//		con.setCantidad(new BigDecimal(f.getCantidad()).setScale(2, RoundingMode.HALF_UP));
//		con.setDescripcion(f.getDescripcion());
//		con.setUnidad(f.getUnidad());
//		con.setClaveUnidad(new C_ClaveUnidad(f.getCveUnidad().split("-")[0]));
//		con.setClaveProdServ(f.getCveProdServ().split("-")[0]);
//		float vu = Float.parseFloat(f.getValorUnitario());
//		float total= vu*con.getCantidad().floatValue();
//		if (f.getIvaIncluido().compareToIgnoreCase("si") == 0) {
//			
//			vu = vu / 1.16f;
//			float importe = vu * con.getCantidad().floatValue();
//			float impuesto = total-importe;
//			con.setValorUnitario(new BigDecimal(vu).setScale(2, RoundingMode.HALF_UP));
//			con.setImporte(new BigDecimal(importe).setScale(2, RoundingMode.HALF_UP));
//			Comprobante.Conceptos.Concepto.Impuestos impuestos= new Comprobante.Conceptos.Concepto.Impuestos();
//			Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados= new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
//			Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado= new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
//			traslado.setBase(new BigDecimal(importe).setScale(2, RoundingMode.HALF_UP));
//			traslado.setImporte(new BigDecimal(impuesto).setScale(2, RoundingMode.HALF_UP));
//			traslado.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
//			traslado.setTipoFactor(new C_TipoFactor("Tasa"));
//			traslado.setImpuesto(new C_Impuesto("002"));
//			traslados.getTraslado().add(traslado);
//			impuestos.setTraslados(traslados);
//			con.setImpuestos(impuestos);
//			
//			c.setSubTotal(new BigDecimal(importe).setScale(2, RoundingMode.HALF_UP));
//			c.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP));
//			tras.setImporte(new BigDecimal(impuesto).setScale(2, RoundingMode.HALF_UP));
//			
//		} else {
//			con.setValorUnitario(new BigDecimal(vu).setScale(2, RoundingMode.HALF_UP));
//			con.setImporte(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP));
//			Comprobante.Conceptos.Concepto.Impuestos impuestos= new Comprobante.Conceptos.Concepto.Impuestos();
//			Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados= new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
//			Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado= new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
//			traslado.setBase(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP));
//			traslado.setImporte(new BigDecimal(total*0.16).setScale(2, RoundingMode.HALF_UP));
//			traslado.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
//			traslado.setTipoFactor(new C_TipoFactor("Tasa"));
//			traslado.setImpuesto(new C_Impuesto("002"));
//			traslados.getTraslado().add(traslado);
//			impuestos.setTraslados(traslados);
//			con.setImpuestos(impuestos);
//			c.setSubTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP));
//			c.setTotal(new BigDecimal(total*1.16).setScale(2, RoundingMode.HALF_UP));
//			tras.setImporte(new BigDecimal(total*0.16).setScale(2, RoundingMode.HALF_UP));
//		} 
//		conceptos.getConcepto().add(con);
//		t.getTraslado().add(tras);
//		imps.setTraslados(t);
//		imps.setTotalImpuestosTrasladados(t.getTraslado().get(0).getImporte());
//		c.setImpuestos(imps);
//		c.setConceptos(conceptos);
//		Serial s = serialDAO.consultar(f.getRfcEmisor(), f.getSerie());
//
//		if(s==null){
//			return "No se encontró la serie: "+f.getSerie();
//		}
//		
//		c.setSerie(s.getSerie());
//		c.setFolio(s.getFolio() + "");
//
//		ComprobanteVO vo= new ComprobanteVO();
//		vo.setComprobante(c);
//		String respuesta=servicioFact.timbrar(vo,sesion);
////		facturarenglondao.eliminar(f.getId());
//		RegistroBitacora bit= new RegistroBitacora();
//		bit.setEvento(respuesta);
//		bit.setTipo("Operacional");
//		bit.setUsuario("Proceso Back");
//		bit.setFecha(new Date());
//		bitacoradao.addReg(bit);
//		return "OK";
//	}
}
