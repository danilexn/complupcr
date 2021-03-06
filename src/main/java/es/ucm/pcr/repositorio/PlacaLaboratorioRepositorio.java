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

package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioAnalistaBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;

public interface PlacaLaboratorioRepositorio extends JpaRepository<PlacaLaboratorio, Integer> {



	@Query("SELECT placa FROM PlacaLaboratorio placa "
			+ "JOIN placa.laboratorioCentro laboratorioCentro "
			+ "JOIN placa.estadoPlacaLaboratorio estadoPlacaLaboratorio "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.fechaCreacionInicio} is null or placa.fechaCreacion >= :#{#params.fechaCreacionInicio}) and "
			+ "(:#{#params.fechaCreacionFin} is null or placa.fechaCreacion <= :#{#params.fechaCreacionFin}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id in (:#{#params.estadosBusqueda})) and "
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioBean params,
			Pageable pageable);	
	
	
	
	
	@Query("SELECT placa FROM PlacaLaboratorio placa "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id = :#{#params.idEstadoPlaca}) and "
			+ "(:#{#params.idJefe} is null or placa.usuario.id = :#{#params.idJefe}) and "
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioJefeBean params,
			Pageable pageable);
	
	
	
	//Si queremos hacer busqueda metiendo el criterio valorada (pendiente, repetir....)
	@Query("SELECT DISTINCT placa FROM PlacaLaboratorio placa "
			+ "JOIN placa.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorios "
			+ "JOIN placaVisavetPlacaLaboratorios.placaVisavet placaVisavet "
			+ "JOIN placaVisavet.lotes lote "
			+ "JOIN lote.muestras muestra "
			+ "JOIN muestra.usuarioMuestras usuariomuestra "
			+ "JOIN usuariomuestra.usuario usuario "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id = :#{#params.idEstadoPlaca}) and "
			+ "(:#{#params.idEstadoMuestras} is null or muestra.estadoMuestra.id = :#{#params.idEstadoMuestras}) and "
			+ "(:#{#params.idAnalistaMuestras} is null or usuario.id = :#{#params.idAnalistaMuestras}) and "			
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro}) and "
			+ "(:#{#params.valoracion} is null or usuariomuestra.valoracion = :#{#params.valoracion})")			
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioAnalistaBean params,
			Pageable pageable);
	
	
	//Si params.valoracion es un null- Devuelve solo aquellas que tengan su valoracion a null (las no valoradas)
	//Si params.valoracion es != null- Devuelve solo aquellas que tengan una valoración (las valoradas)
	//ordenaremos por la fecha en la que se asignaron las muestras al analita (fecha en la que se asignó la placa al analita y a todas sus muestras)
	//la mas antigua primero
	@Query("SELECT DISTINCT placa FROM PlacaLaboratorio placa "
			+ "JOIN placa.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorios "
			+ "JOIN placaVisavetPlacaLaboratorios.placaVisavet placaVisavet "
			+ "JOIN placaVisavet.lotes lote "
			+ "JOIN lote.muestras muestra "
			+ "JOIN muestra.usuarioMuestras usuariomuestra "
			+ "JOIN usuariomuestra.usuario usuario "
			+ "WHERE 1=1 and "
			+ "((:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id = :#{#params.idEstadoPlaca}) and "
			+ "(:#{#params.idEstadoMuestras} is null or muestra.estadoMuestra.id = :#{#params.idEstadoMuestras}) and "
			+ "(:#{#params.idAnalistaMuestras} is null or usuario.id = :#{#params.idAnalistaMuestras}) and "			
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})) and "
			+ "((:#{#params.valoracion} is null and usuariomuestra.valoracion is null) or "
			+ "(:#{#params.valoracion} is not null and usuariomuestra.valoracion is not null)) order by placa.fechaAsignadaJefe asc")						
	public Page<PlacaLaboratorio> findByParamsValoradasAndNotValoradas(@Param("params") BusquedaPlacaLaboratorioAnalistaBean params,
			Pageable pageable);
	
	
	

	Optional<PlacaLaboratorio> findById(Integer id);
	
	List<PlacaLaboratorio> findByUsuario(Usuario usuario);
	
	
}
