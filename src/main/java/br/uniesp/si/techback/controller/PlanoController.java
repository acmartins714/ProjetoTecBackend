package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.service.PlanoService;
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
@RequestMapping("/planos")
@RequiredArgsConstructor
@Slf4j
public class PlanoController {

    private final PlanoService planoService;

    @GetMapping
    public List<PlanoDTO> listar() {
        log.info("Listando todos os planos");
        List<PlanoDTO> planos = planoService.listar();
        log.debug("Total de planos encontradas: {}", planos.size());
        return planos;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<PlanoDTO>> findAll(Pageable pageable) {
        Page<PlanoDTO> dto = planoService.listaPaginada(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDTO> buscarPorId(@PathVariable Long id) {
        try {
            PlanoDTO plano = planoService.buscarPorId(id);
            log.debug("Plano encontrado: {}", plano);
            return ResponseEntity.ok(plano);
        } catch (Exception e) {
            log.error("Erro ao buscar plano com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PlanoDTO> criar(@Valid @RequestBody PlanoDTO planoDTO) {
        log.info("Recebida requisição para criar novo plano: {}", planoDTO.getId());
        try {
            PlanoDTO planoSalvo = planoService.salvar(planoDTO);
            log.info("Plano criado com sucesso. ID: {}, Código: {}", planoSalvo.getId(), planoSalvo.getCodigo());
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(planoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo plano: {}", location);
            
            return ResponseEntity.created(location).body(planoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar plano: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PlanoDTO planoDTO) {
        log.info("Atualizando plano com ID {}: {}", id, planoDTO);
        try {
            PlanoDTO planoAtualizado = planoService.atualizar(id, planoDTO);
            log.debug("Plano ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(planoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar plano ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo plano com ID: {}", id);
        try {
            planoService.excluir(id);
            log.debug("Plano com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir plano com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }


}
