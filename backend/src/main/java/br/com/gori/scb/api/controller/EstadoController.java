package br.com.gori.scb.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gori.scb.api.dto.request.EstadoRequestDTO;
import br.com.gori.scb.api.dto.response.EstadoResponseDTO;
import br.com.gori.scb.domain.service.EstadoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
	
	private EstadoService estadoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoResponseDTO criarEstado(@Valid @RequestBody EstadoRequestDTO estadoRequestDTO){
		
		var estadoResponseDTO = estadoService.criarEstado(estadoRequestDTO);
		
		return estadoResponseDTO;
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<EstadoResponseDTO> atualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequestDTO estadoRequestDTO) {
		
		var estadoResponseDTO = estadoService.atualizarEstado(id, estadoRequestDTO);
		
		return estadoResponseDTO;
	}
	
	@GetMapping
	public Page<EstadoResponseDTO> listarEstados(Pageable pageable){
		
		var estadosResponseDTO = estadoService.listarEstados(pageable);
		
		return estadosResponseDTO;
	}
	
	@GetMapping(value = "/{id}")
	public EstadoResponseDTO buscarEstadoPorId(@PathVariable Long id) {
		var estadoResponseDTO = estadoService.buscarEstadoPorId(id);
		
		return estadoResponseDTO;
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarEstado(@PathVariable Long id) {
		
		var estadoExcluido = estadoService.excluirEstadoPorId(id);
		
		if (!estadoExcluido) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

}
