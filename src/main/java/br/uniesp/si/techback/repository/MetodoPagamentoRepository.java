package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {

    @Query("select mp from MetodoPagamento mp where lower(mp.nomePortador) like lower(concat('%', :nomePortador, '%')) order by mp.nomePortador asc")
    List<MetodoPagamento> buscarPorNomePortador(@Param("nomePortador") String nomePortador);

}
