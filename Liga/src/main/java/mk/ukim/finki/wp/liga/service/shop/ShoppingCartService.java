package mk.ukim.finki.wp.liga.service.shop;

import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getShoppingCart();
    void addFootballProductToCart(Long productId, int quantity);
    void addBasketballProductToCart(Long productId, int quantity);
    void addVolleyballProductToCart(Long productId, int quantity);
    void removeFootballProductFromCart(Long productId);
    void removeBasketballProductFromCart(Long productId);
    void removeVolleyballProductFromCart(Long productId);
    void clearCart();
}
