package Trabalho.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itens")
public class ItemCatalogController {

	@GetMapping("/comidas")
	public String foods(Model model) {
		return showCatalog(FoodCatalog.CATALOG, model);
	}

	@GetMapping("/equipamentos")
	public String equipment(Model model) {
		return showCatalog(EquipmentCatalog.CATALOG, model);
	}

	private String showCatalog(ItemCatalogs.ItemCatalog catalog, Model model) {
		model.addAttribute("catalog", catalog);
		return "item-catalog";
	}
}
