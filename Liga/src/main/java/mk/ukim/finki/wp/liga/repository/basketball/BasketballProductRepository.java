package mk.ukim.finki.wp.liga.repository.basketball;

import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.shop.BasketballProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketballProductRepository extends JpaRepository<BasketballProduct, Long> {
    List<BasketballProduct> findByBasketballMerch(BasketballTeam basketballTeam);

    List<BasketballProduct> findByBasketballMerch_TeamName(String teamName);

}