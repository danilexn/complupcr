package es.ucm.pcr.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.ServicioLog;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.validadores.LogMuestraValidador;

@Controller
@RequestMapping(value = "/gestor")
public class LogMuestraControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	// TODO - LOG, TRAZAR SERVICIOS
	// TODO - LOTE - LABORATORIO
	// TODO - LOTE - MUESTRAS
	// TODO - LOTE - ACCION, ORDENACION, PAGINACION
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "muestra.id", "fechaCambio");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Autowired
	private LogMuestraValidador logValidadorMuestra;
	
	@InitBinder("beanBusqueda")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(logValidadorMuestra);
	}
	
	@ModelAttribute("listaLotes")
	public List<LoteListadoBean> lotes() {
		return loteServicio.findLoteByParam(new LoteBusquedaBean(sesionServicio.getCentro().getId()));
	}
	
	@RequestMapping(value="/log", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");
	
		LogMuestraBusquedaBean beanBusqueda = new LogMuestraBusquedaBean();	
		
		vista.addObject("beanBusqueda", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value = "/log/list", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView buscar(@Valid @ModelAttribute("beanBusqueda") LogMuestraBusquedaBean beanBusqueda, BindingResult result, HttpSession session) throws Exception {
		
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");
		if (result.hasErrors()) {
			vista.addObject("beanBusqueda", beanBusqueda);
			
		} else {		
			session.setAttribute("beanBusqueda", beanBusqueda);
			buscarMuestras(beanBusqueda, vista);
		}
		return vista;
	}

	@RequestMapping(value = "/log/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView buscar(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");

		LogMuestraBusquedaBean beanBusqueda = (LogMuestraBusquedaBean) session.getAttribute("beanBusqueda");
		beanBusqueda = beanBusqueda != null ? beanBusqueda : new LogMuestraBusquedaBean();

		buscarMuestras(beanBusqueda, vista);
		return vista;
	}

	private void buscarMuestras(LogMuestraBusquedaBean beanBusqueda, ModelAndView vista) {
		Page<LogMuestraListadoBean> muestrasPage = servicioLog.findLogMuestraByParam(beanBusqueda,
				PageRequest.of(0, Integer.MAX_VALUE, ORDENACION));
		vista.addObject("beanBusqueda", beanBusqueda);
		vista.addObject("listadoLogMuestras", muestrasPage.getContent());
	}
	
}