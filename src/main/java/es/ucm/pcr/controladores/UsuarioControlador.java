package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.BeanUsuario;

@Controller
public class UsuarioControlador {
	
	@RequestMapping(value="/gestor/gestionUsuario", method=RequestMethod.GET)
	public ModelAndView AltaUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaUsuario");
	
		BeanUsuario beanUsuario = new BeanUsuario();
		
		vista.addObject("formBeanUsuario", beanUsuario);
		
//		// Añado los roles
//		List<BeanRol> listaRoles = new ArrayList<BeanRol>();
//		listaRoles.add(new BeanRol(1, "Analista"));
//		listaRoles.add(new BeanRol(2, "Supersivor"));
//		listaRoles.add(new BeanRol(3, "Técnicp laboratorio"));
//		listaRoles.add(new BeanRol(4, "Auditor"));		
//		vista.addObject("listaRoles", listaRoles);
		
		List<String> listaRoles2 = new ArrayList<String>();
		listaRoles2.add("Analista");
		listaRoles2.add("Programador");
		listaRoles2.add("Jefe proyecto");
		vista.addObject("listaRoles2", listaRoles2);
		
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="/gestor/gestionUsuario", method=RequestMethod.POST)	
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuario beanUsuario, HttpSession session) throws Exception {

		System.out.println("Usuario a grabar: " + beanUsuario.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/gestionUsuario",true));	
		return vista;
		
	}	
	
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
	public ModelAndView GestionUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
	
		List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));

		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));

		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));

		vista.addObject("listaUsuarios", listaUsuarios);
	
		return vista;
	}
	
	@RequestMapping(value = "/gestor/editarUsuario", method = RequestMethod.GET)
	public ModelAndView editarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

		System.out.println("Usuario a editar: " + idUsuario);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/gestionUsuario",true));	
		return vista;
	}
	
	@RequestMapping(value = "/gestor/borrarUsuario", method = RequestMethod.GET)
	public ModelAndView borrarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

		System.out.println("Usuario a borrar: " + idUsuario);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;
	}
	

}
