package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.enuns.PERFIL;
import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.validation.CnpjCpf;
import br.uniesp.si.techback.validation.SenhaForte;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CnpjCpf
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome do usuário é obrigatório!")
    @Size(min = 3, max = 150, message = "O nome do usuário deve ter no mimimo 3 e no máximo 150 caracteres!")
    private String nomeCompleto;

    @NotNull(message = "A data de nascimento do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail do usuário é obrigatório!")
    @Email(message = "e-mail em formato não permitido!")
    private String email;

    @NotBlank(message = "A criação de uma senha é obrigatória!")
    @SenhaForte
    private String senhaHash;

    @NotNull(message = "O Tipo de pessoa do usuário é obrigatório!")
    private TIPOPESSOA tipoPessoa;

    @NotBlank(message = "O C.P.F. ou C.N.P.J. do usuário é obrigatório!")
    private String cpfCnpj;

    private String fantasia;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String municipio;

    private String uf;

    private String cep;

    private String telefone;

    @NotNull(message = "O perfil do usuário é obrigatório!")
    private PERFIL perfil;

    @NotNull(message = "A data de criação do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criadoEm;

    @NotNull(message = "A data de atualização dos dados do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime atualizadoEm;

    private List<AssinaturaDTO> assinaturas;

    private List<MetodoPagamentoDTO> metodosPagamento;

    private List<EventoAssistidoDTO> eventosAssistidos;

}