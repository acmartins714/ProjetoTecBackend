package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "assinatura")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; // UUID **FK** → `usuarios(id)`

    @Column(name = "plano_id", nullable = false)
    private Long planoId; // UUID **FK** → `plano(id)`

    @Column(name = "status", nullable = false, length = 20)
    @Pattern(regexp = "ATIVA|EM_ATRASO|CANCELADA", message = "Este campo aceita apenas os seguintes valores: ATIVA | EM_ATRASO | CANCELADA!")
    private String status;

    @Column(name = "iniciada_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate iniciadaEm;

    @Column(name = "cancelada_em", columnDefinition = "TIMESTAMP")
    private LocalDate canceladaEm;

}
