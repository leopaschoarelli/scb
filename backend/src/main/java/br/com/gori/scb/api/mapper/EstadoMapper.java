package br.com.gori.scb.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.gori.scb.api.dto.request.EstadoRequestDTO;
import br.com.gori.scb.api.dto.response.EstadoResponseDTO;
import br.com.gori.scb.domain.model.Estado;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EstadoMapper {

	private ModelMapper modelMapper;
	
	public EstadoResponseDTO toModel(Estado estado) {
		return modelMapper.map(estado, EstadoResponseDTO.class);
	}
	
	public List<EstadoResponseDTO> toCollectionModel(List<Estado> estado) {
		return estado.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Page<EstadoResponseDTO> toCollectionModelPage(Page<Estado> estado) {
		return estado.map(this::toModel);
	}
	
	public Estado toEntity(EstadoRequestDTO estadoRequestDTO) {
		return modelMapper.map(estadoRequestDTO, Estado.class);
	}
	
}
