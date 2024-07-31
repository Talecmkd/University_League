package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballTeamRepository extends JpaRepository<FootballTeam, Long> {
    List<FootballTeam> findFootballTeamByTeamName(String teamName);
}
