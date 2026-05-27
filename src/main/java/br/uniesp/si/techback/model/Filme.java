package br.uniesp.si.techback.model;

import br.uniesp.si.techback.validation.Genero;
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
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)", length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    @Genero
    private String genero;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "classificacao_indicativa", columnDefinition = "VARCHAR(10)", length = 10)
    private String classificacaoIndicativa;
}
