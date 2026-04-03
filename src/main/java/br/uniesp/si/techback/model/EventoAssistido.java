package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento_assistido")
@IdClass(EventoAssistidoId.class)
public class EventoAssistido {

    @Id
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Id
    @Column(name = "conteudo_id", nullable = false)
    private Long conteudoId;

    @Column(name = "assistido_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate assistidoEm;

}
