package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, columnDefinition = "VARCHAR(200)",length = 200)
    private String titulo;

    @Column(name = "tipo", nullable = false, columnDefinition = "VARCHAR(10)", length = 10)
    private String tipo;

    @Column(name = "ano", nullable = false, columnDefinition = "SMALLINT")
    @Range(min = 1888, max = 2100)
    private int ano;

    @Column(name = "duracao_minutos", nullable = false, columnDefinition = "SMALLINT")
    @Range(min = 1, max = 240)
    private int duracaoMinutos;

    @Column(name = "relevancia", nullable = false, columnDefinition = "NUMERIC(4,2)")
    @Digits(integer = 2, fraction = 2)
    @DecimalMin(value = "0.00", message = "Relevância deve ser um valor entre 0 e 10")
    @DecimalMax(value = "10.00", message = "Relevância deve ser um valor entre 0 e 10")
    private BigDecimal relevancia;

    @Column(name = "sinopse", columnDefinition = "TEXT")
    private String sinopse;

    @Column(name = "trailer_url",columnDefinition = "VARCHAR(500)", length = 500)
    private String trailerUrl;

    @Column(name = "genero",columnDefinition = "VARCHAR(50)", length = 50)
    private String genero;

    @Column(name = "criado_em", columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", columnDefinition = "TIMESTAMP")
    private LocalDateTime atualizadoEm;

}
