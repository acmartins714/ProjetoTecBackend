package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.EventoAssistidoDTO;
import br.uniesp.si.techback.mapper.EventoAssistidoMapper;
import br.uniesp.si.techback.model.EventoAssistido;
import br.uniesp.si.techback.model.EventoAssistidoId;
import br.uniesp.si.techback.repository.EventoAssistidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventoAssistidoService {

    private final EventoAssistidoRepository eventoAssistidoRepository;
    private final EventoAssistidoMapper eventoAssistidoMapper;

    public List<EventoAssistidoDTO> listar() {
        log.info("Buscando todos os eventos assistidos cadastrados");
        try {
            List<EventoAssistido> eventoAssistidos = eventoAssistidoRepository.findAll();
            List<EventoAssistidoDTO> eventoAssistidosDTO = eventoAssistidos.stream()
                    .map(eventoAssistidoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de eventos assistidos encontrados: {}", eventoAssistidosDTO.size());
            return eventoAssistidosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar eventos assistidos: {}", e.getMessage(), e);
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
     * @return lista de eventoAssistidos paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum eventoAssistido cadastrado.
     */
    public Page<EventoAssistidoDTO> listaPaginada(Pageable pageable) {
        Page<EventoAssistido> result = eventoAssistidoRepository.findAll(pageable);
        return result.map(x -> new EventoAssistidoMapper().toDTO(x));
    }

    /**
     * @param id o ID do eventoAssistido.
     * @return o eventoAssistido encontrado, ou lança uma exceção {@link RuntimeException} se o eventoAssistido não existir.
     */
    public EventoAssistidoDTO buscarPorId(EventoAssistidoId id) {

        log.info("Buscando eventos assistidos pelo ID {}:", id);

        EventoAssistido eventoAssistido = eventoAssistidoRepository.findById(id)
                .map(eventoAssistidoEncontrado -> {
                    log.debug("Evento assistido encontrado: ID={}", id);
                    return eventoAssistidoEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Evento assistido não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return eventoAssistidoMapper.toDTO(eventoAssistido);
    }

    /**
     * Atualiza um eventoAssistido existente.
     *
     * @param id    o ID do eventoAssistido a ser atualizado.
     * @param evento assistido o eventoAssistido com as informações atualizadas.
     * @return o eventoAssistido atualizado.
     */
    @Transactional
    public EventoAssistidoDTO atualizar(EventoAssistidoId id, EventoAssistidoDTO eventoAssistidoDTO) {

        log.info("Atualizando evento assistido ID: {}", id);

        EventoAssistido eventoAssistidoAtualizado = eventoAssistidoRepository.findById(id)
                .map(eventoAssistidoExistente -> {
                    log.debug("Dados atuais do eventoAssistido: {}", eventoAssistidoExistente);

                    log.debug("Novos dados: {}", eventoAssistidoDTO);

                    eventoAssistidoDTO.setEventoAssistidoId(id);
                    EventoAssistido eventoAssistidoParaAtualizar = eventoAssistidoMapper.toEntity(eventoAssistidoDTO);

                    EventoAssistido eventoAssistidoSalvo = eventoAssistidoRepository.save(eventoAssistidoParaAtualizar);

                    log.info("Evento assistido ID: {} atualizado com sucesso. Novo id datatítulo: {}",
                            id, eventoAssistidoSalvo.getAssistidoEm());
                    return eventoAssistidoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: evento assistido não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return eventoAssistidoMapper.toDTO(eventoAssistidoAtualizado);
    }

    /**
     * Salva um novo eventoAssistido.
     *
     * @param evento assistido o eventoAssistido a ser salvo.
     * @return o eventoAssistido salvo.
     */
    @Transactional
    public EventoAssistidoDTO salvar(EventoAssistidoDTO eventoAssistidoDTO) {
        log.info("Salvando novo eventoAssistido: {}", eventoAssistidoDTO.getEventoAssistidoId());
        try {
            EventoAssistido eventoAssistido = eventoAssistidoMapper.toEntity(eventoAssistidoDTO);
            EventoAssistido eventoAssistidoSalvo = eventoAssistidoRepository.save(eventoAssistido);
            log.info("EventoAssistido salvo com sucesso. ID: {}", eventoAssistidoSalvo.getEventoAssistidoId());
            return eventoAssistidoMapper.toDTO(eventoAssistidoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar evento assistido ID: {}!", eventoAssistidoDTO.getEventoAssistidoId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um eventoAssistido existente.
     *
     * @param id o ID do eventoAssistido a ser excluído.
     */
    @Transactional
    public void excluir(EventoAssistidoId id) {

        log.info("Excluindo evento assistido ID: {}", id);
        if (!eventoAssistidoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: Evento assistido não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            eventoAssistidoRepository.deleteById(id);
            log.info("Evento assistido ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir eventoAssistido ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
