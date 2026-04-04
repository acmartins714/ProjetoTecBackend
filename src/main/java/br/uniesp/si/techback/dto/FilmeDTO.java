package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO {


    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(min=3, max=120, message = "O Título do filme precisa ter entre 3 e 120 caracteres!")
    private String titulo;

    @NotBlank(message = "A sinopse do filme é obrigatória!")
    private String sinopse;

    @NotNull(message = "A data de lançamento do filme é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataLancamento;

    @NotBlank(message = "O gênero do filme é obigatório!")
    private String genero;

    @NotNull(message = "O tempo de duração do Filme / Documentário é obigatório!")
    private Integer duracaoMinutos;

    @NotBlank(message = "A classificação indicativa do Filme / Documentário é obigatória!")
    private String classificacaoIndicativa;
}
