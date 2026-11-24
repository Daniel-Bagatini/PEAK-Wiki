# Atualização de conteúdo do jogo — Plano de implementação

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Trazer a PEAK Wiki para o estado atual do jogo: biomas Raízes, Melancolia e Cidadela; comidas, equipamentos e insígnias novos; dados atuais corrigidos — tudo em português e no padrão visual do site.

**Architecture:** Os dados saem dos controllers para classes Java próprias (`Biomes`, `FoodCatalog`, `EquipmentCatalog`, `Badges`) com os mesmos `record`s de hoje. Os templates Thymeleaf existentes são reaproveitados; `biome-detail.html` ganha a seção "ESTRUTURAS DO BIOMA" e a home troca o SVG fixo de indicadores por bolinhas CSS. Imagens vêm da `peak.wiki.gg`, convertidas para WebP por um script fora do projeto.

**Tech Stack:** Java 25, Spring Boot 4, Thymeleaf, Bootstrap 5.3.3, JUnit 5 + MockMvc, Python 3 + Pillow (só para conversão de imagens).

**Spec:** `docs/superpowers/specs/2026-11-20-atualizacao-conteudo-jogo-design.md`

## Global Constraints

- Sempre `<div th:replace="~{fragments/header :: siteHeader}">` e `~{fragments/footer :: siteFooter}`; nunca recriar.
- Sempre `th:href="@{...}"`/`th:src="@{...}"`; nenhum caminho fixo em template.
- Bootstrap 5.3.3 via jsdelivr; `site.css` carregado antes do CSS da página.
- Fontes: só Darumadrop One, Bangers e Nunito Sans.
- Toda imagem nova é **WebP**, com nome descritivo em minúsculas e hífen (ex.: `minhoca-madrugadora.webp`).
- Todo texto visível em **português**, traduzido e resumido (nunca colado da wiki). Decimais com vírgula (`2,5`).
- Nomes já usados no site são mantidos (Crispberry → Maçã, Kingberry → Amora/Amora silvestre, Berrynana → Banana, Clusterberry → Amora laranja/vermelha/preta, Winterberry → Laranja, Prickleberry → Espinheiro, Scorchberry → Pimenta).
- Slugs novos: `raizes`, `melancolia`, `cidadela`. Nomes exibidos: "Raízes", "Melancolia", "Cidadela".
- Ordem dos biomas (campo `number` 1–10): Costa, Trópico, Raízes, Alpes, Mesa, Caldeira, Melancolia, O Forno, Cidadela, Pico.
- **Sem commits.** O usuário revisa antes e só commita quando pedir. Cada tarefa termina com os testes verdes, não com commit.
- Fonte de verdade dos dados: as páginas da `peak.wiki.gg` listadas no spec, relidas no momento da tarefa.
- Itens que a wiki marca como removidos do jogo saem; itens que só não aparecem na wiki ficam. Ambos vão para a lista final do usuário.
- Rodar testes: `.\mvnw.cmd test` (PowerShell, na raiz do projeto).

## Review Focus

1. **Celular a 375px:** a fileira de 10 bolinhas do carrossel cabe no diálogo sem rolagem horizontal → coberto na Tarefa 2 (verificação no navegador) e na Tarefa 10.
2. **Volta do Pico para a Costa (e vice-versa) com 10 biomas:** a bolinha amarela acompanha o slide certo pelo teclado e pelas setas → Tarefa 2, passo de navegador.
3. **Bioma sem comida natural (Cidadela):** a página não mostra título de consumíveis vazio nem legenda de efeitos → teste `cidadelaHasNoFoodSection` na Tarefa 5.
4. **Toda imagem citada nas páginas novas existe:** nenhum `src`/`url()` quebrado em biomas novos, catálogos e insígnias → `allHomepageAndBiomeImageSourcesResolve` ampliado nas Tarefas 3–5 e `everyBadgeImageIsServedLocally` na Tarefa 8.
5. **Bioma com seção de estruturas ausente (biomas antigos):** nenhum título "ESTRUTURAS DO BIOMA" vazio aparece → teste `oldBiomesHaveNoFeaturesSection` na Tarefa 3.

---

### Task 0: Script de download e conversão para WebP

**Files:**
- Create: `<scratchpad>/tools/wiki_webp.py` (fora do projeto; `<scratchpad>` = diretório de rascunho da sessão)

