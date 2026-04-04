package br.uniesp.si.techback.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EventoAssistidoId implements Serializable {
    private Long usuarioId;
    private Long conteudoId;
}
