package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.EventoAssistidoId;
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
public class EventoAssistidoDTO {

    @EmbeddedId
    private EventoAssistidoId eventoAssistidoId;

    @NotNull(message = "A data em que o conteúdo foi assistido deve ser informada!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assistidoEm;

}
