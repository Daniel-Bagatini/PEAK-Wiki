const itemDialog = document.querySelector("#item-dialog");
const itemTrigger = document.querySelector("#itens");

if (itemDialog && itemTrigger) {
  const closeButton = itemDialog.querySelector(".biome-close");

  closeButton.addEventListener("click", () => itemDialog.close());
  itemDialog.addEventListener("click", (event) => {
    if (event.target === itemDialog) {
      itemDialog.close();
    }
  });
  itemTrigger.addEventListener("click", () => itemDialog.showModal());
}
