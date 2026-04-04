package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.FavoritoId;
import br.uniesp.si.techback.service.FavoritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
@Slf4j
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping
    public List<FavoritoDTO> listar() {
        log.info("Listando todos os favoritos");
        List<FavoritoDTO> favoritos = favoritoService.listar();
        log.debug("Total de favoritos encontrados: {}", favoritos.size());
        return favoritos;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<FavoritoDTO>> findAll(Pageable pageable) {
        Page<FavoritoDTO> dto = favoritoService.listaPaginada(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<FavoritoDTO> buscarPorId(@PathVariable Long usuarioId, Long conteudoId) {

        FavoritoId id = new FavoritoId(usuarioId, conteudoId);

        try {
            FavoritoDTO favorito = favoritoService.buscarPorId(id);
            log.debug("Conteúdo encontrado: {}", favorito);
            return ResponseEntity.ok(favorito);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdo com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> criar(@Valid @RequestBody FavoritoDTO favoritoDTO) {
        log.info("Recebida requisição para criar novo favorito: {}", favoritoDTO.getFavoritoId());
        try {
            FavoritoDTO favoritoSalvo = favoritoService.salvar(favoritoDTO);
            log.info("Favorito criado com sucesso. ID: {}!", favoritoSalvo.getFavoritoId());
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(favoritoSalvo.getFavoritoId())
                    .toUri();
            log.debug("URI de localização do novo favorito: {}", location);
            
            return ResponseEntity.created(location).body(favoritoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar favorito: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<FavoritoDTO> atualizar(@PathVariable Long usuarioId, @PathVariable Long conteudoId, @Valid @RequestBody FavoritoDTO favoritoDTO) {
        log.info("Atualizando favorito com ID de usuário {} e ID conteúdo {} : {}", usuarioId, conteudoId, favoritoDTO);

        FavoritoId id = new FavoritoId(usuarioId, conteudoId);

        try {
            FavoritoDTO favoritoAtualizado = favoritoService.atualizar(id, favoritoDTO);
            log.debug("Favorito ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(favoritoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar favorito ID {}: {} ", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long usuarioId, Long conteudoId) {

        FavoritoId id = new FavoritoId(usuarioId, conteudoId);

        log.info("Excluindo conteúdo com ID: {}", id);
        try {
            favoritoService.excluir(id);
            log.debug("Conteúdo com ID {} excluído com sucesso!", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir conteúdo com ID {}!: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

}
