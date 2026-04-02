package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome do usuário é obrigatório")
    @Size(min = 3, max = 150, message = "O nome do usuário deve ter no mimimo 3 e no máximo 150 caracteres!")
    private String nomeCompleto;

    @NotBlank(message = "A data de nascimento do usuário é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail do usuário é obrigatório")
    @Email(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}$", message = "e-mail em formato não permitido!")
    private String email;

    @NotBlank(message = "O C.P.F. ou C.N.P.J. do usuário é obrigatório")
    private String cpfCcnpj;

    @NotBlank(message = "O perfil do usuário é obrigatório")
    @Pattern(regexp = "ADMIN|USER", message = "Este campo só permite as valores ADMIN OU USER!")
    private String perfil;

    @NotBlank(message = "A data de criação do usuário é obrigatória")
    private LocalDate criadoEm;

    @NotBlank(message = "A data de atualização dos dados do usuário é obrigatória")
    private LocalDate atualizadoEm;

}
