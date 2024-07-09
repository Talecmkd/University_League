package mk.ukim.finki.wp.liga.service.football;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;

import java.awt.*;
import java.util.Date;
import java.util.List;

public interface FootballTeamService {
    List<FootballTeam> listAllTeams();


    FootballTeam findById(Long id);


    FootballTeam create(String teamName, List<FootballPlayer> players, Image logo);


    FootballTeam update(Long id, String teamName, List<FootballPlayer> players, Image logo);


    FootballTeam delete(Long id);

    FootballTeam addFixtures(Long id, List<FootballMatch> fixtures);

    FootballTeam updateStats(Long id);




}
