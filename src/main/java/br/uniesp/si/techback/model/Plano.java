package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    //@OneToMany(mappedBy = "plano")
    //private List<Assinatura> assinaturas;

}
