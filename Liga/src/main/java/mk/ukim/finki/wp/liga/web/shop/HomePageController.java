package mk.ukim.finki.wp.liga.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class HomePageController {

    @GetMapping
    public String shopHome(Model model) {
        model.addAttribute("bodyContent","shop/homepage");
        return "master_template";
    }

}
