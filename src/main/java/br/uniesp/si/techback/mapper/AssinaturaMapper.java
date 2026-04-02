package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.model.Assinatura;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {

    public Assinatura toEntity(AssinaturaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Assinatura assinatura = new Assinatura();
        assinatura.setId(dto.getId());
        assinatura.setUsuarioId(dto.getUsuarioId());
        assinatura.setPlanoId(dto.getPlanoId());
        assinatura.setStatus(dto.getStatus());
        assinatura.setIniciadaEm(dto.getIniciadaEm());
        assinatura.setCanceladaEm(dto.getCanceladaEm());

        return assinatura;
    }

    public AssinaturaDTO toDTO(Assinatura entity) {
        if (entity == null) {
            return null;
        }
        
        AssinaturaDTO dto = new AssinaturaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setPlanoId(entity.getPlanoId());
        dto.setStatus(entity.getStatus());
        dto.setIniciadaEm(entity.getIniciadaEm());
        dto.setCanceladaEm(entity.getCanceladaEm());

        return dto;
    }

}
