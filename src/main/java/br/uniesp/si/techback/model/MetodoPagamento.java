package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metodo_pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; // UUID **FK** → `usuarios(id)` (**ON DELETE CASCADE**)

    @Column(name = "bandeira", nullable = false, length = 20)
    private String bandeira;

    @Column(name = "ultimos4", nullable = false, columnDefinition = "CHAR(4)", length = 4)
    private String ultimos4;

    @Column(name = "mes_exp", nullable = false, columnDefinition = "SMALLINT CONSTRAINT ck_metodo_pagamento_mes_exp CHECK(mes_exp BETWEEN 1 AND 12)")
    private int mesExp;

    @Column(name = "ano_exp", nullable = false, columnDefinition = "SMALLINT")
    private int anoExp;

    @Column(name = "nome_portador", nullable = false, length = 150)
    private String nomePortador;

    @Column(name = "token_gateway", nullable = false, length = 120)
    private String tokenGateway;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate criadoEm;

}
