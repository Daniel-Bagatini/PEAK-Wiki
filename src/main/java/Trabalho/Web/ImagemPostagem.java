package Trabalho.Web;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Imagem enviada junto com uma postagem da Comunidade.
 * Fica numa tabela própria para que o feed não carregue os bytes de todas as imagens.
 */
@Entity
@Table(name = "imagens_postagens")
public class ImagemPostagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String tipo;

	private int largura;

	private int altura;

	@Column(nullable = false, length = ImagemDaPostagem.BYTES_MAX)
	private byte[] dados;

	protected ImagemPostagem() {
	}

	public ImagemPostagem(ImagemDaPostagem.Info info, byte[] dados) {
		this.tipo = info.tipo();
		this.largura = info.largura();
		this.altura = info.altura();
		this.dados = dados;
	}

	public Long getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	public byte[] getDados() {
		return dados;
	}
}
