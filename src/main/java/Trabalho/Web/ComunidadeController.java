package Trabalho.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class ComunidadeController {

	private final PostagemRepository postagens;

	public ComunidadeController(PostagemRepository postagens) {
		this.postagens = postagens;
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
	public String publicar(@Valid @ModelAttribute("form") NovaPostagemForm form, BindingResult erros,
			RedirectAttributes redirect) {
		if (erros.hasErrors()) {
			return "nova-postagem";
		}
		postagens.save(new Postagem(form.autorFormatado(), form.getTexto().strip()));
		redirect.addFlashAttribute("publicada", true);
		return "redirect:/comunidade";
	}

	/** Apaga uma postagem. Por enquanto qualquer pessoa pode apagar: é só para testes. */
	@PostMapping("/comunidade/postagens/{id}/apagar")
	public String apagar(@PathVariable Long id, RedirectAttributes redirect) {
		if (postagens.existsById(id)) {
			postagens.deleteById(id);
			redirect.addFlashAttribute("apagada", true);
		}
		return "redirect:/comunidade";
	}
}
