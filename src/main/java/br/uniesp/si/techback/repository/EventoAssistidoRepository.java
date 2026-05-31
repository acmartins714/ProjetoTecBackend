package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.EventoAssistido;
import br.uniesp.si.techback.model.EventoAssistidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoAssistidoRepository extends JpaRepository<EventoAssistido, EventoAssistidoId> {

    @Query("select e from EventoAssistido e where e.id.clienteId = :clienteId and e.id.conteudoId = :conteudoId")
    public EventoAssistido buscarPorClienteConteudo(@Param("clienteId") Long clienteId, @Param("conteudoId") Long conteudoId);

}
