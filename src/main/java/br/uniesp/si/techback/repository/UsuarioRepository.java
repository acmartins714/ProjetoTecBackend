package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //@Query(nativeQuery = true, "SELECT u FROM Usuario u JOIN FETCH u.assinaturas")
    @Query(value = "SELECT ASSINATURA.ID, PLANO_ID, USUARIO_ID, STATUS, INICIADA_EM, CANCELADA_EM, CPF_CNPJ, NOME_COMPLETO, DATA_NASCIMENTO, EMAIL, SENHA_HASH, PERFIL, CRIADO_EM, ATUALIZADO_EM FROM ASSINATURA LEFT JOIN USUARIO ON ASSINATURA.USUARIO_ID = USUARIO.ID", nativeQuery = true)
    List<Usuario> findAllWithAssinaturas();

}
