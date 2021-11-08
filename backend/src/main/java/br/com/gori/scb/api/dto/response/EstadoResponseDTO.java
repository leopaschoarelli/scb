package br.com.gori.scb.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoResponseDTO {

	private Long id;
	private String nome;
	private String uf;
	private PaisResponseDTO pais;
	
}
