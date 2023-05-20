package br.com.rodrigoproject.pedidos.controller;

import br.com.rodrigoproject.pedidos.model.dto.PedidoDTO;
import br.com.rodrigoproject.pedidos.model.enumerable.Status;
import br.com.rodrigoproject.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> listarTodos() {
        return pedidoService.obterTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> listarPorId(@PathVariable @NotNull Long id) {
        PedidoDTO dto = pedidoService.obterPorId(id);

        return  ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> realizaPedido(@RequestBody @Valid PedidoDTO dto, UriComponentsBuilder uriBuilder) {
        PedidoDTO pedidoRealizado = pedidoService.criarPedido(dto);

        URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

        return ResponseEntity.created(endereco).body(pedidoRealizado);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizaStatus(@PathVariable Long id, @RequestBody Status status){
        PedidoDTO dto = pedidoService.atualizaStatus(id, status);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
        pedidoService.aprovaPagamentoPedido(id);

        return ResponseEntity.ok().build();

    }
}
