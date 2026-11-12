package Trabalho.Web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PageController {

	private static final List<Biome> BIOMES = List.of(
			new Biome("Costa", "costa", "costa.png", 1,
					"imgImage167.png", "imgImage11.png",
					"O bioma Costa em PEAK é a primeira área do jogo, servindo como introdução às mecânicas de escalada e sobrevivência. Possui praias, rochas e destroços de avião, com clima ameno e desafios leves. Apresenta águas-vivas venenosas, pontes instáveis e ouriços-do-mar como principais perigos. É um ambiente de aprendizado, ideal para coletar itens básicos e se preparar para biomas mais difíceis.",
					List.of(
							food("Coco", "imgImage40.png"),
							food("Maçã vermelha", "imgImage41.png"),
							food("Maçã amarela", "imgImage42.png"),
							food("Maçã verde", "imgImage43.png"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.png", "img64PxClusterShroomPoisonous1.png"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.png"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.png"),
							food("Cenoura medicinal", "imgImage138.png")),
					List.of(
							hazard("Ouriços-do-mar gigantes",
									"Os ouriços-do-mar são armadilhas naturais nas praias da Costa. Seus espinhos podem envenenar quem se aproxima demais; mantenha distância e planeje o caminho.",
									"imgImage59.png", "VENENO"),
							hazard("Água-viva venenosa",
									"Mesmo fora d'água, a água-viva continua perigosa. Evite tocar nela para não sofrer envenenamento durante a travessia.",
									"imgImage65.png", "VENENO"),
							hazard("Pontes",
									"As pontes são frágeis: se mais de duas pessoas atravessarem juntas, elas podem ceder. Atravesse uma pessoa por vez, com cuidado e coordenação.",
									"imgImage70.png", "DANO")),
					List.of()),
			new Biome("Trópico", "tropico", "tropico.png", 2,
					"imgImage167.png", "imgImage242.png",
					"O bioma Trópico em PEAK é a segunda área do jogo, com florestas densas, rios e chuvas frequentes. Apesar do visual vibrante, o clima úmido torna a escalada mais difícil, pois a chuva deixa as superfícies escorregadias e reduz a visibilidade. É um local ideal para coletar recursos naturais e aperfeiçoar habilidades antes de enfrentar biomas mais desafiadores.",
					List.of(
							food("Coco", "imgImage40.png"),
							food("Maçã vermelha", "imgImage41.png"),
							food("Maçã amarela", "imgImage42.png"),
							food("Maçã verde", "imgImage43.png"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.png", "img64PxClusterShroomPoisonous1.png"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.png"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.png"),
							food("Cenoura medicinal", "imgImage139.png"),
							food("Pimenta", "imgImage155.png"),
							food("Amora roxa", "imgImage158.png"),
							food("Amora amarela", "imgImage160.png"),
							food("Amora verde", "imgImage161.png"),
							food("Banana amarela", "imgImage163.png"),
							food("Banana rosa", "imgImage164.png"),
							food("Banana azul", "imgImage165.png"),
							food("Banana marrom", "imgImage166.png"),
							food("Favo de mel", "imgImage211.png"),
							food("Amora vermelha", "imgImage214.png"),
							food("Amora laranja", "imgImage215.png"),
							food("Amora preta", "imgImage216.png"),
							food("Carrapato", "imgCapturaDeTela20251126212803RemovebgPreview5.png")),
					List.of(
							hazard("Chuva no Trópico",
									"A chuva frequente deixa as superfícies escorregadias e reduz a visibilidade. Procure abrigo e avance com cuidado durante as tempestades.",
									"imgImage217.png", "FRIO"),
							hazard("Monstera",
									"As folhas e raízes da monstera bloqueiam passagens e podem surpreender quem escala sem observar o terreno.",
									"imgImage253.png", "ESPINHOS"),
							hazard("Espinhos gigantes",
									"Espinhos gigantes ocupam trechos da rota e causam ferimentos ao contato. Escolha bem os apoios e mantenha distância.",
									"imgImage254.png", "ESPINHOS"),
							hazard("Tubos de lava",
									"Os tubos de lava escondem riscos e estreitam a passagem. Verifique a rota antes de atravessar.",
									"imgImage265.png", "QUEIMADURA"),
							hazard("Cogumelo-bomba explosivo",
									"Este cogumelo detona quando alguém se aproxima, podendo arremessar escoteiros e interromper a escalada.",
									"imgImage220.png", "DANO"),
							hazard("Cogumelo-bomba venenoso",
									"Uma aproximação descuidada pode liberar uma nuvem tóxica. Mantenha distância e procure um caminho seguro.",
									"imgImage74.png", "VENENO"),
							hazard("Hera venenosa",
									"A hera cobre áreas de passagem e envenena quem entra em contato com suas folhas. Evite atravessar a vegetação.",
									"imgImage266.png", "VENENO"),
							hazard("Vagens venenosas",
									"Vagens escondidas na vegetação podem liberar efeitos nocivos quando perturbadas. Observe o ambiente antes de avançar.",
									"imgImage267.png", "VENENO"),
							hazard("Plantas perigosas",
									"A vegetação densa pode esconder perigos e dificultar a leitura da rota. Avance com atenção e mantenha o grupo unido.",
									"imgImage268.png", "DANO")),
					List.of()),
			new Biome("Alpes", "alpes", "alpes.png", 3,
					"imgImage167.png", "imgImage74.png",
					"O bioma Alpes em PEAK é a terceira área do jogo, com picos cobertos de neve, geleiras perigosas e cristas rochosas expostas. Apesar da vista espetacular e da neve intocada, o clima gelado torna a escalada mais difícil, pois o gelo e a neve compactada deixam as superfícies escorregadias e o frio intenso exige gerenciamento constante de recursos. É um local para aperfeiçoar habilidades de escalada em gelo e gerenciamento de temperatura antes de enfrentar biomas mais desafiadores.",
					List.of(
							food("Laranja", "imgImage284.png"),
							food("Laranja amarela", "imgImage285.png"),
							food("Napberry", "imgImage283.png")),
					List.of(
							hazard("Tempestade de neve",
									"A nevasca reduz drasticamente a visibilidade e causa frio rapidamente. Procure abrigo antes que o mau tempo comprometa a escalada.",
									"imgImage253.png", "FRIO"),
							hazard("Pingentes de gelo",
									"Os pingentes podem tremer e cair quando escalados. Avalie a estabilidade do gelo antes de usá-lo como apoio.",
									"imgImage254.png", "DANO"),
							hazard("Pedras de gelo",
									"O contato com as pedras de gelo causa frio e pode tornar a progressão lenta. Prepare-se para manter a temperatura corporal.",
									"imgImage265.png", "FRIO"),
							hazard("Vagens de luz",
									"Quando ativadas, as vagens liberam uma névoa que cobre a visão dos escoteiros próximos e torna a escalada perigosa.",
									"imgImage293.png", "CEGUEIRA"),
							hazard("Gêiser",
									"O vapor do gêiser pode surpreender quem passa perto. Espere o momento seguro para atravessar a área.",
									"imgImage294.png", "DANO")),
					List.of()),
			new Biome("Mesa", "mesa", "mesa.png", 4,
					"imgImage167.png", "imgImage74.png",
					"O bioma Mesa em PEAK é uma região de cânions profundos, plataformas rochosas elevadas e um céu vasto e árido. O clima seco e turbulento torna a escalada mais difícil, pois tornados aparecem espontaneamente e podem arremessar os escoteiros para longe. É um local ideal para aperfeiçoar habilidades de esquiva e gerenciamento de altitude.",
					List.of(
							food("Coco", "imgImage40.png"),
							food("Laranja amarela", "imgImage285.png"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.png"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.png", "img64PxClusterShroomPoisonous1.png"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.png"),
							food("Espinheiro dourado", "imgImage296.png"),
							food("Espinheiro vermelho", "imgImage295.png")),
					List.of(
							hazard("Sol",
									"A exposição direta ao sol causa queimadura e superaquecimento. Procure sombra e controle os recursos de calor durante a travessia.",
									"imgImage217.png", "QUEIMADURA"),
							hazard("Cactos",
									"O contato com cactos causa espinhos e pode prender o escoteiro. Evite as plantas e ajude aliados que ficarem presos.",
									"imgImage253.png", "ESPINHOS"),
							hazard("Capins do deserto",
									"Os capins secos rolam pelo mapa e podem derrubar escoteiros desatentos, causando espinhos e arremessando-os para longe.",
									"imgImage254.png", "ESPINHOS"),
							hazard("Tornado",
									"Os tornados surgem repentinamente e arremessam escoteiros para longe. Observe o céu e desvie assim que possível.",
									"imgImage265.png", "DANO"),
							hazard("Formiga-leão",
									"A areia pode esconder formigas-leão que atacam quem se aproxima. Observe o solo antes de cruzar áreas arenosas.",
									"imgImage267.png", "DANO"),
							hazard("Escorpiões",
									"Nas cavernas da Mesa, escorpiões atacam com uma picada venenosa. Explore as passagens subterrâneas com cautela.",
									"imgImage266.png", "VENENO"),
							hazard("Dinamite",
									"A dinamite acende quando um escoteiro se aproxima e explode pouco depois, causando dano e empurrando jogadores próximos.",
									"imgImage220.png", "DANO")),
					List.of()),
			new Biome("Caldeira", "caldeira", "caldeira.png", 5,
					"imgImage167.png", "imgImage74.png",
					"O bioma Caldeira em PEAK é uma área de alto nível do jogo, com rochas vulcânicas escuras, rios de lava incandescentes e uma atmosfera sufocante. O calor escaldante e a atividade sísmica tornam a escalada extremamente difícil, pois o chão de lava causa queimaduras e a fumaça vulcânica reduz a visibilidade.",
					List.of(
							food("Ovo", "imgImage201.png"),
							food("Frango", "imgImage297.png")),
					List.of(
							hazard("Lava",
									"A lava sobe progressivamente com o tempo e pode causar queimaduras graves. Continue a escalada e evite cair nas áreas incandescentes.",
									"imgImage217.png", "QUEIMADURA"),
							hazard("Erupções",
									"Rochas começam a brilhar antes de irromper em chamas. Afaste-se das áreas aquecidas para evitar queimaduras.",
									"imgImage298.png", "QUEIMADURA"),
							hazard("Cogumelo-bomba venenoso",
									"Uma aproximação descuidada pode liberar veneno. Observe o terreno e mantenha distância do cogumelo.",
									"imgImage220.png", "VENENO")),
					List.of()),
			new Biome("O Forno", "o-forno", "forno.png", 6,
					"imgImage167.png", "imgImage74.png",
					"O bioma Forno em PEAK é a área mais quente do jogo, com rochas vulcânicas avermelhadas, gêiseres de vapor e uma atmosfera sufocante. O calor opressor e os gases tóxicos tornam a escalada extremamente difícil: o chão quente causa dano de fogo e o vapor exige cautela para evitar queimaduras.",
					List.of(),
					List.of(
							hazard("Rochas quentes",
									"Rochas quentes ficam embutidas nas paredes do bioma e causam queimaduras ao contato ou quando o escoteiro permanece muito perto.",
									"imgImage217.png", "QUEIMADURA"),
							hazard("Lava",
									"A lava sobe progressivamente e aquece as rochas próximas. Mantenha a progressão vertical para escapar do calor.",
									"imgImage303.png", "QUEIMADURA")),
					List.of()),
			new Biome("Pico", "pico", "pico.png", 7,
					"imgImage167.png", "imgImage74.png",
					"O Pico em PEAK é o estágio final do jogo, caracterizado por uma subida vertical final, um campo de flores vibrante e um grande sol preenchendo o céu. A área exige a gestão perfeita dos últimos recursos para manter a stamina. É o local ideal para focar na precisão de cada agarre e usar a resistência mental para alcançar o ponto final e a glória da escalada.",
					List.of(), List.of(),
					List.of(
							new ArrivalSection("Após a escalada extenuante e a superação dos biomas mais desafiadores, a jornada atinge seu clímax no Pico. A visão no topo é recompensadora, com um campo de flores vibrante e um grande sol dominando a paisagem.",
									"imgImage299.png", "Bandeira azul no campo de flores do Pico"),
							new ArrivalSection("No ponto mais alto, a recompensa é a Bandeira do Escoteiro. Ao lado dela, uma caixa de resgate contém o sinalizador que finaliza a expedição. Após a contagem regressiva, todos os escoteiros sobreviventes aparecem juntos para celebrar a chegada.",
									"imgImage300.png", "Escoteiros celebrando a chegada ao Pico"))));

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("biomes", BIOMES);
		return "index";
	}

	@GetMapping("/biomas/{slug}")
	public String biome(@PathVariable String slug, Model model) {
		Biome biome = BIOMES.stream()
				.filter(candidate -> candidate.slug().equals(slug))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		model.addAttribute("biome", biome);
		return "biome-detail";
	}

	@GetMapping({"/login", "/cadastro", "/cadastrar"})
	public String login() {
		return "login";
	}

	private static Food food(String name, String... images) {
		return new Food(name, List.of(images));
	}

	private static Hazard hazard(String title, String description, String image, String effect) {
		String effectClass = switch (effect) {
			case "VENENO" -> "poison";
			case "ESPINHOS" -> "thorns";
			case "FRIO", "CEGUEIRA" -> "cold";
			case "QUEIMADURA" -> "burn";
			default -> "damage";
		};
		return new Hazard(title, description, image, effect, effectClass);
	}

	public record Biome(String name, String slug, String image, int number, String backdrop, String banner,
			String description, List<Food> foods, List<Hazard> hazards, List<ArrivalSection> arrival) {
		public String imagePath(String image) {
			return ImagePaths.canonicalize("/images/biome-pages/" + slug + "/" + image);
		}

		public String cardImagePath() {
			return ImagePaths.canonicalize("/images/biomes/" + image);
		}

		public List<String> titleOverlays() {
			return slug.equals("pico")
					? List.of("imgRectangle7.png", "imgRectangle8.svg")
					: List.of("imgRectangle7.svg");
		}

		public List<FoodEffect> foodEffects() {
			return switch (slug) {
				case "costa" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa dano", "damage"),
						new FoodEffect("O alimento causa efeito de queimadura", "burn"));
				case "tropico" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa efeito de espinhos", "thorns"),
						new FoodEffect("O alimento causa dano", "damage"));
				case "alpes" -> List.of(
						new FoodEffect("O alimento causa efeito de sonolência", "sleep"),
						new FoodEffect("O alimento proporciona energia extra", "energy"));
				case "mesa" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa efeito de espinhos", "thorns"));
				default -> List.of();
			};
		}
	}

	public record Food(String name, List<String> images) {
	}

	public record FoodEffect(String label, String cssClass) {
	}

	public record Hazard(String title, String description, String image, String effect, String effectClass) {
	}

	public record ArrivalSection(String description, String image, String alt) {
	}
}
