package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.Aplicacao;
import br.edu.utfpr.api1.model.Equipamento;
import br.edu.utfpr.api1.model.Talhao;
import br.edu.utfpr.api1.model.TipoAplicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, UUID> {

    Page<Aplicacao> findByTalhao(Talhao talhao, Pageable pageable);

    Page<Aplicacao> findByEquipamento(Equipamento equipamento, Pageable pageable);

    Page<Aplicacao> findByTipoAplicacao(TipoAplicacao tipoAplicacao, Pageable pageable);

    Page<Aplicacao> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    Page<Aplicacao> findByFinalizada(Boolean finalizada, Pageable pageable);

    List<Aplicacao> findByTalhaoAndDataInicioBetween(Talhao talhao, LocalDateTime inicio, LocalDateTime fim);
}
