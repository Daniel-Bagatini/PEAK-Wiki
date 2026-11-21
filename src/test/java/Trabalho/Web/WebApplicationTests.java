package Trabalho.Web;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@AutoConfigureMockMvc
class WebApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void rendersHomeWithSharedHeaderAndBiomeCarousel() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().string(containsString("Fórum da Comunidade")))
				.andExpect(content().string(containsString("/biomas/costa")))
				.andExpect(content().string(containsString("href=\"/insignias\"")))
				.andExpect(content().string(containsString("/css/site.css")))
				.andExpect(content().string(containsString("/images/biome-pages/costa/imgImage3.webp")))
				.andExpect(content().string(containsString("class=\"biomes-card\"")))
				.andExpect(content().string(containsString("/images/home/card-items.webp")))
				.andExpect(content().string(containsString("/images/home/card-badges.webp")))
				.andExpect(content().string(containsString("Sobre o jogo")))
				.andExpect(content().string(containsString("Explore uma montanha selvagem em equipe")))
				.andExpect(content().string(containsString("href=\"https://store.steampowered.com/app/3527290/PEAK/\"")))
				.andExpect(content().string(containsString("/images/home/steam-icon.webp")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")))
				.andExpect(content().string(containsString("Rogwerz")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("brand-arrow"))))
				.andExpect(content().string(containsString("biome-slide-name")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("biome-description"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("indicadores.svg"))));
	}

	@Test
	void rendersOneIndicatorPerBiome() throws Exception {
		String html = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		int indicators = html.split("data-biome-indicator=", -1).length - 1;
		assertEquals(Biomes.ALL.size(), indicators);
	}

	@Test
	void rendersSharedFooterDiscordLinksOnEveryPage() throws Exception {
		String[] routes = {
				"/", "/biomas/costa", "/biomas/tropico", "/biomas/alpes", "/biomas/mesa",
				"/biomas/caldeira", "/biomas/o-forno", "/biomas/pico", "/biomas/raizes", "/biomas/melancolia", "/biomas/cidadela", "/itens/comidas",
				"/itens/equipamentos", "/insignias"
		};
		String[] profileLinks = {
				"https://discord.com/users/563476359222132737",
				"https://discord.com/users/758117711925739590"
		};

		for (String route : routes) {
			MvcResult page = mockMvc.perform(get(route))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("Perfil de Denions220 no Discord")))
					.andExpect(content().string(containsString("Perfil de .Henry_006. no Discord")))
					.andExpect(content().string(containsString("Perfil de Lulu no Discord")))
					.andExpect(content().string(containsString("Perfil de Rogwerz no Discord")))
					.andExpect(content().string(containsString("Informações e imagens adaptadas da")))
					.andExpect(content().string(containsString("href=\"https://peak.wiki.gg\"")))
					.andReturn();
			String html = page.getResponse().getContentAsString();
			for (String profileLink : profileLinks) {
				org.junit.jupiter.api.Assertions.assertTrue(html.contains("href=\"" + profileLink + "\""));
			}
		}
	}

	@Test
	void servesLocalHomepageImages() throws Exception {
		MvcResult page = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn();
		Pattern imageSource = Pattern.compile("src=\"(/images/home/[^\"]+)\"");
		Matcher matcher = imageSource.matcher(page.getResponse().getContentAsString());
		int assetCount = 0;
		while (matcher.find()) {
			mockMvc.perform(get(matcher.group(1)))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
			assetCount++;
		}
		org.junit.jupiter.api.Assertions.assertTrue(assetCount > 0, "a home deve usar imagens locais em /images/home/");
		mockMvc.perform(get("/images/home/background.webp"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
	}

	@Test
	void rendersBiomePagesFromTheSharedTemplate() throws Exception {
		String[][] biomes = {
				{ "costa", "Costa" },
				{ "tropico", "Trópico" },
				{ "alpes", "Alpes" },
				{ "mesa", "Mesa" },
				{ "caldeira", "Caldeira" },
				{ "o-forno", "O Forno" },
				{ "pico", "Pico" },
				{ "raizes", "Raízes" },
				{ "melancolia", "Melancolia" },
				{ "cidadela", "Cidadela" }
		};

		for (String[] biome : biomes) {
			var page = mockMvc.perform(get("/biomas/{slug}", biome[0]))
					.andExpect(status().isOk())
					.andExpect(view().name("biome-detail"))
					.andExpect(content().string(containsString(biome[1])))
					.andExpect(content().string(containsString("Fórum da Comunidade")))
					.andExpect(content().string(containsString("/images/biome-pages/" + biome[0] + "/imgImage167.webp")))
					.andExpect(content().string(containsString("--biome-background: url(/images/biome-pages/" + biome[0] + "/imgImage167.webp)")))
					.andExpect(content().string(org.hamcrest.Matchers.not(containsString("biome-hero-backdrop"))))
					.andExpect(content().string(containsString("Denions220")))
					.andExpect(content().string(org.hamcrest.Matchers.not(containsString("figma.com/api/mcp/asset"))));
			if (biome[0].equals("pico")) {
				page.andExpect(content().string(containsString("A CHEGADA")));
			} else {
				page.andExpect(content().string(containsString("PERIGOS DO BIOMA")));
			}
		}
	}

	@Test
	void servesLocalFigmaImagesForBiomePages() throws Exception {
		mockMvc.perform(get("/images/biome-pages/costa/imgImage167.webp"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
		mockMvc.perform(get("/images/biome-pages/alpes/imgRectangle7.svg"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/svg+xml")));
	}

	@Test
	void allHomepageAndBiomeImageSourcesResolve() throws Exception {
		String[] routes = {
				"/", "/biomas/costa", "/biomas/tropico", "/biomas/alpes", "/biomas/mesa",
				"/biomas/caldeira", "/biomas/o-forno", "/biomas/pico", "/biomas/raizes", "/biomas/melancolia", "/biomas/cidadela"
		};
		Pattern imageSource = Pattern.compile("src=\"(/images/[^\"]+)\"");
		Pattern backgroundImage = Pattern.compile("url\\((/images/[^)]+)\\)");

		for (String route : routes) {
			String html = mockMvc.perform(get(route))
					.andExpect(status().isOk())
					.andReturn()
					.getResponse()
					.getContentAsString();
			for (Pattern pattern : new Pattern[] { imageSource, backgroundImage }) {
				Matcher matcher = pattern.matcher(html);
				while (matcher.find()) {
					mockMvc.perform(get(matcher.group(1))).andExpect(status().isOk());
				}
			}
		}
	}

	@Test
	void rendersRaizesFeaturesSection() throws Exception {
		mockMvc.perform(get("/biomas/raizes"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("ESTRUTURAS DO BIOMA")))
				.andExpect(content().string(containsString("Vento")))
				.andExpect(content().string(containsString("Zumbis")))
				.andExpect(content().string(containsString("Sequoias")))
				.andExpect(content().string(containsString("Mandrágora")));
	}

	@Test
	void rendersMelancoliaContent() throws Exception {
		mockMvc.perform(get("/biomas/melancolia"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Névoa sonolenta")))
				.andExpect(content().string(containsString("Fantasma gigante")))
				.andExpect(content().string(containsString("Sinos")))
				.andExpect(content().string(containsString("Minhoca Madrugadora")))
				.andExpect(content().string(containsString("Cachorro-quente")));
	}

	@Test
	void rendersCidadelaContent() throws Exception {
		mockMvc.perform(get("/biomas/cidadela"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Armadilhas de flecha")))
				.andExpect(content().string(containsString("Caldeirão de lava")))
				.andExpect(content().string(containsString("Escadas douradas")));
	}

	@Test
	void cidadelaHasNoFoodSection() throws Exception {
		mockMvc.perform(get("/biomas/cidadela"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("CONSUMÍVEIS NATURAIS DO BIOMA"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("food-effect-legend"))));
	}

	@Test
	void biomesAreNumberedInGameOrder() {
		String[] slugs = { "costa", "tropico", "raizes", "alpes", "mesa", "caldeira", "melancolia", "o-forno", "cidadela", "pico" };
		assertEquals(slugs.length, Biomes.ALL.size());
		for (int i = 0; i < slugs.length; i++) {
			assertEquals(slugs[i], Biomes.ALL.get(i).slug());
			assertEquals(i + 1, Biomes.ALL.get(i).number());
		}
	}

	@Test
	void nonDamagingHazardsAreNotTaggedAsDamage() {
		java.util.Map<String, String> expected = java.util.Map.of(
				"Besouros", "ARREMESSO",
				"Água da Melancolia", "LENTIDÃO",
				"Corvos trapaceiros", "VOZ");
		for (Biomes.Biome biome : Biomes.ALL) {
			for (Biomes.Hazard hazard : biome.hazards()) {
				if (expected.containsKey(hazard.title())) {
					assertEquals(expected.get(hazard.title()), hazard.effect(), hazard.title());
				}
			}
		}
	}

	@Test
	void bellringerBadgeSaysBellsAreLit() throws Exception {
		mockMvc.perform(get("/insignias"))
				.andExpect(content().string(containsString("Acenda 5 sinos na MELANCOLIA")));
	}

	@Test
	void onlyYellowBananaGrowsInMelancolia() {
		for (ItemCatalogs.ItemSection section : FoodCatalog.CATALOG.sections()) {
			for (ItemCatalogs.CatalogItem item : section.items()) {
				if (item.name().startsWith("Banana") && !item.name().equals("Banana amarela")) {
					org.junit.jupiter.api.Assertions.assertFalse(item.locations().contains("Melancolia"), item.name());
				}
			}
		}
	}

	@Test
	void duplicatedEquipmentHasSameDescription() {
		java.util.Map<String, String> seen = new java.util.HashMap<>();
		for (ItemCatalogs.ItemSection section : EquipmentCatalog.CATALOG.sections()) {
			for (ItemCatalogs.CatalogItem item : section.items()) {
				String previous = seen.putIfAbsent(item.name(), item.description());
				if (previous != null) {
					assertEquals(previous, item.description(), item.name());
				}
			}
		}
	}

	@Test
	void oldBiomesHaveNoFeaturesSection() throws Exception {
		for (String slug : new String[] { "costa", "tropico", "alpes", "mesa", "caldeira", "o-forno", "pico" }) {
			mockMvc.perform(get("/biomas/{slug}", slug))
					.andExpect(status().isOk())
					.andExpect(content().string(org.hamcrest.Matchers.not(containsString("ESTRUTURAS DO BIOMA"))));
		}
	}

	@Test
	void homeListsBiomesInGameOrder() throws Exception {
		String html = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		String[] order = { "costa", "tropico", "raizes", "alpes", "mesa", "caldeira", "melancolia", "o-forno", "cidadela", "pico" };
		int previous = -1;
		for (String slug : order) {
			int position = html.indexOf("/biomas/" + slug + "\"");
			org.junit.jupiter.api.Assertions.assertTrue(position > previous, slug + " fora da ordem do jogo");
			previous = position;
		}
	}

	@Test
	void returnsNotFoundForUnknownBiome() throws Exception {
		mockMvc.perform(get("/biomas/{slug}", "desconhecido"))
				.andExpect(status().isNotFound());
	}

	@Test
	void rendersFoodCatalogWithAllSectionsAndLocalIcons() throws Exception {
		mockMvc.perform(get("/itens/comidas"))
				.andExpect(status().isOk())
				.andExpect(view().name("item-catalog"))
				.andExpect(content().string(containsString("Naturais")))
				.andExpect(content().string(containsString("Embalados")))
				.andExpect(content().string(containsString("Cogumelos")))
				.andExpect(content().string(containsString("Míticos")))
				.andExpect(content().string(containsString("Cookies de escoteiro")))
				.andExpect(content().string(containsString("/images/items/food/embalados/imgImage207.webp")))
				.andExpect(content().string(containsString("Fórum da Comunidade")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("figma.com/api/mcp/asset"))));
	}

	@Test
	void foodCatalogMatchesWiki() throws Exception {
		String html = mockMvc.perform(get("/itens/comidas"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Cachorro-quente")))
				.andExpect(content().string(containsString("Mandrágora")))
				.andExpect(content().string(containsString("Minhoca Madrugadora")))
				.andExpect(content().string(containsString("Pernas de sapo")))
				.andExpect(content().string(containsString("Leite fortificado")))
				.andExpect(content().string(containsString("Livro dos Ossos")))
				.andExpect(content().string(containsString("Banana marrom")))
				.andExpect(content().string(containsString("Shroomberry roxo")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("Banana laranja"))))
				.andReturn().getResponse().getContentAsString();
		int napberry = html.indexOf(">Napberry<");
		org.junit.jupiter.api.Assertions.assertTrue(napberry > 0, "Napberry deve existir");
		String napberryRow = html.substring(napberry, html.indexOf("</tr>", napberry));
		org.junit.jupiter.api.Assertions.assertTrue(napberryRow.contains("-100 fome"), napberryRow);
	}

	@Test
	void equipmentCatalogMatchesWiki() throws Exception {
		mockMvc.perform(get("/itens/equipamentos"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Planador")))
				.andExpect(content().string(containsString("Castiçal")))
				.andExpect(content().string(containsString("Canhão de escoteiro")))
				.andExpect(content().string(containsString("Adaga ritual")))
				.andExpect(content().string(containsString("Ídolo ancião")))
				.andExpect(content().string(containsString("Remédio para fungos")))
				.andExpect(content().string(containsString("Efígie de escoteiro")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("2.25"))))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("Efíngie"))));
	}

	@Test
	void rendersEquipmentCatalogWithAllSectionsAndLocalIcons() throws Exception {
		mockMvc.perform(get("/itens/equipamentos"))
				.andExpect(status().isOk())
				.andExpect(view().name("item-catalog"))
				.andExpect(content().string(containsString("Cura")))
				.andExpect(content().string(containsString("Itens Especiais")))
				.andExpect(content().string(containsString("Itens de uso coletivo")))
				.andExpect(content().string(containsString("Itens de uso individual")))
				.andExpect(content().string(containsString("Itens Místicos")))
				.andExpect(content().string(containsString("Anti-Corda")))
				.andExpect(content().string(containsString("/images/items/equipamentos/imgImage219.webp")))
				.andExpect(content().string(containsString("--biome-background: url(/images/biome-pages/costa/imgImage167.webp)")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("figma.com/api/mcp/asset"))));
	}

	@Test
	void rendersBadgesWithSharedLayoutAndLocalAssets() throws Exception {
		MvcResult page = mockMvc.perform(get("/insignias"))
				.andExpect(status().isOk())
				.andExpect(view().name("badges"))
				.andExpect(content().string(containsString("Insígnias")))
				.andExpect(content().string(containsString("Insígnia Florestal")))
				.andExpect(content().string(containsString("Insígnia de Vulcanologia")))
				.andExpect(content().string(containsString("Fórum da Comunidade")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("figma.com/api/mcp/asset"))))
				.andReturn();

		String html = page.getResponse().getContentAsString();
		assertEquals(64, html.split("class=\"badge-card\"").length - 1);
		org.junit.jupiter.api.Assertions.assertTrue(html.contains("Insígnia do Andarilho"));
		org.junit.jupiter.api.Assertions.assertTrue(html.contains("Insígnia do Exorcista"));
		org.junit.jupiter.api.Assertions.assertTrue(html.contains("Náufrago"));
		org.junit.jupiter.api.Assertions.assertTrue(html.contains("Escape da ilha sem comer nenhuma comida embalada."));
		org.junit.jupiter.api.Assertions.assertFalse(html.contains("Naufrágo"));
		org.junit.jupiter.api.Assertions.assertFalse(html.contains("tal coisa"));
		org.junit.jupiter.api.Assertions.assertFalse(html.contains("CHAPADA"));

		Pattern badgeImage = Pattern.compile("src=\"(/images/badges/[^\"]+)\"");
		Matcher matcher = badgeImage.matcher(html);
		int assetCount = 0;
		while (matcher.find()) {
			mockMvc.perform(get(matcher.group(1)))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
			assetCount++;
		}
		org.junit.jupiter.api.Assertions.assertTrue(assetCount >= 64, "cada insígnia precisa de uma imagem local");
	}

	@Test
	void servesLocalCatalogImages() throws Exception {
		mockMvc.perform(get("/images/items/food/naturais/carrapato.webp"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
		mockMvc.perform(get("/images/items/equipamentos/imgImage219.webp"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/webp")));
	}

	@Test
	void everyCatalogImageIsServedLocally() throws Exception {
		Pattern imageSource = Pattern.compile("src=\"(/images/[^\"]+)\"");
		for (String route : new String[] { "/itens/comidas", "/itens/equipamentos" }) {
			MvcResult page = mockMvc.perform(get(route))
					.andExpect(status().isOk())
					.andReturn();
			Matcher matcher = imageSource.matcher(page.getResponse().getContentAsString());
			while (matcher.find()) {
				mockMvc.perform(get(matcher.group(1)))
						.andExpect(status().isOk());
			}
		}
	}
}
