package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoDTO {

    private Long id;

    @NotBlank(message = "O título do filme ou série é obrigatório")
    private String titulo;

    @NotBlank(message = "O tipo do conteúdo não pode ser vazio. Obrigatoriamente deve ser preenchido com FILME ou SERIE!")
    @Pattern(regexp = "FILME|SERIE", message = "Este campo só aceita os valores FILME e SERIE!")
    private String tipo;

    @NotBlank(message = "O ano de lançamento do filme / série deve ser preenchido!")
    @Range(min = 1888, max = 2100, message = "O ano de lançamento do Filme / Série deve ser um valor entre 1888 e 2100")
    private int ano;

    @NotBlank(message = "A duração do filme / série deve ser preenchida!")
    @Range(min = 1, max = 999, message = "A duração Filme / Série deve ser um valor entre 1 e 999!")
    private int duracaoMinutos;

    @NotBlank(message = "A relevância do filme / série deve ter um valor!")
    private BigDecimal relevancia;

    private String sinopse;

    private String trailerUrl;

    private String genero;

    private LocalDate criadoEm;

    private LocalDate atualizadoEm;

}
