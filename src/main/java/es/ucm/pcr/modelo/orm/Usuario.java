package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4869922886448788928L;
	private Integer id;
	private Centro centro;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String password;
	private Integer idLaboratorioVisavet;
	private Integer idLaboratorioCentro;
	private Integer asignadas;
	private Integer acertadas;
	private String habilitado;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<UsuarioMuestra> usuarioMuestras = new HashSet<UsuarioMuestra>(0);
	private Set<Rol> rols = new HashSet<Rol>(0);
	private Set<PlacaLaboratorio> placasLaboratorio = new HashSet<PlacaLaboratorio>(0);

	public Usuario() {
	}

	public Usuario(String nombre, String apellido1, String email, String password, String habilitado) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.email = email;
		this.password = password;
		this.habilitado = habilitado;
	}

	public Usuario(Centro centro, String nombre, String apellido1, String apellido2, String email, String password,
			Integer idLaboratorioVisavet, Integer idLaboratorioCentro, Integer asignadas, Integer acertadas,
			String habilitado, Set<Documento> documentos,
			Set<UsuarioMuestra> usuarioMuestras, Set<Rol> rols) {
		this.centro = centro;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.password = password;
		this.idLaboratorioVisavet = idLaboratorioVisavet;
		this.idLaboratorioCentro = idLaboratorioCentro;
		this.asignadas = asignadas;
		this.acertadas = acertadas;
		this.habilitado = habilitado;
		this.documentos = documentos;
		this.usuarioMuestras = usuarioMuestras;
		this.rols = rols;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idCentro")
	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido1", nullable = false, length = 100)
	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "apellido2", length = 100)
	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Column(name = "email", unique = true, nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "idLaboratorioVisavet")
	public Integer getIdLaboratorioVisavet() {
		return this.idLaboratorioVisavet;
	}

	public void setIdLaboratorioVisavet(Integer idLaboratorioVisavet) {
		this.idLaboratorioVisavet = idLaboratorioVisavet;
	}

	@Column(name = "idLaboratorioCentro")
	public Integer getIdLaboratorioCentro() {
		return this.idLaboratorioCentro;
	}

	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}

	@Column(name = "asignadas")
	public Integer getAsignadas() {
		return this.asignadas;
	}

	public void setAsignadas(Integer asignadas) {
		this.asignadas = asignadas;
	}

	@Column(name = "acertadas")
	public Integer getAcertadas() {
		return this.acertadas;
	}

	public void setAcertadas(Integer acertadas) {
		this.acertadas = acertadas;
	}

	@Column(name = "habilitado", nullable = false)
	public String getHabilitado() {
		return this.habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<UsuarioMuestra> getUsuarioMuestras() {
		return this.usuarioMuestras;
	}

	public void setUsuarioMuestras(Set<UsuarioMuestra> usuarioMuestras) {
		this.usuarioMuestras = usuarioMuestras;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_rol", joinColumns = {
			@JoinColumn(name = "idUsuario", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "idRol", nullable = false, updatable = false) })
	public Set<Rol> getRols() {
		return this.rols;
	}

	public void setRols(Set<Rol> rols) {
		this.rols = rols;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<PlacaLaboratorio> getPlacasLaboratorio() {
		return placasLaboratorio;
	}

	public void setPlacasLaboratorio(Set<PlacaLaboratorio> placasLaboratorio) {
		this.placasLaboratorio = placasLaboratorio;
	}
	
	

}
