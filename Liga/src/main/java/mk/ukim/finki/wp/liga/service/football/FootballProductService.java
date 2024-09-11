package mk.ukim.finki.wp.liga.service.football;

import aj.org.objectweb.asm.commons.Remapper;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;

import java.util.List;
import java.util.Optional;

public interface FootballProductService {
    List<FootballProduct> findByTeam(FootballTeam team);

    List<FootballProduct> findAll();

    List<FootballProduct> findByTeamId(Long teamId);

    FootballProduct findById(Long id);

    void deleteById(Long id);

    void createNewFootballProduct(String name, String description, double price, String imageUrl, Long team);

    void update(Long id, String name, String description, double price, String imageUrl, Long team);
}
