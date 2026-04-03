package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(254)", length = 254, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHhash;

    @Column(name = "cpf_cnpj", nullable = false, columnDefinition = "CHAR(14)", length = 14, unique = true)
    private String cpfCnpj;

    @Column(name = "perfil", nullable = false, columnDefinition = "CHAR(20)", length = 20)
    @Pattern(regexp = "ADMIN|USER", message = "Este campo só permite as valores ADMIN OU USER!")
    private String perfil;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime atualizadoEm;

}
