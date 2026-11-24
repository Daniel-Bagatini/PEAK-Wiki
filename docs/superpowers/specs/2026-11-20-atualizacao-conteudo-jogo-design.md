# Atualização de conteúdo do jogo (biomas, itens e insígnias) — Design

Data: 2026-11-20 · Branch: `atualizacao-para-as-novas-versoes`

## Intenção

O site PEAK Wiki está desatualizado em relação ao jogo. O objetivo é trazer o conteúdo
para o estado atual do jogo — **o que a wiki diz hoje**, sem corte por versão —, **em português**, usando o mesmo padrão
visual e técnico das páginas que já existem, para que tudo fique em harmonia.

Fonte de verdade: a wiki em inglês `peak.wiki.gg` (licença CC BY-SA 4.0):

- https://peak.wiki.gg/wiki/Roots → bioma **Raízes**
- https://peak.wiki.gg/wiki/Gloom → bioma **Melancolia**
- https://peak.wiki.gg/wiki/The_Citadel → bioma **Cidadela**
- https://peak.wiki.gg/wiki/Food → comidas
- https://peak.wiki.gg/wiki/Equipment → equipamentos
- https://peak.wiki.gg/wiki/Badges → insígnias

## Objetivos

1. **3 biomas novos** com página própria (`/biomas/raizes`, `/biomas/melancolia`, `/biomas/cidadela`),
   usando o template `biome-detail.html`: título, descrição, consumíveis naturais, perigos e a
   **seção nova "ESTRUTURAS DO BIOMA"**.
2. **Carrossel da home na ordem do jogo** (igual à wiki):
   Costa, Trópico, **Raízes**, Alpes, Mesa, Caldeira, **Melancolia**, O Forno, **Cidadela**, Pico.
   Os números (`number`) dos biomas passam a seguir essa ordem (1–10).
3. **Comidas novas** no catálogo `/itens/comidas`, entre elas: Cachorro-quente, Mandrágora, os 5 Shroomberries,
   Minhoca Madrugadora (The Early Worm), Pernas de Sapo, Leite Fortificado (embalado),
   Livro dos Ossos (mítico).
4. **Equipamentos novos** no catálogo `/itens/equipamentos` (o site tem 31, a wiki ~60).
5. **Insígnias novas** em `/insignias` (o site tem 24, a wiki tem 64 → 40 novas).
6. **Correção dos itens já existentes** (comidas, equipamentos e insígnias) para bater com a wiki.
   O usuário confirmou que os dados atuais estão apenas desatualizados. Divergências já
   identificadas (lista não exaustiva — o builder deve conferir item a item):
   - Comidas: Meio coco (cozido -20 fome; remove 30 calor), Maçã verde (cru -5 fome),
     Amoras silvestres amarela/verde (veneno 25/5), Pimenta, Favo de mel (50/75 energia),
     Banana rosa (veneno 15; cozida -30), "Banana laranja" → **Banana marrom** (cozida 15 energia),
     Amora laranja (cozida -20/15), Amora vermelha (cura 20 dano), Laranja (remove 30 calor),
     Laranja amarela (remove 50 calor, cura 20 dano, também na Mesa), **Napberry** (efeito errado:
     é -100 fome e remove todos os efeitos exceto maldição/petrificação), Espinheiros,
     Cenoura medicinal (efeitos valem crua; também nas Raízes), Ovo/Frango (também na Melancolia),
     Barra de Granola (peso 2,5), Comida de avião (peso 5), Bebida esportiva (30/45 energia),
     Cookies (4 usos), Energético, Pirulito grande, Cogumelos em grupo (cozido -10),
     cogumelos também nas Raízes, Cura-tudo (-5 maldição), Lancheira de Pandora (também Estátua).
   - Equipamentos: pesos 2.25 → **2,5**; padronizar decimais com vírgula; Bolsa Térmica, Antídoto,
     Zarabatana e Lanterna de fada com descrições incompletas; "Remedio" → "Remédio",
     "Efíngie" → "Efígie".
   - Insígnias: Naturalista (texto provisório), Pico (o Pico agora vem depois do Forno ou da
     Cidadela), 24 Quilates (vale também na Cidadela), "CHAPADA" → **Mesa** (padronizar com o
     resto do site), "Naufrágo" → "Náufrago".
   - Páginas dos biomas existentes: listas de consumíveis que mudaram (ex.: Trópico e Raízes
     compartilham Favo de mel; Ovo/Frango também na Melancolia) — corrigir só dados, não o layout.
