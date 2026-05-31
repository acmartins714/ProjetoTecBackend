package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.dto.FavoritoResponseDTO;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    @Query("""
            SELECT new br.uniesp.si.techback.dto.FavoritoResponseDTO(
                u.id,
                f.criadoEm,
                u.nomeCompleto,
                c.titulo,
                c.sinopse,
                c.ano
            )
            FROM Favorito f
            JOIN f.usuario u
            JOIN f.conteudo c
            WHERE u.id = :usuarioId
            ORDER BY f.criadoEm DESC
            """)
    List<FavoritoResponseDTO> listarFavoritoPorUsuario(@Param("usuarioId") Long usuarioId);
}
