package mk.ukim.finki.wp.liga.service.shop;

import mk.ukim.finki.wp.liga.model.shop.Order;
import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    Order createOrderFromCart(ShoppingCart cart);

    Order findOrderById(Long orderId);

    double calculateTotalPrice(ShoppingCart cart);

    void markAsPaid(Long orderId);

    List<Order> findAll();
}
