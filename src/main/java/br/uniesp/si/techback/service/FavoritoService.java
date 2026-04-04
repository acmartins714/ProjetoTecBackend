package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.mapper.FavoritoMapper;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
import br.uniesp.si.techback.repository.FavoritoRepository;
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
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final FavoritoMapper favoritoMapper;

    public List<FavoritoDTO> listar() {
        log.info("Buscando todos os favoritos cadastrados");
        try {
            List<Favorito> favoritos = favoritoRepository.findAll();
            List<FavoritoDTO> favoritosDTO = favoritos.stream()
                    .map(favoritoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de favoritos encontrados: {}", favoritosDTO.size());
            return favoritosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar favoritos: {}", e.getMessage(), e);
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
     * @return lista de favoritos paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum favorito cadastrado.
     */
    public Page<FavoritoDTO> listaPaginada(Pageable pageable) {
        Page<Favorito> result = favoritoRepository.findAll(pageable);
        return result.map(x -> new FavoritoMapper().toDTO(x));
    }

    /**
     * @param id o ID do favorito.
     * @return o favorito encontrado, ou lança uma exceção {@link RuntimeException} se o favorito não existir.
     */
    public FavoritoDTO buscarPorId(FavoritoId id) {

        log.info("Buscando favoritos ID {}:", id);

        Favorito favorito = favoritoRepository.findById(id)
                .map(favoritoEncontrado -> {
                    log.debug("favorito encontrado: ID={}", id);
                    return favoritoEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("favorito não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return favoritoMapper.toDTO(favorito);
    }

    /**
     * Atualiza um favorito existente.
     *
     * @param id    o ID do favorito a ser atualizado.
     * @param favorito com as informações atualizadas.
     * @return o favorito atualizado.
     */
    @Transactional
    public FavoritoDTO atualizar(FavoritoId id, FavoritoDTO favoritoDTO) {

        log.info("Atualizando favorito ID: {}", id);

        Favorito favoritoAtualizado = favoritoRepository.findById(id)
                .map(favoritoExistente -> {
                    log.debug("Dados atuais do favorito: {}", favoritoExistente);

                    log.debug("Novos dados: {}", favoritoDTO);

                    favoritoDTO.setFavoritoId(id);
                    Favorito favoritoParaAtualizar = favoritoMapper.toEntity(favoritoDTO);

                    Favorito favoritoSalvo = favoritoRepository.save(favoritoParaAtualizar);

                    log.info("Favorito ID: {} atualizado com sucesso.", id);
                    return favoritoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: favorito não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return favoritoMapper.toDTO(favoritoAtualizado);
    }

    /**
     * Salva um novo favorito.
     *
     * @param favorito assistido o favorito a ser salvo.
     * @return o favorito salvo.
     */
    @Transactional
    public FavoritoDTO salvar(FavoritoDTO favoritoDTO) {
        log.info("Salvando novo favorito: {}", favoritoDTO.getFavoritoId());
        try {
            Favorito favorito = favoritoMapper.toEntity(favoritoDTO);
            Favorito favoritoSalvo = favoritoRepository.save(favorito);
            log.info("Favorito salvo com sucesso. ID: {}", favoritoSalvo.getFavoritoId());
            return favoritoMapper.toDTO(favoritoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar favorito ID: {}!", favoritoDTO.getFavoritoId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um favorito existente.
     *
     * @param id o ID do favorito a ser excluído.
     */
    @Transactional
    public void excluir(FavoritoId id) {

        log.info("Excluindo favorito ID: {}", id);
        if (!favoritoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: Favorito não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            favoritoRepository.deleteById(id);
            log.info("Favorito ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir favorito ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
