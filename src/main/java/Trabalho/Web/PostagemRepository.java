package Trabalho.Web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	List<Postagem> findAllByOrderByCriadaEmDesc();

	/** Soma uma curtida direto no banco, sem perder cliques que chegam ao mesmo tempo. */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Postagem p set p.curtidas = p.curtidas + 1 where p.id = :id")
	int curtir(@Param("id") Long id);

	/** Soma uma descurtida direto no banco, sem perder cliques que chegam ao mesmo tempo. */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Postagem p set p.descurtidas = p.descurtidas + 1 where p.id = :id")
	int descurtir(@Param("id") Long id);
}
