package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanoMapper {

    private final ModelMapper modelMapper;

    public PlanoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Plano toEntity(PlanoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Plano.class);
    }

    public PlanoDTO toDTO(Plano entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, PlanoDTO.class);
    }

}
