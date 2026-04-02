package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.model.Conteudo;
import org.springframework.stereotype.Component;

@Component
public class ConteudoMapper {

    public Conteudo toEntity(ConteudoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Conteudo conteudo = new Conteudo();
        conteudo.setId(dto.getId());
        conteudo.setTitulo(dto.getTitulo());
        conteudo.setTipo(dto.getTipo());
        conteudo.setAno(dto.getAno());
        conteudo.setDuracaoMinutos(dto.getDuracaoMinutos());
        conteudo.setRelevancia(dto.getRelevancia());
        conteudo.setSinopse(dto.getSinopse());
        conteudo.setTrailerUrl(dto.getTrailerUrl());
        conteudo.setGenero(dto.getGenero());
        conteudo.setCriadoEm(dto.getCriadoEm());
        conteudo.setAtualizadoEm(dto.getAtualizadoEm());

        return conteudo;
    }

    public ConteudoDTO toDTO(Conteudo entity) {
        if (entity == null) {
            return null;
        }
        
        ConteudoDTO dto = new ConteudoDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setTipo(entity.getTipo());
        dto.setAno(entity.getAno());
        dto.setDuracaoMinutos(entity.getDuracaoMinutos());
        dto.setRelevancia(entity.getRelevancia());
        dto.setSinopse(entity.getSinopse());
        dto.setTrailerUrl(entity.getTrailerUrl());
        dto.setGenero(entity.getGenero());
        dto.setCriadoEm(entity.getCriadoEm());
        dto.setAtualizadoEm(entity.getAtualizadoEm());

        return dto;
    }
    
}
