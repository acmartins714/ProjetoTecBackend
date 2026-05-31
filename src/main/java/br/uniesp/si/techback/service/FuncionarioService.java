package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.exception.CustomBeanException;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final ViaCepClient viaCepClient;

    public List<FuncionarioDTO> listar() {
        log.info("Buscando todos os funcionários cadastrados");
        try {
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                    .map(funcionarioMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de funcionários encontrados: {}", funcionariosDTO.size());
            return funcionariosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar funcionários: {}", e.getMessage(), e);
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
     * @return lista de funcionarios paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum funcionário cadastrado.
    */
    public Page<FuncionarioDTO> listarPaginado(Pageable pageable) {
        Page<Funcionario> result = funcionarioRepository.findAll(pageable);
        return result.map(funcionarioMapper::toDTO);
    }

    /**
     * @param id o ID do funcionario.
     * @return o funcionario encontrado, ou lança uma exceção {@link RuntimeException} se o funcionario não existir.
     */
    public FuncionarioDTO buscarPorId(Long id) {
        log.info("Buscando funcionario pelo ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .map(funcionarioEncontrado -> {
                    log.debug("Funcionario encontrado: ID={}, Título={}", funcionarioEncontrado.getId(), funcionarioEncontrado.getNome());
                    return funcionarioEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Funcionario não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return funcionarioMapper.toDTO(funcionario);
    }

    public List<FuncionarioDTO> buscarPorNome(String nome) {
        log.info("Buscando funcionário por parte do nome: {}", nome);

        try {
            List<Funcionario> funcionarios = funcionarioRepository.buscarPorNome(nome);
            return funcionarios.stream()
                    .map(funcionarioMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
          log.error("Falha ao buscar funcionários: {}", e.getMessage(), e);
          throw e;
        }
    }

    /**
     * Atualiza um funcionario existente.
     *
     * @param id    o ID do funcionario a ser atualizado.
     * @param funcionarioDTO o funcionario com as informações atualizadas.
     * @return o funcionario atualizado.
     */
    @Transactional
    public FuncionarioDTO atualizar(Long id, FuncionarioDTO funcionarioDTO) {
        log.info("Atualizando funcionario ID: {}", id);
        Funcionario funcionarioAtualizado = funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {
                    log.debug("Dados atuais do funcionario: {}", funcionarioExistente);
                    log.debug("Novos dados: {}", funcionarioDTO);
                    funcionarioDTO.setId(id);

                    if (funcionarioDTO.getCep() != null && !funcionarioDTO.getCep().isBlank()) {
                        String cepLimpo = funcionarioDTO.getCep().replaceAll("\\D", "");
                        ViaCepResponseDTO endereco = viaCepClient.buscarPorCep(cepLimpo);

                        // Exemplo simples para a turma: quando a API retorna erro, lancamos a excecao customizada.
                        if (Boolean.TRUE.equals(endereco.getErro())) {
                            throw new CustomBeanException("CEP inválido para consulta no ViaCEP");
                        }

                        funcionarioDTO.setCep(endereco.getCep().replaceAll("\\D", ""));
                        funcionarioDTO.setLogradouro(endereco.getLogradouro());
                        funcionarioDTO.setBairro(endereco.getBairro());
                        funcionarioDTO.setLocalidade(endereco.getLocalidade());
                        funcionarioDTO.setUf(endereco.getUf());
                    }

                    Funcionario funcionarioParaAtualizar = funcionarioMapper.toEntity(funcionarioDTO);
                    Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioParaAtualizar);
                    log.info("Funcionario ID: {} atualizado com sucesso. Novo título: {}",
                            id, funcionarioSalvo.getNome());
                    return funcionarioSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: funcionario não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return funcionarioMapper.toDTO(funcionarioAtualizado);
    }

    /**
     * Salva um novo funcionario.
     *
     * @param funcionarioDTO o funcionario a ser salvo.
     * @return o funcionario salvo.
     */
    @Transactional
    public FuncionarioDTO salvar(FuncionarioDTO funcionarioDTO) {

        log.info("Salvando novo funcionario: {}", funcionarioDTO.getNome());

        if (funcionarioDTO.getCep() != null && !funcionarioDTO.getCep().isBlank()) {
            String cepLimpo = funcionarioDTO.getCep().replaceAll("\\D", "");
            ViaCepResponseDTO endereco = viaCepClient.buscarPorCep(cepLimpo);

            // Exemplo simples para a turma: quando a API retorna erro, lancamos a excecao customizada.
            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new CustomBeanException("CEP invalido para consulta no ViaCEP");
            }

            funcionarioDTO.setCep(endereco.getCep().replaceAll("\\D", ""));
            funcionarioDTO.setLogradouro(endereco.getLogradouro());
            funcionarioDTO.setBairro(endereco.getBairro());
            funcionarioDTO.setLocalidade(endereco.getLocalidade());
            funcionarioDTO.setUf(endereco.getUf());
        }

        try {
            Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
            log.info("Funcionario salvo com sucesso. ID: {}, Nome: {}", funcionarioSalvo.getId(), funcionarioSalvo.getNome());
            return funcionarioMapper.toDTO(funcionarioSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar funcionario '{}': {}", funcionarioDTO.getNome(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um funcionario existente.
     *
     * @param id o ID do funcionario a ser excluído.
     */
    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo funcionario ID: {}", id);
        if (!funcionarioRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: funcionario não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            funcionarioRepository.deleteById(id);
            log.info("Funcionario ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir funcionario ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}

