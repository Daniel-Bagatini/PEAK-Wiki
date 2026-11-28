package Trabalho.Web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
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
		// A mais recente entra no mosaico como as outras, sem um quadro gigante em destaque.
		assertTrue(nova > html.indexOf("class=\"community-comic\""), "a nova postagem deve ficar no mosaico");
		assertTrue(!html.contains("community-post--destaque"));
	}

	@Test
	void textoCurtoGanhaFonteMaior() throws Exception {
		mockMvc.perform(post("/comunidade/nova-postagem")
				.param("autor", "curto")
				.param("texto", "Bom dia, escaladores!"))
				.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/comunidade/nova-postagem")
				.param("autor", "longo")
				.param("texto", "Um texto bem maior que quarenta caracteres para a fonte não crescer."))
				.andExpect(status().is3xxRedirection());

		String html = mockMvc.perform(get("/comunidade")).andReturn().getResponse().getContentAsString();
		assertTrue(html.matches("(?s).*class=\"post-caption post-caption--curta\"\\s*>Bom dia, escaladores!<.*"),
				"o texto curto ganha a fonte maior");
		assertTrue(html.matches("(?s).*class=\"post-caption\"\\s*>Um texto bem maior.*"),
				"o texto longo fica com a fonte normal");
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

	@Autowired
	private ImagemPostagemRepository imagens;

	private static byte[] png(int largura, int altura) throws IOException {
		BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream saida = new ByteArrayOutputStream();
		ImageIO.write(imagem, "png", saida);
		return saida.toByteArray();
	}

	private Postagem maisRecente() {
		return postagens.findAllByOrderByCriadaEmDesc().get(0);
	}

	@Test
	void curtirPeloFormularioSomaUmEVoltaParaAPostagem() throws Exception {
		Postagem alvo = maisRecente();
		int antes = alvo.getCurtidas();

		mockMvc.perform(post("/comunidade/postagens/{id}/curtir", alvo.getId()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/comunidade#postagem-" + alvo.getId()));

		assertEquals(antes + 1, postagens.findById(alvo.getId()).orElseThrow().getCurtidas());
	}

	@Test
	void curtirEDescurtirPeloJavaScriptDevolvemOsNumerosNovos() throws Exception {
		Postagem alvo = maisRecente();

		mockMvc.perform(post("/comunidade/postagens/{id}/curtir", alvo.getId()).header("X-Requested-With", "fetch"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"curtidas\":" + (alvo.getCurtidas() + 1))))
				.andExpect(content().string(containsString("\"descurtidas\":" + alvo.getDescurtidas())));

		mockMvc.perform(post("/comunidade/postagens/{id}/descurtir", alvo.getId()).header("X-Requested-With", "fetch"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"descurtidas\":" + (alvo.getDescurtidas() + 1))));

		Postagem depois = postagens.findById(alvo.getId()).orElseThrow();
		assertEquals(alvo.getCurtidas() + 1, depois.getCurtidas());
		assertEquals(alvo.getDescurtidas() + 1, depois.getDescurtidas());
	}

	@Test
	void reagirAPostagemQueNaoExiste() throws Exception {
		mockMvc.perform(post("/comunidade/postagens/{id}/curtir", 999999).header("X-Requested-With", "fetch"))
				.andExpect(status().isNotFound());
		mockMvc.perform(post("/comunidade/postagens/{id}/descurtir", 999999))
				.andExpect(redirectedUrl("/comunidade"));
	}

	@Test
	void oFeedTemBotoesDeCurtirEDescurtirEOsQuadrosDeHq() throws Exception {
		Postagem alvo = maisRecente();
		mockMvc.perform(get("/comunidade"))
				.andExpect(content().string(containsString("action=\"/comunidade/postagens/" + alvo.getId() + "/curtir\"")))
				.andExpect(content().string(containsString("action=\"/comunidade/postagens/" + alvo.getId() + "/descurtir\"")))
				.andExpect(content().string(containsString("id=\"postagem-" + alvo.getId() + "\"")))
				.andExpect(content().string(containsString("class=\"community-comic\"")))
				.andExpect(content().string(containsString("/comunidade.js")))
				.andExpect(content().string(not(containsString("community-challenge"))));
	}

	@Test
	void oFormularioPermiteEnviarImagem() throws Exception {
		mockMvc.perform(get("/comunidade/nova-postagem"))
				.andExpect(content().string(containsString("enctype=\"multipart/form-data\"")))
				.andExpect(content().string(containsString("type=\"file\"")))
				.andExpect(content().string(containsString("name=\"imagem\"")))
				.andExpect(content().string(containsString("/nova-postagem.js")));
	}

	@Test
	void publicaComImagemHdEMostraNoFeed() throws Exception {
		MockMultipartFile arquivo = new MockMultipartFile("imagem", "foto.png", "image/png", png(1280, 720));

		mockMvc.perform(multipart("/comunidade/nova-postagem")
				.file(arquivo)
				.param("autor", "fotografa")
				.param("texto", "Olha essa vista do pico!"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/comunidade"));

		Postagem nova = maisRecente();
		assertEquals("@fotografa", nova.getAutor());
		assertEquals(1, imagens.count());
		ImagemPostagem salva = imagens.findAll().get(0);
		assertEquals("/comunidade/imagens/" + salva.getId(), nova.getImagem());
		assertEquals(1280, salva.getLargura());
		assertEquals(720, salva.getAltura());
		assertEquals("1.7778", nova.getProporcao());

		// O quadro recebe o formato da foto, para ela aparecer inteira.
		mockMvc.perform(get("/comunidade"))
				.andExpect(content().string(containsString("src=\"/comunidade/imagens/" + salva.getId() + "\"")))
				.andExpect(content().string(containsString("--proporcao: 1.7778")));

		mockMvc.perform(get("/comunidade/imagens/{id}", salva.getId()))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "image/png"))
				.andExpect(header().string("X-Content-Type-Options", "nosniff"));
	}

	@Test
	void aceitaImagemEmPeAte720Por1280() throws Exception {
		mockMvc.perform(multipart("/comunidade/nova-postagem")
				.file(new MockMultipartFile("imagem", "pe.png", "image/png", png(720, 1280)))
				.param("autor", "teste")
				.param("texto", "Foto em pé"))
				.andExpect(status().is3xxRedirection());

		assertEquals(1, imagens.count());
		assertEquals("0.5625", maisRecente().getProporcao());
	}

	@Test
	void recusaImagemMaiorQueHd() throws Exception {
		mockMvc.perform(multipart("/comunidade/nova-postagem")
				.file(new MockMultipartFile("imagem", "grande.png", "image/png", png(1281, 720)))
				.param("autor", "teste")
				.param("texto", "Imagem grande demais"))
				.andExpect(status().isOk())
				.andExpect(view().name("nova-postagem"))
				.andExpect(content().string(containsString("no máximo 1280×720 (HD)")));

		assertEquals(2, postagens.count());
		assertEquals(0, imagens.count());
	}

	@Test
	void recusaArquivoQueNaoEImagem() throws Exception {
		mockMvc.perform(multipart("/comunidade/nova-postagem")
				.file(new MockMultipartFile("imagem", "virus.png", "image/png", "<script>oi</script>".getBytes()))
				.param("autor", "teste")
				.param("texto", "Arquivo falso"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Envie uma imagem PNG, JPG ou WebP.")));

		assertEquals(0, imagens.count());
	}

	@Test
	void apagarPostagemApagaASuaImagem() throws Exception {
		mockMvc.perform(multipart("/comunidade/nova-postagem")
				.file(new MockMultipartFile("imagem", "foto.png", "image/png", png(640, 360)))
				.param("autor", "teste")
				.param("texto", "Vai sumir"))
				.andExpect(status().is3xxRedirection());
		assertEquals(1, imagens.count());

		mockMvc.perform(post("/comunidade/postagens/{id}/apagar", maisRecente().getId()))
				.andExpect(status().is3xxRedirection());

		assertEquals(0, imagens.count());
		assertEquals(2, postagens.count());
	}

	@Test
	void imagemQueNaoExisteDa404() throws Exception {
		mockMvc.perform(get("/comunidade/imagens/{id}", 999999))
				.andExpect(status().isNotFound());
	}
}
