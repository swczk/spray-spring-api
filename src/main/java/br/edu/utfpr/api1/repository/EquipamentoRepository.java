package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.Equipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID> {

    Page<Equipamento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Equipamento> findByModelo(String modelo, Pageable pageable);

    Page<Equipamento> findByFabricante(String fabricante, Pageable pageable);
}
