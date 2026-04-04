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
        eventoAssistido.setEventoAssistidoId(dto.getEventoAssistidoId());
        eventoAssistido.setAssistidoEm(dto.getAssistidoEm());

        return eventoAssistido;
    }

    public EventoAssistidoDTO toDTO(EventoAssistido entity) {
        if (entity == null) {
            return null;
        }
        
        EventoAssistidoDTO dto = new EventoAssistidoDTO();
        dto.setEventoAssistidoId(entity.getEventoAssistidoId());
        dto.setAssistidoEm(entity.getAssistidoEm());

        return dto;
    }

}
