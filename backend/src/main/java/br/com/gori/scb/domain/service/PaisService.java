package br.com.gori.scb.domain.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gori.scb.api.dto.request.PaisRequestDTO;
import br.com.gori.scb.api.dto.response.PaisResponseDTO;
import br.com.gori.scb.api.mapper.PaisMapper;
import br.com.gori.scb.domain.exception.NegocioException;
import br.com.gori.scb.domain.model.Pais;
import br.com.gori.scb.domain.repository.PaisRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PaisService {
	
	private PaisRepository paisRepository;
	private PaisMapper paisMapper;
	
	@Transactional
	public PaisResponseDTO criarPais(PaisRequestDTO paisRequestDTO) {
		var pais = paisMapper.toEntity(paisRequestDTO);
		
		paisRepository.save(pais);
		
		var paisResponseDTO = paisMapper.toModel(pais);
		
		return paisResponseDTO;
	}

	@Transactional(readOnly = true)
	public Page<PaisResponseDTO> listarPaises(Pageable pageable) {
		
		var list = paisRepository.findAll(pageable);
		var listResponse = paisMapper.toCollectionModelPage(list);
		return listResponse;
	}

	@Transactional
	public PaisResponseDTO atualizarPais(Long id, @Valid PaisRequestDTO paisRequestDTO) {
		var pais = paisMapper.toEntity(paisRequestDTO);
		
		pais.setId(id);
		paisRepository.save(pais);
		
		var paisResponseDTO = paisMapper.toModel(pais);
		
		return paisResponseDTO;
	}

	@Transactional
	public void excluirPais(Long id) {
		paisRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public boolean paisExistsById(Long id) {
		return paisRepository.existsById(id);
	}
	
	@Transactional(readOnly = true)
	public Pais buscarPorId(Long id) {
		var pais = paisRepository.findById(id)
				.orElseThrow(() -> new NegocioException("País não encontrado"));
		
		return pais;
	}
	
}
