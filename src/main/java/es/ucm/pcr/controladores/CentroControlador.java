package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.repositorio.CentroRepositorio;
import es.ucm.pcr.servicios.CentroServicio;


@Controller
public class CentroControlador {
	
	@Autowired
	CentroRepositorio centroRepositorio;
	
	@Autowired
	CentroServicio centroServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los centros
	// Punto de entrada a la gestión de centros
	@RequestMapping(value="/gestor/listaCentros", method=RequestMethod.GET)
	public ModelAndView GestionCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionCentros");
	
		// cargo todos los rols de BBDD
		List<BeanCentro> listaCentros = new ArrayList<BeanCentro>();
		for (Centro centro: centroRepositorio.findAll())
		{
			listaCentros.add( 
					new BeanCentro(
							centro.getId(), 
							centro.getNombre(), 
							centro.getCodCentro(), 
							centro.getTelefono(), 
							centro.getEmail(), 
							centro.getDireccion(),
							centro.getUsuarios(),
							centro.getMuestras(),
							centro.getDocumentos(),
							centro.getLotes(),
							"L")
			);
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaCentros);
		vista.addObject("listaCentros", listaCentros);
	
		return vista;
	}	
	
	// da de alta un nuevo centro
	@RequestMapping(value="/gestor/altaCentro", method=RequestMethod.GET)
	public ModelAndView AltaCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaCentro");
	
		BeanCentro beanCentro = new BeanCentro();
		// le indicamos la acción a relizar: A alta de un centro
		beanCentro.setAccion("A");
		vista.addObject("formBeanCentro", beanCentro);

		return vista;
	}
	
   // Alta/modificación de centro 
	@RequestMapping(value="/gestor/altaCentro", method=RequestMethod.POST)	
	public ModelAndView grabarAltaCentro ( @ModelAttribute("formBeanCentro") BeanCentro beanCentro, HttpSession session) throws Exception {

		// Damos de alta nuevo centro
		if (beanCentro.getAccion().equals("A"))
		{
//			Centro centro = new Centro();
//			centro.setCodCentro(beanCentro.getCodCentro());
//			centro.setEmail(beanCentro.getMailCentro());
//			centro.setNombre(beanCentro.getDesCentro());
//			centro.setTelefono(beanCentro.getTelefonoCentro());
//			centroRepositorio.save(centro);
			
//			centroRepositorio.save(centroServicio.mapeoBeanEntidadCentro(beanCentro));
			centroServicio.guardarCentro(centroServicio.mapeoBeanEntidadCentro(beanCentro));
		}
		// Modificamos centro existente
		if (beanCentro.getAccion().equals("M"))
		{
			// Buscamos el centro a modificar, y volcamos los datos recogidos por el formulario
			Optional<Centro> centro = centroRepositorio.findById(beanCentro.getId());
			// añadimos campos del formulario
//			centro.get().setCodCentro(beanCentro.getCodCentro());
//			centro.get().setEmail(beanCentro.getMailCentro());
//			centro.get().setNombre(beanCentro.getDesCentro());
//			centro.get().setTelefono(beanCentro.getTelefonoCentro());
//			centroRepositorio.save(centro.get());
//			centroRepositorio.save(centroServicio.mapeoBeanEntidadCentro(beanCentro));
			centroServicio.guardarCentro(centroServicio.mapeoBeanEntidadCentro(beanCentro));
		}

		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaCentros",true));	
		return vista;		
	}	
	
	// Modificamos un centro
	@RequestMapping(value = "/gestor/editarCentro", method = RequestMethod.GET)
	public ModelAndView editarCentro(@RequestParam("idCentro") Integer idCentro) throws Exception {

		ModelAndView vista = new ModelAndView("VistaCentro");
		
		// Busco el centro a modificar
		Optional<Centro> centro = centroRepositorio.findById(idCentro);
		// cargo el beanCentro con lo datos del centro a modificar
//		BeanCentro beanCentro = new BeanCentro();
//		beanCentro.setIdCentro(centro.get().getId());
//		beanCentro.setCodCentro(centro.get().getCodCentro());
//		beanCentro.setDesCentro(centro.get().getNombre());
//		beanCentro.setMailCentro(centro.get().getEmail());
//		beanCentro.setResponsableCentro("RESPONSAABLE");
//		beanCentro.setTelefonoCentro(centro.get().getTelefono());
//		beanCentro.setTelefonoResponsableCentro(centro.get().getTelefono());
//		
		BeanCentro beanCentro = centroServicio.mapeoEntidadBeanCentro(centro.get());
	
		// le indicamos la acción a relizar: M modificación de un centro
		beanCentro.setAccion("M");
		
		vista.addObject("formBeanCentro", beanCentro);
	
		return vista;
	}	
	
	@RequestMapping(value = "/gestor/borrarCentro", method = RequestMethod.GET)
	public ModelAndView borrarCentro(@RequestParam("idCentro") Integer idCentro) throws Exception {
		
//		centroRepositorio.deleteById(idCentro);
		centroServicio.BorrarCentro(idCentro);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaCentros",true));	
		return vista;
	}	
	
	
//	@RequestMapping(value="AltaCentro", method=RequestMethod.GET)
//	public ModelAndView AltaCentro(HttpSession session) throws Exception {
//		ModelAndView vista = new ModelAndView("VistaCentro");
//	
//		BeanCentro beanCentro = new BeanCentro();
//		
//		vista.addObject("formBeanCentro", beanCentro);
//		return vista;
//	}
//	
//   // Alta de centro 
//	@RequestMapping(value="AltaCentro", method=RequestMethod.POST)	
//	public ModelAndView grabarAltaCentro ( @ModelAttribute("formBeanCentro") BeanCentro beanCentro, HttpSession session) throws Exception {
//
//		System.out.println("Centro a grabar: " + beanCentro.toString());
//		
//		// Volvemos a grabar mas centros
//		ModelAndView vista = new ModelAndView(new RedirectView("AltaCentro",true));	
//		return vista;
//		
//	}

}
