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

package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;

@Service
public class LoteServicioImpl implements LoteServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoteServicioImpl.class);
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private MuestraRepositorio muestraRepositorio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Override
	@Transactional
	public Page<LoteListadoBean> findLoteByParam(LoteBusquedaBean params, Pageable pageable) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		Page<Lote> lotesPage = loteRepositorio.findByParams(params, pageable); 
		
		for (Lote l : lotesPage.getContent()) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		Page<LoteListadoBean> lotesCentro = new PageImpl<>(listLotesBean, pageable, lotesPage.getTotalElements());
		
		return lotesCentro;
	}
	
	@Override
	@Transactional
	public List<LoteListadoBean> findLoteByParam(LoteBusquedaBean params) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		List<Lote> lotes = loteRepositorio.findByParams(params); 
		
		for (Lote l : lotes) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		return listLotesBean;
	}
	
	@Override
	public List<LoteListadoBean> findLoteByEstados(Integer idCentro, List<Integer> idsEstado) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		List<Lote> lotes = loteRepositorio.findByEstado(idCentro, idsEstado); 
		
		for (Lote l : lotes) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		return listLotesBean;
	}

	@Override
	@Transactional
	public LoteCentroBean guardar(LoteCentroBean loteBean) {
		Lote lote = null;
		
		lote = LoteCentroBean.beanToModel(loteBean);
				
		lote.setEstadoLote(new EstadoLote(Estado.LOTE_INICIADO.getCodNum()));
		if (loteBean.getIdLaboratorio() != null) {
			lote.setEstadoLote(new EstadoLote(Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum()));
		}
		
		if (lote.getReferenciaInternaLote()==null)
			lote.setReferenciaInternaLote("");
		
		lote = loteRepositorio.save(lote);
		return LoteCentroBean.modelToBean(lote);
	}
	@Override
	@Transactional
	public LoteCentroBean guardarLote(LoteCentroBean loteBean) {
		Lote lote = null;
		
		lote = LoteCentroBean.beanToModel(loteBean);
		Optional<Lote> lotebbdd= loteRepositorio.findById(loteBean.getId());
		if (lotebbdd.isPresent()) {
			lote.setMuestras(lotebbdd.get().getMuestras());
			
		}
		if (lote.getReferenciaInternaLote()==null)
			lote.setReferenciaInternaLote("");
		lote = loteRepositorio.save(lote);
		return LoteCentroBean.modelToBean(lote);
	}
	
	
	
	@Override
	@Transactional
	public LoteCentroBean findById(Integer id) {
		Lote lote = findByIdLote(id);
		if (lote != null) {
			return LoteCentroBean.modelToBean(lote);
		}
		return new LoteCentroBean();
	}

	@Override
	@Transactional
	public void actualizarEstadoLote(LoteCentroBean loteBean, BeanEstado estadoActualizar) {
		Lote lote = findByIdLote(loteBean.getId());
		if (lote != null) {
			
			// si actualizamos el lote ha enviado ademas rellenamos la fecha de envio
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()) {
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()));
				lote.setFechaEnvio(new Date());
				lote.setFechaRecibido(null);
				// se actualiza el estado y la fecha de cada muestra del lote
				if (!CollectionUtils.isEmpty(lote.getMuestras())) {
					for (Muestra m : lote.getMuestras()) {
						m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()));
						m.setFechaEnvio(new Date());
						
						BeanEstado estadoMuestra = new BeanEstado();
						estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
						servicioLog.actualizarEstadoMuestra(m, m.getLote(), null, null, estadoMuestra);
					}
					muestraRepositorio.saveAll(lote.getMuestras());
				}
				lote.setLaboratorioVisavet(new LaboratorioVisavet(loteBean.getIdLaboratorio()));
			}
			
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()) {
				// TODO - ACTUALIZAR ESTADO LOTE RECIBIDO
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()));
				lote.setFechaRecibido(new Date());
			}
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_PROCESADO_CENTRO_ANALISIS.getCodNum()) {
				// TODO - ACTUALIZAR ESTADO LOTE PROCESADO
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_PROCESADO_CENTRO_ANALISIS.getCodNum()));
			}
			
		}	
		loteRepositorio.save(lote);
	}	
	
	/**
	 * Recupera el lote
	 * @param id
	 * @return
	 */
	@Override
	public Lote findByIdLote(Integer id) {
		Optional<Lote> loteOptional = loteRepositorio.findById(id);
		if (loteOptional.isPresent()) {
			return loteOptional.get();
		}
		return null;
	}

	@Override
	public boolean borrar(Integer id) {
		try {
			loteRepositorio.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("ERROR:: borrar " + e);
			return false;
		}
	}
	
	
	// yoli
	@Override
	@Transactional
	public LoteBeanPlacaVisavet findByIdByPlacas(Integer id) {
		Lote lote = findByIdLote(id);
		if (lote != null) {
			return LoteBeanPlacaVisavet.modelToBean(lote);
		}
		return new LoteBeanPlacaVisavet();
	}
	@Override
	public List<LoteListadoBean> findLoteByReferenciaExterna(String referenciaExterna){
		List<Lote> lotes = loteRepositorio.findByReferenciaExterna(referenciaExterna);
        List<LoteListadoBean> listLotesBean = new ArrayList();
		
		for (Lote l : lotes) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		return listLotesBean;
	}
	
	
}
