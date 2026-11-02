package Trabalho.Web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itens")
public class ItemCatalogController {

	private static final String FOOD_ROOT = "/images/items/food/";
	private static final String EQUIPMENT_ROOT = "/images/items/equipamentos/";

	private static final ItemCatalog FOOD_CATALOG = new ItemCatalog("Comidas", true, List.of(
			new ItemSection("naturais", "Naturais", List.of(
					food("Coco", "/images/items/food/naturais/coco.png", "", "",
							"Costa, Trópicos, Mesa", "12,5",
							"Quebrado ao colidir com uma superfície a certa velocidade, resulta em 2 meios cocos."),
					food("Meio coco", biomeImage("costa", "imgImage40.png"), "-10 fome", "-10 fome 10 energia",
							"Coco", "5", ""),
					food("Maçã vermelha", biomeImage("costa", "imgImage41.png"), "-5 fome", "-10 fome 10 energia",
							"Costa, Trópicos", "2,5", ""),
					food("Maçã amarela", biomeImage("costa", "imgImage42.png"), "-5 fome 10 energia",
							"-10 fome 15 energia", "Costa, Trópicos", "2,5", ""),
					food("Maçã verde", biomeImage("costa", "imgImage43.png"), "-10 fome 10 veneno",
							"-10 fome 10 energia", "Costa, Trópicos", "2,5", ""),
					food("Amora roxa", biomeImage("tropico", "imgImage158.png"), "-30 fome 35 energia",
							"-60 fome 52,5 energia", "Trópicos", "5", ""),
					food("Amora silvestre amarela", biomeImage("tropico", "imgImage160.png"), "-30 fome",
							"-60 fome 10 energia", "Trópicos", "5", ""),
					food("Amora silvestre verde", biomeImage("tropico", "imgImage161.png"), "-30 fome",
							"-60 fome 10 energia", "Trópicos", "5", ""),
					food("Pimenta", biomeImage("tropico", "imgImage155.png"), "-10 fome 10 energia 25 fogo",
							"-10 fome 10 energia 25 fogo", "Trópicos", "2,5", ""),
					food("Colmeia", "/images/items/food/naturais/colmeia.png", "", "",
							"Trópicos", "10",
							"Quebrada ao colidir com uma superfície a certa velocidade, resulta em 4 favos de mel."),
					food("Favo de Mel", "/images/items/food/naturais/favo-de-mel.png", "-10 fome",
							"-10 fome 10 energia", "Colmeia", "2,5", ""),
					food("Banana amarela", biomeImage("tropico", "imgImage163.png"), "-15 fome",
							"-30 fome 10 energia", "Trópicos", "2,5", ""),
					food("Banana rosa", biomeImage("tropico", "imgImage164.png"), "-15 fome",
							"-10 fome 10 energia", "Trópicos", "2,5", ""),
					food("Banana azul", biomeImage("tropico", "imgImage165.png"), "-30 fome",
							"-60 fome 10 energia", "Trópicos", "2,5", ""),
					food("Banana laranja", biomeImage("tropico", "imgImage166.png"), "-15 fome 10 energia",
							"-30 fome 10 energia", "Trópicos", "2,5", ""),
					food("Amora laranja", biomeImage("tropico", "imgImage215.png"), "-10 fome 10 energia",
							"-10 fome 10 energia", "Trópicos", "2,5", ""),
					food("Amora vermelha", biomeImage("tropico", "imgImage214.png"), "-10 fome",
							"-20 fome 10 energia", "Trópicos", "2,5", ""),
					food("Amora preta", biomeImage("tropico", "imgImage216.png"), "-20 fome",
							"-40 fome 10 energia", "Trópicos", "2,5", ""),
					food("Laranja", biomeImage("alpes", "imgImage284.png"), "-15 fome",
							"-30 fome 10 energia", "Alpes", "2,5", ""),
					food("Laranja amarela", biomeImage("alpes", "imgImage285.png"), "-30 fome 35 energia",
							"-60 fome 52,5 energia", "Alpes", "2,5", ""),
					food("Napberry", biomeImage("alpes", "imgImage283.png"), "-50 queimadura -20 dano", "",
							"Alpes", "2,5",
							"Substitui todos os efeitos (exceto maldição e energia extra) por sonolência."),
					food("Espinheiro vermelho", biomeImage("mesa", "imgImage295.png"), "-15 fome 10 espinhos",
							"-30 fome 10 energia 10 espinhos", "Mesa", "2,5", ""),
					food("Espinheiro dourado", biomeImage("mesa", "imgImage296.png"), "-10 fome",
							"-30 fome 10 energia", "Mesa", "2,5", ""),
					food("Cenoura medicinal", biomeImage("costa", "imgImage138.png"), "-5 fome",
							"-10 fome 10 energia -25 veneno -10 dano", "Costa, Trópicos, Mesa", "2,5", ""),
					food("Carrapato", "/images/items/food/naturais/carrapato.png",
							"Enquanto preso ao corpo: causa veneno e -20 fome", "-40 fome 10 energia",
							"Trópicos", "2,5", ""),
					food("Escorpião", "/images/items/food/naturais/escorpiao.png",
							"Está vivo, portanto ataca e causa veneno em dose fatal", "-20 fome 10 energia",
							"Mesa", "2,5", ""),
					food("Marshmallow", "/images/items/food/naturais/marshmallow.png", "-15 fome",
							"-30 fome 10 energia", "Fogueiras em todos os biomas", "2,5", ""),
					food("Ovo grande", "/images/items/food/naturais/ovo-grande.png", "", "",
							"Caldeira", "5",
							"Quebrado ao colidir com uma superfície a certa velocidade, resulta em um ovo ou um pássaro cozido."),
					food("Ovo", biomeImage("caldeira", "imgImage201.png"), "-15 fome", "-30 fome 10 energia",
							"Ovo", "5", ""),
					food("Frango", biomeImage("caldeira", "imgImage297.png"), "Não é possível obter cru",
							"-40 fome 10 energia", "Ovo", "10", ""))),
			new ItemSection("embalados", "Embalados", List.of(
					food("Barra de Granola", FOOD_ROOT + "embalados/imgImage201.png", "-15 fome 20 energia",
							"-30 fome 30 energia", "Bagagem normal e grande", "5", ""),
					food("Mix de trilhas", FOOD_ROOT + "embalados/imgImage202.png", "-10 fome 10 energia",
							"-20 fome 15 energia", "Bagagem normal e grande", "2,5", ""),
					food("Bebida esportiva", FOOD_ROOT + "embalados/imgImage204.png",
							"-200 queimadura 20 energia", "-200 queimadura 20 energia",
							"Bagagem normal e grande", "2,5", ""),
					food("Comida de avião", FOOD_ROOT + "embalados/imgImage206.png", "-40 fome 40 energia",
							"-80 fome 60 energia", "Bagagem normal e grande", "2,5", ""),
					food("Cookies de escoteiro", FOOD_ROOT + "embalados/imgImage207.png", "-10 fome 10 energia",
							"-20 fome 15 energia", "Bagagem normal e grande", "5", ""),
					food("Energético", FOOD_ROOT + "embalados/imgImage208.png", "10 energia",
							"25 sonolência", "Bagagem normal e grande", "2,5",
							"Dá um bônus de velocidade temporário. Pode ser consumido 4 vezes."),
					food("Pirulito grande", FOOD_ROOT + "embalados/imgImage209.png", "-5 fome",
							"-15 fome 20 energia", "Bagagem normal e grande", "2,5",
							"Dá um bônus de energia infinita temporário."))),
			new ItemSection("cogumelos", "Cogumelos", List.of(
					food("Cogumelo trombeta", List.of(
									biomeImage("tropico", "imgImage44.png"),
									biomeImage("tropico", "imgImage45.png")),
							"-15 fome", "-30 fome 10 energia", "Costa, Trópicos, Mesa", "2,5",
							"O cogumelo envenenado pode ser diferenciado pelas irregularidades na superfície do caule."),
					food("Cogumelo botão", List.of(
									biomeImage("tropico", "imgImage47.png"),
									biomeImage("tropico", "img64PxButtonShroomPoisonous1.png")),
							"-10 fome", "-20 fome 10 energia", "Costa, Trópicos, Mesa", "2,5",
							"O cogumelo envenenado tem o topo liso, manchado com pequenas bolinhas."),
					food("Cogumelos em grupo", List.of(
									biomeImage("tropico", "img64PxClusterShroom1.png"),
									biomeImage("tropico", "img64PxClusterShroomPoisonous1.png")),
							"-5 fome", "-20 fome 10 energia", "Costa, Trópicos, Mesa", "2,5",
							"O cogumelo envenenado pode ser diferenciado por uma espécie de mini saia."),
					food("Cogumelo gordinho", biomeImage("tropico", "img64PxChubbyShroom1.png"),
							"-10 fome", "-20 fome 10 energia", "Costa, Trópicos, Mesa", "2,5",
							"Este cogumelo não possui variação venenosa."))),
			new ItemSection("miticos", "Míticos", List.of(
					food("Cura-tudo", FOOD_ROOT + "miticos/imgImage201.png",
							"-35 veneno; -35 dano; -35 frio; -35 queimadura; -25 espinhos; -20 fome; -35 sonolência; -35 maldição",
							"Pode ser consumido 3 vezes, cada vez contendo os efeitos indicados.",
							"Estátua anciã", "5", ""),
					food("Lancheira de Pandora", FOOD_ROOT + "miticos/imgImage202.png",
							"Efeitos aleatórios", "Remove todos os efeitos de status, inclusive maldição.",
							"Bagagem anciã", "2,5",
							"Pode ser consumida 3 vezes; cada uso aplica efeitos aleatórios.")))));

