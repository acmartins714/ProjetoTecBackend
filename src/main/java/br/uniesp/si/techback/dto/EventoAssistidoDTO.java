package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoAssistidoDTO {

    @NotBlank(message = "A identificação do usuário deve ser informada!")
    private Long usuarioId;

    @NotBlank(message = "A identificação do conteúdo deve ser informada!")
    private Long conteudoId;

    @NotBlank(message = "A data em que o conteúdo foi assistido deve ser informada!")
    private LocalDate assistidoEm;

}
