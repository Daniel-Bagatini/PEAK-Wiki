# Redesign da home (tema Hum) — Design curto

Data: 2026-11-27 · Branch: `redesign-da-home` (a partir de `teste-de-skill-de-design`)

## Intenção

Repetir na home (`/`) o redesign completo do login: tema **Hum adulto** (papel com grão, bordas
rasgadas, botões de papel-cartão), com `/hallmark redesign` e o processo do Superpowers. O usuário
quer analisar o resultado outra vez, pelo celular.

## Objetivos

1. Home reformulada quase por inteiro, no mesmo sistema visual do login (tokens, papel, bordas).
2. **Ordem fixa das seções:** Biomas → Itens → Insígnias → botão da Steam.
3. **Rodapé: o atual, sem alteração** (`fragments/footer.html`, estilos do `site.css`).
4. **Botão da Steam: intocado.** Mesmo HTML e mesmo CSS (`.steam-promo-btn` do `site.css`); só posição
   e largura podem mudar, por um contêiner ao redor.
5. "Sobre o jogo" pode ser reestilizado e mudar de lugar na página.
6. Em 360, 390 e 1280px, sem rolagem horizontal.

## Não-objetivos

- `site.css`, `item-dialog.css`, `header.html`, `footer.html`, scripts (`biomes.js`, `items.js`) e
  back-end: não mudam. Outras páginas: não mudam.
- Sem texto, número ou imagem inventados.

## Anti-objetivos

Parecer infantil ou "template de IA"; quebrar o carrossel de biomas, o aviso de local secreto ou a
escolha de itens; deixar o botão da Steam diferente do atual.

## Restrições

- **Fios mantidos:** todos os ids, classes e `data-*` que `biomes.js`/`items.js` e os testes usam
  (`#biomas`, `.biomes-card` com o atributo `class="biomes-card"` exato, `#itens`, `#insignias`,
  `#biome-dialog`, `#secret-dialog`, `#item-dialog`, `.biome-slide-name`, etc.), os `th:each` e os links.
- Os testes atuais da home continuam valendo sem edição: "Fórum da Comunidade", logo
  `/images/biome-pages/costa/imgImage3.webp`, `/css/site.css`, `/images/home/card-items.webp`,
  `/images/home/card-badges.webp`, "Sobre o jogo" e o texto, link e ícone da Steam, rodapé do Discord.
- Fontes só Darumadrop One, Bangers e Nunito Sans. Imagens novas em WebP.
- Hallmark: tokens em `:root`, foco visível, `prefers-reduced-motion`, sem eyebrow nem rótulo em
  caixa-alta espaçada, carimbo na linha 1 do CSS, `sloplint` sem FAIL nos meus arquivos.

## Abordagens consideradas

1. **CSS novo por cima do `site.css` (escolhida).** Mantém o HTML e as classes, carrega `home-hum.css`
   depois do `site.css` e o sobrepõe só na home. Menor risco para JS, testes, outras páginas e para o
   botão da Steam (continua vindo do `site.css`). Custo: algumas regras antigas precisam ser anuladas.
2. **Reescrever o markup com classes novas.** CSS mais limpo, mas arrisca quebrar o JS e os testes
   e obriga a recriar o botão da Steam.
3. **Mover o CSS da home para fora do `site.css`.** É a arrumação certa a longo prazo, mas mexe num
   arquivo que todas as páginas usam. Fica para outra hora.

## Decisões

Do usuário: ordem das seções; rodapé como está; botão da Steam como está (só tamanho/posição);
"Sobre o jogo" livre; estilo Hum.

Minhas (a corrigir):
- **Cabeçalho novo** em fragmento próprio `fragments/hum-header.html` (`humHeader`): logo à esquerda
  (etiqueta escura, como no login) e à direita os dois links atuais, "Fórum da Comunidade" e
  "Entrar / Cadastrar", como botões de papel. O `header.html` não muda.
- **Hero:** o título "Wiki do PEAK" e o cartão grande de **Biomas** (foto da Steam, a que já existe)
  com borda rasgada e o nome "Biomas" numa etiqueta de papel. Sem frase extra, porque não adiciono
  texto; fica registrado como exceção do Hallmark.
- **Itens e Insígnias:** duas folhas de papel lado a lado com as fotos atuais e o nome em etiqueta
  (uma coluna no celular).
- **Sobre o jogo:** bilhete de papel com a imagem atual, depois das Insígnias e antes da Steam.
- **Steam:** envolvida num contêiner que só a centraliza e dá espaço; o botão fica idêntico.
- **Janelas** (biomas, local secreto, itens): só retoque visual por cima (moldura de papel, título,
  fechar), sem mexer no funcionamento.
- **Rodapé:** sem alteração; a página termina nele como hoje.
- Valores de Hum (grão, bordas rasgadas, sombras) copiados de `login-hum.css` para o CSS da home,
  para não mexer nas páginas de login.

## A cargo de quem constrói

Tamanhos, espaçamentos, polígonos das bordas, estados de hover/foco e como anular as regras antigas.

## Verificação

`sloplint` (0 FAIL nos arquivos novos), capturas em 360/390/1280px, janelas abertas num pré-visualizador
estático com biomas de exemplo, conferência de que `site.css`, `footer.html` e o botão da Steam não
mudaram (`git diff`), e `mvnw test` se o Java 25 existir.

## Commits

1. Este documento. 2. O plano. 3. A home redesenhada.
