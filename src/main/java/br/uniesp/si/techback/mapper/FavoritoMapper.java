package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Favorito;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FavoritoMapper {

    private final ModelMapper modelMapper;

    public FavoritoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Favorito toEntity(FavoritoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Favorito.class);
    }

    public FavoritoDTO toDTO(Favorito entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, FavoritoDTO.class);
    }
}
