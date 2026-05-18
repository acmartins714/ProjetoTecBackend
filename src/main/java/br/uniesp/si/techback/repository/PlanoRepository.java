package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

    @Query("SELECT p FROM Plano p JOIN FETCH p.assinaturas")
    List<Plano> findAllWithAssinaturas();

}
