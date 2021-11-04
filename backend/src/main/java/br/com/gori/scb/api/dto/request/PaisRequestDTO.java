package br.com.gori.scb.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisRequestDTO {

	@NotBlank
	@Size(max=30, min=1)
	private String nome;
	
	@NotBlank
	@Size(max=10, min=1)
	private String sigla;
	
}
