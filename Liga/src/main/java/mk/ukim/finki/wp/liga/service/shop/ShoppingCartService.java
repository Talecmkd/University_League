package mk.ukim.finki.wp.liga.service.shop;

import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getShoppingCart();
    void addFootballProductToCart(Long productId);
    void addBasketballProductToCart(Long productId);
    void addVolleyballProductToCart(Long productId);
    void removeFootballProductFromCart(Long productId);
    void removeBasketballProductFromCart(Long productId);
    void removeVolleyballProductFromCart(Long productId);
    void clearCart();
}
