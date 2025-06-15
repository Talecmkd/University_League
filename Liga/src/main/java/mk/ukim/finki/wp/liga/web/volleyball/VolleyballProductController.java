package mk.ukim.finki.wp.liga.web.volleyball;


import mk.ukim.finki.wp.liga.model.shop.BasketballProduct;
import mk.ukim.finki.wp.liga.model.shop.VolleyballProduct;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballProductService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/volleyball/products")
public class VolleyballProductController {

    private final VolleyballProductService volleyballProductService;
    private final VolleyballTeamService volleyballTeamService;

    @Autowired
    public VolleyballProductController(VolleyballProductService volleyballProductService, VolleyballTeamService volleyballTeamService) {
        this.volleyballProductService = volleyballProductService;
        this.volleyballTeamService = volleyballTeamService;
    }

    @GetMapping
    public String getAllVolleyballProducts(@RequestParam(value = "team", required = false) String team, Model model) {
        List<VolleyballProduct> products;

        if (team != null && !team.isEmpty()) {
            products = volleyballProductService.findByTeamName(team);
        } else {
            products = volleyballProductService.findAll();
        }
        model.addAttribute("teams", volleyballTeamService.listAllTeams());
        model.addAttribute("products", products);
        return "volleyball/volleyball_products";
    }

    @GetMapping("/team/{teamId}")
    public String getVolleyballProductsByTeam(@PathVariable Long teamId, Model model) {
        List<VolleyballProduct> products = volleyballProductService.findByTeamId(teamId);
        model.addAttribute("products", products);
        return "volleyball/volleyball_products";
    }

    @GetMapping("/{id}")
    public String getVolleyballProductById(@PathVariable Long id, Model model) {
        VolleyballProduct product = volleyballProductService.findById(id);
        model.addAttribute("product", product);
        return "volleyball/volleyball_product_detail";
    }

    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("teams", volleyballTeamService.listAllTeams());
        return "volleyball/volleyball_product_add";
    }

    @PostMapping
    public String createVolleyballProduct(@RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam double price,
                                          @RequestParam String imageUrl,
                                          @RequestParam Long team) {
        volleyballProductService.createNewVolleyballProduct(name,description,price,imageUrl,team);
        return "redirect:/volleyball/products";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        VolleyballProduct product = volleyballProductService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("teams", volleyballTeamService.listAllTeams());
        return "volleyball/volleyball_product_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateVolleyballProduct(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam double price,
                                          @RequestParam String imageUrl,
                                          @RequestParam Long team) {
        volleyballProductService.update(id, name, description, price, imageUrl, team);
        return "redirect:/volleyball/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteVolleyballProduct(@PathVariable Long id) {
        volleyballProductService.deleteById(id);
        return "redirect:/volleyball/products";
    }
}
