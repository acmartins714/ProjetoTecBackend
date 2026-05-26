package br.uniesp.si.techback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assinatura")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @JsonIgnoreProperties("assinaturas")
    @ManyToOne
    @JoinColumn(name = "pacote_id")
    private Plano plano;

    @Column(name = "status", nullable = false, length = 20)
    @Pattern(regexp = "ATIVA|EM_ATRASO|CANCELADA", message = "Este campo aceita apenas os seguintes valores: ATIVA | EM_ATRASO | CANCELADA!")
    private String status;

    @Column(name = "iniciada_em", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime iniciadaEm;

    @Column(name = "cancelada_em", columnDefinition = "TIMESTAMP")
    private LocalDateTime canceladaEm;

}
