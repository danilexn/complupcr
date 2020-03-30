package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 12:25:35 by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Documento generated by hbm2java
 */
@Entity
@Table(name = "documento")
public class Documento implements java.io.Serializable {

	private Integer id;
	private Centro centro;
	private LaboratorioCentro laboratorioCentro;
	private LaboratorioVisavet laboratorioVisavet;
	private Muestra muestra;
	private PlacaLaboratorio placaLaboratorio;
	private PlacaVisavet placaVisavet;
	private Usuario usuario;
	private String nombre;
	private String tipo;
	private byte[] fichero;
	private Integer tamanoDocumento;
	private String fechaDocumento;

	public Documento() {
	}

	public Documento(String nombre, String tipo, byte[] fichero) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.fichero = fichero;
	}

	public Documento(Centro centro, LaboratorioCentro laboratorioCentro, LaboratorioVisavet laboratorioVisavet,
			Muestra muestra, PlacaLaboratorio placaLaboratorio, PlacaVisavet placaVisavet, Usuario usuario,
			String nombre, String tipo, byte[] fichero, Integer tamanoDocumento, String fechaDocumento) {
		this.centro = centro;
		this.laboratorioCentro = laboratorioCentro;
		this.laboratorioVisavet = laboratorioVisavet;
		this.muestra = muestra;
		this.placaLaboratorio = placaLaboratorio;
		this.placaVisavet = placaVisavet;
		this.usuario = usuario;
		this.nombre = nombre;
		this.tipo = tipo;
		this.fichero = fichero;
		this.tamanoDocumento = tamanoDocumento;
		this.fechaDocumento = fechaDocumento;
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

	@ManyToOne
	@JoinColumn(name = "idLaboratorioCentro")
	public LaboratorioCentro getLaboratorioCentro() {
		return this.laboratorioCentro;
	}

	public void setLaboratorioCentro(LaboratorioCentro laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
	}

	@ManyToOne
	@JoinColumn(name = "idLaboratorioVisavet")
	public LaboratorioVisavet getLaboratorioVisavet() {
		return this.laboratorioVisavet;
	}

	public void setLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) {
		this.laboratorioVisavet = laboratorioVisavet;
	}

	@ManyToOne
	@JoinColumn(name = "idMuestra")
	public Muestra getMuestra() {
		return this.muestra;
	}

	public void setMuestra(Muestra muestra) {
		this.muestra = muestra;
	}

	@ManyToOne
	@JoinColumn(name = "IdPlacaLaboratorio")
	public PlacaLaboratorio getPlacaLaboratorio() {
		return this.placaLaboratorio;
	}

	public void setPlacaLaboratorio(PlacaLaboratorio placaLaboratorio) {
		this.placaLaboratorio = placaLaboratorio;
	}

	@ManyToOne
	@JoinColumn(name = "idPlacaVisavet")
	public PlacaVisavet getPlacaVisavet() {
		return this.placaVisavet;
	}

	public void setPlacaVisavet(PlacaVisavet placaVisavet) {
		this.placaVisavet = placaVisavet;
	}

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nombre", nullable = false, length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "tipo", nullable = false, length = 3)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "fichero", nullable = false)
	public byte[] getFichero() {
		return this.fichero;
	}

	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}

	@Column(name = "tamanoDocumento")
	public Integer getTamanoDocumento() {
		return this.tamanoDocumento;
	}

	public void setTamanoDocumento(Integer tamanoDocumento) {
		this.tamanoDocumento = tamanoDocumento;
	}

	@Column(name = "fechaDocumento", length = 45)
	public String getFechaDocumento() {
		return this.fechaDocumento;
	}

	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

}
