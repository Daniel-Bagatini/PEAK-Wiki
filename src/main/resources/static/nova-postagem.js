// Escolha de imagem da nova postagem.
// Antes de enviar, a imagem é reduzida para caber em HD (1280×720, ou 720×1280 em pé) e convertida
// para WebP, para ocupar pouco espaço no banco. O servidor confere os mesmos limites.
(() => {
  const LADO_MAIOR = 1280;
  const LADO_MENOR = 720;
  const BYTES_MAX = 1024 * 1024;
  const TIPOS = ["image/png", "image/jpeg", "image/webp"];

  const entrada = document.getElementById("imagem");
  const area = document.querySelector("[data-area-imagem]");
  const previa = document.querySelector("[data-previa]");
  const previaImg = document.querySelector("[data-previa-img]");
  const previaInfo = document.querySelector("[data-previa-info]");
  const remover = document.querySelector("[data-remover-imagem]");
  const erro = document.querySelector("[data-erro-imagem]");
  if (!entrada || !area) {
    return;
  }

  let urlPrevia = null;
  let processando = false;

  function mostrarErro(mensagem) {
    erro.textContent = mensagem;
    erro.hidden = !mensagem;
    area.classList.toggle("is-invalid", Boolean(mensagem));
  }

  function limpar() {
    entrada.value = "";
    if (urlPrevia) {
      URL.revokeObjectURL(urlPrevia);
      urlPrevia = null;
    }
    previa.hidden = true;
    area.hidden = false;
  }

  function escala(largura, altura) {
    const deitada = largura >= altura;
    const maxL = deitada ? LADO_MAIOR : LADO_MENOR;
    const maxA = deitada ? LADO_MENOR : LADO_MAIOR;
    return Math.min(1, maxL / largura, maxA / altura);
  }

  function paraBlob(canvas, tipo, qualidade) {
    return new Promise((resolve) => canvas.toBlob(resolve, tipo, qualidade));
  }

  async function reduzir(arquivo) {
    const bitmap = await createImageBitmap(arquivo);
    const fator = escala(bitmap.width, bitmap.height);
    const largura = Math.max(1, Math.round(bitmap.width * fator));
    const altura = Math.max(1, Math.round(bitmap.height * fator));

    const canvas = document.createElement("canvas");
    canvas.width = largura;
    canvas.height = altura;
    canvas.getContext("2d").drawImage(bitmap, 0, 0, largura, altura);
    bitmap.close();

    // WebP quando o navegador sabe gerar; senão JPG. Baixa a qualidade se ainda passar de 1 MB.
    for (const qualidade of [0.82, 0.7, 0.55, 0.4]) {
      let blob = await paraBlob(canvas, "image/webp", qualidade);
      if (!blob || blob.type !== "image/webp") {
        blob = await paraBlob(canvas, "image/jpeg", qualidade);
      }
      if (blob && blob.size <= BYTES_MAX) {
        return { blob, largura, altura };
      }
    }
    throw new Error("grande");
  }

  async function escolher(arquivo) {
    mostrarErro("");
    if (!arquivo) {
      return;
    }
    if (!TIPOS.includes(arquivo.type)) {
      limpar();
      mostrarErro("Envie uma imagem PNG, JPG ou WebP.");
      return;
    }

    processando = true;
    area.classList.add("is-busy");
    try {
      const { blob, largura, altura } = await reduzir(arquivo);
      const extensao = blob.type === "image/webp" ? "webp" : "jpg";
      const reduzido = new File([blob], "imagem." + extensao, { type: blob.type });
      const lista = new DataTransfer();
      lista.items.add(reduzido);
      entrada.files = lista.files;

      if (urlPrevia) {
        URL.revokeObjectURL(urlPrevia);
      }
      urlPrevia = URL.createObjectURL(reduzido);
      previaImg.src = urlPrevia;
      previaInfo.textContent = `${largura}×${altura} · ${Math.max(1, Math.round(reduzido.size / 1024))} KB`;
      previa.hidden = false;
      area.hidden = true;
    } catch (e) {
      limpar();
      mostrarErro(e.message === "grande"
        ? "Não foi possível deixar esta imagem com menos de 1 MB. Tente outra."
        : "Não foi possível ler esta imagem. Tente outro arquivo.");
    } finally {
      processando = false;
      area.classList.remove("is-busy");
    }
  }

  entrada.addEventListener("change", () => {
    const arquivo = entrada.files[0];
    // Evita reprocessar o arquivo que este próprio script acabou de colocar no campo.
    if (arquivo && !processando) {
      escolher(arquivo);
    }
  });

  remover.addEventListener("click", () => {
    limpar();
    mostrarErro("");
    entrada.focus();
  });

  ["dragenter", "dragover"].forEach((tipo) =>
    area.addEventListener(tipo, (evento) => {
      evento.preventDefault();
      area.classList.add("is-dragging");
    }));
  ["dragleave", "drop"].forEach((tipo) =>
    area.addEventListener(tipo, () => area.classList.remove("is-dragging")));
  area.addEventListener("drop", (evento) => {
    evento.preventDefault();
    escolher(evento.dataTransfer.files[0]);
  });

  // Não deixa publicar enquanto a imagem ainda está sendo reduzida.
  entrada.form.addEventListener("submit", (evento) => {
    if (processando) {
      evento.preventDefault();
    }
  });
})();
