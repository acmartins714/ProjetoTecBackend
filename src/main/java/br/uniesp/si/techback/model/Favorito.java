package br.uniesp.si.techback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "favorito")
public class Favorito {

    @EmbeddedId
    private FavoritoId favoritoId;

    @ManyToOne
    @MapsId("clienteId")
    @JoinColumn(name = "cliente_id")
    @JsonIgnore // Impede que o usuário seja serializado dentro do evento
    private Usuario cliente;

    @ManyToOne
    @MapsId("conteudoId")
    @JoinColumn(name = "conteudo_id")
    @JsonIgnore
    private Conteudo conteudo;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

}
