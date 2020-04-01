package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
@Service
public interface ServicioLotes {
	public List<BeanElemento> buscarEstadosLotes();

}
