package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {

    @Query("select c from Conteudo c where lower(c.titulo) like lower(concat('%', :titulo, '%')) order by c.titulo asc")
    List<Conteudo> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("select c from Conteudo c order by c.relevancia desc limit :limite")
    List<Conteudo> listaRanking(@Param("limite") Long limite);

    @Query("select c from Conteudo c where ano > :ano order by c.ano desc")
    List<Conteudo> listaConteudoAposAnoLancamento(@Param("ano") int ano);

    @Query("""
            select c
            from Conteudo c
            where lower(c.titulo) like concat('%', :keyword, '%') or
            lower(c.sinopse) like concat('%', :keyword, '%')
             order by c.titulo asc
           """)
    List<Conteudo> listaConteudoChaveTituloSinopse(@Param("keyword") String keyword);

}
