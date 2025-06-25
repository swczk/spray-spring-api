package br.edu.utfpr.springapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aplicacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Aplicacao extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "talhao_id", nullable = false)
    private Talhao talhao;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(name = "tipo_aplicacao_id", nullable = false)
    private TipoAplicacao tipoAplicacao;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column
    private BigDecimal dosagem;

    @Column(name = "volume_aplicado")
    private BigDecimal volumeAplicado;

    @Column
    private String operador;

    @Column
    private String condicaoClimatica;

    @Column
    private String observacoes;

    @Column
    private Boolean finalizada = false;
}
