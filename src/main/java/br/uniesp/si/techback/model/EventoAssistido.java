package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento_assistido")
public class EventoAssistido {

    @EmbeddedId
    private EventoAssistidoId eventoAssistidoId;

    @ManyToOne
    @MapsId("clienteId")
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @MapsId("conteudoId")
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    @Column(name = "assistido_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime assistidoEm;

}