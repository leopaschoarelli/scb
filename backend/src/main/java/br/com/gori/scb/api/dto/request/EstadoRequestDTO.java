package br.com.gori.scb.api.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.gori.scb.api.dto.request.id.PaisIdRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequestDTO {

	@NotBlank
	@Size(min = 1, max = 20)
	private String nome;
	
	@NotBlank
	@Size(min = 1, max = 2)
	private String uf;
	
	@Valid
	@NotNull
	private PaisIdRequestDTO pais;
	
}
