package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.mapper.ConteudoMapper;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.repository.ConteudoRepository;
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
public class ConteudoService {

    private final ConteudoRepository conteudoRepository;
    private final ConteudoMapper conteudoMapper;

    public List<ConteudoDTO> listar() {
        log.info("Buscando todos os conteudos cadastrados");
        try {
            List<Conteudo> conteudos = conteudoRepository.findAll();
            List<ConteudoDTO> conteudosDTO = conteudos.stream()
                    .map(conteudoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de conteúdos encontrados: {}", conteudosDTO.size());
            return conteudosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar conteúdos: {}", e.getMessage(), e);
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
     * @return lista de conteúdos paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum conteúdo cadastrado.
     */
    public Page<ConteudoDTO> listaPaginada(Pageable pageable) {
        Page<Conteudo> result = conteudoRepository.findAll(pageable);
        return result.map(x -> new ConteudoMapper().toDTO(x));
    }

    /**
     * @param id o ID do conteudo.
     * @return o conteudo encontrado, ou lança uma exceção {@link RuntimeException} se o conteudo não existir.
     */
    public ConteudoDTO buscarPorId(Long id) {
        log.info("Buscando conteudo pelo ID: {}!", id);
        Conteudo conteudo = conteudoRepository.findById(id)
                .map(conteudoEncontrado -> {
                    log.debug("Conteúdo encontrado: ID={}, Título={}!", conteudoEncontrado.getId(), conteudoEncontrado.getTitulo());
                    return conteudoEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Conteúdo não encontrado com o ID: %d!", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return conteudoMapper.toDTO(conteudo);
    }

    /**
     * Atualiza um conteudo existente.
     *
     * @param id    o ID do conteudo a ser atualizado.
     * @param conteudo o conteudo com as informações atualizadas.
     * @return o conteudo atualizado.
     */
    @Transactional
    public ConteudoDTO atualizar(Long id, ConteudoDTO conteudoDTO) {
        log.info("Atualizando conteudo ID: {}", id);
        Conteudo conteudoAtualizado = conteudoRepository.findById(id)
                .map(conteudoExistente -> {
                    log.debug("Dados atuais do conteudo: {}", conteudoExistente);
                    log.debug("Novos dados: {}", conteudoDTO);
                    conteudoDTO.setId(id);
                    Conteudo conteudoParaAtualizar = conteudoMapper.toEntity(conteudoDTO);
                    Conteudo conteudoSalvo = conteudoRepository.save(conteudoParaAtualizar);
                    log.info("Conteúdo ID: {} atualizado com sucesso. Novo título: {}",
                            id, conteudoSalvo.getTitulo());
                    return conteudoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: conteúdo não encontrado com o ID: %d!", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return conteudoMapper.toDTO(conteudoAtualizado);
    }

    /**
     * Salva um novo conteudo.
     *
     * @param conteudo o conteudo a ser salvo.
     * @return o conteudo salvo.
     */
    @Transactional
    public ConteudoDTO salvar(ConteudoDTO conteudoDTO) {
        log.info("Salvando novo conteúdo: {}", conteudoDTO.getTitulo());
        try {
            Conteudo conteudo = conteudoMapper.toEntity(conteudoDTO);
            Conteudo conteudoSalvo = conteudoRepository.save(conteudo);
            log.info("Conteúdo salvo com sucesso. ID: {}, Título: {}!", conteudoSalvo.getId(), conteudoSalvo.getTitulo());
            return conteudoMapper.toDTO(conteudoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar conteúdo '{}': {}!", conteudoDTO.getTitulo(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um conteúdo existente.
     *
     * @param id o ID do conteúdo a ser excluído.
     */
    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo conteudo ID: {}", id);
        if (!conteudoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: conteúdo não encontrado com o ID: %d!", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            conteudoRepository.deleteById(id);
            log.info("Conteúdo ID: {} excluído com sucesso!", id);
        } catch (Exception e) {
            log.error("Erro ao excluir conteúdo ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
