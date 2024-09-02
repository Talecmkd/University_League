package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolleyballMatchRepository extends JpaRepository<VolleyballMatch, Long> {
    List<VolleyballMatch> findAllByHomeTeamOrAwayTeam(VolleyballTeam homeTeam, VolleyballTeam awayTeam);
    List<VolleyballMatch> findAllByIsVolleyballPlayoffMatchTrue();
    void deleteAllByIsVolleyballPlayoffMatchTrue();
}
