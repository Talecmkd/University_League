package mk.ukim.finki.wp.liga.web.shop;

import mk.ukim.finki.wp.liga.model.shop.Order;
import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;
import mk.ukim.finki.wp.liga.service.shop.OrderService;
import mk.ukim.finki.wp.liga.service.shop.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }


    @PostMapping("/create")
    public String createOrder() {
        ShoppingCart cart = shoppingCartService.getShoppingCart();
        Order order = orderService.createOrderFromCart(cart);
        return "redirect:/orders/" + order.getOrderId();
    }


    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("bodyContent","shop/order_details");
        return "master_template";
    }


    @PostMapping("/{orderId}/pay")
    public String payOrder(@PathVariable Long orderId) {
        orderService.markAsPaid(orderId);
        return "redirect:/orders/order_confirmation/" + orderId;
    }

    @GetMapping("/order_confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.findOrderById(orderId));
        model.addAttribute("bodyContent","shop/order_confirmation");
        return "master_template";
    }

    @GetMapping
    public String listAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("bodyContent","shop/orders");
        return "master_template";
    }
}