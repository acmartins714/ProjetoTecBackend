package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.EventoAssistidoDTO;
import br.uniesp.si.techback.model.EventoAssistidoId;
import br.uniesp.si.techback.service.EventoAssistidoService;
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
@RequestMapping("/eventosassistidos")
@RequiredArgsConstructor
@Slf4j
public class EventoAssistidoController {

    private final EventoAssistidoService eventoAssistidoService;

    @GetMapping
    public List<EventoAssistidoDTO> listar() {
        log.info("Listando todos os eventos assistidos");
        List<EventoAssistidoDTO> eventoAssistidos = eventoAssistidoService.listar();
        log.debug("Total de eventos assistidos encontrados: {}", eventoAssistidos.size());
        return eventoAssistidos;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<EventoAssistidoDTO>> findAll(Pageable pageable) {
        Page<EventoAssistidoDTO> dto = eventoAssistidoService.listaPaginada(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<EventoAssistidoDTO> buscarPorId(@PathVariable Long usuarioId, Long conteudoId) {

        EventoAssistidoId id = new EventoAssistidoId(usuarioId, conteudoId);

        try {
            EventoAssistidoDTO eventoAssistido = eventoAssistidoService.buscarPorId(id);
            log.debug("Conteúdo encontrado: {}", eventoAssistido);
            return ResponseEntity.ok(eventoAssistido);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdo com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EventoAssistidoDTO> criar(@Valid @RequestBody EventoAssistidoDTO eventoAssistidoDTO) {
        log.info("Recebida requisição para criar novo evento assistido: {}", eventoAssistidoDTO.getEventoAssistidoId());
        try {
            EventoAssistidoDTO eventoAssistidoSalvo = eventoAssistidoService.salvar(eventoAssistidoDTO);
            log.info("Evento Assistido criado com sucesso. ID: {}!", eventoAssistidoSalvo.getEventoAssistidoId());
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(eventoAssistidoSalvo.getEventoAssistidoId())
                    .toUri();
            log.debug("URI de localização do novo evento assistido: {}", location);
            
            return ResponseEntity.created(location).body(eventoAssistidoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar evento assitido: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<EventoAssistidoDTO> atualizar(@PathVariable Long usuarioId, @PathVariable Long conteudoId, @Valid @RequestBody EventoAssistidoDTO eventoAssistidoDTO) {
        log.info("Atualizando evento assistido com ID de usuário {} e ID conteúdo {} : {}", usuarioId, conteudoId, eventoAssistidoDTO);

        EventoAssistidoId id = new EventoAssistidoId(usuarioId, conteudoId);

        try {
            EventoAssistidoDTO eventoAssistidoAtualizado = eventoAssistidoService.atualizar(id, eventoAssistidoDTO);
            log.debug("Evento assistido ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(eventoAssistidoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar evento assistido ID {}: {} ", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long usuarioId, Long conteudoId) {

        EventoAssistidoId id = new EventoAssistidoId(usuarioId, conteudoId);

        log.info("Excluindo conteúdo com ID: {}", id);
        try {
            eventoAssistidoService.excluir(id);
            log.debug("Conteúdo com ID {} excluído com sucesso!", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir conteúdo com ID {}!: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

}
