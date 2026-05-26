package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.enuns.PERFIL;
import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.validation.CnpjCpf;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private long id;

    @NotBlank(message = "O nome do usuário é obrigatório!")
    @Size(min = 3, max = 150, message = "O nome do usuário deve ter no mimimo 3 e no máximo 150 caracteres!")
    private String nomeCompleto;

    @NotNull(message = "A data de nascimento do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail do usuário é obrigatório!")
    @Email(message = "e-mail em formato não permitido!")
    private String email;

    @NotBlank(message = "A criação de uma senha é obrigatório!")
    @Size(min = 8, max = 60, message = "A senha deve ter entre 8 e 60 caracteres!")
    private String senhaHash;

    @NotNull(message = "O Tipo de pessoa do usuário é obrigatório!")
    private TIPOPESSOA tipoPessoa;

    @NotBlank(message = "O C.P.F. ou C.N.P.J. do usuário é obrigatório!")
    @CnpjCpf(tipoPessoa = TIPOPESSOA.JURIDICA)
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

}
