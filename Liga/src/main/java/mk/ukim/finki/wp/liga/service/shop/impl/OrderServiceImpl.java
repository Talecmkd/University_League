package mk.ukim.finki.wp.liga.service.shop.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidOrderException;
import mk.ukim.finki.wp.liga.model.shop.*;
import mk.ukim.finki.wp.liga.repository.shop.OrderRepository;
import mk.ukim.finki.wp.liga.service.shop.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional
    public Order createOrderFromCart(ShoppingCart cart) {
        Order order = new Order();

        List<FootballProduct> footballProducts = new ArrayList<>(cart.getItemsFootball());
        order.setOrderedFootballProducts(footballProducts);
        footballProducts.forEach(product -> product.setOrder(order));

        List<BasketballProduct> basketballProducts = new ArrayList<>(cart.getItemsBasketball());
        order.setOrderedBasketballProducts(basketballProducts);
        basketballProducts.forEach(product -> product.setOrder(order));

        List<VolleyballProduct> volleyballProducts = new ArrayList<>(cart.getItemsVolleyball());
        order.setOrderedVolleyballProducts(volleyballProducts);
        volleyballProducts.forEach(product -> product.setOrder(order));

        double totalPrice = calculateTotalPrice(cart);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(InvalidOrderException::new);
    }

    @Override
    public double calculateTotalPrice(ShoppingCart cart) {

        double footballTotal = cart.getItemsFootball().stream()
                .mapToDouble(FootballProduct::getPrice)
                .sum();

        double basketballTotal = cart.getItemsBasketball().stream()
                .mapToDouble(BasketballProduct::getPrice)
                .sum();

        double volleyballTotal = cart.getItemsVolleyball().stream()
                .mapToDouble(VolleyballProduct::getPrice)
                .sum();

        return footballTotal + basketballTotal + volleyballTotal;
    }

    @Override
    public void markAsPaid(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(InvalidOrderException::new);
        order.setPaid(true);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
