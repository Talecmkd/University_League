package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballTeamServiceImpl implements FootballTeamService {
    private final FootballTeamRepository footballTeamRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballMatchRepository footballMatchRepository;

    @Override
    public List<FootballTeam> listAllTeams() {
        return footballTeamRepository.findAll();
    }

    @Override
    public FootballTeam findById(Long id) {
        return footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
    }
    @Override
    public FootballTeam create(String teamName, List<FootballPlayer> players, byte [] logo) {
        FootballTeam team = new FootballTeam(teamName, players, logo);
        return footballTeamRepository.save(team);
    }

    public FootballTeam update(Long id, String teamName, List<FootballPlayer> players, byte [] logo) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        team.setTeamName(teamName);
        team.setPlayers(players);
        team.setLogo(logo);
        return footballTeamRepository.save(team);
    }

    @Override
    public FootballTeam delete(Long id) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        footballTeamRepository.delete(team);
        return team;
    }

    @Override
    public FootballTeam addFixtures(Long id, List<FootballMatch> fixtures) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);

        team.getFootballFixtures().addAll(fixtures);
        return footballTeamRepository.save(team);
    }

    @Override
   public FootballTeam updateStats(Long id) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        int wins = 0;
        int loses = 0;
        int draws = 0;
        int leaguePoints = 0;

        for (FootballMatch match : team.getFootballResults()) {
            if (match.getEndTime().isBefore(java.time.LocalDateTime.now())) { // Match has been played
                boolean isHomeTeam = match.getHomeTeam().equals(team);
                int teamPoints = isHomeTeam ? match.getHomeTeamPoints() : match.getAwayTeamPoints();
                int opponentPoints = isHomeTeam ? match.getAwayTeamPoints() : match.getHomeTeamPoints();

                if (teamPoints > opponentPoints) {
                    wins++;
                    leaguePoints += 3; // Assuming a win gives 3 points
                } else if (teamPoints < opponentPoints) {
                    loses++;
                } else {
                    draws++;
                    leaguePoints += 1; // Assuming a draw gives 1 point
                }
            }
        }

        team.setTeamMatchesPlayed(wins + loses + draws);
        team.setTeamWins(wins);
        team.setTeamLoses(loses);
        team.setTeamDraws(draws);
        team.setTeamLeaguePoints(leaguePoints);

        return footballTeamRepository.save(team);
    }

    @Override
    public FootballTeam findByName(String teamName) {
        return footballTeamRepository.findFootballTeamByTeamName(teamName).get(0);
    }

}
