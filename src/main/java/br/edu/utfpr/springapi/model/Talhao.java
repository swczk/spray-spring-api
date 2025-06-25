package br.edu.utfpr.springapi.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "talhoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Talhao extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal areaHectares;

    @Column
    private String cultura;

    @Column
    private String variedade;

    @Column(name = "coordenadas_geograficas")
    private String coordenadasGeograficas;

    @OneToMany(mappedBy = "talhao")
    @JsonIgnore
    private List<Aplicacao> aplicacoes = new ArrayList<>();
}
