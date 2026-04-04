package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDTO {

    private Long id;

    @NotBlank(message = "O código do plano deve ser informado!")
    private String codigo;

    @NotNull(message = "O limite diário de utilização do plano deve ser informado!")
    private int limiteDiario;

    @NotNull(message = "A quantidade de acessos simultaneos do plano deve ser informada!")
    private int streams_simultaneos;


}
