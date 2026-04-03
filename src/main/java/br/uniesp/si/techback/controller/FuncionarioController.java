package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Slf4j
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioDTO> listar() {
        log.info("Listando todos os funcionarios");
        List<FuncionarioDTO> funcionarios = funcionarioService.listar();
        log.debug("Total de funcionarios encontrados: {}", funcionarios.size());
        return funcionarios;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<FuncionarioDTO>> findAll(Pageable pageable) {
        Page<FuncionarioDTO> dto = funcionarioService.listarPaginado(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        try {
            FuncionarioDTO funcionario = funcionarioService.buscarPorId(id);
            log.debug("Funcionario encontrado: {}", funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            log.error("Erro ao buscar funcionario com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        log.info("Recebida requisição para criar novo funcionario: {}", funcionarioDTO.getNome());
        try {
            FuncionarioDTO funcionarioSalvo = funcionarioService.salvar(funcionarioDTO);
            log.info("Funcionario criado com sucesso. ID: {}, Título: {}", funcionarioSalvo.getId(), funcionarioSalvo.getNome());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(funcionarioSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo funcionario: {}", location);

            return ResponseEntity.created(location).body(funcionarioSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar funcionario: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        log.info("Atualizando funcionario com ID {}: {}", id, funcionarioDTO);
        try {
            FuncionarioDTO funcionarioAtualizado = funcionarioService.atualizar(id, funcionarioDTO);
            log.debug("Funcionario ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar funcionario ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo funcionario com ID: {}", id);
        try {
            funcionarioService.excluir(id);
            log.debug("Funcionario com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir funcionario com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
    
}

