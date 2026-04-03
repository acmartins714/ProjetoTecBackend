package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @NotBlank(message = "A criação de uma senha é obrigatório!")
    @Size(min = 8, max = 60, message = "A senha deve ter entre 8 e 60 caracteres!")
    private String senhaHhash;

    @NotBlank(message = "O C.P.F. ou C.N.P.J. do usuário é obrigatório!")
    @CPF(message = "O C.P.F. não é valido")
    private String cpfCnpj;

    @NotBlank(message = "O perfil do usuário é obrigatório!")
    @Pattern(regexp = "ADMIN|USER", message = "Este campo só permite as valores ADMIN OU USER!")
    private String perfil;

    @NotNull(message = "A data de criação do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criadoEm;

    @NotNull(message = "A data de atualização dos dados do usuário é obrigatória!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime atualizadoEm;

}
