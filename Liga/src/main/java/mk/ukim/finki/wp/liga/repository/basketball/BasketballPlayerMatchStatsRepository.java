package mk.ukim.finki.wp.liga.repository.basketball;

import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballPlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketballPlayerMatchStatsRepository extends JpaRepository<BasketballPlayerMatchStats, Long> {
    List<BasketballPlayerMatchStats> findByBasketballMatch(BasketballMatch basketballMatch);
    BasketballPlayerMatchStats findByIdAndBasketballMatch(Long id, BasketballMatch basketballMatch);
}
