package Trabalho.Web;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** Coloca as postagens de demonstração no banco quando ele ainda está vazio. */
@Component
public class PostagensIniciais implements ApplicationRunner {

	private final PostagemRepository postagens;

	public PostagensIniciais(PostagemRepository postagens) {
		this.postagens = postagens;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (postagens.count() > 0) {
			return;
		}
		Instant agora = Instant.now();

		Postagem richetimber = new Postagem("@richetimber", "se liga nesse mod pra deixar o jogo em ultrawide");
		richetimber.setAvatar("/images/biome-pages/costa/imgImage169.webp");
		richetimber.setImagem("/images/home/community.webp");
		richetimber.setReacoes(350, 20, 70);
		richetimber.setCriadaEm(agora.minus(1, ChronoUnit.DAYS));

		Postagem lukitos = new Postagem("@Lukitos", "Bela equipe, tomara que chegem ao topo");
		lukitos.setAvatar("/images/biome-pages/costa/imgImage168.webp");
		lukitos.setImagem("/images/home/community.webp");
		lukitos.setReacoes(350, 20, 70);
		lukitos.setCriadaEm(agora);

		postagens.save(richetimber);
		postagens.save(lukitos);
	}
}
