package mk.ukim.finki.wp.liga.web.football;

import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;
import mk.ukim.finki.wp.liga.service.football.FootballProductService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/football/products")
public class FootballProductController {

    private final FootballProductService footballProductService;
    private final FootballTeamService footballTeamService;

    @Autowired
    public FootballProductController(FootballProductService footballProductService, FootballTeamService footballTeamService) {
        this.footballProductService = footballProductService;
        this.footballTeamService = footballTeamService;
    }

    @GetMapping
    public String getAllFootballProducts(@RequestParam(value = "team", required = false) String team, Model model) {
        List<FootballProduct> products;

        if (team != null && !team.isEmpty()) {
            products = footballProductService.findByTeamName(team);
        } else {
            products = footballProductService.findAll();
        }
        model.addAttribute("teams", footballTeamService.listAllTeams());
        model.addAttribute("products", products);
        return "football_products";
    }

    @GetMapping("/team/{teamId}")
    public String getFootballProductsByTeam(@PathVariable Long teamId, Model model) {
        List<FootballProduct> products = footballProductService.findByTeamId(teamId);
        model.addAttribute("products", products);
        return "football_products";  // Return Thymeleaf template
    }

    // Display a specific football product by id
    @GetMapping("/{id}")
    public String getFootballProductById(@PathVariable Long id, Model model) {
        FootballProduct product = footballProductService.findById(id);
        model.addAttribute("product", product);
        return "football_product_detail";
    }


    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("teams", footballTeamService.listAllTeams());
        return "football_product_add";  // Return form template
    }


    @PostMapping
    public String createFootballProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String imageUrl,
            @RequestParam Long team) {

        footballProductService.createNewFootballProduct(name,description,price,imageUrl,team);
        return "redirect:/football/products";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        FootballProduct product = footballProductService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("teams", footballTeamService.listAllTeams());
        return "football_product_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateFootballProduct(
                                        @PathVariable Long id,
                                        @RequestParam String name,
                                        @RequestParam String description,
                                        @RequestParam double price,
                                        @RequestParam String imageUrl,
                                        @RequestParam Long team){
        footballProductService.update(id, name, description, price, imageUrl, team);
        return "redirect:/football/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteFootballProduct(@PathVariable Long id) {
        footballProductService.deleteById(id);
        return "redirect:/football/products";
    }
}

