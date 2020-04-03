package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.CentroRepositorio;
import es.ucm.pcr.repositorio.PasswordTokenRepositorio;
import es.ucm.pcr.repositorio.RolRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicioImp implements UsuarioServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;
	
	@Autowired
	CentroRepositorio centroRepositorio;
	
	@Autowired
	RolRepositorio rolRepositorio;
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	PasswordTokenRepositorio passwordTokenRepositorio;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		email = email.trim().toLowerCase();
		Optional<Usuario> usuario = usurep.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			return null;
		}
	}

	@Override
	public Set<Rol> getRoles(Usuario usuario) {
		Optional<Usuario> usuWithRoles = usurep.findByEmailWithRoles(usuario.getEmail());
		if (usuWithRoles.isPresent()) {
			return usuWithRoles.get().getRols();
		} else {
			return null;
		}
	}
	
	// Un usuario puede no estar asociado a ningún centro,
	// estar asociado a un centro de salud, a un laboratorio UCM
	// o a un laboratotrio Visavet.
	// Para laboratorio Ucm y Visavet el bean guarda su Id, pero 
	// para centros de salud guarda una entidad Centro, que puede
	// ser null,o el id de dicho centro.
	// Esta función devuelve el valos de ese IdCentro es caso de que exista
	public Integer centroSaludSeleccionadoUsuario (Usuario usuario) throws Exception
	{
		Integer centroSeleccionado;
		if (usuario.getCentro() != null)
		{
			centroSeleccionado = usuario.getCentro().getId();
		}
		else
		{
			centroSeleccionado = null;
		}
		return centroSeleccionado;
	}
	
	// Nos dice si un usuario tiene asignado un Centro de salud, un laboratorio UCM,
	// Un laboratorio Visavet o ninguno de ellos.
	public  String tipoCentroSeleccionadoUsuario (Usuario usuario) throws Exception
	{
		String tipoCentroAsignado = null;
        // Es un centro de salud
        if ( usuario.getCentro() != null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() == null)
        {
        	tipoCentroAsignado = "C";
        }
        // Es un laboratorio Centro UCM
        if ( usuario.getCentro() == null && usuario.getIdLaboratorioCentro() != null && usuario.getIdLaboratorioVisavet() == null)
        {
        	tipoCentroAsignado = "L";
        }  
        // Es un laboratorio Visavet
        if (usuario.getCentro() == null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() != null)
        {
        	tipoCentroAsignado = "V";
        }  
        // Todos null; A elegir
        if (usuario.getCentro() == null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() == null)
        {
        	tipoCentroAsignado = null;
        }  
        return tipoCentroAsignado;
	}
	
	@Override
	public BeanUsuarioGestion mapeoEntidadBeanUsuario (Usuario usuario) throws Exception
	{
		BeanUsuarioGestion beanUsuario =  new BeanUsuarioGestion();
		
		beanUsuario.setId(usuario.getId());
		beanUsuario.setAcertadas(usuario.getAcertadas());
		beanUsuario.setApellido1(usuario.getApellido1());
		beanUsuario.setApellido2(usuario.getApellido2());
		beanUsuario.setAsignadas(usuario.getAsignadas());
		beanUsuario.setCentro(usuario.getCentro());
		beanUsuario.setDocumentos(usuario.getDocumentos());
		beanUsuario.setEmail(usuario.getEmail());
		beanUsuario.setHabilitado(usuario.getHabilitado());
		beanUsuario.setId(usuario.getId());
		beanUsuario.setIdLaboratorioCentro(usuario.getIdLaboratorioCentro());
		beanUsuario.setIdLaboratorioVisavet(usuario.getIdLaboratorioVisavet());
		beanUsuario.setNombre(usuario.getNombre());
		beanUsuario.setPassword(usuario.getPassword());
		beanUsuario.setRols(usuario.getRols());
		beanUsuario.setUsuarioMuestras(usuario.getUsuarioMuestras());	
        beanUsuario.setCentroSeleccionado(centroSaludSeleccionadoUsuario(usuario));
        beanUsuario.setTipoCentroSeleccionado(tipoCentroSeleccionadoUsuario(usuario));
 		
		return beanUsuario;
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioAlta (BeanUsuarioGestion beanUsuario, int[] roles) throws Exception{
		
		Usuario usuario = new Usuario();
		
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, pero no puede ir vacio
		if (beanUsuario.getPassword() == null)
		{
			usuario.setPassword("PWD");
		}
		else
		{
			usuario.setPassword(beanUsuario.getPassword());
		}		
		// Añado los roles seleccionado
		Set<Rol> rolesSeleccionados = new HashSet<Rol>(0);
		if(roles != null) 
		{
		    for (int i = 0; i < roles.length; i++)  
		    {
		    	Optional<Rol> rol = rolRepositorio.findById(roles[i]);
		    	rolesSeleccionados.add(rol.get());
		    }
		}
		usuario.setRols(rolesSeleccionados);
		// Centro seleccionado
		// Según el tipo de centro seleccionado, almaceno los tres 
		// posibles centros: de salud, lab. UCM y Lab. Visavet
		switch (beanUsuario.getTipoCentroSeleccionado()) 
		{
			// Centro de salud
			case "C":
				// Si el centro seleccionado corresponde a un centro existente,
				// distinto de null, comprobamos si hay que asociarlo al usuario
				if (centroRepositorio.existsById(beanUsuario.getCentroSeleccionado()))
				{
						Optional<Centro> centroGuardar = centroRepositorio.findById(beanUsuario.getCentroSeleccionado());
						usuario.setCentro(centroGuardar.get());
				}
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(null);
				break;
				
			// laboratorio centro Ucm
			case "L":
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());
				usuario.setIdLaboratorioVisavet(null);
				break;
				
			// Laboratorio Visavet
			case "V":
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
				break;
				
			// Sin centro asignado
			default:
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(null);
				break;
		}	
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
		
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioModificar (BeanUsuarioGestion beanUsuario, Usuario usuario, int[] roles) throws Exception {
		
		// No asigno el id del usuario
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		//  El mail es único, y asociado al usuario, no poemos modifciarlo
//		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, no podemos modificarlo
//		usuario.setPassword(beanUsuario.getPassword());
		// Añado los roles seleccionado
		Set<Rol> rolesSeleccionados = new HashSet<Rol>(0);
		if(roles != null) 
		{
		    for (int i = 0; i < roles.length; i++)  
		    {
		    	Optional<Rol> rol = rolRepositorio.findById(roles[i]);
		    	rolesSeleccionados.add(rol.get());
		    }
		}
		usuario.setRols(rolesSeleccionados);
		
		// Centro seleccionado
		// Según el tipo de centro seleccionado, almaceno los tres 
		// posibles centros: de salud, lab. UCM y Lab. Visavet
		switch (beanUsuario.getTipoCentroSeleccionado()) 
		{
			// Centro de salud
			case "C":
				// Si el centro seleccionado corresponde a un centro existente,
				// distinto de null, comprobamos si hay que asociarlo al usuario
				if (centroRepositorio.existsById(beanUsuario.getCentroSeleccionado()))
				{
						Optional<Centro> centroGuardar = centroRepositorio.findById(beanUsuario.getCentroSeleccionado());
						usuario.setCentro(centroGuardar.get());
				}
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(null);
				break;
				
			// laboratorio centro Ucm
			case "L":
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());
				usuario.setIdLaboratorioVisavet(null);
				break;
				
			// Laboratorio Visavet
			case "V":
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
				break;
				
			// Sin centro asignado
			default:
				usuario.setCentro(null);
				usuario.setIdLaboratorioCentro(null);
				usuario.setIdLaboratorioVisavet(null);
				break;
		}	
		
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
	}
	
	public List<BeanUsuarioGestion> listaUsuariosOrdenada() throws Exception {
		
		List<BeanUsuarioGestion> listaUsuarios = new ArrayList<BeanUsuarioGestion>();
		for (Usuario usuario: usuarioRepositorio.findAll())
		{
			BeanUsuarioGestion beanUsuarioGestion = new BeanUsuarioGestion();
			beanUsuarioGestion = mapeoEntidadBeanUsuario (usuario);
			listaUsuarios.add(beanUsuarioGestion);
//			beanUsuarioGestion.setAccion("L");
//			beanUsuarioGestion.setAcertadas(usuario.getAcertadas());
//			beanUsuarioGestion.setApellido1(usuario.getApellido1());
//			beanUsuarioGestion.setApellido2(usuario.getApellido2());
//			beanUsuarioGestion.setAsignadas(usuario.getAsignadas());
//			beanUsuarioGestion.setCentro(usuario.getCentro());
//			beanUsuarioGestion.setDocumentos(usuario.getDocumentos());
//			beanUsuarioGestion.setEmail(usuario.getEmail());
//			beanUsuarioGestion.setHabilitado(usuario.getHabilitado());
//			beanUsuarioGestion.setId(usuario.getId());
//			beanUsuarioGestion.setIdLaboratorioCentro(usuario.getIdLaboratorioCentro());
//			beanUsuarioGestion.setIdLaboratorioVisavet(usuario.getIdLaboratorioVisavet());
//			beanUsuarioGestion.setNombre(usuario.getNombre());
//			beanUsuarioGestion.setPassword(usuario.getPassword());
//			beanUsuarioGestion.setRols(usuario.getRols());
//			beanUsuarioGestion.setUsuarioMuestras(usuario.getUsuarioMuestras());
//			// Tipo de centro seleccionado
//			
//			// Centro Seccionado
			
			
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaUsuarios);
		return listaUsuarios;

	}
	
	public void createPasswordResetTokenForUser(Usuario user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(user,token);
	    passwordTokenRepositorio.save(myToken);
	}

	@Override
	public void cambiarContrasena(String email, String contrasena) {
		Usuario user = buscarUsuarioPorEmail(email);
		user.setPassword(passwordEncoder.encode(contrasena));
		user.setHabilitado("A");
		usurep.save(user);
		passwordTokenRepositorio.deleteByUsuario(user);
		
	}

	@Override
	public List<Usuario> buscarUsuarioInhabilitados() {
		return usurep.findByHabilitadoOrderById("I");
	}

	@Override 
	public Usuario guardar(Usuario usuario) {
		usurep.save(usuario);
		return usuario;
	}
	

	
}
