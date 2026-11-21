# PEAK Wiki

Site em Spring Boot 4 + Thymeleaf + Bootstrap 5.3 (Java 25, Maven).

## Comandos (Windows / PowerShell)
- Rodar o site: `.\mvnw.cmd spring-boot:run` e abrir http://localhost:8080
- Testes: `.\mvnw.cmd test`
- Para conferir uma mudança visual, rode o site e confira a página no navegador.

## Estrutura
- Controllers: `src/main/java/Trabalho/Web/`
- Páginas: `src/main/resources/templates/`
- Cabeçalho e rodapé: `src/main/resources/templates/fragments/` (`header.html` → `siteHeader`, `footer.html` → `siteFooter`)
- CSS: `src/main/resources/static/css/` (`site.css` é o global, e cada página tem o seu próprio CSS)
- Imagens: `src/main/resources/static/images/`

## Regras para páginas
- **SEMPRE** use o cabeçalho e o rodapé que já existem em toda página nova. Nunca recrie esses blocos:
  ```html
  <div th:replace="~{fragments/header :: siteHeader}"></div>
  ...
  <div th:replace="~{fragments/footer :: siteFooter}"></div>
  ```
- Monte as páginas com Thymeleaf (`th:href="@{...}"`, `th:src="@{...}"`, `th:each` etc.), nunca com caminhos fixos.
- Use o Bootstrap 5.3.3 (CDN do jsdelivr), como nas páginas que já existem, e carregue o `site.css` antes do CSS da página.
- Para uma página nova, use `badges.html` como modelo de `<head>`.
- Imagens novas devem ser **WebP**.
- Páginas vindas do Figma devem seguir o design fielmente.

## Fontes
- Use só **Darumadrop One** e **Bangers**, via Google Fonts.
- Qualquer outra fonte é exceção: **pergunte antes** de usar.
- Exceção já aprovada: **Nunito Sans**. Ela pode continuar sendo usada onde já está.

## Git
- Commits em **português**, com o verbo no presente da 3ª pessoa e uma frase curta que diz o que mudou. Exemplos: "Implementa…", "Corrige…", "Ajusta…", "Converte…".
- Só faça commit quando eu pedir. Eu reviso as alterações antes.
- Nunca faça commit direto na `main`. Trabalhe em uma branch com nome descritivo em português (ex.: `otimiza-imagens-webp`).
- Só abra PR quando eu pedir.
- Quando mudar o layout da home, atualize os testes correspondentes.
