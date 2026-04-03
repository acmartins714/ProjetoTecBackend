package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHhash(dto.getSenhaHhash());
        usuario.setCpfCnpj(dto.getCpfCnpj());
        usuario.setPerfil(dto.getPerfil());
        usuario.setCriadoEm(dto.getCriadoEm());
        usuario.setAtualizadoEm(dto.getAtualizadoEm());

        return usuario;
    }

    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setEmail(entity.getEmail());
        dto.setSenhaHhash(entity.getSenhaHhash());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setPerfil(entity.getPerfil());
        dto.setCriadoEm(entity.getCriadoEm());
        dto.setAtualizadoEm(entity.getAtualizadoEm());

        return dto;
    }

}
