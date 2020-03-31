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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * LaboratorioVisavet generated by hbm2java
 */
@Entity
@Table(name = "laboratorioVisavet")
public class LaboratorioVisavet implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4091833129817479905L;
	private Integer id;
	private String nombre;
	private Integer capacidad;
	private Integer ocupacion;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<PlacaVisavet> placaVisavets = new HashSet<PlacaVisavet>(0);

	public LaboratorioVisavet() {
	}

	public LaboratorioVisavet(String nombre) {
		this.nombre = nombre;
	}

	public LaboratorioVisavet(String nombre, Integer capacidad, Integer ocupacion, Set<Documento> documentos,
			Set<PlacaVisavet> placaVisavets) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.ocupacion = ocupacion;
		this.documentos = documentos;
		this.placaVisavets = placaVisavets;
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

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "capacidad")
	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	@Column(name = "ocupacion")
	public Integer getOcupacion() {
		return this.ocupacion;
	}

	public void setOcupacion(Integer ocupacion) {
		this.ocupacion = ocupacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratorioVisavet")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratorioVisavet")
	public Set<PlacaVisavet> getPlacaVisavets() {
		return this.placaVisavets;
	}

	public void setPlacaVisavets(Set<PlacaVisavet> placaVisavets) {
		this.placaVisavets = placaVisavets;
	}

}
