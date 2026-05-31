package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaDTO {

    private Long id;

    @NotNull(message = "A identificação do usuário responsável pela assinatura é obrigatória")
    @JsonIgnoreProperties("assinaturas")
    private UsuarioDTO cliente;

    @NotNull(message = "A identificação do plano é obrigatória")
    @JsonIgnoreProperties("assinaturas")
    private PlanoDTO plano;

    @NotBlank(message = "O Status da assinatura deve ser informado!")
    @Pattern(regexp = "ATIVA|EM_ATRASO|CANCELADA", message = "Este campo aceita apenas os seguintes valores: ATIVA | EM_ATRASO | CANCELADA!")
    private String status;

    @NotNull(message = "A data de inicio da vigência da assinatura é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime iniciadaEm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime canceladaEm;

}
