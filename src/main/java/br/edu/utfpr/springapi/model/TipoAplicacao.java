package br.edu.utfpr.springapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipos_aplicacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TipoAplicacao extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column(name = "vazao_padrao")
    private Double vazaoPadrao;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

    @OneToMany(mappedBy = "tipoAplicacao")
    @JsonIgnore
    private List<Aplicacao> aplicacoes = new ArrayList<>();
}
