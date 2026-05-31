package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.service.ConteudoService;
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
@RequestMapping("/conteudos")
@RequiredArgsConstructor
@Slf4j
public class ConteudoController {

    private final ConteudoService conteudoService;

    @GetMapping
    public List<ConteudoDTO> listar() {
        log.info("Listando todos os conteúdos");
        List<ConteudoDTO> conteudos = conteudoService.listar();
        log.debug("Total de conteúdos encontrados: {}", conteudos.size());
        return conteudos;
    }

    @GetMapping("/listapaginada")
    public ResponseEntity<Page<ConteudoDTO>> findAll(Pageable pageable) {
        Page<ConteudoDTO> dto = conteudoService.listaPaginada(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoDTO> buscarPorId(@PathVariable Long id) {
        try {
            ConteudoDTO conteudo = conteudoService.buscarPorId(id);
            log.debug("Conteúdo encontrado: {}", conteudo);
            return ResponseEntity.ok(conteudo);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdo com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("titulo/{titulo}")
    public ResponseEntity<List<ConteudoDTO>> buscarPorTitulo(@PathVariable String titulo) {
        try {
            List<ConteudoDTO> conteudos = conteudoService.buscarPorTitulo(titulo);
            log.debug("Conteúdo(s) encontrado(s): {}", conteudos.size());
            return ResponseEntity.ok(conteudos);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdo com título ou parte do título igual a {}: {}", titulo, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("ranking/{limite}")
    public ResponseEntity<List<ConteudoDTO>> listaRanking(@PathVariable Long limite) {
        try {
            List<ConteudoDTO> conteudos = conteudoService.listaRanking(limite);
            log.debug("Conteúdo(s) para montagem do ranking dos TOP: {}", limite);
            return ResponseEntity.ok(conteudos);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdos para formação do ranking dos TOP: {} - {}", limite, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("anoLancamento/{ano}")
    public ResponseEntity<List<ConteudoDTO>> listaConteudoAposAnoLancamento(@PathVariable int ano) {
        try {
            List<ConteudoDTO> conteudos = conteudoService.listaConteudoAposAnoLancamento(ano);
            log.debug("Conteúdo(s) lançados após {} ({})", ano, conteudos.size());
            return ResponseEntity.ok(conteudos);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdos com ano de lançamento após {} - {}", ano, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("buscaPalavraChave")
    public ResponseEntity<List<ConteudoDTO>> listaConteudoChaveTituloSinopse(@RequestParam String keyword) {
         try {
            List<ConteudoDTO> conteudos = conteudoService.listaConteudoChaveTituloSinopse(keyword);
            log.debug("Foram encontrados {} conteúdos com a palavra chave: {}.", conteudos.size(), keyword);
            return ResponseEntity.ok(conteudos); // Retorna 200 com a lista
        } catch (Exception e) {
            log.error("Erro interno ao buscar conteúdos pela palavra chave: {} - {}", keyword, e.getMessage(), e);
            return ResponseEntity.internalServerError().build(); // Em caso de erro real, o ideal é 500
         }
    }

    @PostMapping
    public ResponseEntity<ConteudoDTO> criar(@Valid @RequestBody ConteudoDTO conteudoDTO) {
        log.info("Recebida requisição para criar novo conteúdo: {}", conteudoDTO.getId());
        try {
            ConteudoDTO conteudoSalvo = conteudoService.salvar(conteudoDTO);
            log.info("Conteúdo criado com sucesso. ID: {}, Título: {}", conteudoSalvo.getId(), conteudoSalvo.getTitulo());
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(conteudoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo conteúdo: {}", location);
            
            return ResponseEntity.created(location).body(conteudoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar conteúdo: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ConteudoDTO conteudoDTO) {
        log.info("Atualizando conteúdo com ID {}: {}", id, conteudoDTO);
        try {
            ConteudoDTO conteudoAtualizado = conteudoService.atualizar(id, conteudoDTO);
            log.debug("Conteúdo ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(conteudoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar conteúdo ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo conteúdo com ID: {}", id);
        try {
            conteudoService.excluir(id);
            log.debug("Conteúdo com ID {} excluído com sucesso!", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir conteúdo com ID {}!: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }


}
