package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query("select f from Filme f order by f.titulo asc")
    public List<Filme> listarFilmesOrdenados();

    @Query("select f from Filme f where lower(f.genero) = lower(:genero) order by f.titulo")
    public List<Filme> buscarPorGenero(@Param("genero") String genero);

    @Query("select f from Filme f where f.genero = :genero and f.titulo = :titulo")
    public List<Filme> buscarPorGeneroETitulo(@Param("genero") String genero, @Param("titulo") String titulo);

}
