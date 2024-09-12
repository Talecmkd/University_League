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

import java.util.Optional;

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
    public void addFootballProductToCart(Long productId, int quantity) {
//        FootballProduct product = footballProductRepository.findById(productId)
//                .orElseThrow(InvalidFootballProductException::new);
//        product.setQuantity(quantity);
//        getShoppingCart().getItemsFootball().add(product);
//        shoppingCartRepository.save(getShoppingCart());

        FootballProduct product = footballProductRepository.findById(productId)
                .orElseThrow(InvalidFootballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<FootballProduct> existingProduct = cart.getItemsFootball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            FootballProduct existing = existingProduct.get();
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            product.setQuantity(quantity);
            cart.getItemsFootball().add(product);
        }

        shoppingCartRepository.save(cart);
    }

    @Override
    public void addBasketballProductToCart(Long productId, int quantity) {
//        BasketballProduct product = basketballProductRepository.findById(productId)
//                .orElseThrow(InvalidBasketballProductException::new);
//        product.setQuantity(quantity);
//        getShoppingCart().getItemsBasketball().add(product);
//        shoppingCartRepository.save(getShoppingCart());
        BasketballProduct product = basketballProductRepository.findById(productId)
                .orElseThrow(InvalidBasketballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<BasketballProduct> existingProduct = cart.getItemsBasketball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            BasketballProduct existing = existingProduct.get();
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            product.setQuantity(quantity);
            cart.getItemsBasketball().add(product);
        }

        shoppingCartRepository.save(cart);
    }

    @Override
    public void addVolleyballProductToCart(Long productId, int quantity) {
//        VolleyballProduct product = volleyballProductRepository.findById(productId)
//                .orElseThrow(InvalidVolleyballProductException::new);
//        product.setQuantity(quantity);
//        getShoppingCart().getItemsVolleyball().add(product);
//        shoppingCartRepository.save(getShoppingCart());
        VolleyballProduct product = volleyballProductRepository.findById(productId)
                .orElseThrow(InvalidVolleyballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<VolleyballProduct> existingProduct = cart.getItemsVolleyball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            VolleyballProduct existing = existingProduct.get();
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            product.setQuantity(quantity);
            cart.getItemsVolleyball().add(product);
        }

        shoppingCartRepository.save(cart);
    }

    @Override
    public void removeFootballProductFromCart(Long productId) {
//        FootballProduct product = footballProductRepository.findById(productId)
//                .orElseThrow(InvalidFootballProductException::new);
//        product.setQuantity(product.getQuantity() - 1);
//        getShoppingCart().getItemsFootball().remove(product);
//        shoppingCartRepository.save(getShoppingCart());
        FootballProduct product = footballProductRepository.findById(productId)
                .orElseThrow(InvalidFootballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<FootballProduct> existingProduct = cart.getItemsFootball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            FootballProduct existing = existingProduct.get();
            if (existing.getQuantity() > 1) {
                existing.setQuantity(existing.getQuantity() - 1);
            } else {
                cart.getItemsFootball().remove(existing);
            }
            shoppingCartRepository.save(cart);
        }
    }

    @Override
    public void removeBasketballProductFromCart(Long productId) {
//        BasketballProduct product = basketballProductRepository.findById(productId)
//                .orElseThrow(InvalidBasketballProductException::new);
//        product.setQuantity(product.getQuantity() - 1);
//        getShoppingCart().getItemsBasketball().remove(product);
//        shoppingCartRepository.save(getShoppingCart());
        BasketballProduct product = basketballProductRepository.findById(productId)
                .orElseThrow(InvalidBasketballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<BasketballProduct> existingProduct = cart.getItemsBasketball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            BasketballProduct existing = existingProduct.get();
            if (existing.getQuantity() > 1) {
                existing.setQuantity(existing.getQuantity() - 1);
            } else {
                cart.getItemsBasketball().remove(existing);
            }
            shoppingCartRepository.save(cart);
        }
    }

    @Override
    public void removeVolleyballProductFromCart(Long productId) {
//        VolleyballProduct product = volleyballProductRepository.findById(productId)
//                .orElseThrow(InvalidVolleyballProductException::new);
//        product.setQuantity(product.getQuantity() - 1);
//        getShoppingCart().getItemsVolleyball().remove(product);
//        shoppingCartRepository.save(getShoppingCart());
        VolleyballProduct product = volleyballProductRepository.findById(productId)
                .orElseThrow(InvalidVolleyballProductException::new);

        ShoppingCart cart = getShoppingCart();
        Optional<VolleyballProduct> existingProduct = cart.getItemsVolleyball().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            VolleyballProduct existing = existingProduct.get();
            if (existing.getQuantity() > 1) {
                existing.setQuantity(existing.getQuantity() - 1);
            } else {
                cart.getItemsVolleyball().remove(existing);
            }
            shoppingCartRepository.save(cart);
        }
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
