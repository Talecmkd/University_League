package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballPlayerScored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {

    List<FootballPlayer> findByTeamId(Long teamId);
}