**Interfaces:**
- Produces: `python -I <scratchpad>/tools/wiki_webp.py <url-da-imagem> <caminho-destino.webp> [--max 512]` — baixa a imagem original (não a miniatura) para `<scratchpad>/downloads/`, converte para WebP (qualidade 85, mantendo transparência, redimensionando o lado maior para no máximo `--max` px) e grava no destino. Imprime tamanho final.

- [ ] **Step 1:** Criar venv em `<scratchpad>/venv` e instalar Pillow (`python -m venv`, `pip install pillow`). Esperado: `python -c "import PIL"` sem erro dentro do venv.
- [ ] **Step 2:** Escrever o script. URL original: `https://peak.wiki.gg/images/<Arquivo>` (sem `/thumb/`). User-Agent genérico (sem dados do usuário). Downloads ficam numa pasta própria e são só lidos pelo Pillow.
- [ ] **Step 3:** Testar com `Hot_Dog.png` → `<scratchpad>/teste/cachorro-quente.webp`. Esperado: arquivo WebP válido (`PIL.Image.open(...).format == "WEBP"`). Se a wiki bloquear o download (403/Cloudflare), parar e avisar o usuário.

---

### Task 1: Tirar os dados dos controllers (sem mudar comportamento)

**Files:**
- Create: `src/main/java/Trabalho/Web/Biomes.java`, `FoodCatalog.java`, `EquipmentCatalog.java`, `Badges.java`
- Modify: `PageController.java`, `ItemCatalogController.java`, `BadgeController.java`
- Test: `src/test/java/Trabalho/Web/WebApplicationTests.java` (sem mudanças; deve continuar verde)

**Interfaces:**
- Produces:
  - `final class Biomes { static final List<Biome> ALL; static Optional<Biome> bySlug(String slug); }` com os records `Biome`, `Food`, `FoodEffect`, `Hazard`, `ArrivalSection` e os helpers `food(...)`/`hazard(...)` movidos de `PageController`.
  - `final class FoodCatalog { static final ItemCatalog CATALOG; }` e `final class EquipmentCatalog { static final ItemCatalog CATALOG; }`; records `ItemCatalog`, `ItemSection`, `CatalogItem` e os helpers `food(...)`, `gear(...)`, `biomeImage(...)` vão para um arquivo `ItemCatalogs.java` (compartilhado pelos dois).
  - `final class Badges { static final List<Badge> ALL; }` com o record `Badge`.
  - Controllers só leem dessas classes.

- [ ] **Step 1:** Rodar `.\mvnw.cmd test` antes de mexer. Esperado: todos verdes (linha de base).
- [ ] **Step 2:** Mover dados, records e helpers para as classes acima; controllers ficam só com as rotas.
- [ ] **Step 3:** Rodar `.\mvnw.cmd test`. Esperado: todos verdes, sem alterar nenhum teste.

---

### Task 2: Indicadores do carrossel em CSS

**Files:**
- Modify: `src/main/resources/templates/index.html:50-56`, `src/main/resources/static/css/site.css:394-448` (e o bloco de celular correspondente, se houver), `src/main/resources/static/biomes.js:43`
- Test: `WebApplicationTests.java`

**Interfaces:**
- Produces: marcação `<div class="biome-indicators">` sem `<img>`; cada `<button data-biome-indicator="i">` é a bolinha. A bolinha amarela `.biome-indicator-current` continua existindo e é posicionada pelo JS com `indicators[selectedIndex].offsetLeft`.

- [ ] **Step 1: Teste que falha** — em `rendersHomeWithSharedHeaderAndBiomeCarousel`, acrescentar:
  ```java
  .andExpect(content().string(org.hamcrest.Matchers.not(containsString("indicadores.svg"))))
  ```
  e um teste novo `rendersOneIndicatorPerBiome`: contar `data-biome-indicator=` no HTML da home e comparar com `Biomes.ALL.size()`.
