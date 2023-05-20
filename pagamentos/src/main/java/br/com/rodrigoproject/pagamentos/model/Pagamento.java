package br.com.rodrigoproject.pagamentos.model;

import br.com.rodrigoproject.pagamentos.model.enumerable.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamentosSequence")
    private Long id;

    @NonNull
    @Positive
    @Column(name = "valor")
    private BigDecimal valor;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;

    @NotBlank
    @Size(max = 19)
    @Column(name = "numero")
    private String numero;

    @NotBlank
    @Size(max = 7)
    @Column(name = "expiracao")
    private String expiracao;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "codigo")
    private String codigo;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull
    @Column(name = "pedido_id")
    private Long pedidoId;

    @NotNull
    @Column(name = "forma_de_pagamento")
    private Long formaDePagamento;
}
