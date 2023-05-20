package br.com.rodrigoproject.pagamentos.model.dto;

import br.com.rodrigoproject.pagamentos.model.enumerable.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoDTO {

    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long pedidoId;
    private Long formaDePagamento;

}
