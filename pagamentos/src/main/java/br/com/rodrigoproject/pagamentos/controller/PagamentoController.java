package br.com.rodrigoproject.pagamentos.controller;

import br.com.rodrigoproject.pagamentos.model.dto.PagamentoDTO;
import br.com.rodrigoproject.pagamentos.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<PagamentoDTO> result = pagamentoService.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable(value = "id") @NotNull Long id) {
        PagamentoDTO result = pagamentoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriComponentsBuilder) {
        PagamentoDTO result = pagamentoService.criarPagamento(pagamentoDTO);
        URI endereco = uriComponentsBuilder.path("/pagamento/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(endereco).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable(value = "id") @NotNull Long id, @RequestBody @Valid PagamentoDTO pagamentoDTO) {
        PagamentoDTO result = pagamentoService.updatePagamento(id, pagamentoDTO);

        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable(value = "id") @NotNull Long id) {

        pagamentoService.excluirPagamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/confirmar")
    public void confirmarPagamento(@PathVariable(value = "id") @NotNull Long id){
        pagamentoService.confirmarPagamento(id);
    }
}
