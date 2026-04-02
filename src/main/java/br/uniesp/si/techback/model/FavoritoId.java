package br.uniesp.si.techback.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoId implements Serializable {
    private Long usuarioId;
    private Long conteudoId;
}
