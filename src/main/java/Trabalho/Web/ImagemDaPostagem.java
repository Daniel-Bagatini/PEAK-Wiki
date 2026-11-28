package Trabalho.Web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Confere se um arquivo enviado é uma imagem aceita na Comunidade.
 *
 * <p>O banco tem pouco espaço, então a imagem pode ter no máximo HD (1280×720, ou 720×1280 em pé)
 * e {@value #BYTES_MAX} bytes. A página de nova postagem já reduz e converte a imagem para WebP
 * no navegador; esta classe garante o limite mesmo se alguém enviar o arquivo por outro caminho.
 */
public final class ImagemDaPostagem {

	public static final int LADO_MAIOR_MAX = 1280;
	public static final int LADO_MENOR_MAX = 720;
	public static final int BYTES_MAX = 1_048_576;

	/** Tipo e tamanho de uma imagem já conferida. */
	public record Info(String tipo, int largura, int altura) {
	}

	/** Erro com uma mensagem que pode ser mostrada para quem está postando. */
	public static class ImagemInvalidaException extends Exception {
		public ImagemInvalidaException(String mensagem) {
			super(mensagem);
		}
	}

	private ImagemDaPostagem() {
	}

	public static Info conferir(byte[] dados) throws ImagemInvalidaException {
		if (dados.length > BYTES_MAX) {
			throw new ImagemInvalidaException("A imagem pode ter no máximo 1 MB.");
		}
		Info info = ler(dados);
		int maior = Math.max(info.largura(), info.altura());
		int menor = Math.min(info.largura(), info.altura());
		if (maior > LADO_MAIOR_MAX || menor > LADO_MENOR_MAX) {
			throw new ImagemInvalidaException("A imagem pode ter no máximo 1280×720 (HD). Esta tem "
					+ info.largura() + "×" + info.altura() + ".");
		}
		return info;
	}

	static Info ler(byte[] d) throws ImagemInvalidaException {
		if (d.length >= 30 && ascii(d, 0, "RIFF") && ascii(d, 8, "WEBP")) {
			return lerWebp(d);
		}
		if (d.length >= 8 && (d[0] & 0xFF) == 0x89 && ascii(d, 1, "PNG")) {
			return lerComImageIO(d, "image/png");
		}
		if (d.length >= 3 && (d[0] & 0xFF) == 0xFF && (d[1] & 0xFF) == 0xD8 && (d[2] & 0xFF) == 0xFF) {
			return lerComImageIO(d, "image/jpeg");
		}
		throw new ImagemInvalidaException("Envie uma imagem PNG, JPG ou WebP.");
	}

	/** Lê só o cabeçalho do WebP (VP8, VP8L ou VP8X), sem decodificar a imagem. */
	private static Info lerWebp(byte[] d) throws ImagemInvalidaException {
		int largura;
		int altura;
		if (ascii(d, 12, "VP8 ")) {
			if ((d[23] & 0xFF) != 0x9D || (d[24] & 0xFF) != 0x01 || (d[25] & 0xFF) != 0x2A) {
				throw invalida();
			}
			largura = le16(d, 26) & 0x3FFF;
			altura = le16(d, 28) & 0x3FFF;
		} else if (ascii(d, 12, "VP8L")) {
			if ((d[20] & 0xFF) != 0x2F) {
				throw invalida();
			}
			int b1 = d[21] & 0xFF, b2 = d[22] & 0xFF, b3 = d[23] & 0xFF, b4 = d[24] & 0xFF;
			largura = 1 + (b1 | ((b2 & 0x3F) << 8));
			altura = 1 + ((b2 >> 6) | (b3 << 2) | ((b4 & 0x0F) << 10));
		} else if (ascii(d, 12, "VP8X")) {
			largura = 1 + le24(d, 24);
			altura = 1 + le24(d, 27);
		} else {
			throw invalida();
		}
		if (largura <= 0 || altura <= 0) {
			throw invalida();
		}
		return new Info("image/webp", largura, altura);
	}

	private static Info lerComImageIO(byte[] d, String tipo) throws ImagemInvalidaException {
		try (ImageInputStream entrada = ImageIO.createImageInputStream(new ByteArrayInputStream(d))) {
			Iterator<ImageReader> leitores = ImageIO.getImageReaders(entrada);
			if (!leitores.hasNext()) {
				throw invalida();
			}
			ImageReader leitor = leitores.next();
			try {
				leitor.setInput(entrada, true, true);
				return new Info(tipo, leitor.getWidth(0), leitor.getHeight(0));
			} finally {
				leitor.dispose();
			}
		} catch (IOException e) {
			throw invalida();
		}
	}

	private static ImagemInvalidaException invalida() {
		return new ImagemInvalidaException("Não foi possível ler esta imagem. Tente outro arquivo.");
	}

	private static boolean ascii(byte[] d, int inicio, String texto) {
		if (d.length < inicio + texto.length()) {
			return false;
		}
		for (int i = 0; i < texto.length(); i++) {
			if (d[inicio + i] != (byte) texto.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private static int le16(byte[] d, int i) {
		return (d[i] & 0xFF) | ((d[i + 1] & 0xFF) << 8);
	}

	private static int le24(byte[] d, int i) {
		return (d[i] & 0xFF) | ((d[i + 1] & 0xFF) << 8) | ((d[i + 2] & 0xFF) << 16);
	}
}
