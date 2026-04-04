package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.FavoritoId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EmbeddedId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {

    @EmbeddedId
    private FavoritoId favoritoId;

    @NotNull(message = "A data em que o conteúdo foi favoritado deve ser informada!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criadoEm;

}
