package mk.ukim.finki.wp.liga.service.shop.impl;

import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballProductException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballProductException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidShoppingCartException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballProductException;
import mk.ukim.finki.wp.liga.model.shop.*;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballProductRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballProductRepository;
import mk.ukim.finki.wp.liga.repository.shop.ShoppingCartRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballProductRepository;
import mk.ukim.finki.wp.liga.service.shop.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final FootballProductRepository footballProductRepository;
    private final BasketballProductRepository basketballProductRepository;
    private final VolleyballProductRepository volleyballProductRepository;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, FootballProductRepository footballProductRepository, BasketballProductRepository basketballProductRepository, VolleyballProductRepository volleyballProductRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.footballProductRepository = footballProductRepository;
        this.basketballProductRepository = basketballProductRepository;
        this.volleyballProductRepository = volleyballProductRepository;
    }

    @Override
    public ShoppingCart getShoppingCart() {
        return shoppingCartRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    return shoppingCartRepository.save(newCart);
                });
    }

    @Override
    public void addFootballProductToCart(Long productId) {
        FootballProduct product = footballProductRepository.findById(productId)
                .orElseThrow(InvalidFootballProductException::new);
        getShoppingCart().getItemsFootball().add(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void addBasketballProductToCart(Long productId) {
        BasketballProduct product = basketballProductRepository.findById(productId)
                .orElseThrow(InvalidBasketballProductException::new);
        getShoppingCart().getItemsBasketball().add(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void addVolleyballProductToCart(Long productId) {
        VolleyballProduct product = volleyballProductRepository.findById(productId)
                .orElseThrow(InvalidVolleyballProductException::new);
        getShoppingCart().getItemsVolleyball().add(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void removeFootballProductFromCart(Long productId) {
        FootballProduct product = footballProductRepository.findById(productId)
                .orElseThrow(InvalidFootballProductException::new);
        getShoppingCart().getItemsFootball().remove(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void removeBasketballProductFromCart(Long productId) {
        BasketballProduct product = basketballProductRepository.findById(productId)
                .orElseThrow(InvalidBasketballProductException::new);
        getShoppingCart().getItemsBasketball().remove(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void removeVolleyballProductFromCart(Long productId) {
        VolleyballProduct product = volleyballProductRepository.findById(productId)
                .orElseThrow(InvalidVolleyballProductException::new);
        getShoppingCart().getItemsVolleyball().remove(product);
        shoppingCartRepository.save(getShoppingCart());
    }

    @Override
    public void clearCart() {
        ShoppingCart cart = getShoppingCart();
        cart.getItemsFootball().clear();
        cart.getItemsBasketball().clear();
        cart.getItemsVolleyball().clear();
        shoppingCartRepository.save(cart);
    }
}
