package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.service.MetodoPagamentoService;
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
@RequestMapping("/metodopagamento")
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    @GetMapping
    public List<MetodoPagamentoDTO> listar() {
        log.info("Listando todos os métodos de pagamento");
        List<MetodoPagamentoDTO> metodoPagamentos = metodoPagamentoService.listar();
        log.debug("Total de métodos de pagamento encontrados: {}", metodoPagamentos.size());
        return metodoPagamentos;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<MetodoPagamentoDTO>> findAll(Pageable pageable) {
        Page<MetodoPagamentoDTO> dto = metodoPagamentoService.listarPaginado(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagamentoDTO> buscarPorId(@PathVariable Long id) {
        try {
            MetodoPagamentoDTO metodoPagamento = metodoPagamentoService.buscarPorId(id);
            log.debug("Método de pagamento encontrado: {}", metodoPagamento);
            return ResponseEntity.ok(metodoPagamento);
        } catch (Exception e) {
            log.error("Erro ao buscar método de pagamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MetodoPagamentoDTO> criar(@Valid @RequestBody MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Recebida requisição para criar novo método de pagamento: {}, para o portador: {}", metodoPagamentoDTO.getId(), metodoPagamentoDTO.getNomePortador());
        try {
            MetodoPagamentoDTO metodoPagamentoSalvo = metodoPagamentoService.salvar(metodoPagamentoDTO);
            log.info("Método de pagamento criado com sucesso. ID: {}, Nome do Portador: {}", metodoPagamentoSalvo.getId(), metodoPagamentoSalvo.getNomePortador());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(metodoPagamentoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo método de pagamento: {}", location);

            return ResponseEntity.created(location).body(metodoPagamentoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar método de pagamento: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Atualizando método de pagamento com ID {}: {}", id, metodoPagamentoDTO);
        try {
            MetodoPagamentoDTO metodoPagamentoAtualizado = metodoPagamentoService.atualizar(id, metodoPagamentoDTO);
            log.debug("Método de pagamento ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(metodoPagamentoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar método de Pagamento ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo método de pagamento com ID: {}", id);
        try {
            metodoPagamentoService.excluir(id);
            log.debug("Método de pagamento com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluír método de pagamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
    
}

