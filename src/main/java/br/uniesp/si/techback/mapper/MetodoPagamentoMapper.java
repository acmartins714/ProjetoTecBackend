package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.MetodoPagamento;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagamentoMapper {

    public MetodoPagamento toEntity(MetodoPagamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        MetodoPagamento metodoPagamento = new MetodoPagamento();
        metodoPagamento.setId(dto.getId());
        metodoPagamento.setUsuarioId(dto.getUsuarioId());
        metodoPagamento.setBandeira(dto.getBandeira());
        metodoPagamento.setUltimos4(dto.getUltimos4());
        metodoPagamento.setMesExp(dto.getMesExp());
        metodoPagamento.setAnoExp(dto.getAnoExp());
        metodoPagamento.setNomePortador(dto.getNomePortador());
        metodoPagamento.setTokenGateway(dto.getTokenGateway());
        metodoPagamento.setCriadoEm(dto.getCriadoEm());

        return metodoPagamento;
    }

    public MetodoPagamentoDTO toDTO(MetodoPagamento entity) {
        if (entity == null) {
            return null;
        }
        
        MetodoPagamentoDTO dto = new MetodoPagamentoDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setBandeira(entity.getBandeira());
        dto.setUltimos4(entity.getUltimos4());
        dto.setMesExp(entity.getMesExp());
        dto.setAnoExp(entity.getAnoExp());
        dto.setNomePortador(entity.getNomePortador());
        dto.setTokenGateway(entity.getTokenGateway());
        dto.setCriadoEm(entity.getCriadoEm());

        return dto;
    }

}
