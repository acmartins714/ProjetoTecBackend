package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Conteudo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConteudoMapper {

    private final ModelMapper modelMapper;

    public ConteudoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Conteudo toEntity(ConteudoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Conteudo.class);
    }

    public ConteudoDTO toDTO(Conteudo entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, ConteudoDTO.class);
    }

}
