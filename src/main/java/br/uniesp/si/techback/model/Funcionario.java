package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", columnDefinition = "VARCHAR(255)", length = 255)
    private String nome;

    @Column(name = "cargo", columnDefinition = "VARCHAR(255)", length = 255)
    private String cargo;

    @Column(name = "cep", columnDefinition = "CHAR(8)", length = 8)
    private String cep;

    @Column(name = "logradouro", columnDefinition = "VARCHAR(255)", length = 255)
    private String logradouro;

    @Column(name = "numero", columnDefinition = "VARCHAR(10)", length = 10)
    private String numero;

    @Column(name = "bairro", columnDefinition = "VARCHAR(255)", length = 255)
    private String bairro;

    @Column(name = "localidade", columnDefinition = "VARCHAR(255)", length = 255)
    private String localidade;

    @Column(name = "uf", columnDefinition = "CHAR(2)", length = 2)
    private String uf;
}