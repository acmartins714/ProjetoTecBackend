package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.EventoAssistidoDTO;
import br.uniesp.si.techback.model.EventoAssistido;
import org.springframework.stereotype.Component;

@Component
public class EventoAssistidoMapper {

    public EventoAssistido toEntity(EventoAssistidoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EventoAssistido eventoAssistido = new EventoAssistido();
        eventoAssistido.setUsuarioId(dto.getUsuarioId());
        eventoAssistido.setConteudoId(dto.getUsuarioId());
        eventoAssistido.setAssistidoEm(dto.getAssistidoEm());

        return eventoAssistido;
    }

    public EventoAssistidoDTO toDTO(EventoAssistido entity) {
        if (entity == null) {
            return null;
        }
        
        EventoAssistidoDTO dto = new EventoAssistidoDTO();
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setConteudoId(entity.getConteudoId());
        dto.setAssistidoEm(entity.getAssistidoEm());

        return dto;
    }

}
