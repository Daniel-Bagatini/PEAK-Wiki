// Curtir e descurtir sem recarregar a página.
// Sem JavaScript, os botões continuam funcionando como formulários comuns.
document.addEventListener("submit", async (evento) => {
  const form = evento.target.closest(".post-react-form");
  if (!form) {
    return;
  }
  evento.preventDefault();

  const botao = form.querySelector("button");
  if (botao.disabled) {
    return;
  }
  botao.disabled = true;

  try {
    const resposta = await fetch(form.action, {
      method: "POST",
      headers: { "X-Requested-With": "fetch", Accept: "application/json" },
    });
    if (!resposta.ok) {
      throw new Error("Falha ao reagir: " + resposta.status);
    }
    const reacoes = await resposta.json();
    const card = form.closest(".community-post");
    card.querySelector('[data-contador="curtidas"]').textContent = reacoes.curtidas;
    card.querySelector('[data-contador="descurtidas"]').textContent = reacoes.descurtidas;

    botao.classList.remove("post-react--pulso");
    void botao.offsetWidth; // reinicia a animação
    botao.classList.add("post-react--pulso");
  } catch (erro) {
    form.submit();
  } finally {
    botao.disabled = false;
  }
});

// ---------- Quadros de HQ em mosaico ----------
// Sem JavaScript, os quadros ficam em linhas (veja .community-comic no comunidade.css). Com ele, viram
// um mosaico de 6 colunas (no máximo 3 postagens por linha): cada postagem entra no espaço livre mais alto, então um quadro baixo ao lado
// de um alto deixa a postagem seguinte subir para o vão embaixo dele. Se nenhuma postagem couber
// no vão, o quadro de cima estica até fechá-lo e a foto fica centralizada nele.
(() => {
  const mosaico = document.querySelector(".community-comic");
  if (!mosaico) {
    return;
  }
  // 6 colunas e quadros de no mínimo 2 colunas: no máximo 3 postagens por linha.
  const COLUNAS = 6;
  const ALTURA_FOTO = 520; // altura máxima da foto, igual ao max-height do comunidade.css
  const VAO_MAXIMO = 160; // vão que um quadro pode esticar para fechar; acima disso, a próxima postagem encolhe
  const LARGURA_MINIMA = 560; // abaixo disso os quadros ficam um embaixo do outro
  const TOLERANCIA = 24; // desníveis menores que isso contam como a mesma altura
  const OLHAR_ADIANTE = 4; // quantas postagens seguintes podem pular na frente para tapar um vão

  // Quantas colunas o quadro quer, pelo formato da foto (ou pelo tamanho do texto).
  function colunasDesejadas(card) {
    const proporcao = parseFloat(card.style.getPropertyValue("--proporcao"));
    if (!proporcao) {
      const texto = card.querySelector(".post-caption")?.textContent.trim().length ?? 0;
      return card.classList.contains("community-post--com-foto") || texto > 160 ? 3 : 2;
    }
    if (proporcao < 0.8) return 2; // em pé
    if (proporcao < 1.25) return 3; // quase quadrada
    if (proporcao < 2.2) return 4; // deitada
    return 6; // panorâmica
  }

  // Um quadro aceita até 1/4 a menos de largura do que queria (ex.: foto larga em 3 colunas em vez de 4).
  function cabe(desejo, colunas) {
    return colunas >= 2 && colunas >= Math.ceil(desejo * 0.75);
  }

  function soltar(card) {
    ["left", "top", "width", "height"].forEach((p) => card.style.removeProperty(p));
  }

  let larguraMontada = 0;

  function montar() {
    const cards = [...mosaico.children];
    const largura = mosaico.clientWidth;
    larguraMontada = largura;
    if (largura < LARGURA_MINIMA) {
      mosaico.classList.remove("community-comic--mosaico");
      mosaico.style.removeProperty("height");
      cards.forEach(soltar);
      return;
    }
    mosaico.classList.add("community-comic--mosaico");
    const vao = parseFloat(getComputedStyle(mosaico).columnGap) || 14;
    const coluna = (largura - vao * (COLUNAS - 1)) / COLUNAS;
    const topo = new Array(COLUNAS).fill(0); // onde cada coluna está livre
    // O quadro não fica muito mais largo que a foto: ela tem no máximo ALTURA_FOTO de altura.
    const larguraDe = (colunas) => colunas * coluna + (colunas - 1) * vao;
    const limitar = (card, desejo) => {
      const proporcao = parseFloat(card.style.getPropertyValue("--proporcao"));
      if (!proporcao) {
        return desejo;
      }
      let maximo = COLUNAS;
      while (maximo > 2 && larguraDe(maximo) > ALTURA_FOTO * proporcao + 40) {
        maximo--;
      }
      return Math.min(desejo, maximo);
    };
    const fila = cards.map((card) => ({ card, desejo: limitar(card, colunasDesejadas(card)) }));
    const postos = [];

    while (fila.length) {
      // Trecho livre mais alto: colunas vizinhas que estão (quase) no nível mais alto.
      const nivel = Math.min(...topo);
      const inicio = topo.findIndex((t) => t <= nivel + TOLERANCIA);
      let fim = inicio;
      while (fim + 1 < COLUNAS && topo[fim + 1] <= nivel + TOLERANCIA) {
        fim++;
      }
      const livre = fim - inicio + 1;

      if (livre === 1) {
        // Uma coluna só não comporta quadro: o quadro de cima vai esticar até o vizinho mais baixo.
        const vizinhos = [topo[inicio - 1], topo[inicio + 1]].filter((t) => t !== undefined);
        topo[inicio] = Math.min(...vizinhos);
        continue;
      }

      // Se a próxima postagem não cabe, uma das seguintes que caiba pode subir para este vão.
      let indice = 0;
      if (fila[0].desejo > livre) {
        const quemCabe = fila.slice(0, OLHAR_ADIANTE + 1).findIndex((item) => cabe(item.desejo, livre));
        if (quemCabe >= 0) {
          indice = quemCabe;
        } else if (nivel > 0) {
          // Nenhuma cabe. Se o vão for baixo, o quadro de cima estica para fechá-lo (com a foto
          // centralizada); se for alto, esticar deixaria um espaço vazio enorme, então a postagem encolhe.
          const vizinhos = [topo[inicio - 1], topo[fim + 1]].filter((t) => t !== undefined);
          const ate = Math.min(...vizinhos);
          if (ate - nivel <= VAO_MAXIMO) {
            for (let c = inicio; c <= fim; c++) {
              topo[c] = ate;
            }
            continue;
          }
        }
        // Na primeira linha (ou num vão alto) não há como esticar: a postagem encolhe para caber.
      }
      const { card, desejo } = fila.splice(indice, 1)[0];
      let span = Math.min(desejo, livre);
      // O que sobrar ao lado precisa ser preenchido por uma das próximas postagens. Se nenhuma couber,
      // tenta dividir o trecho ao meio (duas fotos largas lado a lado); se nem assim, o quadro ocupa o
      // trecho todo, em vez de deixar um buraco. A foto continua inteira, só muda de tamanho.
      const proximas = fila.slice(0, OLHAR_ADIANTE);
      const alguemCabe = (sobra) => proximas.some((item) => cabe(item.desejo, sobra));
      if (span < livre && !alguemCabe(livre - span)) {
        const metade = Math.ceil(livre / 2);
        span = cabe(desejo, metade) && alguemCabe(livre - metade) ? metade : livre;
      }

      const y = Math.max(...topo.slice(inicio, inicio + span));
      soltar(card);
      card.style.width = `${span * coluna + (span - 1) * vao}px`;
      card.style.left = `${inicio * (coluna + vao)}px`;
      card.style.top = `${y}px`;
      const altura = card.offsetHeight;
      for (let c = inicio; c < inicio + span; c++) {
        topo[c] = y + altura + vao;
      }
      postos.push({ card, x: inicio, span, y, altura });
    }

    // Fecha os vãos que sobraram: cada quadro estica até o quadro de baixo (ou até o fim do mosaico).
    const fundo = Math.max(...postos.map((p) => p.y + p.altura));
    for (const p of postos) {
      let limite = fundo;
      for (const q of postos) {
        const mesmaColuna = q.x < p.x + p.span && p.x < q.x + q.span;
        if (q !== p && mesmaColuna && q.y > p.y) {
          limite = Math.min(limite, q.y - vao);
        }
      }
      if (limite - p.y > p.altura + 1) {
        p.card.style.height = `${limite - p.y}px`;
      }
    }
    mosaico.style.height = `${fundo}px`;
  }

  let agendado = false;
  function remontar() {
    if (!agendado) {
      agendado = true;
      requestAnimationFrame(() => {
        agendado = false;
        montar();
      });
    }
  }

  montar();
  // Refaz quando a largura muda, quando as fontes chegam e quando uma foto carrega.
  new ResizeObserver(() => {
    if (mosaico.clientWidth !== larguraMontada) {
      remontar();
    }
  }).observe(mosaico);
  document.fonts?.ready.then(remontar);
  mosaico.querySelectorAll(".post-photo img").forEach((foto) => {
    if (!foto.complete) {
      foto.addEventListener("load", remontar, { once: true });
    }
  });
  window.comunidadeRemontar = remontar;
})();

// Postagens antigas não têm o tamanho da foto salvo no banco: descobre quando a foto carrega,
// para o quadro também ganhar o formato dela (veja --proporcao no comunidade.css).
document.querySelectorAll(".community-post--com-foto").forEach((card) => {
  if (card.style.getPropertyValue("--proporcao")) {
    return;
  }
  const foto = card.querySelector(".post-photo img");
  const aplicar = () => {
    if (foto.naturalWidth && foto.naturalHeight) {
      card.style.setProperty("--proporcao", (foto.naturalWidth / foto.naturalHeight).toFixed(4));
      window.comunidadeRemontar?.();
    }
  };
  if (foto.complete) {
    aplicar();
  } else {
    foto.addEventListener("load", aplicar, { once: true });
  }
});
