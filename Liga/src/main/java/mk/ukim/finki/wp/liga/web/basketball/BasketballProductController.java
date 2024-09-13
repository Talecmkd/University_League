package mk.ukim.finki.wp.liga.web.basketball;


import mk.ukim.finki.wp.liga.model.shop.BasketballProduct;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;
import mk.ukim.finki.wp.liga.service.basketball.BasketballProductService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basketball/products")
public class BasketballProductController {

    private final BasketballProductService basketballProductService;
    private final BasketballTeamService basketballTeamService;

    @Autowired
    public BasketballProductController(BasketballProductService basketballProductService, BasketballTeamService basketballTeamService) {
        this.basketballProductService = basketballProductService;
        this.basketballTeamService = basketballTeamService;
    }

    @GetMapping
    public String getAllBasketballProducts(@RequestParam(value = "team", required = false) String team, Model model) {
        List<BasketballProduct> products;

        if (team != null && !team.isEmpty()) {
            products = basketballProductService.findByTeamName(team);
        } else {
            products = basketballProductService.findAll();
        }
        model.addAttribute("teams", basketballTeamService.listAllTeams());
        model.addAttribute("products", products);

        return "basketball/basketball_products";
    }

    @GetMapping("/team/{teamId}")
    public String getBasketballProductsByTeam(@PathVariable Long teamId, Model model) {
        List<BasketballProduct> products = basketballProductService.findByTeamId(teamId);
        model.addAttribute("products", products);
        return "basketball/basketball_products";
    }

    @GetMapping("/{id}")
    public String getBasketballProductById(@PathVariable Long id, Model model) {
        BasketballProduct product = basketballProductService.findById(id);
        model.addAttribute("product", product);
        return "basketball/basketball_product_detail";
    }

    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("teams", basketballTeamService.listAllTeams());
        return "basketball/basketball_product_add";
    }

    @PostMapping
    public String createBasketballProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String imageUrl,
            @RequestParam Long team) {

        basketballProductService.createNewBasketballProduct(name, description, price, imageUrl, team);
        return "redirect:/basketball/products";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        BasketballProduct product = basketballProductService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("teams", basketballTeamService.listAllTeams());
        return "basketball/basketball_product_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBasketballProduct(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam double price,
                                          @RequestParam String imageUrl,
                                          @RequestParam Long team) {
        basketballProductService.update(id, name, description, price, imageUrl, team);
        return "redirect:/basketball/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteBasketballProduct(@PathVariable Long id) {
        basketballProductService.deleteById(id);
        return "redirect:/basketball/products";
    }
}
