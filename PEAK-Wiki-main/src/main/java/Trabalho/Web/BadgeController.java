package Trabalho.Web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BadgeController {

	private static final List<Badge> BADGES = List.of(
			new Badge("Insígnia Florestal: Escale as RAÍZES",
					"Chegue à fogueira no topo da segunda nova área do jogo.",
					"image-287.png", "image-286.png", 117),
			new Badge("Insígnia de Escapa-teira",
					"Escape de uma teia de aranha e sobreviva.",
					"image-291.png", null, 119),
			new Badge("Insígnia do Morto-vivo",
					"Conseguida ao se curar da mordida de zumbi.",
					"image-289.png", "image-286.png", 116),
			new Badge("Insígnia das RAÍZES",
					"Escale as RAÍZES sem nunca ultrapassar 25% de esporos.",
					"image-292.png", null, 116),
			new Badge("Insígnia de Ingestão de Cálcio",
					"Bloqueie 100 pontos de dano total com o Leite Fortificado.",
					"image-294.png", null, 129),
			new Badge("Insígnia de Micoacrobacia",
					"Salte até 40 m usando um cogumelo.",
					"image-293.png", null, 141),
			new Badge("Insígnia do Nomadê: Escale a CHAPADA",
					"Chegue à fogueira no topo da terceira nova área do jogo.",
					"image-304.png", null, 130),
			new Badge("Insígnia do Intrépido",
					"Atravesse o cânion da CHAPADA num disparo de canhão.",
					"image-305.png", null, 121),
			new Badge("Insígnia da Engenhosidade",
					"Atinja pelo menos 75% de fome sem comer nada e então coma um amigo.",
					"image-306.png", null, 121),
			new Badge("Conquista do Rato de Biblioteca",
					"Leia as oito páginas que faltam do diário do Chefe Escoteiro.",
					"image-308.png", null, 116),
			new Badge("Insígnia do Glutão",
					"Escape da ilha depois de cozinhar e consumir meio coco, um favo de mel, uma gelabaga amarela e um ovo.",
					"image-309.png", null, 140),
			new Badge("Insígnia do Naturalista",
					"Conseguida ao fazer tal coisa.",
					"image-310.png", null, 128),
			new Badge("Insígnia do Balão",
					"Escape da ilha sem receber dano por queda.",
					"image-312.png", null, 110),
			new Badge("Insígnia do Apressado",
					"Escape da ilha em menos de uma hora.",
					"image-313.png", null, 117),
			new Badge("Insígnia do Ermitão",
					"Escape da ilha em uma expedição solo.",
					"image-314.png", null, 117),
			new Badge("Insígnia do Sobrevivente",
					"Escape da ilha sem nunca perder a consciência.",
					"image-318.png", null, 117),
			new Badge("Insígnia do Naufrágo: Escale a COSTA",
					"Chegue à fogueira no topo da primeira área do jogo.",
					"image-286.png", null, 159),
			new Badge("Insígnia 24 Quilates",
					"Ofereça um sacrifício digno ao FORNO.",
					"image-288.png", null, 150),
			new Badge("Insígnia do Bing Bong",
					"Ajude o Bing Bong a escapar da ilha.",
					"image-315.png", null, 114),
			new Badge("Insígnia de Astronomia",
					"Olhe para o sol com o binóculo no bioma da Mesa.",
					"image-307.png", null, 118),
			new Badge("Insígnia da Resistência",
					"Suba 50 m sem tocar no chão.",
					"image-290.png", null, 130),
			new Badge("Insígnia Sem Rastros",
					"Escape da ilha sem largar nada na montanha.",
					"image-311.png", null, 115),
			new Badge("Insígnia do Pico: Alcance o PICO",
					"Chegue à área no topo do VULCÃO, o último estágio após a Caldeira.",
					"image-320.png", null, 115),
			new Badge("Insígnia de Vulcanologia: Escale a CALDEIRA",
					"Chegue à fogueira no final da quarta área do jogo.",
					"image-321.png", null, 132));

	@GetMapping("/insignias")
	public String badges(Model model) {
		model.addAttribute("badges", BADGES);
		return "badges";
	}

	public record Badge(String name, String description, String image, String baseImage, int imageSize) {
	}
}
