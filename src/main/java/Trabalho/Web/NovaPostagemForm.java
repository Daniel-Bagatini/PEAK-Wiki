package Trabalho.Web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dados enviados pelo formulário de nova postagem. */
public class NovaPostagemForm {

	@NotBlank(message = "Informe um apelido.")
	@Size(max = Postagem.AUTOR_MAX, message = "O apelido pode ter no máximo {max} caracteres.")
	@Pattern(regexp = "\\s*@?[\\p{L}\\p{N}_.?-]*\\s*", message = "Use só letras, números, _ . - ou ? no apelido.")
	private String autor;

	@NotBlank(message = "Escreva alguma coisa para publicar.")
	@Size(max = Postagem.TEXTO_MAX, message = "O texto pode ter no máximo {max} caracteres.")
	private String texto;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	/** Apelido sem espaços nas pontas e sempre começando com @. */
	public String autorFormatado() {
		String limpo = autor.strip();
		return limpo.startsWith("@") ? limpo : "@" + limpo;
	}
}
