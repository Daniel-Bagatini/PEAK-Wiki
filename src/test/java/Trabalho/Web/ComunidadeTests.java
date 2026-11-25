package Trabalho.Web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComunidadeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostagemRepository postagens;

	@Test
	void mostraAsPostagensDeDemonstracaoQuandoOBancoEstaVazio() throws Exception {
		mockMvc.perform(get("/comunidade"))
				.andExpect(status().isOk())
				.andExpect(view().name("comunidade"))
				.andExpect(content().string(containsString("@Lukitos")))
				.andExpect(content().string(containsString("Bela equipe, tomara que chegem ao topo")))
				.andExpect(content().string(containsString("@richetimber")))
				.andExpect(content().string(containsString("href=\"/comunidade/nova-postagem\"")));
	}

	@Test
	void mostraOFormularioDeNovaPostagem() throws Exception {
		mockMvc.perform(get("/comunidade/nova-postagem"))
				.andExpect(status().isOk())
				.andExpect(view().name("nova-postagem"))
				.andExpect(content().string(containsString("name=\"autor\"")))
				.andExpect(content().string(containsString("name=\"texto\"")))
				.andExpect(content().string(containsString("/css/site.css")))
				.andExpect(content().string(containsString("Siga os criadores da página no Discord")));
	}

	@Test
	void publicaUmaPostagemEMostraNoTopoDoFeed() throws Exception {
		mockMvc.perform(post("/comunidade/nova-postagem")
				.param("autor", "  escalador_123 ")
				.param("texto", "Levem cordas extras para os Alpes!"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/comunidade"))
				.andExpect(flash().attribute("publicada", true));

		assertEquals(3, postagens.count());

		String html = mockMvc.perform(get("/comunidade"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		int nova = html.indexOf("@escalador_123");
		assertTrue(nova > 0, "a nova postagem deve aparecer");
		assertTrue(nova < html.indexOf("@Lukitos"), "a postagem mais recente deve vir primeiro");
		assertTrue(html.contains("Levem cordas extras para os Alpes!"));
	}

	@Test
	void recusaPostagemVaziaEMostraOsErros() throws Exception {
		mockMvc.perform(post("/comunidade/nova-postagem")
				.param("autor", "")
				.param("texto", "   "))
				.andExpect(status().isOk())
				.andExpect(view().name("nova-postagem"))
				.andExpect(content().string(containsString("Informe um apelido.")))
				.andExpect(content().string(containsString("Escreva alguma coisa para publicar.")));

		assertEquals(2, postagens.count());
	}

	@Test
	void escapaHtmlNoTextoDaPostagem() throws Exception {
		mockMvc.perform(post("/comunidade/nova-postagem")
				.param("autor", "teste")
				.param("texto", "<script>alert('oi')</script>"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(get("/comunidade"))
				.andExpect(content().string(not(containsString("<script>alert('oi')</script>"))))
				.andExpect(content().string(containsString("&lt;script&gt;")));
	}

	@Test
	void apagaUmaPostagem() throws Exception {
		Postagem alvo = postagens.findAllByOrderByCriadaEmDesc().get(0);

		mockMvc.perform(get("/comunidade"))
				.andExpect(content().string(containsString("action=\"/comunidade/postagens/" + alvo.getId() + "/apagar\"")));

		mockMvc.perform(post("/comunidade/postagens/{id}/apagar", alvo.getId()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/comunidade"))
				.andExpect(flash().attribute("apagada", true));

		assertEquals(1, postagens.count());
		assertTrue(postagens.findById(alvo.getId()).isEmpty());
	}

	@Test
	void apagarPostagemQueNaoExisteSoVoltaParaOFeed() throws Exception {
		mockMvc.perform(post("/comunidade/postagens/{id}/apagar", 999999))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/comunidade"));

		assertEquals(2, postagens.count());
	}
}