7. **Crédito no rodapé** (`fragments/footer.html`): uma linha como
   "Informações e imagens adaptadas da PEAK Wiki (wiki.gg), CC BY-SA 4.0", com link para a wiki.
8. **Testes atualizados**: `WebApplicationTests` cobre as rotas novas (incluindo o teste do rodapé
   em todas as páginas) e a nova ordem do carrossel na home.

## Não objetivos

- Adicionar a seção "ESTRUTURAS DO BIOMA" aos biomas antigos (fica para outra sessão).
- Mudar o layout das páginas existentes, além dos dados (exceção: os indicadores do carrossel,
  ver abaixo).
- Bioma Nadir ou outros conteúdos não pedidos (variantes de bioma, ascensões, histórico de patches).
- Banco de dados ou carregamento por JSON.
- Commits e PR — só quando o usuário pedir.

## Anti-objetivos (seria falha mesmo funcionando)

- Página nova que destoe visualmente das atuais (fontes, cores, cartões diferentes).
- Recriar cabeçalho/rodapé em vez de usar os fragments.
- Imagens novas que não sejam WebP.
- Texto em inglês aparecendo no site (exceto nomes próprios sem tradução estabelecida).
- Copiar trechos longos da wiki literalmente: o texto deve ser traduzido e resumido.

## Restrições (do CLAUDE.md)

- Spring Boot 4 + Thymeleaf + Bootstrap 5.3.3 (jsdelivr); `site.css` antes do CSS da página.
- Sempre `th:href="@{...}"`/`th:src="@{...}"`, nunca caminho fixo.
- Fontes: só Darumadrop One, Bangers e Nunito Sans (já aprovada).
- Imagens novas em **WebP**.
- Ao mudar o layout da home, atualizar os testes correspondentes.
- Commits em português, na branch atual, só quando pedido.

## Abordagens consideradas

1. **Dados em Java, em classes separadas (escolhida).** Mantém o padrão atual (listas de `record`s),
   mas tira os dados dos controllers para arquivos próprios: `Biomes.java`, `FoodCatalog.java`,
   `EquipmentCatalog.java`, `Badges.java` no pacote `Trabalho.Web`. Controllers ficam enxutos;
   erros de digitação aparecem na compilação; templates não mudam de contrato.
2. **Dados em JSON** em `src/main/resources/data/`, lidos na inicialização. Editar conteúdo sem
   Java, mas adiciona código de leitura e erros só aparecem em execução. Rejeitada pelo usuário.
3. **Banco de dados.** Descartada: complexo demais para conteúdo que só muda com o jogo.

O usuário escolheu a abordagem 1.

## Decisões do usuário

- Biomas novos: Raízes, Melancolia e Cidadela (só esses).
- Ordem do carrossel: a do jogo, igual à wiki.
- Adicionar itens novos **e** atualizar os existentes conforme a wiki.
- Seção nova "ESTRUTURAS DO BIOMA" só nos 3 biomas novos.
- Download das imagens da `peak.wiki.gg` autorizado, com conversão para WebP.
- Manter as categorias de equipamento do site (Cura, Especiais, Equipe/Coletivos,
  Solo/Individuais, Míticos) e encaixar os itens novos nelas.