- [ ] **Step 2:** Rodar os testes. Esperado: FALHA por causa do `indicadores.svg`.
- [ ] **Step 3:** No `index.html`, remover o `<img>` do SVG. No `site.css`, trocar a largura fixa de 232px e os 7 `nth-of-type` por `display:flex` com `gap: var(--indicator-gap, 13px)` (22px + 13px = o passo de 35px atual). Botões de 22px com fundo `#9a9583` (cinza das bolinhas do SVG, conferir a cor exata em `indicadores.svg`) e a amarela `#ddca40` por cima, posicionada absolutamente. Abaixo de 420px de largura, reduzir `--indicator-gap` para caber 10 bolinhas. No `biomes.js`, trocar `selectedIndex * 35` pelo `offsetLeft` do botão selecionado. Remover `biome-indicator-first` se ficar redundante (ajustar o JS).
- [ ] **Step 4:** Rodar os testes. Esperado: todos verdes.
- [ ] **Step 5: Navegador** — `.\mvnw.cmd spring-boot:run`, abrir http://localhost:8080, abrir o diálogo de biomas: (a) as bolinhas parecem as de antes; (b) setas, teclado e clique nas bolinhas movem a amarela para o lugar certo, inclusive ao dar a volta do último para o primeiro; (c) a 375px de largura a fileira cabe sem rolagem horizontal.

---

### Task 3: Bioma Raízes + seção "ESTRUTURAS DO BIOMA"

**Files:**
- Modify: `Biomes.java`, `templates/biome-detail.html` (nova seção entre "PERIGOS DO BIOMA" e "A CHEGADA"), `static/css/biome-detail.css`
- Create: `static/images/biome-pages/raizes/*.webp`, `static/images/biomes/raizes.webp`
- Test: `WebApplicationTests.java`

**Interfaces:**
- Consumes: `Biomes` (Tarefa 1), script (Tarefa 0).
- Produces:
  - `record Feature(String title, String description, String image)`.
  - `Biome` ganha o último componente `List<Feature> features`; biomas antigos recebem `List.of()`.
  - `hazard(...)` aceita os efeitos novos `ESPOROS`, `SONOLÊNCIA`, `TEIA`, `PETRIFICAÇÃO`, `FLECHA`, mapeados para as classes CSS `spores`, `sleep`, `web`, `petrify`, `arrow` (cores escolhidas pelo implementador dentro da paleta do `biome-detail.css`).
  - Seção: `<section class="biome-features" aria-labelledby="biome-features-title" th:if="${!#lists.isEmpty(biome.features)}">` com `<h2 id="biome-features-title">ESTRUTURAS DO BIOMA</h2>` e cartões `article.danger-row` (`img.danger-image` + `div.danger-copy` com `h3` e `p`, **sem** `.hazard-effect`).

- [ ] **Step 1: Testes que falham**
  - Em `rendersBiomePagesFromTheSharedTemplate`, acrescentar `{ "raizes", "Raízes" }`; em `rendersSharedFooterDiscordLinksOnEveryPage` e `allHomepageAndBiomeImageSourcesResolve`, acrescentar `/biomas/raizes`.
  - Novo `rendersRaizesFeaturesSection`: GET `/biomas/raizes` contém `"ESTRUTURAS DO BIOMA"`, `"Vento"`, `"Zumbis"`, `"Sequoias"`, `"Mandrágora"`.
  - Novo `oldBiomesHaveNoFeaturesSection`: para os 7 slugs antigos, o HTML **não** contém `"ESTRUTURAS DO BIOMA"`.
  - Novo `homeListsBiomesInGameOrder`: na home, `html.indexOf("/biomas/tropico") < html.indexOf("/biomas/raizes") < html.indexOf("/biomas/alpes")`.
