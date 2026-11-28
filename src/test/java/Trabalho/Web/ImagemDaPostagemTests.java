package Trabalho.Web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/** Confere a leitura do tamanho de imagens WebP, que o Java não sabe abrir sozinho. */
class ImagemDaPostagemTests {

	/** Cabeçalho RIFF/WEBP com um bloco do tipo informado e 30 bytes de espaço para os dados. */
	private static byte[] webp(String bloco) {
		byte[] d = new byte[40];
		System.arraycopy("RIFF".getBytes(StandardCharsets.US_ASCII), 0, d, 0, 4);
		System.arraycopy("WEBP".getBytes(StandardCharsets.US_ASCII), 0, d, 8, 4);
		System.arraycopy(bloco.getBytes(StandardCharsets.US_ASCII), 0, d, 12, 4);
		return d;
	}

	@Test
	void leWebpComPerdas() throws Exception {
		byte[] d = webp("VP8 ");
		d[23] = (byte) 0x9D;
		d[24] = 0x01;
		d[25] = 0x2A;
		d[26] = (byte) (1280 & 0xFF);
		d[27] = (byte) (1280 >> 8);
		d[28] = (byte) (720 & 0xFF);
		d[29] = (byte) (720 >> 8);

		ImagemDaPostagem.Info info = ImagemDaPostagem.conferir(d);
		assertEquals("image/webp", info.tipo());
		assertEquals(1280, info.largura());
		assertEquals(720, info.altura());
	}

	@Test
	void leWebpSemPerdas() throws Exception {
		int l = 800 - 1;
		int a = 600 - 1;
		byte[] d = webp("VP8L");
		d[20] = 0x2F;
		d[21] = (byte) (l & 0xFF);
		d[22] = (byte) (((l >> 8) & 0x3F) | ((a & 0x03) << 6));
		d[23] = (byte) ((a >> 2) & 0xFF);
		d[24] = (byte) ((a >> 10) & 0x0F);

		ImagemDaPostagem.Info info = ImagemDaPostagem.conferir(d);
		assertEquals(800, info.largura());
		assertEquals(600, info.altura());
	}

	@Test
	void recusaWebpEstendidoMaiorQueHd() {
		int l = 1920 - 1;
		int a = 1080 - 1;
		byte[] d = webp("VP8X");
		d[24] = (byte) (l & 0xFF);
		d[25] = (byte) ((l >> 8) & 0xFF);
		d[26] = (byte) ((l >> 16) & 0xFF);
		d[27] = (byte) (a & 0xFF);
		d[28] = (byte) ((a >> 8) & 0xFF);
		d[29] = (byte) ((a >> 16) & 0xFF);

		ImagemDaPostagem.ImagemInvalidaException erro = assertThrows(
				ImagemDaPostagem.ImagemInvalidaException.class, () -> ImagemDaPostagem.conferir(d));
		assertEquals("A imagem pode ter no máximo 1280×720 (HD). Esta tem 1920×1080.", erro.getMessage());
	}

	@Test
	void recusaArquivoAcimaDe1Mb() {
		byte[] d = new byte[ImagemDaPostagem.BYTES_MAX + 1];
		assertThrows(ImagemDaPostagem.ImagemInvalidaException.class, () -> ImagemDaPostagem.conferir(d));
	}
}
