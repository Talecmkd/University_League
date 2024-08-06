package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
List<FootballMatch> findAllByHomeTeamOrAwayTeam(FootballTeam homeTeam, FootballTeam awayTeam);
}