- Dados em classes Java separadas dos controllers.
- Indicadores do carrossel em CSS (no lugar do SVG fixo de 7 bolinhas).

## Design

### Dados (Java)

- `Biomes.java`: lista `BIOMES` com os 10 biomas na ordem do jogo. O record `Biome` ganha o campo
  `List<Feature> features` (título, descrição, imagem); biomas antigos usam `List.of()`.
  `foodEffects()` ganha casos para `raizes`, `melancolia` e `cidadela` conforme os efeitos que os
  consumíveis daquele bioma realmente têm (Cidadela não tem comida natural → sem lista de comidas,
  e a seção de consumíveis some sozinha pelo `th:if`).
- `FoodCatalog.java`, `EquipmentCatalog.java`: o conteúdo que hoje está em `ItemCatalogController`,
  corrigido e ampliado. O controller só expõe as rotas.
- `Badges.java`: as 64 insígnias. O record `Badge` continua igual (`baseImage` pode ser `null`).
- `PageController`, `ItemCatalogController` e `BadgeController` passam a ler dessas classes.

### Página de bioma

- Nova seção em `biome-detail.html`, entre "PERIGOS DO BIOMA" e "A CHEGADA":
  `<section class="biome-features" th:if="${!#lists.isEmpty(biome.features)}">` com título
  **"ESTRUTURAS DO BIOMA"**, reaproveitando a marcação e o estilo dos cartões de perigo
  (`danger-row`: imagem + título + texto), **sem** a etiqueta de efeito. CSS em `biome-detail.css`,
  reaproveitando as regras de `.danger-*` ao máximo.
- Conteúdo dos biomas novos (traduzido e resumido da wiki):
  - **Raízes**: perigos — Vento, Pontes, Nuvens de esporos, Cogumelos saltitantes malignos,
    Cogumelo-bomba de esporos, Cogumelo-bomba explosivo, Besouros, Aranhas, Zumbis.
    Estruturas — Sequoias, Tocos, Cipós, Fungos-prateleira gigantes, Cogumelos saltitantes,
    Água, Cachoeiras. Comidas — 5 Shroomberries, Cenoura medicinal, Mandrágora, cogumelos, Favo de mel.
  - **Melancolia**: perigos — Névoa sonolenta, Água da Melancolia, Plantas carnívoras,
    Fantasma gigante, Sapos, Corvos trapaceiros, Grades de ferro. Estruturas — Sinos, Minhoca
    Madrugadora, Vitórias-régias, Escoteiros petrificados, Ninhos de corvo, Cipós, Entrada da
    Cidadela. Comidas — Ovo, Frango, Minhoca Madrugadora, Cachorro-quente, Banana amarela.
  - **Cidadela**: perigos — Armadilhas de flecha, Armadilhas de espinhos, Maças balançantes,
    Serras circulares, Bagagens-armadilha, Pedras petrificantes, Rolos de espinhos verticais,
    Rolos de espinhos horizontais, Caldeirão de lava. Estruturas — Correntes douradas, Escadas
    douradas, Lâmpadas, Estátuas de escoteiros, Escadarias, Janelas. Sem comida natural.
- Etiquetas de efeito dos perigos: hoje existem VENENO, ESPINHOS, FRIO, CEGUEIRA, QUEIMADURA e
  DANO. Efeitos novos que aparecem (ESPOROS, SONOLÊNCIA, TEIA, PETRIFICAÇÃO, FLECHA etc.) entram
  no `switch` de `hazard(...)` mapeados para uma classe CSS: reutilizar uma cor existente quando
  fizer sentido ou criar uma nova seguindo a paleta atual (decisão do builder).
- Arte do título: os biomas atuais usam arte do Figma; os novos usam a imagem principal da wiki
  (infobox) com as mesmas molduras (`titleOverlays`). O usuário pode trocar depois.
- Cartão do carrossel: imagem do bioma em `images/biomes/<slug>.webp`.

