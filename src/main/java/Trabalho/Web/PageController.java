package Trabalho.Web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PageController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("biomes", Biomes.ALL);
		return "index";
	}

	@GetMapping("/biomas/{slug}")
	public String biome(@PathVariable String slug, Model model) {
		Biomes.Biome biome = Biomes.bySlug(slug)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		model.addAttribute("biome", biome);
		return "biome-detail";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping({ "/cadastro", "/cadastrar" })
	public String cadastro() {
		return "cadastro";
	}

	@GetMapping({ "/recuperar-senha", "/esqueci-senha" })
	public String recuperarSenha() {
		return "recuperar-senha";
	}
}
