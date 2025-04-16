package br.edu.utfpr.api1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Equipamento extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String modelo;

    @Column
    private String fabricante;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "largura_barra")
    private BigDecimal larguraBarra;

    @Column(name = "capacidade_tanque")
    private BigDecimal capacidadeTanque;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @OneToMany(mappedBy = "equipamento")
    private List<Aplicacao> aplicacoes = new ArrayList<>();
}
