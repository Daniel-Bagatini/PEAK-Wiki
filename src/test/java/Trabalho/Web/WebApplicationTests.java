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
				.andExpect(content().string(containsString("/images/biome-pages/costa/imgImage3.png")))
				.andExpect(content().string(containsString("/images/home/card-biomes.png")))
				.andExpect(content().string(containsString("/images/home/card-items.png")))
				.andExpect(content().string(containsString("/images/home/card-badges.png")))
				.andExpect(content().string(containsString("Sobre o jogo")))
				.andExpect(content().string(containsString("jogo cooperativo de escalada")))
				.andExpect(content().string(containsString("href=\"https://store.steampowered.com/app/3527290/PEAK/\"")))
				.andExpect(content().string(containsString("/images/home/steam-icon.png")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")))
				.andExpect(content().string(containsString("Rogwerz")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("brand-arrow"))))
				.andExpect(content().string(containsString("biome-slide-name")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("biome-description"))));
	}

	@Test
	void rendersSharedFooterDiscordLinksOnEveryPage() throws Exception {
		String[] routes = {
				"/", "/biomas/costa", "/biomas/tropico", "/biomas/alpes", "/biomas/mesa",
				"/biomas/caldeira", "/biomas/o-forno", "/biomas/pico", "/itens/comidas",
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
					.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
			assetCount++;
		}
		assertEquals(14, assetCount);
		mockMvc.perform(get("/images/home/background.png"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
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
				{ "pico", "Pico" }
		};

		for (String[] biome : biomes) {
			var page = mockMvc.perform(get("/biomas/{slug}", biome[0]))
					.andExpect(status().isOk())
					.andExpect(view().name("biome-detail"))
					.andExpect(content().string(containsString(biome[1])))
					.andExpect(content().string(containsString("Fórum da Comunidade")))
					.andExpect(content().string(containsString("/images/biome-pages/" + biome[0] + "/imgImage167.png")))
					.andExpect(content().string(containsString("--biome-background: url(/images/biome-pages/" + biome[0] + "/imgImage167.png)")))
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
		mockMvc.perform(get("/images/biome-pages/costa/imgImage167.png"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
		mockMvc.perform(get("/images/biome-pages/alpes/imgRectangle7.svg"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("image/svg+xml")));
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
				.andExpect(content().string(containsString("/images/items/food/embalados/imgImage207.png")))
				.andExpect(content().string(containsString("Fórum da Comunidade")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")))
				.andExpect(content().string(org.hamcrest.Matchers.not(containsString("figma.com/api/mcp/asset"))));
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
				.andExpect(content().string(containsString("/images/items/equipamentos/imgImage219.png")))
				.andExpect(content().string(containsString("--biome-background: url(/images/biome-pages/costa/imgImage167.png)")))
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
		assertEquals(24, html.split("class=\"badge-card\"").length - 1);

		Pattern badgeImage = Pattern.compile("src=\"(/images/badges/[^\"]+)\"");
		Matcher matcher = badgeImage.matcher(html);
		int assetCount = 0;
		while (matcher.find()) {
			mockMvc.perform(get(matcher.group(1)))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
			assetCount++;
		}
		assertEquals(30, assetCount);
	}

	@Test
	void servesLocalCatalogImages() throws Exception {
		mockMvc.perform(get("/images/items/food/naturais/carrapato.png"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
		mockMvc.perform(get("/images/items/equipamentos/imgImage219.png"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
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
