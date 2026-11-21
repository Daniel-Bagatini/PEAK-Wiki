package Trabalho.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BadgeController {

	@GetMapping("/insignias")
	public String badges(Model model) {
		model.addAttribute("badges", Badges.ALL);
		return "badges";
	}
}
