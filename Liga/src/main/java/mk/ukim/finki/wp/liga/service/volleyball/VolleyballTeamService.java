package mk.ukim.finki.wp.liga.service.volleyball;

import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.dtos.VolleyBallStandings;

import java.util.List;

public interface VolleyballTeamService {

    List<VolleyballTeam> listAllTeams();


    VolleyballTeam findById(Long id);


    VolleyballTeam create(String teamName, byte [] logo);


    VolleyballTeam update(Long id, String teamName, List<VolleyballPlayer> players, byte [] logo);


    VolleyballTeam delete(Long id);

    VolleyballTeam addFixtures(Long id, List<VolleyballMatch> fixtures);

    VolleyballTeam updateStats(Long id);

    VolleyballTeam findByName(String teamName);

    VolleyballTeam saveTable(Long id, String teamName, byte [] logo);
    List<VolleyballTeam> findAllOrderByPointsDesc();
    List<VolleyBallStandings> getStandings();
    void incrementMatchesPlayed(Long teamId);
    void addWin(Long teamId);
    void addLoss(Long teamId);
    void addPoints(Long teamId, int points);
}
