package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {
    
}
