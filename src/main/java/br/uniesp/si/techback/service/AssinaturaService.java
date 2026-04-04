package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.mapper.AssinaturaMapper;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.repository.AssinaturaRepository;
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
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final AssinaturaMapper assinaturaMapper;

    public List<AssinaturaDTO> listar() {
        log.info("Buscando todas as assinaturas cadastradas");
        try {
            List<Assinatura> assinaturas = assinaturaRepository.findAll();
            List<AssinaturaDTO> assinaturasDTO = assinaturas.stream()
                    .map(assinaturaMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de assinaturas encontradas: {}", assinaturasDTO.size());
            return assinaturasDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar assinaturas: {}", e.getMessage(), e);
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
     * @return lista de assinaturas paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir alguma assinatura cadastrado.
     */
    public Page<AssinaturaDTO> listaPaginada(Pageable pageable) {
        Page<Assinatura> result = assinaturaRepository.findAll(pageable);
        return result.map(x -> new AssinaturaMapper().toDTO(x));
    }

    /**
     * @param id o ID do assinatura.
     * @return o assinatura encontrada, ou lança uma exceção {@link RuntimeException} se o assinatura não existir.
     */
    public AssinaturaDTO buscarPorId(Long id) {
        log.info("Buscando assinatura pelo ID: {}", id);
        Assinatura assinatura = assinaturaRepository.findById(id)
                .map(assinaturaEncontrada -> {
                    log.debug("Assinatura encontrada: ID={}, ID Usuário={}", assinaturaEncontrada.getId(), assinaturaEncontrada.getUsuarioId());
                    return assinaturaEncontrada;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Assinatura não encontrada com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return assinaturaMapper.toDTO(assinatura);
    }

    /**
     * Atualiza um assinatura existente.
     *
     * @param id    o ID do assinatura a ser atualizado.
     * @param assinatura o assinatura com as informações atualizadas.
     * @return o assinatura atualizado.
     */
    @Transactional
    public AssinaturaDTO atualizar(Long id, AssinaturaDTO assinaturaDTO) {
        log.info("Atualizando assinatura ID: {}", id);
        Assinatura assinaturaAtualizado = assinaturaRepository.findById(id)
                .map(assinaturaExistente -> {
                    log.debug("Dados atuais da assinatura: {}", assinaturaExistente);
                    log.debug("Novos dados: {}", assinaturaDTO);
                    assinaturaDTO.setId(id);
                    Assinatura assinaturaParaAtualizar = assinaturaMapper.toEntity(assinaturaDTO);
                    Assinatura assinaturaSalvo = assinaturaRepository.save(assinaturaParaAtualizar);
                    log.info("Assinatura ID: {} atualizada com sucesso. Novo ID de Usuário: {}",
                            id, assinaturaSalvo.getUsuarioId());
                    return assinaturaSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: assinatura não encontrada com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return assinaturaMapper.toDTO(assinaturaAtualizado);
    }

    /**
     * Salva um novo assinatura.
     *
     * @param assinatura o assinatura a ser salvo.
     * @return o assinatura salvo.
     */
    @Transactional
    public AssinaturaDTO salvar(AssinaturaDTO assinaturaDTO) {
        log.info("Salvando novo assinatura: {}", assinaturaDTO.getUsuarioId());
        try {
            Assinatura assinatura = assinaturaMapper.toEntity(assinaturaDTO);
            Assinatura assinaturaSalva = assinaturaRepository.save(assinatura);
            log.info("Assinatura salva com sucesso. ID: {}, Identificador de Usuário : {}", assinaturaSalva.getId(), assinaturaSalva.getUsuarioId());
            return assinaturaMapper.toDTO(assinaturaSalva);
        } catch (Exception e) {
            log.error("Falha ao salvar assinatura '{}': {}", assinaturaDTO.getUsuarioId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um assinatura existente.
     *
     * @param id o ID do assinatura a ser excluído.
     */
    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo assinatura ID: {}", id);
        if (!assinaturaRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: assinatura não encontrada com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            assinaturaRepository.deleteById(id);
            log.info("Assinatura ID: {} excluída com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir assinatura ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
