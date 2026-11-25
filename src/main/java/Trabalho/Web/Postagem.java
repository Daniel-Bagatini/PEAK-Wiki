package Trabalho.Web;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	/** Caminho de uma imagem em /static (opcional). */
	private String imagem;

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
