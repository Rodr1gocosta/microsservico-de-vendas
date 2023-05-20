package br.com.rodrigoproject.pedidos.service.mapper;

import br.com.rodrigoproject.pedidos.model.Pedido;
import br.com.rodrigoproject.pedidos.model.dto.PedidoDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {
}
