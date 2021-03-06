/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;

@Controller
public class LaboratorioVisavetControlador {
	
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los laboratorioVisavets
	// Punto de entrada a la gestión de laboratorioVisavets
	@RequestMapping(value="/gestor/listaLaboratorioVisavet", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionLaboratorioVisavet(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionLaboratorioVisavet");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
	
		// cargo todos los laboratorioVisavets de BBDD
		List<BeanLaboratorioVisavet> listaLaboratorioVisavet = new ArrayList<BeanLaboratorioVisavet>();
		listaLaboratorioVisavet = laboratorioVisavetServicio.listaLaboratoriosVisavetOrdenada();
		vista.addObject("listaLaboratorioVisavet", listaLaboratorioVisavet);
		return vista;
	}	
	
	// da de alta un nuevo laboratorio Visavet
	@RequestMapping(value="/gestor/altaLaboratorioVisavet", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaLaboratorioVisavet(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLaboratorioVisavet");
	
		BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
		// le indicamos la acción a relizar: A alta de un rol
		beanLaboratorioVisavet.setAccion("A");
		vista.addObject("formBeanLaboratorioVisavet", beanLaboratorioVisavet);

		return vista;
	}
	
	   // Alta/modificación de laboratorioVisavet 
		@RequestMapping(value="/gestor/altaLaboratorioVisavet", method=RequestMethod.POST)	
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView grabarAltaLaboratorioVisavet ( @ModelAttribute("formBeanLaboratorioVisavet") BeanLaboratorioVisavet beanLaboratorioVisavet, HttpSession session) throws Exception {

			// Damos de alta nuevo laboratorioVisavet
			if (beanLaboratorioVisavet.getAccion().equals("A"))
			{
				laboratorioVisavetServicio.save(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
				
			}
			// Modificamos laboratorioVisavet existente, menos mail
			if (beanLaboratorioVisavet.getAccion().equals("M"))
			{	
				// Buscamos el laboratorioVisavet a modificar, y volcamos los datos recogidos por el formulario
				Optional<LaboratorioVisavet> laboratorioVisavet = laboratorioVisavetServicio.findById(beanLaboratorioVisavet.getId());
				// añadimos campos del formulario
				laboratorioVisavetServicio.save(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
			}

			// Volvemos a grabar mas laboratorioVisavet
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioVisavet",true));	
			return vista;		
		}	
		
		// Modificamos un laboratorioVisavet
		@RequestMapping(value = "/gestor/editarLaboratorioVisavet", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView editarLaboratorioVisavet(@RequestParam("idLaboratorioVisavet") Integer idLaboratorioVisavet) throws Exception {

			ModelAndView vista = new ModelAndView("VistaLaboratorioVisavet");
			
			// Busco el laboratorioVisavet a modificar
			Optional<LaboratorioVisavet> laboratorioVisavet = laboratorioVisavetServicio.findById(idLaboratorioVisavet);
			// cargo el beanLaboratorioVisavet con lo datos del laboratorioVisavet a modificar
			BeanLaboratorioVisavet beanLaboratorioVisavet = laboratorioVisavetServicio.mapeoEntidadBeanLaboratorioVisavet(laboratorioVisavet.get());
			
			// le indicamos la acción a relizar: M modificación de un laboratorioVisavet
			beanLaboratorioVisavet.setAccion("M");
			
			vista.addObject("formBeanLaboratorioVisavet", beanLaboratorioVisavet);
		
			return vista;
		}		
		
		@RequestMapping(value = "/gestor/borrarLaboratorioVisavet", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView borrarLaboratorioVisavet(@RequestParam("idLaboratorioVisavet") Integer idLaboratorioVisavet, HttpSession session) throws Exception {
			try {
			laboratorioVisavetServicio.deleteById(idLaboratorioVisavet);
			} catch (DataIntegrityViolationException e) {
				String mensaje = "No se puede borrar el centro " + laboratorioVisavetServicio.findById(idLaboratorioVisavet).get().getNombre() + " porque tiene información asociada.";
				session.setAttribute("mensajeError", mensaje);
			}
			
			// Volvemos a grabar mas visavet
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioVisavet",true));	
			return vista;
		}		
		
}
