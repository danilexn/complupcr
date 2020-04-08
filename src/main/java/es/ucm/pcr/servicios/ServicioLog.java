package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;

public interface ServicioLog {

	/**
	 * Actualiza el estado del log de una muestra
	 * 
	 * @param idMuestra
	 * @param estado
	 */
	public void actualizarEstadoMuestra(Integer idMuestra, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de un lote
	 * 
	 * @param idLote
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorLote(Integer idLote, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa visavet
	 * 
	 * @param idPlacaVisavet
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaVisavet(Integer idPlacaVisavet, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa de laboratorio
	 * 
	 * @param idPlacaLaboratorio
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaLaboratorio(Integer idPlacaLaboratorio, BeanEstado estado);
	
	/**
	 * Busqueda de logs por parametros
	 * 
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<LogMuestraListadoBean> findLogMuestraByParam(LogMuestraBusquedaBean params, Pageable pageable);
	
	/**
	 * Borra estados asociados a una muestra
	 * @param idMuestra
	 */
	public void borrarEstadosMuestra(Integer idMuestra);

}
