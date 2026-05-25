package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.MetodoPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagamentoMapper {

    private final ModelMapper modelMapper;

    public MetodoPagamentoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MetodoPagamento toEntity(MetodoPagamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, MetodoPagamento.class);
    }

    public MetodoPagamentoDTO toDTO(MetodoPagamento entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, MetodoPagamentoDTO.class);
    }

}
