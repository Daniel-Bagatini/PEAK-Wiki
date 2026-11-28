package Trabalho.Web;

import java.time.Instant;
import java.util.Locale;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/** Uma publicação da página Comunidade, salva no banco de dados. */
@Entity
@Table(name = "postagens")
public class Postagem {

	public static final int AUTOR_MAX = 30;
	public static final int TEXTO_MAX = 500;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = AUTOR_MAX)
	private String autor;

	@Column(nullable = false, length = TEXTO_MAX)
	private String texto;

	/** Endereço da imagem da postagem (opcional): um arquivo em /static ou uma imagem enviada. */
	private String imagem;

	/** Imagem enviada pelo formulário. É apagada junto com a postagem. */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "imagem_enviada_id")
	private ImagemPostagem imagemEnviada;

	/** Tamanho da imagem enviada, para a página montar o quadro na proporção certa sem cortar a foto. */
	private Integer imagemLargura;

	private Integer imagemAltura;

	/** Caminho da foto de perfil em /static (opcional). Sem ela, a página mostra a inicial do autor. */
	private String avatar;

	private int curtidas;

	private int descurtidas;

	private int comentarios;

	@Column(nullable = false)
	private Instant criadaEm;

	protected Postagem() {
	}

	public Postagem(String autor, String texto) {
		this.autor = autor;
		this.texto = texto;
		this.criadaEm = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getAutor() {
		return autor;
	}

	public String getTexto() {
		return texto;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	/** Liga uma imagem enviada (já salva) a esta postagem. */
	public void setImagemEnviada(ImagemPostagem imagemEnviada) {
		this.imagemEnviada = imagemEnviada;
		this.imagem = "/comunidade/imagens/" + imagemEnviada.getId();
		this.imagemLargura = imagemEnviada.getLargura();
		this.imagemAltura = imagemEnviada.getAltura();
	}

	/**
	 * Largura dividida pela altura da imagem (ex.: "1.7778"), ou null quando não se sabe o tamanho.
	 * A página usa esse número para dar a cada quadro o formato da sua foto.
	 */
	public String getProporcao() {
		if (imagemLargura == null || imagemAltura == null || imagemAltura == 0) {
			return null;
		}
		return String.format(Locale.ROOT, "%.4f", imagemLargura / (double) imagemAltura);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getCurtidas() {
		return curtidas;
	}

	public int getDescurtidas() {
		return descurtidas;
	}

	public int getComentarios() {
		return comentarios;
	}

	public void setReacoes(int curtidas, int descurtidas, int comentarios) {
		this.curtidas = curtidas;
		this.descurtidas = descurtidas;
		this.comentarios = comentarios;
	}

	public Instant getCriadaEm() {
		return criadaEm;
	}

	public void setCriadaEm(Instant criadaEm) {
		this.criadaEm = criadaEm;
	}

	/** Primeira letra do autor, usada quando não há foto de perfil. */
	public String getInicial() {
		return autor.isEmpty() ? "?" : autor.substring(0, 1).toUpperCase();
	}
}
