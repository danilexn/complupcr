package es.ucm.pcr.beans;

import java.util.Calendar;

public class BeanMuestra{
	private int id;
	private String etiqueta;
	private String tipoMuestra;
	private Calendar fecha;
	private String referenciaInterna;
	private BeanEstado estado;
	private BeanCentro centro;
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getTipoMuestra() {
		return tipoMuestra;
	}
	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public String getReferenciaInterna() {
		return referenciaInterna;
	}
	public void setReferenciaInterna(String referenciaInterna) {
		this.referenciaInterna = referenciaInterna;
	}
	public BeanEstado getEstado() {
		return estado;
	}
	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	public BeanAnalisis getBeanAnalisis() {
		return beanAnalisis;
	}
	public void setBeanAnalisis(BeanAnalisis beanAnalisis) {
		this.beanAnalisis = beanAnalisis;
	}
	public BeanCentro getCentro() {
		return centro;
	}
	public void setCentro(BeanCentro centro) {
		this.centro = centro;
	}
	
	
	
	
}