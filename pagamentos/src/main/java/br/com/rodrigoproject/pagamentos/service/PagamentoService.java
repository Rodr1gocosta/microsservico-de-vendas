package br.com.rodrigoproject.pagamentos.service;

import br.com.rodrigoproject.pagamentos.client.PedidoClient;
import br.com.rodrigoproject.pagamentos.exception.PagamentoException;
import br.com.rodrigoproject.pagamentos.model.Pagamento;
import br.com.rodrigoproject.pagamentos.model.dto.PagamentoDTO;
import br.com.rodrigoproject.pagamentos.model.enumerable.Status;
import br.com.rodrigoproject.pagamentos.repository.PagamentoRepository;
import br.com.rodrigoproject.pagamentos.service.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoMapper pagamentoMapper;
    private final PedidoClient pedidoClient;

    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
        return pagamentoRepository.findAll(pageable).map(obj -> mapperToDto(obj));
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id) {
        return pagamentoRepository.findById(id)
                .map(obj -> mapperToDto(obj))
                .orElseThrow(() -> new PagamentoException("Pagamento não encotrado!"));
    }

    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = mapperToEntity(pagamentoDTO);
        pagamento.setStatus(Status.CRIADO);

        Pagamento result = pagamentoRepository.save(pagamento);
        return mapperToDto(result);
    }

    public PagamentoDTO updatePagamento(Long id, PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamentoDTO1 = findById(id);
        pagamentoDTO.setId(pagamentoDTO1.getId());

        Pagamento result = pagamentoRepository.save(mapperToEntity(pagamentoDTO));
        return mapperToDto(result);
    }

    public void excluirPagamento(Long id) {
        PagamentoDTO pagamentoDTO = findById(id);
        pagamentoRepository.delete(mapperToEntity(pagamentoDTO));
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        pedidoClient.atualizaPagamento(pagamento.get().getPedidoId());
    }


    //MAPEAMENTO DE ENTIDADE E DTO
    public PagamentoDTO mapperToDto(Pagamento pagamento) {
        return pagamentoMapper.toDto(pagamento);
    }

    public Pagamento mapperToEntity(PagamentoDTO pagamentoDTO) {
        return pagamentoMapper.toEntity(pagamentoDTO);
    }
}
