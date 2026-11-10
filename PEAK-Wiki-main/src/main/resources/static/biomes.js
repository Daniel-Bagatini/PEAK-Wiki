document.querySelectorAll("[data-placeholder]").forEach((button) => {
  button.addEventListener("click", () => {
    const status = button.querySelector(".placeholder-status") || button.nextElementSibling;
    if (status) {
      status.textContent = `O link de ${button.dataset.placeholder} será adicionado em breve.`;
    }
  });
});

const dialog = document.querySelector("#biome-dialog");

if (dialog) {
  const track = dialog.querySelector("[data-biome-track]");
  const indicators = [...dialog.querySelectorAll("[data-biome-indicator]")];
  const currentIndicator = dialog.querySelector(".biome-indicator-current");
  const firstIndicator = dialog.querySelector(".biome-indicator-first");
  const biomes = [...track.querySelectorAll(".biome-slide")];
  let selectedIndex = 0;

  function showBiome(index) {
    const wrapsAround = index < 0 || index >= biomes.length;
    selectedIndex = (index + biomes.length) % biomes.length;
    const activeSlide = biomes[selectedIndex];
    const trackStyles = getComputedStyle(track);
    const gap = Number.parseFloat(trackStyles.columnGap);
    const slideWidth = activeSlide.getBoundingClientRect().width;

    biomes.forEach((slide, position) => {
      const active = position === selectedIndex;
      slide.tabIndex = active ? 0 : -1;
      slide.inert = !active;
      slide.setAttribute("aria-hidden", String(!active));
    });

    if (wrapsAround) {
      track.style.transition = "none";
    }
    track.style.transform = `translateX(-${selectedIndex * (slideWidth + gap)}px)`;
    if (wrapsAround) {
      track.offsetHeight;
      requestAnimationFrame(() => track.style.removeProperty("transition"));
    }
    currentIndicator.style.transform = `translateX(${selectedIndex * 35}px)`;
    firstIndicator.hidden = selectedIndex === 0;

    indicators.forEach((indicator, position) => {
      indicator.setAttribute("aria-pressed", String(position === selectedIndex));
    });
  }

  dialog.querySelector(".biome-close").addEventListener("click", () => dialog.close());
  dialog.querySelector(".biome-arrow--previous").addEventListener("click", () => {
    showBiome(selectedIndex - 1);
  });
  dialog.querySelector(".biome-arrow--next").addEventListener("click", () => {
    showBiome(selectedIndex + 1);
  });

  indicators.forEach((indicator) => {
    indicator.addEventListener("click", () => {
      showBiome(Number(indicator.dataset.biomeIndicator));
    });
  });

  dialog.addEventListener("click", (event) => {
    if (event.target === dialog) {
      dialog.close();
    }
  });

  dialog.addEventListener("keydown", (event) => {
    if (event.key === "ArrowLeft") {
      showBiome(selectedIndex - 1);
    } else if (event.key === "ArrowRight") {
      showBiome(selectedIndex + 1);
    }
  });

  document.querySelector("#biomas").addEventListener("click", () => {
    showBiome(selectedIndex);
    dialog.showModal();
  });

  window.addEventListener("resize", () => {
    showBiome(selectedIndex);
  });

  showBiome(0);
}
