package mk.ukim.finki.wp.liga.repository.basketball;

import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketballPlayerRepository extends JpaRepository<BasketballPlayer, Long> {
    List<BasketballPlayer> findByTeamId(Long teamId);
}
