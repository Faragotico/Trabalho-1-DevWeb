package pagamentos.futebol.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
    Entidade de pagamentos
*/
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pagamento")
    private Long codPagamento;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 2000, message = "Ano deve ser maior ou igual a 2000")
    @Max(value = 2100, message = "Ano deve ser menor ou igual a 2100")
    @Column(name = "ano", nullable = false)
    private Short ano;

    @NotNull(message = "Mês é obrigatório")
    @Min(value = 1, message = "Mês deve ser entre 1 e 12")
    @Max(value = 12, message = "Mês deve ser entre 1 e 12")
    @Column(name = "mes", nullable = false)
    private Byte mes;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    // Relacionamento many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_jogador", nullable = false)
    @JsonBackReference
    private Jogador jogador;

    // Construtores
    public Pagamento() {
    }

    public Pagamento(Short ano, Byte mes, BigDecimal valor, Jogador jogador) {
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.jogador = jogador;
    }

    // Getters e Setters
    public Long getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(Long codPagamento) {
        this.codPagamento = codPagamento;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(Short ano) {
        this.ano = ano;
    }

    public byte getMes() {
        return mes;
    }

    public void setMes(Byte mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}