package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.dtos.TeamStandingsDTO;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FootballTeamServiceImpl implements FootballTeamService {
    private final FootballTeamRepository footballTeamRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballMatchRepository footballMatchRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FootballTeam> listAllTeams() {
        return footballTeamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FootballTeam findById(Long id) {
        return footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
    }
    @Override
    @Transactional
    public FootballTeam create(String teamName, byte [] logo) {
        FootballTeam team = new FootballTeam(teamName, logo);
        return footballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public FootballTeam update(Long id, String teamName, List<FootballPlayer> players, byte [] logo) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        team.setTeamName(teamName);
        team.setPlayers(players);
        team.setLogo(logo);
        return footballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public FootballTeam delete(Long id) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        footballTeamRepository.delete(team);
//        List<FootballMatch> matchesToDelete = footballMatchRepository.findAllByHomeTeamOrAwayTeam(team, team);
//        footballMatchRepository.deleteAll(matchesToDelete);
        return team;
    }

    @Override
    @Transactional
    public FootballTeam addFixtures(Long id, List<FootballMatch> fixtures) {
        FootballTeam team = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);

        team.getFootballFixtures().addAll(fixtures);
        return footballTeamRepository.save(team);
    }

    @Override
    @Transactional
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
                    leaguePoints += 3;
                } else if (teamPoints < opponentPoints) {
                    loses++;
                } else {
                    draws++;
                    leaguePoints += 1;
                }
            }
        }

        team.setTeamMatchesPlayed(wins + loses + draws);
        team.setTeamWins(wins);
        team.setTeamLoses(loses);
        team.setTeamDraws(draws);
        team.setTeamLeaguePoints(leaguePoints);

        // Removed lines that set goalsFor and goalsAgainst

        return footballTeamRepository.save(team);
    }


    @Override
    @Transactional(readOnly = true)
    public FootballTeam findByName(String teamName) {
        return footballTeamRepository.findFootballTeamByTeamName(teamName).get(0);
    }

    @Override
    @Transactional
    public FootballTeam saveTable(Long id, int teamPoints) {
        FootballTeam ft = footballTeamRepository.findById(id).orElseThrow(InvalidFootballTeamException::new);
        ft.setTeamLeaguePoints(teamPoints);
        return footballTeamRepository.save(ft);
    }
    @Override
    @Transactional
    public List<FootballTeam> findAllOrderByPointsDesc() {
        return footballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();
    }
    @Override
    @Transactional
    public List<TeamStandingsDTO> getStandings() {
        List<FootballTeam> teams = footballTeamRepository.findAll();

        return teams.stream()
                .map(team -> new TeamStandingsDTO(
                        team.getTeamName(),
                        team.getTeamMatchesPlayed(),
                        team.getTeamWins(),
                        team.getTeamDraws(),
                        team.getTeamLoses(),
                        team.getTeamLeaguePoints(),
                        team.getGoalsFor(),
                        team.getGoalsAgainst(),
                        team.getGoalDifference(),
                        team.getLastFiveMatches()))
                .sorted(Comparator.comparingInt(TeamStandingsDTO::getPoints).reversed())
                .collect(Collectors.toList());
    }


    //
    //    @Override
    //    public List<String> getLastFiveMatchesForm(FootballTeam team) {
    //        return team.getFootballResults().stream()
    //                .sorted(Comparator.comparing(FootballMatch::getStartTime).reversed())
    //                .limit(5)
    //                .map(match -> {
    //                    if (match.getHomeTeam().equals(team)) {
    //                        return match.getHomeTeamPoints() > match.getAwayTeamPoints() ? "W" :
    //                                (match.getHomeTeamPoints() < match.getAwayTeamPoints() ? "L" : "D");
    //                    } else {
    //                        return match.getAwayTeamPoints() > match.getHomeTeamPoints() ? "W" :
    //                                (match.getAwayTeamPoints() < match.getHomeTeamPoints() ? "L" : "D");
    //                    }
    //                })
    //                .collect(Collectors.toList());
    //    }
    @Override
    @Transactional
    public void incrementMatchesPlayed(Long teamId) {
        FootballTeam team = footballTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));
        team.setTeamMatchesPlayed(team.getTeamMatchesPlayed() + 1);
        footballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public void addWin(Long teamId) {
        FootballTeam team = footballTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));

        team.setTeamWins(team.getTeamWins() + 1);
        footballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public void addLoss(Long teamId) {
        FootballTeam team = footballTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));

        team.setTeamLoses(team.getTeamLoses() + 1);
        footballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public void addDraw(Long teamId) {
        FootballTeam team = footballTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));

        team.setTeamDraws(team.getTeamDraws() + 1);
        footballTeamRepository.save(team);
    }
    @Override
    @Transactional
    public void addPoints(Long teamId, int points) {
        FootballTeam team = footballTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));

        team.setTeamLeaguePoints(team.getTeamLeaguePoints() + points);
        footballTeamRepository.save(team);
    }

}
