# Redesign da home V3 (tema Spore) — Design curto

Data: 2026-11-27 · Branch: `redesign-da-home-v3` (a partir de `teste-de-skill-de-design`)

## Intenção

Explorar na home um tema que **não** seja o Hum. O usuário escolheu o **Spore** (do Hallmark): uma adega à
noite, chão de café escuro aquecido, pergaminho como tinta, um único rosa-ostra, fotos que parecem iluminadas
por lâmpada. Comparar com a V1 e a V2 (Hum). Resultado conferido por capturas, pelo celular.

## Objetivos

1. Home reformulada por inteiro no sistema Spore (tokens, superfícies, controles, movimento).
2. **Ordem fixa:** Biomas → Itens → Insígnias → botão da Steam. "Sobre o jogo" pode mudar de lugar.
3. **Rodapé idêntico**; **botão da Steam intocado** (só posição/largura); **carrossel e janelas no visual
   original**.
4. Sem rolagem horizontal em 360, 390 e 1280px.

## Não-objetivos

`site.css`, `item-dialog.css`, `header.html`, `footer.html`, `biomes.js`, `items.js`, back-end e as outras
páginas não mudam. Sem texto, número ou imagem inventados.

## Anti-objetivos

Parecer "modo escuro com brilho" ou rótulo de bem-estar; segundo matiz (só o rosa); luz desenhada (feixes,
brilhos); rosa em texto corrido; foto atrás de foto sem texto no meio.

## Restrições

- **Fontes só Darumadrop One, Bangers e Nunito Sans** (CLAUDE.md). O Spore pede Young Serif (serifada) e
  Instrument Sans; ficam **Darumadrop One** no lugar da serifada (títulos, uma só espessura) e **Nunito Sans**
  no lugar da Instrument Sans. A elegância serifada se perde; a hierarquia vem só do tamanho.
- Fios mantidos: ids, classes e `data-*` de `biomes.js`/`items.js` e dos testes (`class="biomes-card"` exato,
  `#biomas`, `#itens`, `#insignias`, `.biome-slide-name`, "Fórum da Comunidade", "Sobre o jogo", etc.).
- Hallmark: tokens em `:root`, foco visível, `prefers-reduced-motion`, sem eyebrow nem rótulo em caixa-alta
  espaçada, carimbo (`spore: cellar`), `sloplint` sem FAIL.

## Abordagens consideradas

1. **CSS novo por cima do `site.css` (escolhida, como nas V1 e V2).** Menor risco para JS, testes e botão da Steam.
2. Reescrever o markup: mais liberdade, mas arrisca JS, testes e o botão da Steam.

## Decisões (minhas, a corrigir)

- **Colourway `cellar`** (o padrão do tema): chão `oklch(15% 0.012 60)`, tinta pergaminho, acento rosa-ostra.
  Todo degrau escuro tinge para o matiz 60 (nada de preto azulado).
- **Cabeçalho novo** (fragmento `fragments/spore-header.html`): pílula centralizada e flutuante, como no
  desenho do tema, com o logo (branco, já legível no escuro), "Fórum da Comunidade" e "Entrar / Cadastrar"
  (esse contornado em pílula).
- **Primeira tela (Stage):** título "WIKI DO PEAK" enorme à esquerda, sem frase (não adiciono texto); a foto de
  Biomas à direita, sangrando pela borda, com o botão "Biomas" em pílula rosa sobre a foto.
- **Itens e Insígnias como uma lista de linhas separadas por fio de 1px** (a "prateleira" do tema): o nome
  gigante à esquerda e a foto arredondada à direita; no hover a linha sobe um degrau de tom.
- **Sobre o jogo:** um poço granulado (`paper-3` com granulado) com o texto do jogo em tamanho grande, como
  declaração, e a imagem atual arredondada ao lado.
- **Fotos:** tratamento quente e um pouco mais escuro por CSS (`filter`), sem alterar os arquivos.
- **Steam:** botão intocado, centralizado sobre o chão escuro; depois, o rodapé idêntico (cinza-neutro, como é hoje).
- Movimento: só estado (hover/foco), sem revelar nem animar.

## A cargo de quem constrói

Tamanhos, proporções, o granulado, os estados de hover/foco e como anular as regras antigas do `site.css`.

## Verificação

`sloplint` (0 FAIL nos arquivos novos), capturas em 360/390/1280px, carrossel e janela de itens abertos num
pré-visualizador estático, conferência de que o botão da Steam, o rodapé e as janelas não mudaram, e
`mvnw test` se o Java 25 existir.

## Commits

1. Este documento. 2. A home Spore.

## Revisão 1

- **Itens invertido:** a linha de Itens passa a ter a foto à esquerda e o nome à direita; Insígnias mantém o
  nome à esquerda e a foto à direita (zigue-zague). No celular as duas voltam a nome e foto empilhados.
- **Fundo do PEAK de volta:** a imagem do site (`background.webp`, a do `body::before` do `site.css`) volta como
  fundo **fixo** (não rola), bem escurecida e aquecida (`filter` + uma camada de `--color-scrim`, derivada do chão
  `paper`), para continuar dentro da paleta quente do Spore.
