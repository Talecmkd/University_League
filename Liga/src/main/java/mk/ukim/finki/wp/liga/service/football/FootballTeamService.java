package mk.ukim.finki.wp.liga.service.football;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.dtos.TeamStandingsDTO;

import java.awt.*;
import java.util.Date;
import java.util.List;

public interface FootballTeamService {
    List<FootballTeam> listAllTeams();


    FootballTeam findById(Long id);


    FootballTeam create(String teamName, byte [] logo);


    FootballTeam update(Long id, String teamName, List<FootballPlayer> players, byte [] logo);
    FootballTeam update(Long id, String teamName, byte [] logo);
    FootballTeam delete(Long id);

    FootballTeam addFixtures(Long id, List<FootballMatch> fixtures);

    FootballTeam updateStats(Long id);

    FootballTeam findByName(String teamName);

    FootballTeam saveTable(Long id, String teamName);
    List<FootballTeam> findAllOrderByPointsDesc();
    List<TeamStandingsDTO> getStandings();
//  List<String> getLastFiveMatchesForm(FootballTeam team);
    void incrementMatchesPlayed(Long teamId);
    void addWin(Long teamId);
    void addLoss(Long teamId);
    void addDraw(Long teamId);
    void addPoints(Long teamId, int points);

}
