package br.com.rodrigoproject.pedidos.service;

import br.com.rodrigoproject.pedidos.model.Pedido;
import br.com.rodrigoproject.pedidos.model.dto.PedidoDTO;
import br.com.rodrigoproject.pedidos.model.enumerable.Status;
import br.com.rodrigoproject.pedidos.repository.PedidoRepository;
import br.com.rodrigoproject.pedidos.service.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository repository;

    private final PedidoMapper pedidoMapper;


    @Transactional(readOnly = true)
    public List<PedidoDTO> obterTodos() {
        List<Pedido> result = repository.findAll();
        return mapperToDtoList(result);
    }

    @Transactional(readOnly = true)
    public PedidoDTO obterPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapperToDto(pedido);
    }

    public PedidoDTO criarPedido(PedidoDTO dto) {
        Pedido pedido = mapperToEntity(dto);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = repository.save(pedido);

        return mapperToDto(salvo);
    }

    public PedidoDTO atualizaStatus(Long id, Status dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto);
        repository.atualizaStatus(dto, pedido);
        return mapperToDto(pedido);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

    //MAPEAMENTO DE ENTIDADE E DTO
    public PedidoDTO mapperToDto(Pedido pedido) {
        return pedidoMapper.toDto(pedido);
    }

    public List<PedidoDTO> mapperToDtoList(List<Pedido> pedidoList) { return pedidoMapper.toDto(pedidoList); }

    public Pedido mapperToEntity(PedidoDTO pedidoDTO) {
        return pedidoMapper.toEntity(pedidoDTO);
    }
}
