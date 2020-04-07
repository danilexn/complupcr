package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;

/**
 * Lote generated by hbm2java
 */
@Entity
@Table(name = "logMuestras")
public class LogMuestras implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 79262158874864875L;
	
	private Integer id;
	private Muestra muestra;
	private Lote lote;
	private PlacaVisavet placaVisavet;
	private PlacaLaboratorio placaLaboratorio;
	private EstadoMuestra estadoMuestra;
	private Date fechaCambio;
	private Usuario autorCambio;
	
	

	public LogMuestras() {
	}
	
	
	public LogMuestras(Muestra muestra, Lote lote, PlacaVisavet placaVisavet,
			PlacaLaboratorio placaLaboratorio, EstadoMuestra estadoMuestra, Date fechaCambio,
			Usuario autorCambio) {
		super();
		this.muestra = muestra;
		this.lote = lote;
		this.placaVisavet = placaVisavet;
		this.placaLaboratorio = placaLaboratorio;
		this.estadoMuestra = estadoMuestra;
		this.fechaCambio = fechaCambio;
		this.autorCambio = autorCambio;
	}


	public LogMuestras(Integer id) {
		super();
		this.id = id;
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
	@JoinColumn(name = "idMuestra", nullable = false)
	public Muestra getMuestra() {
		return muestra;
	}

	
	public void setMuestra(Muestra muestra) {
		this.muestra = muestra;
	}


	@ManyToOne
	@JoinColumn(name = "idLote")
	public Lote getLote() {
		return lote;
	}

	
	public void setLote(Lote lote) {
		this.lote = lote;
	}

	@ManyToOne
	@JoinColumn(name = "idPlacaVisavet")
	public PlacaVisavet getPlacaVisavet() {
		return placaVisavet;
	}


	public void setPlacaVisavet(PlacaVisavet placaVisavet) {
		this.placaVisavet = placaVisavet;
	}

	@ManyToOne
	@JoinColumn(name = "idPlacaLaboratorio")
	public PlacaLaboratorio getPlacaLaboratorio() {
		return placaLaboratorio;
	}

	
	public void setPlacaLaboratorio(PlacaLaboratorio placaLaboratorio) {
		this.placaLaboratorio = placaLaboratorio;
	}

	@ManyToOne
	@JoinColumn(name = "idEstadoMuestra", nullable = false)
	public EstadoMuestra getEstadoMuestra() {
		return estadoMuestra;
	}

	
	public void setEstadoMuestra(EstadoMuestra estadoMuestra) {
		this.estadoMuestra = estadoMuestra;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCambio",nullable = false, length = 10)
	public Date getFechaCambio() {
		return fechaCambio;
	}

	
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	@ManyToOne
	@JoinColumn(name = "idAutorCambio", nullable = false)
	public Usuario getAutorCambio() {
		return autorCambio;
	}

	
	public void setAutorCambio(Usuario autorCambio) {
		this.autorCambio = autorCambio;
	}

}
