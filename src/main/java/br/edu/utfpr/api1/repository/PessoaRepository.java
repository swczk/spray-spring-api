package br.edu.utfpr.api1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.api1.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    public Pessoa findByEmail(String email);

}
