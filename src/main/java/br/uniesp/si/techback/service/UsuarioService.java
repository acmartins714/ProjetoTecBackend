package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.client.RfbClient;
import br.uniesp.si.techback.dto.RfbApiResponseDTO;
import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.exception.CustomBeanException;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ViaCepClient viaCepClient;
    private final RfbClient rfbClient;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listar() {
        log.info("Buscando todos os usuários cadastrados");
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<UsuarioDTO> usuariosDTO = usuarios.stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total dos usuários encontrados: {}", usuariosDTO.size());
            return usuariosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar usuários: {}", e.getMessage(), e);
            throw e;
        }
    }

    /*
    public List<UsuarioDTO> listarUsuariosAssinaturas() {
        log.info("Buscando todos os Usuários e seus planos");
        try {
            List<Usuario> usuarios = usuarioRepository.findAllWithAssinaturas();
            List<UsuarioDTO> usuarioDTOS = usuarios.stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total dos usuários encontrados: {}", usuarioDTOS.size());
            return usuarioDTOS;
        } catch (Exception e) {
            log.error("Falha ao buscar usuários e suas assinaturas: {}", e.getMessage(), e);
            throw e;
        }
    }
    */

    /**
     * @param pageable o json
     *  {
     *   "page": 0,
     *   "size": 5,
     *   "sort": "@param1, @param2, [descending]"
     *  }
     * @return lista de filmes paginada, ou lança uma exceção {@link RuntimeException}
     * se não existir algum filme cadastrado.
    */
    public Page<UsuarioDTO> listaPaginada(Pageable pageable) {
        Page<Usuario> result = usuarioRepository.findAll(pageable);
        return result.map(usuarioMapper::toDTO);
    }

    /**
     * @param id o ID do usuário.
     * @return o usuário encontrado, ou lança uma exceção {@link RuntimeException} se o usuário não existir.
     */
    public UsuarioDTO buscarPorId(Long id) {
        log.info("Buscando usuário pelo ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .map(usuarioEncontrado -> {
                    log.debug("Usuário encontrado: ID={}", usuarioEncontrado.getId());
                    return usuarioEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Usuário não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return usuarioMapper.toDTO(usuario);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id    o ID do usuário a ser atualizado.
     * @param usuarioDTO o usuário com as informações atualizadas.
     * @return o usuário atualizado.
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        log.info("Atualizando usuário ID: {}", id);
        Usuario usuarioAtualizado = usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    log.debug("Dados atuais do usuário: {}", usuarioExistente);
                    log.debug("Novos dados: {} ", usuarioDTO);
                    usuarioDTO.setId(id);
                    
                    // Criptografa a senha antes de atualizar
                    usuarioDTO.setSenhaHash(passwordEncoder.encode(usuarioDTO.getSenhaHash()));
                    
                    Usuario usuarioParaAtualizar = usuarioMapper.toEntity(usuarioDTO);
                    Usuario usuarioSalvo = usuarioRepository.save(usuarioParaAtualizar);
                    log.info("Usuário ID: {} atualizado com sucesso. ", id);
                    return usuarioSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: Usuário não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    /**
     * Salva um novo Usuário.
     *
     * @param usuarioDTO o usuário a ser salvo.
     * @return o usuário salvo.
     */
    @Transactional
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {

        log.info("Salvando novo usuário: {}", usuarioDTO.getId());

        if (usuarioDTO.getTipoPessoa() == TIPOPESSOA.JURIDICA) {

            String cnpjLimpo = usuarioDTO.getCpfCnpj().replaceAll("\\D", "");
            RfbApiResponseDTO dadosCnpj = rfbClient.buscarPorCnpj(cnpjLimpo);

            if (dadosCnpj.getStatus().equals("ERROR")) {
                throw new CustomBeanException("C.N.P.J. inválido para consulta na RFB");
            }

            usuarioDTO.setFantasia(dadosCnpj.getFantasia());
            usuarioDTO.setTelefone(dadosCnpj.getTelefone().replaceAll("\\D", ""));
            usuarioDTO.setEmail(dadosCnpj.getEmail());
            usuarioDTO.setComplemento(dadosCnpj.getComplemento());
        }

        if (usuarioDTO.getCep() != null && !usuarioDTO.getCep().isBlank()) {
            String cepLimpo = usuarioDTO.getCep().replaceAll("\\D", "");
            ViaCepResponseDTO endereco = viaCepClient.buscarPorCep(cepLimpo);

            // Exemplo simples para a turma: quando a API retorna erro, lancamos a excecao customizada.
            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new CustomBeanException("CEP invalido para consulta no ViaCEP");
            }

            usuarioDTO.setCep(endereco.getCep().replaceAll("\\D", ""));
            usuarioDTO.setLogradouro(endereco.getLogradouro());
            usuarioDTO.setBairro(endereco.getBairro());
            usuarioDTO.setMunicipio(endereco.getLocalidade());
            usuarioDTO.setUf(endereco.getUf());
        }

        // Criptografa a senha antes de salvar
        usuarioDTO.setSenhaHash(passwordEncoder.encode(usuarioDTO.getSenhaHash()));

        try {
            Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            log.info("Usuário salvo com sucesso. ID: {}, Nome: {}", usuarioSalvo.getId(), usuarioSalvo.getNomeCompleto());
            return usuarioMapper.toDTO(usuarioSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar usuário: {} : {}", usuarioDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Exclui um usuario existente.
     *
     * @param id o ID do usuario a ser excluído.
     */
    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo usuário ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: usuário não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            usuarioRepository.deleteById(id);
            log.info("Usuário ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir usuário ID: {} : {}", id, e.getMessage(), e);
            throw e;
        }
    }

}