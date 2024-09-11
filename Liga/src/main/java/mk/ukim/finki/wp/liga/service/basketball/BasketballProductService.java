package mk.ukim.finki.wp.liga.service.basketball;

import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.shop.BasketballProduct;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;

import java.util.List;
import java.util.Optional;

public interface BasketballProductService {
    List<BasketballProduct> findByTeam(BasketballTeam team);

    List<BasketballProduct> findAll();

    List<BasketballProduct> findByTeamId(Long teamId);

    BasketballProduct findById(Long id);

    void deleteById(Long id);

    void createNewBasketballProduct(String name, String description, double price, String imageUrl, Long team);

    void update(Long id, String name, String description, double price, String imageUrl, Long team);
}
