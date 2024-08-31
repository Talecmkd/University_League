package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
List<FootballMatch> findAllByHomeTeamOrAwayTeam(FootballTeam homeTeam, FootballTeam awayTeam);
    List<FootballMatch> findAllByIsPlayoffMatchTrue();
    void deleteAllByIsPlayoffMatchTrue();
    @Query("SELECT m FROM FootballMatch m WHERE m.homeTeam = :team OR m.awayTeam = :team")
    List<FootballMatch> findByTeam(@Param("team") FootballTeam team);


}
