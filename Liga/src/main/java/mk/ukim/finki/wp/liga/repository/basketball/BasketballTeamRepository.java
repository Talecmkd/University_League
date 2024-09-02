package mk.ukim.finki.wp.liga.repository.basketball;

import mk.ukim.finki.wp.liga.model.BasketballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketballTeamRepository extends JpaRepository<BasketballTeam, Long> {
    List<BasketballTeam> findBasketballTeamByTeamName(String teamName);
    List<BasketballTeam> findAllByOrderByTeamLeaguePointsDesc();
}
