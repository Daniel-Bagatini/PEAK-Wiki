package Trabalho.Web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	List<Postagem> findAllByOrderByCriadaEmDesc();
}
