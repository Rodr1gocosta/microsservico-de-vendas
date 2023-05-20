package br.com.rodrigoproject.pagamentos.service.mapper;

import br.com.rodrigoproject.pagamentos.model.Pagamento;
import br.com.rodrigoproject.pagamentos.model.dto.PagamentoDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento>{
}
