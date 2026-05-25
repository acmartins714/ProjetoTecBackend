package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.dto.EventoAssistidoDTO;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.EventoAssistido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventoAssistidoMapper {

    private final ModelMapper modelMapper;

    public EventoAssistidoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventoAssistido toEntity(EventoAssistidoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, EventoAssistido.class);
    }

    public EventoAssistidoDTO toDTO(EventoAssistido entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, EventoAssistidoDTO.class);
    }
}
