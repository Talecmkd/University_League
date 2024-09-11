package mk.ukim.finki.wp.liga.repository.shop;

import mk.ukim.finki.wp.liga.model.shop.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
