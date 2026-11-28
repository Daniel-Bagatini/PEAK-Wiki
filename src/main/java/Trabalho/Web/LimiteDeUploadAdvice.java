package Trabalho.Web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Quando alguém envia um arquivo maior que o limite do servidor, o Spring recusa antes de chegar
 * no controller. Aqui a pessoa volta para o formulário com um aviso, em vez de ver uma página de erro.
 */
@ControllerAdvice
public class LimiteDeUploadAdvice {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String arquivoGrandeDemais() {
		return "redirect:/comunidade/nova-postagem?imagemGrande";
	}
}
