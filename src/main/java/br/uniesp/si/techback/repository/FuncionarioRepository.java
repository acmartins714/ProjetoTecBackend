package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("select f from Funcionario f where lower(f.nome) like lower(concat('%', :nome, '%')) order by f.nome asc")
    List<Funcionario> buscarPorNome(@Param("nome") String nome);

}