	private static final ItemCatalog EQUIPMENT_CATALOG = new ItemCatalog("Equipamentos", false, List.of(
			new ItemSection("cura", "Cura", List.of(
					gear("Bandagem", "imgImage219.png", "2.25", "Malas", "Remove 30 ferimentos."),
					gear("Remedio para fungos", "imgImage226.png", "2.25", "No chão",
							"Explode ao atingir uma superfície, criando uma névoa temporária de cura."),
					gear("Kit de primeiros socorros", "imgImage222.png", "5", "Malas",
							"Remove 100 de ferimentos."),
					gear("Cura-Tudo", "imgImage245.png", "5", "Estátua",
							"Cura todos os efeitos negativos (3 usos)."),
					gear("Lanterna de fada", "imgImage247.png", "5", "Estátua",
							"Cura efeitos negativos ao longo do tempo quando ativa."))),
			new ItemSection("especiais", "Itens Especiais", List.of(
					gear("Bing Bong", "imgImage235.png", "5", "Avião", "Bicho de pelúcia interagível."),
					gear("Disco Voador", "imgImage238.png", "0", "Avião",
							"Disco arremessável que atordoa."),
					gear("Zarabatana", "imgImage223.png", "2.25", "Malas",
							"Dispara dardo tranquilizante que atordoa."),
					gear("Sinalizador", "imgImage224.png", "0", "Malas",
							"Usado para se localizar e zerar o jogo."),
					gear("Bússola", "imgImage237.png", "0", "Avião", "Aponta para o Pico."),
					gear("Lanterna", "imgImage239.png", "5", "Avião",
							"Clareia o ambiente e aquece no frio."),
					gear("Corneta", "imgImage236.png", "0", "Avião",
							"Emite uma nota musical quando utilizada."),
					gear("Mochila", "imgImage234.png", "0", "Fogueiras",
							"Permite carregar mais 4 itens."))),
			new ItemSection("coletivos", "Itens de uso coletivo", List.of(
					gear("Corda", "imgImage232.png", "2.25", "Malas",
							"Prende na parede e solta corda."),
					gear("Fogão Portátil", "imgImage230.png", "5", "Malas",
							"É posto no chão e serve para cozinhar alimentos."),
					gear("Feijão Mágico", "imgImage228.png", "2.25", "Malas",
							"Cria um feijão mágico apontado para onde foi posto."),
					gear("Anti-Corda", null, "-2,5", "Estátua",
							"Prende na parede e solta uma corda que levita para o alto."),
					gear("Canhão de Corda", "imgImage231.png", "2.25", "Malas",
							"Dispara um gancho que prende na parede e libera uma corda."),
					gear("Fungo plataforma", "imgImage233.png", "2.25", "No chão",
							"Cria uma plataforma cogumelo ao atingir uma superfície."),
					gear("Lançador de Corrente", "imgImage227.png", "2.25", "Malas",
							"Lança uma corrente entre o ponto onde você está e o ponto mirado."),
					gear("Canhão anti-Corda", "imgImage243.png", "0", "Estátua",
							"Dispara um gancho que prende na parede e libera uma corda que levita para o alto."),
					gear("Piton", "imgImage229.png", "2.25", "Malas",
							"É posto na parede e serve para descansar entre escaladas."))),
			new ItemSection("individuais", "Itens de uso individual", List.of(
					gear("Balão", "imgImage251.png", "-5", "Malas", "Reduz a gravidade em 18%."),
					gear("Bolsa Térmica", "imgImage225.png", "0", "Malas",
							"Tira todo o frio e mantém você aquecido (3 usos)."),
					gear("Antídoto", "imgImage252.png", "2.25", "Malas",
							"Cura um pouco do seu envenenamento (3 usos)."),
					gear("Buquê de Balões", "imgImage221.png", "-15", "Malas",
							"Reduz a gravidade em 54% por conjunto."))),
			new ItemSection("miticos", "Itens Místicos", List.of(
					gear("Lancheira de Pandora", "imgImage248.png", "2.25", "Estátua/Bagagem",
							"Quando utilizada, aplica efeitos aleatórios no jogador (3 usos)."),
					gear("Efíngie de Escoteiro", "imgImage249.png", "5", "Estátua",
							"Revive os companheiros quando posta no chão."),
					gear("Cura-Tudo", "imgImage245.png", "5", "Estátua/Bagagem",
							"Cura todos os efeitos negativos (3 usos)."),
					gear("Lanterna de fada", "imgImage247.png", "5", "Estátua/Bagagem",
							"Cura efeitos negativos ao longo do tempo quando ativa."),
					gear("Crânio Amaldiçoado", "imgImage246.png", "5", "Estátua/Bagagem",
							"Sacrifica quem a usa e cura os atributos dos jogadores próximos.")))));

