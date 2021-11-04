package br.com.gori.scb.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gori.scb.domain.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

	Optional<Pais> findByNome(String nome);
	
	List<Pais> findBySigla(String sigla);
	
}
