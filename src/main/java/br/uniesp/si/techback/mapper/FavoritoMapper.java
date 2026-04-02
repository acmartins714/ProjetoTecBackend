package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.Favorito;
import org.springframework.stereotype.Component;

@Component
public class FavoritoMapper {

    public Favorito toEntity(FavoritoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Favorito favorito = new Favorito();
        favorito.setUsuarioId(dto.getUsuarioId());
        favorito.setConteudoId(dto.getUsuarioId());
        favorito.setCriadoEm(dto.getCriadoEm());

        return favorito;
    }

    public FavoritoDTO toDTO(Favorito entity) {
        if (entity == null) {
            return null;
        }
        
        FavoritoDTO dto = new FavoritoDTO();
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setConteudoId(entity.getConteudoId());
        dto.setCriadoEm(entity.getCriadoEm());

        return dto;
    }

}