	@GetMapping("/comidas")
	public String foods(Model model) {
		return showCatalog(FOOD_CATALOG, model);
	}

	@GetMapping("/equipamentos")
	public String equipment(Model model) {
		return showCatalog(EQUIPMENT_CATALOG, model);
	}

	private String showCatalog(ItemCatalog catalog, Model model) {
		model.addAttribute("catalog", catalog);
		return "item-catalog";
	}

	private static CatalogItem food(String name, String image, String effect, String cookedEffect,
			String locations, String weight, String note) {
		return food(name, List.of(image), effect, cookedEffect, locations, weight, note);
	}

	private static CatalogItem food(String name, List<String> images, String effect, String cookedEffect,
			String locations, String weight, String note) {
		return new CatalogItem(name, images, effect, cookedEffect, locations, weight, note);
	}

	private static CatalogItem gear(String name, String image, String weight, String locations, String description) {
		return new CatalogItem(name, image == null ? List.of() : List.of(EQUIPMENT_ROOT + image),
				"", "", locations, weight, description);
	}

	private static String biomeImage(String biome, String image) {
		return "/images/biome-pages/" + biome + "/" + image;
	}

	public record ItemCatalog(String title, boolean food, List<ItemSection> sections) {
	}

	public record ItemSection(String id, String title, List<CatalogItem> items) {
	}

	public record CatalogItem(String name, List<String> images, String effect, String cookedEffect,
			String locations, String weight, String description) {
	}
}
