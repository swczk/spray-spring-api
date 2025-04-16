package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.TipoAplicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TipoAplicacaoRepository extends JpaRepository<TipoAplicacao, UUID> {

    Page<TipoAplicacao> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<TipoAplicacao> findByTipoProduto(String tipoProduto, Pageable pageable);
}
