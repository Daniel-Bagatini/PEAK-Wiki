package Trabalho.Web;

import java.io.IOException;
import java.time.Duration;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class ComunidadeController {

	/** Cabeçalho que o JavaScript da página manda ao curtir, para receber JSON em vez de recarregar a página. */
	static final String CABECALHO_FETCH = "X-Requested-With=fetch";

	private final PostagemRepository postagens;
	private final ImagemPostagemRepository imagens;

	public ComunidadeController(PostagemRepository postagens, ImagemPostagemRepository imagens) {
		this.postagens = postagens;
		this.imagens = imagens;
	}

	@GetMapping("/comunidade")
	public String comunidade(Model model) {
		model.addAttribute("postagens", postagens.findAllByOrderByCriadaEmDesc());
		return "comunidade";
	}

	@GetMapping("/comunidade/nova-postagem")
	public String novaPostagem(Model model) {
		model.addAttribute("form", new NovaPostagemForm());
		return "nova-postagem";
	}

	@PostMapping("/comunidade/nova-postagem")
	@Transactional
	public String publicar(@Valid @ModelAttribute("form") NovaPostagemForm form, BindingResult erros,
			RedirectAttributes redirect) throws IOException {
		ImagemPostagem imagem = null;
		if (form.temImagem()) {
			byte[] dados = form.getImagem().getBytes();
			try {
				imagem = new ImagemPostagem(ImagemDaPostagem.conferir(dados), dados);
			} catch (ImagemDaPostagem.ImagemInvalidaException e) {
				erros.rejectValue("imagem", "imagem.invalida", e.getMessage());
			}
		}
		if (erros.hasErrors()) {
			return "nova-postagem";
		}

		Postagem postagem = new Postagem(form.autorFormatado(), form.getTexto().strip());
		if (imagem != null) {
			postagem.setImagemEnviada(imagens.save(imagem));
		}
		postagens.save(postagem);
		redirect.addFlashAttribute("publicada", true);
		return "redirect:/comunidade";
	}

	/** Devolve uma imagem enviada numa postagem. Ela nunca muda, então o navegador pode guardar por muito tempo. */
	@GetMapping("/comunidade/imagens/{id}")
	public ResponseEntity<byte[]> imagem(@PathVariable Long id) {
		return imagens.findById(id)
				.map(img -> ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(img.getTipo()))
						.cacheControl(CacheControl.maxAge(Duration.ofDays(365)).cachePublic())
						.header("X-Content-Type-Options", "nosniff")
						.body(img.getDados()))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/** Números de reações que o JavaScript da página usa para atualizar o card. */
	public record Reacoes(int curtidas, int descurtidas) {
	}

	/**
	 * Curtir ou descurtir pelo JavaScript da página: soma 1 e devolve os números novos.
	 * Ainda não há limite por pessoa; isso vai ser feito junto com o login.
	 */
	@PostMapping(path = "/comunidade/postagens/{id}/{reacao:curtir|descurtir}", headers = CABECALHO_FETCH)
	@ResponseBody
	public ResponseEntity<Reacoes> reagirPorFetch(@PathVariable Long id, @PathVariable String reacao) {
		if (!reagir(id, reacao)) {
			return ResponseEntity.notFound().build();
		}
		return postagens.findById(id)
				.map(p -> ResponseEntity.ok(new Reacoes(p.getCurtidas(), p.getDescurtidas())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/** Curtir ou descurtir sem JavaScript: soma 1 e volta para a mesma postagem no feed. */
	@PostMapping("/comunidade/postagens/{id}/{reacao:curtir|descurtir}")
	public String reagirPorFormulario(@PathVariable Long id, @PathVariable String reacao) {
		if (!reagir(id, reacao)) {
			return "redirect:/comunidade";
		}
		return "redirect:/comunidade#postagem-" + id;
	}

	private boolean reagir(Long id, String reacao) {
		int alteradas = "curtir".equals(reacao) ? postagens.curtir(id) : postagens.descurtir(id);
		return alteradas > 0;
	}

	/** Apaga uma postagem (e a imagem enviada com ela). Por enquanto qualquer pessoa pode apagar: é só para testes. */
	@PostMapping("/comunidade/postagens/{id}/apagar")
	public String apagar(@PathVariable Long id, RedirectAttributes redirect) {
		if (postagens.existsById(id)) {
			postagens.deleteById(id);
			redirect.addFlashAttribute("apagada", true);
		}
		return "redirect:/comunidade";
	}
}
