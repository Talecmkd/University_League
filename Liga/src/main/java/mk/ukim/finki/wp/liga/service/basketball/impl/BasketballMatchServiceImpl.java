package mk.ukim.finki.wp.liga.service.basketball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.service.basketball.BasketballMatchService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BasketballMatchServiceImpl implements BasketballMatchService {
    @Override
    public List<BasketballMatch> listAllBasketballMatches() {
        return List.of();
    }

    @Override
    public BasketballMatch findById(Long id) {
        return null;
    }

    @Override
    public BasketballMatch create(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        return null;
    }

    @Override
    public BasketballMatch update(Long id, BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        return null;
    }

    @Override
    public BasketballMatch delete(Long id) {
        return null;
    }

    @Override
    public void updateTeamStatistics(BasketballMatch match) {

    }

    @Override
    public BasketballMatch createAndAddToFixtures(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        return null;
    }

    @Override
    public List<BasketballMatch> createPlayoffMatches() {
        return List.of();
    }

    @Override
    public List<BasketballMatch> listPlayoffMatches() {
        return List.of();
    }

    @Override
    public BasketballMatch updatePlayoffMatchPoints(Long id, BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints) {
        return null;
    }

    @Override
    public List<BasketballMatch> createSemiFinalMatches() {
        return List.of();
    }

    @Override
    public List<BasketballMatch> createFinalMatch() {
        return List.of();
    }

    @Override
    public void updateLiveStats(Long basketballMatchId, int pointsScored, Long playerId) {

    }
}
