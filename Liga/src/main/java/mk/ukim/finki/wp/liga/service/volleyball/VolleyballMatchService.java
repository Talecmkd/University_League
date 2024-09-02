package mk.ukim.finki.wp.liga.service.volleyball;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;

import java.time.LocalDateTime;
import java.util.List;

public interface VolleyballMatchService {
    List<VolleyballMatch> listAllVolleyballMatches();


    VolleyballMatch findById(Long id);


    VolleyballMatch create(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);


    VolleyballMatch update(Long id, VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);


    VolleyballMatch delete(Long id);

    void updateTeamStatistics(VolleyballMatch match);

    VolleyballMatch createAndAddToFixtures(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);
    List<VolleyballMatch> createPlayoffMatches();
    List<VolleyballMatch> listPlayoffMatches();
    VolleyballMatch updatePlayoffMatchPoints(Long id,VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints);
    List<VolleyballMatch> createSemiFinalMatches();
    List<VolleyballMatch> createFinalMatch();


    void updateLiveStats(Long volleyballMatchId, int pointsScored, Long playerId);
}
