package mk.ukim.finki.wp.liga.web.shop;

import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;
import mk.ukim.finki.wp.liga.service.shop.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @GetMapping
    public String viewCart(Model model) {
        ShoppingCart cart = shoppingCartService.getShoppingCart();
        model.addAttribute("cart", cart);
        return "shop/cart";
    }


    @PostMapping("/add/football/{productId}")
    public String addFootballProductToCart(@PathVariable Long productId) {
        shoppingCartService.addFootballProductToCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/add/basketball/{productId}")
    public String addBasketballProductToCart(@PathVariable Long productId) {
        shoppingCartService.addBasketballProductToCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/add/volleyball/{productId}")
    public String addVolleyballProductToCart(@PathVariable Long productId) {
        shoppingCartService.addVolleyballProductToCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/remove/football/{productId}")
    public String removeFootballProductFromCart(@PathVariable Long productId) {
        shoppingCartService.removeFootballProductFromCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/remove/basketball/{productId}")
    public String removeBasketballProductFromCart(@PathVariable Long productId) {
        shoppingCartService.removeBasketballProductFromCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/remove/volleyball/{productId}")
    public String removeVolleyballProductFromCart(@PathVariable Long productId) {
        shoppingCartService.removeVolleyballProductFromCart(productId);
        return "redirect:/cart";
    }


    @PostMapping("/clear")
    public String clearCart() {
        shoppingCartService.clearCart();
        return "redirect:/cart";
    }
}