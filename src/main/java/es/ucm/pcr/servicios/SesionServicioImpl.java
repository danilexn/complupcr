package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.config.security.PcrUserDetails;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;

@Service
public class SesionServicioImpl implements SesionServicio {

	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;

	@Override
	public Usuario getUsuario() {
		String email = this.getEmail();
		if (email == null) {
			return null;
		}
		return usuarioServicio.buscarUsuarioPorEmail(email);
	}

	@Override
	public String getEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		String email = authentication.getName();
		return email;
	}

	@Override
	public List<String> getRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority auth : authorities) {
			roles.add(auth.getAuthority());
		}
		return roles;
	}

	@Override
	public Boolean tieneRol(String rol) {
		String rolSprSec = "ROLE_" + rol;
		return this.getRoles().contains(rolSprSec);
	}

	@Override
	public Centro getCentro() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PcrUserDetails ud = (PcrUserDetails) authentication.getPrincipal();
		return ud.getUsuario().getCentro();
	}

	@Override
	public List<MenuBean> getMenu() {
		List<MenuBean> menuPrincipal = null;
		List<MenuBean> menuSecundario = null;
		MenuBean opcionSecundaria = null;
		MenuBean opcionPrincipal = null;

		menuPrincipal = new ArrayList<MenuBean>();
//	Centro de Salud	
		if (this.tieneRol("ADMIN") || this.tieneRol("CENTROSALUD")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Gestión muestras", "/centroSalud/muestra", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestión lotes", "/centroSalud/lote", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Centro de salud", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//  Recepcion Laboratorio
		if (this.tieneRol("ADMIN") || this.tieneRol("RECEPCIONLABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Gestionar entregas", "/laboratorioUni/buscarLotes", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Recepción laboratorio", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//	Tecnico laboratorio
		if (this.tieneRol("ADMIN") || this.tieneRol("TECNICOLABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Gestionar entregas", "/laboratorioUni/buscarLotes", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestionar placas", "/laboratorioUni/buscarPlacas", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Tecnico laboratorio", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Responsable PCR
		if (this.tieneRol("ADMIN") || this.tieneRol("RESPONSABLEPCR")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Recepción placas", "/laboratorioCentro/recepcionPlacas", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestión de placas", "/laboratorioCentro/gestionPlacas", null);
			menuSecundario.add(opcionSecundaria);			
			opcionPrincipal = new MenuBean("Responsable PCR", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Jefe de servicio
		if (this.tieneRol("ADMIN") || this.tieneRol("JEFESERVICIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Asignar y revisar muestras", "/analisis/", null);
			menuSecundario.add(opcionSecundaria);
			//opcionSecundaria = new MenuBean("Revisar  muestras", "", null);
			//menuSecundario.add(opcionSecundaria);
			//opcionSecundaria = new MenuBean("Estado  muestras", "", null);
			//menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Coger placas", "/analisis/cogerPlacas", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Jefe de servicio", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//			Analista
		if (this.tieneRol("ADMIN") || this.tieneRol("ANALISTALABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Revisar muestras", "/analisis/listarMuestrasAnalista", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Analista", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//	GESTOR
		if (this.tieneRol("ADMIN") || this.tieneRol("GESTOR")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Centros de salud", "/gestor/listaCentros", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Laboratorios Centros UCM","/gestor/listaLaboratorioCentro",null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Laboratorios Logísticos","/gestor/listaLaboratorioVisavet",null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Usuarios","/gestor/listaUsuarios",null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Gestor", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
		
		return menuPrincipal;
	}

	@Override
	public LaboratorioVisavet getLaboratorioVisavet() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PcrUserDetails ud = (PcrUserDetails) authentication.getPrincipal();
		Integer idLabV = ud.getUsuario().getIdLaboratorioVisavet();
		Optional<LaboratorioVisavet> labV = laboratorioVisavetRepositorio.findById(idLabV);
		if (labV.isPresent()) {
			return labV.get();
		} else {
			return null;
		}
	}

	@Override
	public LaboratorioCentro getLaboratorioCentro() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PcrUserDetails ud = (PcrUserDetails) authentication.getPrincipal();
		Integer idLabC = ud.getUsuario().getIdLaboratorioCentro();
		Optional<LaboratorioCentro> labC = laboratorioCentroRepositorio.findById(idLabC);
		if (labC.isPresent()) {
			return labC.get();
		} else {
			return null;
		}
	}
}
