package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolleyballTeamRepository extends JpaRepository<VolleyballTeam, Long> {
    List<VolleyballTeam> findVolleyballTeamByTeamName(String teamName);
    //@Query("SELECT t FROM FootballTeam t ORDER BY t.teamLeaguePoints desc")
    List<VolleyballTeam> findAllByOrderByTeamLeaguePointsDesc();
}
