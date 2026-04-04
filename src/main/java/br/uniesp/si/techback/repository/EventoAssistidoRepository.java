package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.EventoAssistido;
import br.uniesp.si.techback.model.EventoAssistidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoAssistidoRepository extends JpaRepository<EventoAssistido, EventoAssistidoId> {

}
