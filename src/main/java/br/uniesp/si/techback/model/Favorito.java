package br.uniesp.si.techback.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

}
