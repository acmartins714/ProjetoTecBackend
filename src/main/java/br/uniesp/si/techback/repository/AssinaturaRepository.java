package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaRepository extends JpaRepository<Filme, Long> {

}
