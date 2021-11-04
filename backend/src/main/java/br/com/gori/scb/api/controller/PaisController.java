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

import br.com.gori.scb.api.dto.request.PaisRequestDTO;
import br.com.gori.scb.api.dto.response.PaisResponseDTO;
import br.com.gori.scb.domain.service.PaisService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/paises")
public class PaisController {
	
	private PaisService paisService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaisResponseDTO criarPais(@Valid @RequestBody PaisRequestDTO paisRequestDTO) {
		var paisResponseDTO = paisService.criarPais(paisRequestDTO);
		return paisResponseDTO;
	}
	
	@GetMapping
	public ResponseEntity<Page<PaisResponseDTO>> listarPaises(Pageable pageable) {
		var listPaises = paisService.listarPaises(pageable);
		return ResponseEntity.ok(listPaises);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PaisResponseDTO> atualizarPais(@PathVariable Long id, 
			                                             @Valid @RequestBody PaisRequestDTO paisRequestDTO) {
		
		if (!paisService.paisExistsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		var paisResponseDTO = paisService.atualizarPais(id,paisRequestDTO);
		
		return ResponseEntity.ok(paisResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id){
	
		if (!paisService.paisExistsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		paisService.excluirPais(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
}