- [ ] **Step 2:** Rodar. Esperado: FALHA (404 em `/biomas/raizes`).
- [ ] **Step 3: Imagens** — com o script, gerar em `images/biome-pages/raizes/`: arte do título (`Roots_Location.jpg`), uma por perigo e uma por estrutura (nomes de arquivo da wiki listados no spec/relatório: `Roots_wind.jpg`, `Roots_Bridge.png`, `Roots_spores_outside.jpg`, `Evil_Bounce_Fungus.png`, `Roots_bombshroom.jpg`, `Spore_Bomb_-_explosive_-_closeup.png`, `Beetle.jpg`, `Spider_closeup.jpg`, `Zombie_run.jpg`, `Roots_redwood_tree.png`, `Roots_stump.png`, `Roots_vines.png`, `Roots_shelf_fungus.png`, `Roots_red_shroom.jpg`, `Roots_water.png`, `Roots_waterfall.png`) e os ícones de comida que ainda não existem no site (5 Shroomberries, `Mandrake.png`). Copiar `biome-pages/costa/imgImage167.webp` para `biome-pages/raizes/imgImage167.webp` (fundo; o teste exige esse caminho literal). Card do carrossel: `images/biomes/raizes.webp`, a partir de `Roots_top.jpg`, com `--max 900`.
- [ ] **Step 4: Dados** — adicionar Raízes em `Biomes.ALL` na posição 3 (renumerar os seguintes), com: descrição traduzida (floresta de sequoias tomada por cogumelos, 2º trecho, alternativa ao Trópico; vento como mecânica principal); comidas (5 Shroomberries, Cenoura medicinal, Mandrágora, cogumelos botão/trombeta/em grupo/gordinho com variante venenosa, Favo de mel); 9 perigos e 7 estruturas conforme o spec; `foodEffects()` para `raizes` (veneno, energia, esporos). Card: `cardImagePath()` precisa achar `raizes.webp` — se o campo `image` hoje aceita só o nome do arquivo, passar `"raizes.webp"`.
- [ ] **Step 5: Template e CSS** — adicionar a seção (Interfaces acima). No CSS, `.biome-features` reaproveita as regras de `.biome-dangers` (agrupar seletores) e define as cores das 5 classes de efeito novas em `.hazard-effect.effect-*` e `.effect-dot.effect-*`.
- [ ] **Step 6:** Rodar os testes. Esperado: todos verdes.
- [ ] **Step 7: Navegador** — abrir `/biomas/raizes` em desktop e 375px: título com moldura igual aos outros biomas, cartões de estrutura com a mesma aparência dos de perigo, sem etiqueta de efeito.

---

### Task 4: Bioma Melancolia

**Files:**
- Modify: `Biomes.java`
- Create: `static/images/biome-pages/melancolia/*.webp`, `static/images/biomes/melancolia.webp`
- Test: `WebApplicationTests.java`

**Interfaces:**
- Consumes: `Feature`, `hazard(...)` com efeitos novos e a seção de estruturas (Tarefa 3).

- [ ] **Step 1: Testes que falham** — acrescentar `{ "melancolia", "Melancolia" }` / `/biomas/melancolia` nos três testes de rota; novo `rendersMelancoliaContent`: contém `"Névoa sonolenta"`, `"Fantasma gigante"`, `"Sinos"`, `"Minhoca Madrugadora"`, `"Cachorro-quente"`; ordem na home `caldeira < melancolia < o-forno`.
- [ ] **Step 2:** Rodar. Esperado: FALHA (404).
- [ ] **Step 3: Imagens** — arte do título `Gloom.png`; perigos `Sleepy_gloom.png`, `Gloom_water.png`, `Flytrap.png`, `Big_ghost_(hiquality).png`, `Frog.png`, `Tricky_raven.png`, `Fencing.png`; estruturas `Belltower_Lit.png`, `Gloom_Early_Worm.png`, `Gloom_lilypad.png`, `Gloom_Statue.jpg`, `Gloom_Nest.png`, `GloomBananaVines.png` e uma imagem da entrada da Cidadela (se a wiki não tiver uma própria, usar `Gloom_WallTexture.jpg`); ícones de comida que faltam (`The_Early_Worm.png`, `Hot_Dog.png`, `Frog_Legs.png`); fundo `imgImage167.webp` copiado; card `melancolia.webp`.
- [ ] **Step 4: Dados** — Melancolia na posição 7: descrição (pântano assombrado sob névoa roxa, 4º trecho, alternativa à Caldeira, leva à Cidadela; acender a fogueira da entrada libera a Cidadela); comidas (Ovo, Frango, Minhoca Madrugadora, Cachorro-quente, Banana amarela, Pernas de Sapo); 7 perigos (efeitos: SONOLÊNCIA, DANO, VENENO, FRIO, DANO, DANO, DANO — conferir na wiki); 7 estruturas; `foodEffects()` para `melancolia`.
- [ ] **Step 5:** Rodar os testes. Esperado: todos verdes.

---

### Task 5: Bioma Cidadela

**Files:**
- Modify: `Biomes.java`
- Create: `static/images/biome-pages/cidadela/*.webp`, `static/images/biomes/cidadela.webp`
- Test: `WebApplicationTests.java`

