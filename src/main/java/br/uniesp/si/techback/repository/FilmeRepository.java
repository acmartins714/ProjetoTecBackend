package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query("select f from Filme f order by f.titulo asc")
    public List<Filme> listarFilmesOrdenados();

    @Query("select f from Filme f where f.genero = :genero order by f.titulo")
    Optional<Filme> buscarPorGenero(@Param("genero") String genero);

    @Query("select f from Filme f where f.genero = :genero and f.titulo = :titulo")
    Optional<Filme> buscarPorGenero(@Param("genero") String genero, @Param("titulo") String titulo);

    //Optional<Filme> findByGenero(String genero);

    //public List<Filme> findAllByOrderByTituloAsc();


}
