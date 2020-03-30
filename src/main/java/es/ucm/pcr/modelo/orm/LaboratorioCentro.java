package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 12:25:35 by Hibernate Tools 5.2.12.Final

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
 * LaboratorioCentro generated by hbm2java
 */
@Entity
@Table(name = "laboratorioCentro")
public class LaboratorioCentro implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<PlacaLaboratorio> placaLaboratorios = new HashSet<PlacaLaboratorio>(0);
	private Set<Equipo> equipos = new HashSet<Equipo>(0);

	public LaboratorioCentro() {
	}

	public LaboratorioCentro(String nombre) {
		this.nombre = nombre;
	}

	public LaboratorioCentro(String nombre, Set<Documento> documentos, Set<PlacaLaboratorio> placaLaboratorios,
			Set<Equipo> equipos) {
		this.nombre = nombre;
		this.documentos = documentos;
		this.placaLaboratorios = placaLaboratorios;
		this.equipos = equipos;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratorioCentro")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratorioCentro")
	public Set<PlacaLaboratorio> getPlacaLaboratorios() {
		return this.placaLaboratorios;
	}

	public void setPlacaLaboratorios(Set<PlacaLaboratorio> placaLaboratorios) {
		this.placaLaboratorios = placaLaboratorios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratorioCentro")
	public Set<Equipo> getEquipos() {
		return this.equipos;
	}

	public void setEquipos(Set<Equipo> equipos) {
		this.equipos = equipos;
	}

}
