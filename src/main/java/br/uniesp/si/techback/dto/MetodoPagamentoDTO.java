package br.uniesp.si.techback.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MetodoPagamentoDTO {

    private Long id;

    @NotBlank(message = "A identificação do usuário deve ser informada!")
    private Long usuarioId;

    @NotBlank(message = "A bancdeira do cartão deve ser informada!")
    private String bandeira;

    @NotBlank(message = "Os quarto últimos digitos da numeração do cartão deve ser informada!")
    private String ultimos4;

    @NotBlank(message = "Os mês de validade do cartão precisa ser informado!")
    @Range(min = 1, max = 12, message = "O mês da validade do cartão precisa ser informado como um número entre 1 e 12!")
    private int mesExp;

    @NotBlank(message = "O ano de expiração do cartão precisa ser informado!")
    private int anoExp;

    @NotBlank(message = "O nome do títular do cartão precisa ser informado!")
    private String nomePortador;

    @NotBlank(message = "A senha do cartão precisa ser informada!")
    private String tokenGateway;

    @NotBlank(message = "A data da criação precisa estar preencida!")
    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate criadoEm;

}
