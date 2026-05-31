package br.uniesp.si.techback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoResponseDTO
{

    private Long usuarioId;
    private LocalDateTime criadoEm;
    private String nomeCompleto;
    private String titulo;
    private String sinopse;
    private int ano;

}