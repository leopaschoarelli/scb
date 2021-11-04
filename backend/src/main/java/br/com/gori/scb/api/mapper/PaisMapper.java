package br.com.gori.scb.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.gori.scb.api.dto.request.PaisRequestDTO;
import br.com.gori.scb.api.dto.response.PaisResponseDTO;
import br.com.gori.scb.domain.model.Pais;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PaisMapper {
	
	private ModelMapper modelMapper;
	
	public PaisResponseDTO toModel(Pais pais) {
		return modelMapper.map(pais, PaisResponseDTO.class);
	}
	
	public List<PaisResponseDTO> toCollectionModel(List<Pais> pais) {
		return pais.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Page<PaisResponseDTO> toCollectionModelPage(Page<Pais> pais) {
		return pais.map(this::toModel);
	}
	
	public Pais toEntity(PaisRequestDTO paisRequestDTO) {
		return modelMapper.map(paisRequestDTO, Pais.class);
	}
}
