# Redesign da página de login (Hum) — Plano de implementação

> **Para quem executa:** use superpowers:subagent-driven-development (recomendado) ou superpowers:executing-plans. Os passos usam caixas `- [ ]`.

**Objetivo:** reconstruir o estilo de `/login` no tema Hum "adulto", com cabeçalho e rodapé novos próprios da página.

**Arquitetura:** `login.html` passa a usar dois fragmentos novos (`loginHeader`, `loginFooter`) e um CSS novo; o formulário mantém o mesmo "fio". Nenhum arquivo existente além do `login.html` muda.

**Stack:** Spring Boot 4, Thymeleaf, Bootstrap 5.3.3 (CDN), CSS puro; ferramentas do Hallmark (`sloplint`, `theme.mjs`) e Playwright para conferir.

**Spec:** `docs/superpowers/specs/2026-11-26-redesign-login-hum-design.md`

## Restrições globais

- Fontes só Darumadrop One, Bangers e Nunito Sans (Google Fonts). Imagens novas em WebP.
- Thymeleaf (`th:href`, `th:src`, `th:replace`); `site.css` carregado antes do CSS da página.
- Não alterar: `header.html`, `footer.html`, `login.css`, `auth.css`, `site.css`, `cadastro.html`, `recuperar-senha.html`, back-end.
- Manter no formulário: `method="post" action="#"`, ids/names `email`, `password`, `remember`, links `/recuperar-senha` e `/cadastro`, botão "Entrar", botão "Criar conta", link "Cadastre-se".
- Floor do Hallmark: tokens em `:root`, `:focus-visible`, `prefers-reduced-motion`, sem eyebrow nem rótulo em caixa-alta espaçada, hero visível em 1280x800, `overflow-x: clip` em `html` e `body`, carimbo na 1ª linha do CSS.
- Os 4 perfis do Discord: mesmos `href`, nomes, handles e imagens de `fragments/footer.html`.

## Foco de revisão

- Celular estreito (360px): o formulário aparece sem rolar horizontalmente e o botão "Entrar" é alcançável.
- Teclado: Tab percorre campos, links e botões com foco visível; o checkbox "Lembrar-me" é clicável pelo rótulo.
- Sem JavaScript e com movimento reduzido: a página inteira está visível e utilizável.
- Texto grande do sistema (zoom 200%): nada sobrepõe nem corta.
- Imagens que falham: o texto e os links dos perfis continuam legíveis.

## Mapa de arquivos

- Criar `src/main/resources/templates/fragments/login-header.html` (`loginHeader`)
- Criar `src/main/resources/templates/fragments/login-footer.html` (`loginFooter`)
- Criar `src/main/resources/static/css/login-tokens.css`, `login-base.css` (gerados pelo scaffold do Hallmark) e `login-hum.css` (a página)
- Modificar `src/main/resources/templates/login.html`

### Tarefa 1: Estado "antes" e andaime

**Arquivos:** `.hallmark/before*` (gerado), `login-tokens.css`, `login-base.css`

- [ ] **Passo 1:** subir o site (`./mvnw spring-boot:run` em segundo plano) e registrar o antes: `node .claude/skills/hallmark/scripts/sloplint.mjs src/main/resources/templates/login.html --genre playful --render --before --url http://localhost:8080/login`. Esperado: `.hallmark/before.json` e capturas criadas.
- [ ] **Passo 2:** `node .claude/skills/hallmark/scripts/theme.mjs scaffold hum --dealt "2/3" --redesign full`, gravando como `login-tokens.css` e `login-base.css` em `static/css/`. Ajustar tokens: `--font-display` Darumadrop One, `--font-body` Nunito Sans, coral/azul/raios conforme a spec; sem Chillax e sem Geist Mono.

### Tarefa 2: Fragmentos novos

**Arquivos:** `fragments/login-header.html`, `fragments/login-footer.html`

**Interfaces:** `th:fragment="loginHeader"` e `th:fragment="loginFooter"`. Produzem as classes `login-bar`, `login-brand`, `login-crew`, `login-crew__tag`, que a Tarefa 3 estiliza.

- [ ] **Passo 1:** `loginHeader`: `<header>` com a marca (imagem `images/biome-pages/costa/imgImage3.webp`, `alt="PEAK"`) dentro de `<a th:href="@{/}" aria-label="Voltar ao início PEAK">`.
- [ ] **Passo 2:** `loginFooter`: `<footer id="discord">` com título "Siga os criadores da página no Discord" e os 4 links de perfil, copiados com os mesmos `href`, `target="_blank" rel="noopener noreferrer"`, `aria-label`, imagem, nome e handle.
- [ ] **Passo 3:** conferir `git diff --stat` (só arquivos novos) e que `fragments/header.html` e `footer.html` não aparecem.

### Tarefa 3: Página e estilo

**Arquivos:** `login.html` (modificar), `login-hum.css` (criar)

- [ ] **Passo 1:** reescrever o `<head>` de `login.html` seguindo `badges.html`: Google Fonts (Bangers, Darumadrop One, Nunito Sans), Bootstrap 5.3.3, `site.css`, depois `login-tokens.css`, `login-base.css`, `login-hum.css`.
- [ ] **Passo 2:** `<body>` com `th:replace="~{fragments/login-header :: loginHeader}"`, `<main>` com a folha do formulário (mesmo fio da restrição global) e a foto de bioma com borda rasgada (escolher em `images/biome-pages/`), depois `th:replace="~{fragments/login-footer :: loginFooter}"`.
- [ ] **Passo 3:** `login-hum.css`: carimbo do Hallmark na linha 1; sobrescreve o fundo escuro do `site.css` neste body; papel com grão (`feTurbulence` em data URI); bordas rasgadas por `clip-path` fixo; botões de papel-cartão que afundam (primário em amarelo-pera, `--ease-spring` só nele); inputs de 52px, raio 14px; foco azul; faixa escura do rodapé com etiquetas de papel; layout em duas colunas ≥ 900px e uma coluna abaixo, com `minmax(0, 1fr)` e `overflow-wrap`.
- [ ] **Passo 4:** conferir no navegador (Playwright, 1280x800 e 390x844) e salvar capturas.

### Tarefa 4: Verificação e entrega

- [ ] **Passo 1:** `node .claude/skills/hallmark/scripts/sloplint.mjs src/main/resources/templates/login.html --genre playful --render --kept --url http://localhost:8080/login`. Esperado: 0 FAIL; corrigir ou dispensar cada REFLEX com motivo.
- [ ] **Passo 2:** revisão final do Hallmark em contexto limpo (conforme `gates.md` § 6); agir sobre o veredito e reconferir uma vez.
- [ ] **Passo 3:** `./mvnw test`. Esperado: testes existentes passam (o login não tem teste próprio). Se o Java 25 não existir aqui, dizer isso.
- [ ] **Passo 4:** conferir que cadastro (`/cadastro`) e recuperação (`/recuperar-senha`) continuam renderizando igual (captura).
- [ ] **Passo 5:** commit em português: `git add` dos arquivos acima e de `.hallmark/` aplicável, mensagem "Redesenha a página de login no estilo Hum" com as linhas de atribuição. Sem push até autorização.
- [ ] **Passo 6:** entregar ao usuário o resumo "antes/depois" do Hallmark, as capturas e a lista de textos alterados.
