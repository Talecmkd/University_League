package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballProductRepository extends JpaRepository<FootballProduct, Long> {
    List<FootballProduct> findByFootballMerch(FootballTeam footballTeam);
}