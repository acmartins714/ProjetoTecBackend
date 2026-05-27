package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MetodoPagamentoDTO {

    private Long id;

    @NotNull(message = "A identificação do usuário deve ser informada!")
    private Usuario usuario;

    @NotBlank(message = "A bancdeira do cartão deve ser informada!")
    private String bandeira;

    @NotBlank(message = "Os quarto últimos digitos da numeração do cartão deve ser informada!")
    private String ultimos4;

    @NotNull(message = "Os mês de validade do cartão precisa ser informado!")
    @Range(min = 1, max = 12, message = "O mês da validade do cartão precisa ser informado como um número entre 1 e 12!")
    private int mesExp;

    @NotNull(message = "O ano de expiração do cartão precisa ser informado!")
    private int anoExp;

    @NotBlank(message = "O nome do títular do cartão precisa ser informado!")
    private String nomePortador;

    @NotBlank(message = "A senha do cartão precisa ser informada!")
    private String tokenGateway;

    @NotNull(message = "A data da criação precisa estar preencida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criadoEm;

}