### Indicadores do carrossel (home)

- Hoje: `images/biomes/indicadores.svg` (7 círculos, 232px), 7 botões posicionados à mão em
  `site.css` (`nth-of-type`, passo de 35px) e `biomes.js` movendo o indicador em passos de 35px.
- Decisão do usuário: **bolinhas em CSS**. O SVG sai; cada indicador vira uma bolinha de 22px
  desenhada em CSS, com as mesmas cores (atual `#ddca40`, demais no cinza atual/`#9a9583`) e o
  mesmo passo de 35px, gerada pelo `th:each` para qualquer quantidade de biomas.
- O deslocamento da bolinha atual deixa de ser fixo em 35px no JS: passa a usar a posição real do
  botão (ou uma variável CSS de passo), para que o espaçamento possa encolher no celular e a
  fileira caiba sem rolagem horizontal.
- Esta é a única mudança de layout em página existente; os testes da home são atualizados.

### Imagens

- Baixar de `https://peak.wiki.gg/images/...` (autorizado) para uma pasta temporária
  (scratchpad), converter para WebP e copiar para `src/main/resources/static/images/`:
  - biomas novos → `images/biome-pages/<slug>/` e `images/biomes/<slug>.webp`;
  - comidas → `images/items/food/<categoria>/`;
  - equipamentos → `images/items/equipamentos/`;
  - insígnias → `images/badges/`.
- Nomes de arquivo novos descritivos em minúsculas com hífen (ex.: `minhoca-madrugadora.webp`),
  como os mais recentes do projeto (`carrapato.webp`, `colmeia.webp`).
- Imagens já existentes no site que estiverem certas são mantidas; não trocar arte do Figma.
- Ferramenta de conversão: decisão do builder (ex.: Python + Pillow; `cwebp`/ImageMagick não
  estão instalados).

### Tradução

- Seguir os nomes que o site já usa (Crispberry → Maçã; Kingberry → Amora / Amora silvestre;
  Berrynana → Banana; Clusterberry → Amora laranja/vermelha/preta; Winterberry → Laranja;
  Prickleberry → Espinheiro; Scorchberry → Pimenta; Luggage → Bagagem/Mala; Ancient Statue →
  Estátua anciã).
- Nomes de status: fome, energia, veneno, dano, frio, queimadura/calor, espinhos, sonolência,
  maldição, esporos, teia, petrificação, flecha.
- Nomes novos sem equivalente no site: o builder escolhe uma tradução natural e lista as
  escolhas no fim do trabalho para o usuário revisar.

### Itens do site que a wiki não lista (decisão do Claude)

- A comparação é nos dois sentidos: wiki → site (itens novos) e site → wiki (itens atuais).
- Item que ainda existe no jogo com outro nome é mantido e corrigido (ex.: "gelabaga amarela" no
  texto do Glutão → "laranja amarela", o nome que o site usa).
- Item que a wiki marca como **removido do jogo** sai do site; item que simplesmente não aparece
  na wiki fica como está. Em ambos os casos, o builder lista esses itens no fim para o usuário.
- Testes que citam itens específicos (`Cookies de escoteiro`, `Anti-Corda`, imagens etc.) são
  ajustados se o item mudar de nome ou sair.

## O que fica para o builder

- Tradução e redação dos textos (curtos, no tom das descrições atuais).
- Em qual categoria existente cada equipamento novo entra.
- Ordem das insígnias na grade e o `imageSize` de cada uma.
- Cores das etiquetas de efeito novas.
- Ferramenta de download/conversão.
- **Verificar** na wiki os dados que vieram só de resumo na leitura inicial: insígnias 45–64 e
  os equipamentos depois de "Scout's Ambition" (Scout's Generosity, Initiative, Tenacity,
  Scoutmaster's Bugle, The Book of Bones, Scout's Honor). Itens marcados na wiki como removidos
  do jogo não entram.
