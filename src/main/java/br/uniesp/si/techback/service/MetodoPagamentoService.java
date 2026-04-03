package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import br.uniesp.si.techback.mapper.MetodoPagamentoMapper;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final MetodoPagamentoMapper metodoPagamentoMapper;

    public List<MetodoPagamentoDTO> listar() {
        log.info("Buscando todos os métodos de pagamento cadastrados");
        try {
            List<MetodoPagamento> metodoPagamentos = metodoPagamentoRepository.findAll();
            List<MetodoPagamentoDTO> metodoPagamentosDTO = metodoPagamentos.stream()
                    .map(metodoPagamentoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total dos métodos de pagamento encontrados: {}", metodoPagamentosDTO.size());
            return metodoPagamentosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar método de pagamentos: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @param pageable o json
     *  {
     *   "page": 0,
     *   "size": 5,
     *   "sort": "@param1, @param2, [descending]"
     *  }
     * @return lista de metodos de pagamento paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum metodo de pagamento cadastrado.
     */
    public Page<MetodoPagamentoDTO> listarPaginado(Pageable pageable) {
        Page<MetodoPagamento> result = metodoPagamentoRepository.findAll(pageable);
        return result.map(x -> new MetodoPagamentoMapper().toDTO(x));
    }

    /**
     * @param id o ID do método de Pagamento.
     * @return o método de pagamento encontrado, ou lança uma exceção {@link RuntimeException} se o método de pagamento não existir.
     */
    public MetodoPagamentoDTO buscarPorId(Long id) {
        log.info("Buscando metodoPagamento pelo ID: {}", id);
        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findById(id)
                .map(metodoPagamentoEncontrado -> {
                    log.debug("Método de pagamento encontrado: ID={}, Bandeira={}", metodoPagamentoEncontrado.getId(), metodoPagamentoEncontrado.getBandeira());
                    return metodoPagamentoEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Método de pagamento não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return metodoPagamentoMapper.toDTO(metodoPagamento);
    }

    /**
     * Atualiza um método de pagamento existente.
     *
     * @param id    o ID do método de pagamento a ser atualizado.
     * @param metodoPagamentoDTO o método de pagamento com as informações atualizadas.
     * @return o metodoPagamento atualizado.
     */
    @Transactional
    public MetodoPagamentoDTO atualizar(Long id, MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Atualizando método de pagamento ID: {}", id);
        MetodoPagamento metodoPagamentoAtualizado = metodoPagamentoRepository.findById(id)
                .map(metodoPagamentoExistente -> {
                    log.debug("Dados atuais do metodoPagamento: {}", metodoPagamentoExistente);
                    log.debug("Novos dados: {}", metodoPagamentoDTO);
                    metodoPagamentoDTO.setId(id);
                    MetodoPagamento metodoPagamentoParaAtualizar = metodoPagamentoMapper.toEntity(metodoPagamentoDTO);
                    MetodoPagamento metodoPagamentoSalvo = metodoPagamentoRepository.save(metodoPagamentoParaAtualizar);
                    log.info("MetodoPagamento ID: {} atualizado com sucesso. Nova Bandeira: {}",
                            id, metodoPagamentoSalvo.getBandeira());
                    return metodoPagamentoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: método de pagamento não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return metodoPagamentoMapper.toDTO(metodoPagamentoAtualizado);
    }

    /**
     * Salva um novo método de pagamento.
     *
     * @param metodoPagamentoDTO o método de Pagamento a ser salvo.
     * @return o método de pagamento salvo.
     */
    @Transactional
    public MetodoPagamentoDTO salvar(MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Salvando novo método de pagamento: {}", metodoPagamentoDTO.getBandeira());
        try {
            MetodoPagamento metodoPagamento = metodoPagamentoMapper.toEntity(metodoPagamentoDTO);
            MetodoPagamento metodoPagamentoSalvo = metodoPagamentoRepository.save(metodoPagamento);
            log.info("Método de pagamento salvo com sucesso. ID: {}, Bandeira: {}", metodoPagamentoSalvo.getId(), metodoPagamentoSalvo.getBandeira());
            return metodoPagamentoMapper.toDTO(metodoPagamentoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar método de Pagamento '{}': {}", metodoPagamentoDTO.getBandeira(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um método de pagamento existente.
     *
     * @param id o ID do método de pagamento a ser excluído.
     */
    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo método de pagamento ID: {}", id);
        if (!metodoPagamentoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: método de Pagamento não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            metodoPagamentoRepository.deleteById(id);
            log.info("Método de pagamento ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir método de pagamento ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}

