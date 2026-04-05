package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.model.Plano;
import org.springframework.stereotype.Component;

@Component
public class PlanoMapper {

    public Plano toEntity(PlanoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Plano plano = new Plano();
        plano.setId(dto.getId());
        plano.setCodigo(dto.getCodigo());
        plano.setLimiteDiario(dto.getLimiteDiario());
        plano.setStreams_simultaneos(dto.getStreams_simultaneos());
        return plano;
    }

    public PlanoDTO toDTO(Plano entity) {
        if (entity == null) {
            return null;
        }
        
        PlanoDTO dto = new PlanoDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setLimiteDiario(entity.getLimiteDiario());
        dto.setStreams_simultaneos(entity.getStreams_simultaneos());

        return dto;
    }

}
