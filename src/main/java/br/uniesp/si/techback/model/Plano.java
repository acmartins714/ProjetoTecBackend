package br.uniesp.si.techback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "limite_diario", nullable = false, columnDefinition = "SMALLINT")
    private int limiteDiario;

    @Column(name = "streams_simultaneos", nullable = false, columnDefinition = "SMALLINT")
    private int streams_simultaneos;

    @JsonManagedReference
    @OneToMany(mappedBy = "plano")
    private List<Assinatura> assinaturas;
}
