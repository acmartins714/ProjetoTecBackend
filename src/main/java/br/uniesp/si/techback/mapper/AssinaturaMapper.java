package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.model.Assinatura;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {

    private final ModelMapper modelMapper;

    public AssinaturaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Assinatura toEntity(AssinaturaDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Assinatura.class);
    }

    public AssinaturaDTO toDTO(Assinatura entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, AssinaturaDTO.class);
    }

}
