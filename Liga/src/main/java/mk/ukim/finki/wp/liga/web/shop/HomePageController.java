package mk.ukim.finki.wp.liga.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class HomePageController {

    @GetMapping
    public String shopHome() {
        return "shop/homepage";
    }

}
