package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaDTO {

    private Long id;

    @NotNull(message = "A identificação do usuário responsável pela assinatura é obrigatória")
    @Range(min=1, message = "Só são perminitidos números positivos para identificação do usuário!")
    private Long usuarioId;

    @NotNull(message = "A identificação do plano é obrigatória")
    @Range(min=1, message = "Só são perminitidos números positivos para identificação do plano!")
    private Long planoId;
    //private Plano plano;

    @NotBlank(message = "O Status da assinatura deve ser informado!")
    @Pattern(regexp = "ATIVA|EM_ATRASO|CANCELADA", message = "Este campo aceita apenas os seguintes valores: ATIVA | EM_ATRASO | CANCELADA!")
    private String status;

    @NotNull(message = "A data de inicio da vigência da assinatura é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime iniciadaEm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime canceladaEm;

}
