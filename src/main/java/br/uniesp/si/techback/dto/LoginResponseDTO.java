package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.enuns.PERFIL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;

    private String nomeCompleto;

    private String email;

    private PERFIL perfil;

}
