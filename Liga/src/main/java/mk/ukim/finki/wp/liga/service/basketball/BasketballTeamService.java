package mk.ukim.finki.wp.liga.service.basketball;

import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.dtos.BasketballTeamStandings;

import java.util.List;

public interface BasketballTeamService {

    List<BasketballTeam> listAllTeams();
    BasketballTeam findById(Long id);
    BasketballTeam create(String teamName, byte [] logo);
    BasketballTeam update(Long id, String teamName, List<BasketballPlayer> players, byte [] logo);
    BasketballTeam delete(Long id);
    BasketballTeam addFixtures(Long id, List<BasketballMatch> fixtures);
   // BasketballTeam updateStats(Long id);
    BasketballTeam findByName(String teamName);
    BasketballTeam saveTable(Long id, int teamPoints);
    List<BasketballTeam> findAllOrderByPointsDesc();
    void incrementMatchesPlayed(Long teamId);
    void addWin(Long teamId);
    void addLoss(Long teamId);
    //void addDraw(Long teamId);
    void addPoints(Long teamId, int points);
    List<BasketballTeamStandings> getStandings();
}
