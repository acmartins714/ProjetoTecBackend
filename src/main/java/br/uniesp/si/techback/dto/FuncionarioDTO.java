package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.Funcionario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo requerido!")
    @Size(min=3, max=80, message = "Nome precisa ter entre 3 e 70 caracters!")
    private String nome;

    @NotBlank(message = "Campo requerido!")
    @Size(min=3, max=80, message = "Nome precisa ter entre 3 e 50 caracters!")
    private String cargo;

    public FuncionarioDTO(Funcionario entity) {
        id = entity.getId();
        nome = entity.getNome();
        cargo = entity.getCargo();
    }

}
