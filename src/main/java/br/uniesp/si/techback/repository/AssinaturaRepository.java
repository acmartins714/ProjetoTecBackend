package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    @Query("select a from Assinatura a where lower(a.status) like lower(concat('%', :status, '%')) order by a.status asc")
    List<Assinatura> buscarPorStatus(@Param("status") String status);

}