- [ ] **Step 1: Testes que falham** — acrescentar `{ "cidadela", "Cidadela" }` / `/biomas/cidadela` nos três testes de rota; novo `rendersCidadelaContent`: contém `"Armadilhas de flecha"`, `"Caldeirão de lava"`, `"Escadas douradas"`; novo `cidadelaHasNoFoodSection`: não contém `"CONSUMÍVEIS NATURAIS DO BIOMA"` nem `food-effect-legend`; ordem na home `o-forno < cidadela < pico`; e `Biomes.ALL` tem 10 itens com `number` 1..10 na ordem do spec (teste unitário direto na lista).
- [ ] **Step 2:** Rodar. Esperado: FALHA (404).
- [ ] **Step 3: Imagens** — arte do título `Citadel.jpg`; perigos `Arrow_Trap.png`, `SpikeTrap.png`, `SwingingMace.png`, `Sawblade.png`, `MimicLuggage.png`, `PetrifyingRocks.png`, `RotatingVerticalCrusher.png`, `RotatingHorizontalCrushers.png`, `Crucible_of_lava.png`; estruturas: correntes douradas (a página da Cidadela não tem foto própria; usar `Citadel_top.png` ou outra da galeria e avisar na lista final), `Citadel_Ladder.png`, `Lamp_Citadel.png`, `Golden_scout_statue.png`, escadarias e janelas (mesma regra das correntes); fundo copiado; card `cidadela.webp`.
- [ ] **Step 4: Dados** — Cidadela na posição 9: descrição (interior de uma torre cheia de armadilhas, 5º trecho, só depois da Melancolia, alternativa ao Forno; sem fogueira no fim, leva direto ao Pico; na ascensão "Pico" ou maior a névoa sonolenta sobe); `foods` = `List.of()`; 9 perigos (FLECHA, DANO, DANO, DANO, SONOLÊNCIA, PETRIFICAÇÃO, DANO, DANO, QUEIMADURA); 6 estruturas; `foodEffects()` vazio.
- [ ] **Step 5:** Rodar os testes. Esperado: todos verdes.

---

### Task 6: Catálogo de comidas (correções + novas) e listas de comida dos biomas antigos

**Files:**
- Modify: `FoodCatalog.java`, `Biomes.java` (só listas `foods`)
- Create: `static/images/items/food/<categoria>/*.webp` das comidas novas
- Test: `WebApplicationTests.java`

- [ ] **Step 1: Testes que falham** — novo `foodCatalogMatchesWiki`: GET `/itens/comidas` contém `"Cachorro-quente"`, `"Mandrágora"`, `"Minhoca Madrugadora"`, `"Pernas de sapo"`, `"Leite fortificado"`, `"Livro dos Ossos"`, `"Banana marrom"`; **não** contém `"Banana laranja"`; a linha da Napberry contém `"-100 fome"`. Ajustar `everyCatalog...` (o `assertEquals(30, assetCount)`) para o novo total se for o teste de contagem de imagens.
- [ ] **Step 2:** Rodar. Esperado: FALHA.
- [ ] **Step 3: Imagens** — baixar os ícones que faltam (Shroomberries e Mandrágora já vieram na Tarefa 3; Cachorro-quente, Minhoca e Pernas de sapo na Tarefa 4 — reaproveitar copiando para `items/food/...` ou apontando o caminho; evitar duplicar arquivos sem necessidade): `Fortified_Milk.png` → `embalados/leite-fortificado.webp`, `The_Book_of_Bones.png` → `miticos/livro-dos-ossos.webp`, `Napberry`, `Big_Egg`, etc. só se o ícone atual estiver ausente ou errado.
- [ ] **Step 4: Dados** — reler https://peak.wiki.gg/wiki/Food e: (a) aplicar todas as correções listadas no objetivo 6 do spec; (b) adicionar as comidas novas em Naturais (Cachorro-quente, Mandrágora, 5 Shroomberries, Minhoca Madrugadora, Pernas de sapo), Embalados (Leite fortificado) e Míticos (Livro dos Ossos); (c) colocar Raízes e Melancolia nos `locations` corretos; (d) corrigir em `Biomes.java` as listas `foods` dos biomas antigos onde a wiki diverge (só dados).
- [ ] **Step 5:** Rodar os testes. Esperado: todos verdes.

---

### Task 7: Catálogo de equipamentos (correções + novos)

**Files:**
- Modify: `EquipmentCatalog.java`
- Create: `static/images/items/equipamentos/*.webp` dos itens novos
- Test: `WebApplicationTests.java`

