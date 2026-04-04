package br.uniesp.si.techback.model;

import jakarta.persistence.*;
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

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "tipo", nullable = false, columnDefinition = "CHAR(10)", length = 10)
    private String tipo;

    @Column(name = "ano", nullable = false, columnDefinition = "SMALLINT")
    @Range(min = 1888, max = 2100)
    private int ano;

    @Column(name = "duracao_minutos", nullable = false, columnDefinition = "SMALLINT")
    @Range(min = 1, max = 999)
    private int duracaoMinutos;

    @Column(name = "relevancia", nullable = false, columnDefinition = "NUMBER(4,2)")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal relevancia;

    @Column(name = "sinopse", columnDefinition = "TEXT")
    private String sinopse;

    @Column(name = "trailer_url", length = 500)
    private String trailerUrl;

    @Column(name = "genero", length = 50)
    private String genero;

    @Column(name = "criado_em", columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", columnDefinition = "TIMESTAMP")
    private LocalDateTime atualizadoEm;

}
