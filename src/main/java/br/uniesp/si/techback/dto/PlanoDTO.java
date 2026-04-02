package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O limite diário de utilização do plano deve ser informado!")
    private int limiteDiario;

    @NotBlank(message = "A quantidade de acessos simultaneos do plano deve ser informada!")
    private int streams_simultaneos;

}
