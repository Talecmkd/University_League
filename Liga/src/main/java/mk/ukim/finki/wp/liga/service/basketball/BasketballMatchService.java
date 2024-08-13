package mk.ukim.finki.wp.liga.service.basketball;

import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballTeam;

import java.time.LocalDateTime;
import java.util.List;

public interface BasketballMatchService {
    List<BasketballMatch> listAllBasketballMatches();
    BasketballMatch findById(Long id);
    BasketballMatch create(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);
    BasketballMatch update(Long id, BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);
    BasketballMatch delete(Long id);
    void updateTeamStatistics(BasketballMatch match);

    BasketballMatch createAndAddToFixtures(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);
    List<BasketballMatch> createPlayoffMatches();
    List<BasketballMatch> listPlayoffMatches();
    BasketballMatch updatePlayoffMatchPoints(Long id,BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints);
    List<BasketballMatch> createSemiFinalMatches();
    List<BasketballMatch> createFinalMatch();

    void updateLiveStats(Long basketballMatchId, int pointsScored, Long playerId);
}
