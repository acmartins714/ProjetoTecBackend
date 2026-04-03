package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {

}
