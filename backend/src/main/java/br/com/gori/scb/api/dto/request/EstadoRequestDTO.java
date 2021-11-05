package br.com.gori.scb.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequestDTO {

	private String nome;
	private String uf;
	private Long paisId;
	
}
