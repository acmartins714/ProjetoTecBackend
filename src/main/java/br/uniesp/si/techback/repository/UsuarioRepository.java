package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where lower(u.nomeCompleto) like lower(concat('%', :nome, '%')) order by u.nomeCompleto asc")
    List<Usuario> buscarPorNome(@Param("nome") String nome);

}