- [ ] **Step 1: Testes que falham** — novo `equipmentCatalogMatchesWiki`: GET `/itens/equipamentos` contém `"Planador"`, `"Castiçal"`, `"Canhão de escoteiro"`, `"Adaga ritual"`, `"Ídolo ancião"`, `"Remédio"`, `"Efígie"`; não contém `"2.25"` nem `"Efíngie"`. Manter as asserções de seção existentes.
- [ ] **Step 2:** Rodar. Esperado: FALHA.
- [ ] **Step 3: Dados e imagens** — reler https://peak.wiki.gg/wiki/Equipment (confirmando os itens depois de "Scout's Ambition", que vieram só de resumo). Corrigir pesos/descrições do spec; adicionar cada item novo numa das seções existentes (`cura`, `especiais`, `coletivos`, `individuais`, `miticos`) com imagem `<nome-em-portugues>.webp`. Itens removidos do jogo não entram.
- [ ] **Step 4:** Rodar os testes. Esperado: todos verdes.

---

### Task 8: Insígnias (correções + 40 novas)

**Files:**
- Modify: `Badges.java`
- Create: `static/images/badges/<nome>.webp`
- Test: `WebApplicationTests.java`

- [ ] **Step 1: Testes que falham** — novo `rendersAllBadges`: GET `/insignias` tem exatamente 64 ocorrências de `class="badge-card"`; contém `"Andarilho"` (Wanderer — ou a tradução escolhida), `"Exorcista"`, `"Náufrago"`, `"Escape da ilha sem comer comida embalada"`; não contém `"Naufrágo"`, `"tal coisa"`, `"CHAPADA"`. Novo `everyBadgeImageIsServedLocally`: todo `src="/images/..."` da página responde 200.
- [ ] **Step 2:** Rodar. Esperado: FALHA.
- [ ] **Step 3: Dados e imagens** — reler https://peak.wiki.gg/wiki/Badges (inteira; as de 45–64 vieram só de resumo). Corrigir as 24 atuais (spec, objetivo 6) e adicionar as 40 que faltam, com ícone `<Nome>_Badge.png` → `badges/<nome-em-portugues>.webp`, `baseImage = null` e `imageSize` coerente com as atuais (~110–160).
- [ ] **Step 4:** Rodar os testes. Esperado: todos verdes.

---

### Task 9: Crédito da wiki no rodapé

**Files:**
- Modify: `templates/fragments/footer.html`, `static/css/site.css`
- Test: `WebApplicationTests.java`

- [ ] **Step 1: Teste que falha** — em `rendersSharedFooterDiscordLinksOnEveryPage`, cada rota contém `"Informações e imagens adaptadas da PEAK Wiki"` e `href="https://peak.wiki.gg"`.
- [ ] **Step 2:** Rodar. Esperado: FALHA.
- [ ] **Step 3:** Adicionar ao fim do `<footer>` uma linha pequena: "Informações e imagens adaptadas da PEAK Wiki (wiki.gg), CC BY-SA 4.0", com link para `https://peak.wiki.gg` e para a licença `https://creativecommons.org/licenses/by-sa/4.0/` (`target="_blank" rel="noopener noreferrer"`), em Nunito Sans, cor discreta, ocupando a largura toda.
- [ ] **Step 4:** Rodar os testes. Esperado: todos verdes.

---

### Task 10: Verificação final e relatório

- [ ] **Step 1:** `.\mvnw.cmd test`. Esperado: BUILD SUCCESS, 0 falhas.
- [ ] **Step 2: Navegador** (desktop e 375px): home (carrossel com 10 biomas na ordem certa, bolinhas), as 3 páginas novas, `/itens/comidas`, `/itens/equipamentos`, `/insignias`, rodapé. Nenhuma imagem quebrada, nenhum texto em inglês, nenhuma rolagem horizontal.
- [ ] **Step 3:** Conferir que todo arquivo novo em `static/images/` é `.webp` (`Get-ChildItem -Recurse` filtrando extensões ≠ `.webp`/`.svg` entre os arquivos novos).
- [ ] **Step 4: Relatório ao usuário** — (a) traduções novas escolhidas; (b) itens removidos por estarem marcados como removidos na wiki e itens mantidos que a wiki não lista; (c) imagens substitutas usadas onde a wiki não tinha uma própria; (d) lembrete de que nada foi commitado.
