package br.uniesp.si.techback.model;

import br.uniesp.si.techback.enuns.PERFIL;
import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.validation.CnpjCpf;
import jakarta.persistence.*;
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
    private String senhaHash;

    @Column(name = "tipo_pessoa", nullable = false, columnDefinition = "INTEGER")
    private TIPOPESSOA tipoPessoa;

    @Column(name = "cpf_cnpj", nullable = false, columnDefinition = "CHAR(14)", length = 14, unique = true)
    private String cpfCnpj;

    @Column(name = "nome_fantasia", columnDefinition = "CHAR(255)", length = 255)
    private String fantasia;

    @Column(name = "rua", columnDefinition = "CHAR(255)", length = 255)
    private String logradouro;

    @Column(name = "numero", columnDefinition = "CHAR(10)", length = 10)
    private String numero;

    @Column(name = "complemento", columnDefinition = "CHAR(255)", length = 255)
    private String complemento;

    @Column(name = "bairro", columnDefinition = "CHAR(255)", length = 255)
    private String bairro;

    @Column(name = "municipio", columnDefinition = "CHAR(255)", length = 255)
    private String municipio;

    @Column(name = "uf", columnDefinition = "CHAR(2)", length = 2)
    private String uf;

    @Column(name = "cep", columnDefinition = "CHAR(8)", length = 8)
    private String cep;

    @Column(name = "telefone", columnDefinition = "CHAR(11)", length = 11)
    private String telefone;

    @Column(name = "perfil", nullable = false, columnDefinition = "INTEGER")
    private PERFIL perfil;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime atualizadoEm;

    //@JsonManagedReference
    //@OneToMany(mappedBy = "usuario")
    //private List<Assinatura> assinaturas;

}
