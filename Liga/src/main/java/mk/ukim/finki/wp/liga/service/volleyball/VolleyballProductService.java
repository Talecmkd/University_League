package mk.ukim.finki.wp.liga.service.volleyball;

import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.model.shop.BasketballProduct;
import mk.ukim.finki.wp.liga.model.shop.VolleyballProduct;

import java.util.List;
import java.util.Optional;

public interface VolleyballProductService {
    List<VolleyballProduct> findByTeam(VolleyballTeam team);

    List<VolleyballProduct> findAll();

    List<VolleyballProduct> findByTeamId(Long teamId);

    VolleyballProduct findById(Long id);

    void deleteById(Long id);

    void createNewFootballProduct(String name, String description, double price, String imageUrl, Long team);

    void update(Long id, String name, String description, double price, String imageUrl, Long team);
}
