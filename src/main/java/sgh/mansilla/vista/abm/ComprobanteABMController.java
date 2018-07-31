package sgh.mansilla.vista.abm;


import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sgh.mansilla.modelo.datos.facturacion.ClienteComprobante;
import sgh.mansilla.modelo.datos.facturacion.Comprobante;
import sgh.mansilla.modelo.datos.facturacion.Concepto;
import sgh.mansilla.modelo.datos.facturacion.ConceptosAIncluir;
import sgh.mansilla.modelo.datos.facturacion.FormaDePago;
import sgh.mansilla.modelo.datos.facturacion.Moneda;
import sgh.mansilla.modelo.datos.facturacion.PreTicket;
import sgh.mansilla.modelo.datos.facturacion.RespuestaProcesarFactura;
import sgh.mansilla.modelo.datos.facturacion.TipoComprobante;
import sgh.mansilla.modelo.dto.facturacion.ComprobanteDTO;
import sgh.mansilla.modelo.dto.facturacion.TicketAcceso;
import sgh.mansilla.modelo.dto.facturacion.afip.AlicIva;
import sgh.mansilla.modelo.negocio.ABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.ClienteComprobanteABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.ComprobanteABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.ConceptosAIncluirABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.FormaDePagoABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.MonedaABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.PreTicketABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.TicketAccesoABM;
import sgh.mansilla.modelo.negocio.facturacion.Abm.TipoComprobanteABM;
import sgh.mansilla.modelo.negocio.facturacion.Servicios.FacturaToPDF;
import sgh.mansilla.modelo.negocio.facturacion.Servicios.ServicioAfip;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteABMController extends AbstractABMController<Integer, Comprobante> {

	@Autowired
	TipoComprobanteABM tipoComprobanteABM;
	@Autowired
	ClienteComprobanteABM clienteComprobanteABM;
	@Autowired
	MonedaABM monedaABM;
	@Autowired
	ConceptosAIncluirABM conceptosAIncluirABM;
	@Autowired
	FormaDePagoABM formaDePagoABM;
	@Autowired
	PreTicketABM preTicketABM;
	@Autowired
	TicketAccesoABM ticketAccesoABM;
	
	@Autowired
	@Qualifier("comprobanteABM")
	protected void setAbm(ABM<Integer, Comprobante> abm) {
		super.setAbm(abm);
	}

	public ComprobanteABMController() {
		super("abm/comprobante");
	}

	@Override
	protected Comprobante createEntity() {
		return new Comprobante();
	}
	
	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newComprobante"}, method = RequestMethod.GET)
	public String newEntity(ModelMap model) {
		Comprobante entity = createEntity();
		model.addAttribute("titulo", "Crear Comprobante");
		model.addAttribute("entity", entity);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return viewBaseLocation + "/form";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newComprobante" }, method = RequestMethod.POST)
	public String saveEntity(@Valid Comprobante entity, BindingResult result, ModelMap model,
			@RequestParam(required = false) Boolean facturar) {
		try{
	
			if (result.hasErrors()) {
				return viewBaseLocation + "/form";
			}
			
			Iterator<Concepto> i = entity.getConceptos().iterator();
			while (i.hasNext()) {
				Concepto o = i.next();
			  //some condition
				if(o.getTipoIva() == 0)
					i.remove();
			}
			
			if(facturar){
				RespuestaProcesarFactura rpf = Facturar(entity);
				if(rpf.getCae() != null && rpf.getCae() != ""){
					entity.setCae(rpf.getCae());
					GregorianCalendar fechaGregorian = new GregorianCalendar();
					fechaGregorian.add(GregorianCalendar.DAY_OF_MONTH, 10);
					entity.setVencimientoCae(fechaGregorian.getTime());
					model.addAttribute("facturado", "El comprobante fue emitido correctamente");
				}else{
					model.addAttribute("observacionesComprobante", rpf.getListaObservaciones());
					model.addAttribute("erroresComprobante", rpf.getListaErrores());
				}
			}
	
			abm.guardar(entity);
	
			model.addAttribute("success", "La creaci&oacuten se realiz&oacute correctamente.");
			
		} catch(Exception e) {
			model.addAttribute("success", "La creaci&oacuten no pudo realizarse.");
		}
		model.addAttribute("loggedinuser", getPrincipal());
		return "redirect:list";
	}
	
	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/editComprobante-{id}" }, method = RequestMethod.GET)
	public String editEntity(@PathVariable int id, ModelMap model) {
		Comprobante entity = abm.buscarPorId(id);
		model.addAttribute("entity", entity);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("titulo", "Editar Comprobante");
		return viewBaseLocation + "/form";
	}

	@RequestMapping(value = { "/editComprobante-{id}" }, method = RequestMethod.POST)
	public String updateEntity(@Valid Comprobante entity, BindingResult result,
			ModelMap model, @PathVariable int id,@RequestParam(required = false) Boolean facturar) {
		try{


			if (result.hasErrors()) {
				return super.viewBaseLocation + "/form";
			}

			
			model.addAttribute("success", "La modificaci&oacuten se realiz&oacute correctamente.");
		} catch (Exception exception) {
			model.addAttribute("success", "La modificaci&oacuten no pudo realizarse.");
		}
		
		Iterator<Concepto> i = entity.getConceptos().iterator();
		while (i.hasNext()) {
			Concepto o = i.next();
		  //some condition
			if(o.getTipoIva() == 0)
				i.remove();
		}
		
		if(facturar){
			RespuestaProcesarFactura rpf = Facturar(entity);
			if(rpf.getCae() != null && rpf.getCae() != ""){
				entity.setCae(rpf.getCae());
				GregorianCalendar fechaGregorian = new GregorianCalendar();
				fechaGregorian.add(GregorianCalendar.DAY_OF_MONTH, 10);
				entity.setVencimientoCae(fechaGregorian.getTime());
				model.addAttribute("facturado", "El comprobante fue emitido correctamente");
			}else{
				model.addAttribute("observacionesComprobante", rpf.getListaObservaciones());
				model.addAttribute("erroresComprobante", rpf.getListaErrores());
			}
		}
		abm.actualizar(entity);

		model.addAttribute("entity", entity);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", super.getPrincipal());
		//return viewBaseLocation + "/list";
		return super.viewBaseLocation + "/form";

	}
	
//	@RequestMapping(value = { "/TraerPreTicketsPorCliente" }, method = RequestMethod.POST)
//	public String traerPreTicketsPorCliente(@RequestParam(required = false) Integer idCliente, ModelMap model) {
//		List<PreTicket> preTickets = preTicketABM.listar();
//		ModelAndView
//		model.addAttribute("entities", preTickets);
//		return viewBaseLocation + "/list";
//		
//	}
//	
	@RequestMapping(value="/TraerPreTicketsPorCliente", method=RequestMethod.GET)
    public ModelAndView TraerPreTicketsPorCliente(@RequestParam  int id) {
        ModelAndView mav = new ModelAndView("abm/comprobante/TraerPreTicketsPorCliente");
        List<PreTicket> preTickets = preTicketABM.TraerPorCliente(id);
        mav.addObject("preTickets", preTickets);
        return mav;
    }
	
	@RequestMapping(value = { "/Download/{id}" }, method = RequestMethod.GET, produces = "application/pdf")
	public @ResponseBody Resource downloadC(HttpServletResponse response, @PathVariable int id) throws FileNotFoundException {
		Comprobante comprobante = abm.buscarPorId(id);
		

		ClassLoader classLoader = getClass().getClassLoader();
		String pathRaiz = classLoader.getResource("").getPath();
		String facturaPath = pathRaiz+"Factura_Nro_"+(comprobante.getNroComprobante())+".pdf";
		String barCodePath = pathRaiz+"cod.gif";
		String limpiadorPruebaPath = "";

        String limpiadorPath = limpiadorPruebaPath;
        String pdfPath = "";
        if(comprobante.esFacturaA()){
            pdfPath = facturaPath;
        }
        String codBarraPath = barCodePath;
        int cantidadCopias = 1;

        boolean isCreated = FacturaToPDF.ObtenerPDF(pdfPath, limpiadorPath, codBarraPath, comprobante,"20359967447",cantidadCopias,true);
		
        facturaPath =  facturaPath.replace(".pdf","_ORIGINAL.pdf");
        
		File file = new File(facturaPath);
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    return new FileSystemResource(file);
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaComprobante", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "fechaFacturadoDesde", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "fechaFacturadoHasta", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "vencimientoPago", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "vencimientoCae", new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(ClienteComprobante.class, new ClienteComprobanteEditor());
		binder.registerCustomEditor(TipoComprobante.class, new TipoComprobanteEditor());
		binder.registerCustomEditor(ConceptosAIncluir.class, new ConceptosAIncluirEditor());
		binder.registerCustomEditor(Moneda.class, new MonedaEditor());
		binder.registerCustomEditor(FormaDePago.class, new FormaDePagoEditor());
		binder.registerCustomEditor(PreTicket.class, new PreTicketEditor());
    }
	
	@ModelAttribute("clienteComprobante")
	public List<ClienteComprobante> initializeClienteComprobante() {
		return clienteComprobanteABM.listar();
	}
	
	@ModelAttribute("tipoComprobante")
	public List<TipoComprobante> initializeTipoComprobante() {
		return tipoComprobanteABM.listar();
	}
	
	@ModelAttribute("conceptosAIncluir")
	public List<ConceptosAIncluir> initializeConceptosAIncluir() {
		return conceptosAIncluirABM.listar();
	}
	
	@ModelAttribute("moneda")
	public List<Moneda> initializeMoneda() {
		return monedaABM.listar();
	}
	
	
	@ModelAttribute("formaDePago")
	public List<FormaDePago> initializeFormaDePago() {
		return formaDePagoABM.listar();
	}
	
	private class ClienteComprobanteEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(clienteComprobanteABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private class TipoComprobanteEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(tipoComprobanteABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private class MonedaEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(monedaABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private class FormaDePagoEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(formaDePagoABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private class ConceptosAIncluirEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(conceptosAIncluirABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private class PreTicketEditor extends PropertyEditorSupport {

		private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		@Override
		public void setAsText(String text) {
			if (text == null || 0 == text.length()) {
				setValue(null);
				return;
			}

			setValue(preTicketABM.buscarPorId(typeConverter.convertIfNecessary(text, Integer.class)));
		}
	}
	
	private RespuestaProcesarFactura Facturar(Comprobante comprobante) {
		ComprobanteDTO f1 = new ComprobanteDTO();
		
		//COMPROBANTE
		f1.setNroDocumentoCliente(comprobante.getClienteComprobante().getDocumento());
		f1.setCodigoDocumento(String.valueOf(comprobante.getClienteComprobante().getTipoDocumento().getIdDocumentoAfip()));
		f1.setFecha(new GregorianCalendar());
		f1.setCodigoComprobante(comprobante.getTipoComprobante().getCodigo());
		f1.setCondicionVenta(String.valueOf(comprobante.getFormaDePago().getCodigo()));
		f1.setIdConcepto(comprobante.getConceptosAIncluir().getCodigo());
		f1.setCantidadRegistros(1);
		//f1.setNumeroComprobante(comprobante.getNroComprobante());
		
		//MONEDA
		f1.setIdMoneda(comprobante.getMoneda().getCodigo());
		f1.setCotizacionMoneda(1);
		
		//IVA
		f1.setTotalIva(comprobante.getTotalIva());
		f1.setIdIva(5);
		f1.setPorcentajeIva(21);
		
		//TOTALES
		f1.setImporteTotal(comprobante.getImporteTotal());
		f1.setImporteNeto(comprobante.getImporteTotal()-comprobante.getTotalIva());
		f1.setSubtotal(comprobante.getImporteTotal()-comprobante.getTotalIva());
		
		//ALICUOTAS IVA
		f1.setAlicIvas(generarArrayAlicuotas(comprobante));
		
		ServicioAfip servicioAfip = new ServicioAfip();
		
		TicketAcceso ticketAcceso = GetTicketAcceso(servicioAfip);
		
		int nroUltimoComprobante = servicioAfip.obtenerUltimoComprobante(ticketAcceso, f1.getCodigoComprobante(),2);
		RespuestaProcesarFactura rpf =servicioAfip.procesarComprobante(f1,ticketAcceso,nroUltimoComprobante,2);
		comprobante.setNroComprobante(rpf.getNroComprobante());

		return rpf;
	}

	
	private TicketAcceso GetTicketAcceso(ServicioAfip servicioAfip) {
		TicketAcceso ticketAcceso = null;
		
		try{
			ticketAcceso = ticketAccesoABM.buscarPorId(1);
		}catch(Exception e){
			
		}
		Calendar calendar = Calendar.getInstance();
        java.util.Date date =  calendar.getTime();
		
		if(ticketAcceso == null){
			ticketAcceso =  servicioAfip.obtenerTicketAcceso();
			if(ticketAcceso.getSign() != null && ticketAcceso.getSign() != "")
				ticketAccesoABM.guardar(ticketAcceso);
		}else if(ticketAcceso.getFechaHoraExpiracion().compareTo(date) < 0){
			TicketAcceso ticketAccesoNuevo =  servicioAfip.obtenerTicketAcceso();
			if(ticketAccesoNuevo.getSign() != null && ticketAccesoNuevo.getSign() != ""){
				ticketAcceso.setCuit(ticketAccesoNuevo.getCuit());
				ticketAcceso.setSign(ticketAccesoNuevo.getSign());
				ticketAcceso.setToken(ticketAccesoNuevo.getToken());
				ticketAcceso.setFechaHoraExpiracion(ticketAccesoNuevo.getFechaHoraExpiracion());
				ticketAccesoABM.actualizar(ticketAcceso);
			}
		}
		return ticketAcceso;
	}
	
	private AlicIva[] generarArrayAlicuotas(Comprobante comprobante){
		double iva0bi = 0;
		double iva0importe = 0;
		double iva105bi = 0;
		double iva105importe = 0;
		double iva21bi = 0;
		double iva21importe = 0;
		double iva27bi = 0;
		double iva27importe = 0;
		for(Concepto concepto : comprobante.getConceptos()){
			if(concepto.getTipoIva() == 3){
				iva0bi = iva0bi + concepto.getPrecio()*concepto.getCantidad();
				iva0importe = 0;
			}
			if(concepto.getTipoIva() == 4){
				iva105bi = iva105bi + concepto.getPrecio()*concepto.getCantidad();
				iva105importe = iva105importe + (concepto.getPrecio()*concepto.getCantidad())*0.105;
			}
			if(concepto.getTipoIva() == 5){
				iva21bi = iva21bi + concepto.getPrecio()*concepto.getCantidad();
				iva21importe = iva21importe + (concepto.getPrecio()*concepto.getCantidad())*0.21;
			}
			if(concepto.getTipoIva() == 6){
				iva27bi = iva27bi + concepto.getPrecio()*concepto.getCantidad();
				iva27importe = iva27importe + (concepto.getPrecio()*concepto.getCantidad())*0.27;
			}
		}
		List<AlicIva> alicuotasList = new ArrayList<AlicIva>();
		if(iva0bi>0)
			alicuotasList.add(new AlicIva(3,iva0bi, iva0importe));
		if(iva105bi>0)
			alicuotasList.add(new AlicIva(4,iva105bi, iva105importe));
		if(iva21bi>0)
			alicuotasList.add(new AlicIva(5,iva21bi, iva21importe));
		if(iva27bi>0)
			alicuotasList.add(new AlicIva(6,iva27bi, iva27importe));
		
		AlicIva[] alicIvas = new AlicIva[alicuotasList.size()];
		alicuotasList.toArray(alicIvas);
		
		return alicIvas;
	}
	

}