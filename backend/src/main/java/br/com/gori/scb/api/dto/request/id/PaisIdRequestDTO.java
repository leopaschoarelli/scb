package br.com.gori.scb.api.dto.request.id;

import lombok.Setter;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
@Setter
public class PaisIdRequestDTO {

	@NotNull
	private Long id;
	
}
