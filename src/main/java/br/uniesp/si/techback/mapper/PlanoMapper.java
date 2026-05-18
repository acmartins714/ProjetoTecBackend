package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanoMapper {

    @Autowired
    private AssinaturaMapper assinaturaMapper;

    public Plano toEntity(PlanoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Plano plano = new Plano();
        plano.setId(dto.getId());
        plano.setCodigo(dto.getCodigo());
        plano.setLimiteDiario(dto.getLimiteDiario());
        plano.setStreams_simultaneos(dto.getStreams_simultaneos());
        if (dto.getAssinaturas() != null) {
            List<Assinatura> assinaturas = dto.getAssinaturas().stream()
                .map(assinaturaMapper::toEntity)
                .collect(Collectors.toList());
            plano.setAssinaturas(assinaturas);
        }
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
        if (entity.getAssinaturas() != null) {
            List<AssinaturaDTO> assinaturas = entity.getAssinaturas().stream()
                .map(assinaturaMapper::toDTO)
                .collect(Collectors.toList());
            dto.setAssinaturas(assinaturas);
        }
        return dto;
    }

}
