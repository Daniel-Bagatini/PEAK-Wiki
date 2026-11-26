# Redesign da página de login (tema Hum) — Design curto

Data: 2026-11-26 · Branch: `teste-de-skill-de-design`

## Intenção

Reconstruir só o **estilo** da página `/login`, como teste das skills Superpowers (processo) e
Hallmark (design anti "cara de IA"). Quem pediu está testando e aprendendo as skills, e trabalha
pelo celular: o resultado precisa ser conferível por capturas de tela que eu mesmo gero.

## Objetivos

1. Redesign **completo** (`/hallmark redesign`) de `login.html`: sistema visual e composição novos.
2. Tema **Hum** (papel cremoso com grão, bordas rasgadas, botões de "papel-cartão" que afundam),
   numa versão **mais adulta** que a original (ver Decisões).
3. Cabeçalho e rodapé do site **não** são usados. No lugar: uma barra mínima com a marca (link para a
   home) e um rodapé novo, só com os **4 perfis do Discord** (mesmos links, nomes e imagens), reestilizado.
4. Fontes do projeto: **Darumadrop One**, **Bangers** e **Nunito Sans** (via Google Fonts). Nada de Chillax.
5. Funciona em tela de celular (390px) e desktop (1280px), sem rolagem horizontal.

## Não-objetivos

- Back-end (o formulário continua `action="#"`; o banco de dados ainda não existe).
- `cadastro.html`, `recuperar-senha.html`, `login.css`, `auth.css`, `site.css` e os fragmentos
  `header.html`/`footer.html`: **não mudam** (as duas outras páginas dependem de `login.css`).
- Não inventar conteúdo (números, depoimentos, logos). Não usar o Figma do login.

## Anti-objetivos (falha mesmo que funcione)

- Parecer infantil (berrante, "creche"). Parecer template de IA (rótulo em caixa-alta espaçada,
  gradiente roxo-azul, card dentro de card, emoji como ícone).

## Restrições

- Mantém todo o "fio": campos (`email`, `password`, `remember`, ids e `name`), `method="post"`,
  links `/recuperar-senha` e `/cadastro`, botão "Entrar", botão "Criar conta" e o link "Cadastre-se".
- Thymeleaf (`th:href`, `th:src`), Bootstrap 5.3.3 por CDN e `site.css` antes do CSS da página
  (regra do projeto). Imagens novas, se houver, em WebP.
- Regras do Hallmark (Floor): tokens em `:root`, foco visível, `prefers-reduced-motion`, sem eyebrow
  nem rótulos em caixa-alta espaçada, hero visível sem rolar em 1280x800, `sloplint --render` limpo.

## Abordagens consideradas

- **Piste** (neve/pôster, cantos retos): combina com montanha, mas é frio e o mais "sério".
- **Paste-up** (papelão kraft, folhas coladas): casual e comunitário, mas foge do que foi pedido.
- **Hum**, escolhido por quem pediu ("não tem aspecto sério"). Risco: parecer infantil → mitigado abaixo.

## Decisões

Do usuário: tema **Hum**; só o login; pode commitar; fontes do projeto.

Minhas (a corrigir se quiser):
- **Hum "adulto":** mantém papel com grão, bordas rasgadas e botões que afundam; o amarelo-pera
  fica só no botão principal; coral vira um tom mais fechado (só para erro); azul mais profundo (links
  e foco); sem plantinhas, sem adesivos coloridos; raios de canto um pouco menores que os do tema.
- **Tipografia:** títulos em Darumadrop One, corpo e rótulos em Nunito Sans (frase normal, sem caixa-alta);
  Bangers só num detalhe pontual. Geist Mono e Chillax descartadas.
- **Composição (desktop):** papel cremoso de fundo; folha-cartão do formulário de um lado e uma foto de
  bioma com borda rasgada do outro (imagem que já existe no projeto, a escolher em `biome-pages/`);
  no celular, uma coluna com o formulário primeiro.
- **Rodapé:** faixa escura "de papel rasgado" com os 4 perfis do Discord como etiquetas de papel.
  Fica dentro do `login.html` (não mexe no fragmento usado nas outras páginas).
- **Texto:** mantém "Entrar" e "Explore o mundo, suba mais alto e construa sua história." O rótulo
  "Acesso" sai (é só rótulo); "Sua jornada começa aqui" perde a caixa-alta espaçada. Toda mudança
  de texto é listada no fim.
- **Arquivos:** editar `login.html`; criar CSS novo em `static/css/` (nome `login-*`), sem tocar nos
  existentes.

## A cargo de quem constrói

Escolha da foto de bioma, tamanhos, o desenho das bordas rasgadas, o texto do estado de erro e
detalhes de hover/pressionado.

## Verificação

`sloplint --render` (Floor sem FAIL), capturas em 390px e 1280px, `mvnw test` se o Java 25 existir
no ambiente, e o relatório "antes/depois" do Hallmark (`.hallmark/compare.html`).

## Commits

1. Este documento.
2. O login redesenhado. Sem push até o usuário autorizar.
