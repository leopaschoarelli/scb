package br.com.gori.scb.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gori.scb.api.dto.request.EstadoRequestDTO;
import br.com.gori.scb.api.dto.response.EstadoResponseDTO;
import br.com.gori.scb.api.mapper.EstadoMapper;
import br.com.gori.scb.domain.exception.NegocioException;
import br.com.gori.scb.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EstadoService {

	private EstadoRepository estadoRepository;
	private EstadoMapper estadoMapper;
	private PaisService paisService;
	
	@Transactional
	public EstadoResponseDTO criarEstado(EstadoRequestDTO estadoRequestDTO) {
		
		var estadoResponseDTO = criarAtualizarEstado(null, estadoRequestDTO);
		
		return estadoResponseDTO;
	}
	
	@Transactional
	public ResponseEntity<EstadoResponseDTO> atualizarEstado(Long id, EstadoRequestDTO estadoRequestDTO) {
		
		if (!estadoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		var estadoResponseDTO = criarAtualizarEstado(id, estadoRequestDTO);
		
		return ResponseEntity.ok(estadoResponseDTO);
	}
	
	@Transactional(readOnly = true)
	public Page<EstadoResponseDTO> listarEstados(Pageable pageable) {
		
		var estados = estadoRepository.findAll(pageable);
		
		var estadosPage = estadoMapper.toCollectionModelPage(estados);
		
		return estadosPage;
	}
	
	@Transactional
	public boolean excluirEstadoPorId(Long id) {
		
		if (!estadoRepository.existsById(id)) {
			return false;
		}
		
		estadoRepository.deleteById(id);		
		
		return true;
	}
	
	@Transactional(readOnly = true)
	public EstadoResponseDTO buscarEstadoPorId(Long id) {
		var estado = estadoRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Estado não encontrado"));
		
		var estadoResponseDTO = estadoMapper.toModel(estado);
		
		return estadoResponseDTO;
	}
	
	private EstadoResponseDTO criarAtualizarEstado(Long id, EstadoRequestDTO estadoRequestDTO) {
		
		var estado = estadoMapper.toEntity(estadoRequestDTO);
		var pais = paisService.buscarPorId(estado.getPais().getId());
		
		estado.setId(id);
		estado.setPais(pais);
		estadoRepository.save(estado);
		
		var estadoResponseDTO = estadoMapper.toModel(estado);
		
		return estadoResponseDTO;
	}

}
