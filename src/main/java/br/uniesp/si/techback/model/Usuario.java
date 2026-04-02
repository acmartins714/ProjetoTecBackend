package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Geração de getts and setters toString e hashcode para todos os campos
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(254) CONSTRAINT ck_usuario_email CHECK (email REGEXP '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}$')", length = 254, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}$", message = "e-mail em formato não permitido!")
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHhash;

    @Column(name = "cpf_cnpj", nullable = false, columnDefinition = "CHAR(14) CONSTRAINT ck_usuario_cpf_cnpj CHECK(cpf_cnpj REGEXP ('^[A-Z0-9]+$'))", length = 14, unique = true)
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Só são permitidas letras de A a Z (maiusculos) e digitos de 0 a 9")
    private String cpfCcnpj;

    @Column(name = "perfil", nullable = false, columnDefinition = "CHAR(20) CONSTRAINT ck_usuario_perfil CHECK(perfil IN ('ADMIN','USER'))", length = 20)
    @Pattern(regexp = "ADMIN|USER", message = "Este campo só permite as valores ADMIN OU USER!")
    private String perfil;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate atualizadoEm;

}
