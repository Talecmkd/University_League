package mk.ukim.finki.wp.liga.service.basketball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballTeamException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballTeamRepository;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketballTeamServiceImpl implements BasketballTeamService {

    private final BasketballTeamRepository basketballTeamRepository;


    @Override
    @Transactional(readOnly = true)
    public List<BasketballTeam> listAllTeams() {
        return basketballTeamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BasketballTeam findById(Long id) {
        return basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);
    }

    @Override
    @Transactional
    public BasketballTeam create(String teamName, byte[] logo) {
        BasketballTeam team = new BasketballTeam(teamName, logo);
        return basketballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public BasketballTeam update(Long id, String teamName, List<BasketballPlayer> players, byte[] logo) {
        BasketballTeam team = basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);
        team.setTeamName(teamName);
        team.setPlayers(players);
        team.setLogo(logo);
        return basketballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public BasketballTeam delete(Long id) {
        BasketballTeam team = basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);
        basketballTeamRepository.delete(team);
        return team;
    }

    @Override
    @Transactional
    public BasketballTeam addFixtures(Long id, List<BasketballMatch> fixtures) {
        BasketballTeam team = basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);

        team.getBasketballFixtures().addAll(fixtures);
        return basketballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public BasketballTeam updateStats(Long id) {
        BasketballTeam team = basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);
        int wins = 0;
        int loses = 0;
        int leaguePoints = 0;

        for (BasketballMatch match : team.getBasketballResults()) {
            if (match.getEndTime().isBefore(java.time.LocalDateTime.now())) { // Match has been played
                boolean isHomeTeam = match.getHomeTeam().equals(team);
                int teamPoints = isHomeTeam ? match.getHomeTeamPoints() : match.getAwayTeamPoints();
                int opponentPoints = isHomeTeam ? match.getAwayTeamPoints() : match.getHomeTeamPoints();

                if (teamPoints > opponentPoints) {
                    wins++;
                    leaguePoints += 3; // Assuming a win gives 3 points
                } else {
                    loses++;
                }
            }
        }

        team.setTeamMatchesPlayed(wins + loses);
        team.setTeamWins(wins);
        team.setTeamLoses(loses);
        team.setTeamLeaguePoints(leaguePoints);

        return basketballTeamRepository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public BasketballTeam findByName(String teamName) {
        return basketballTeamRepository.findBasketballTeamByTeamName(teamName).get(0);
    }

    @Override
    @Transactional
    public BasketballTeam saveTable(Long id, int teamPoints) {
        BasketballTeam bt = basketballTeamRepository.findById(id).orElseThrow(InvalidBasketballTeamException::new);
        bt.setTeamLeaguePoints(teamPoints);
        return basketballTeamRepository.save(bt);
    }

    @Override
    @Transactional
    public List<BasketballTeam> findAllOrderByPointsDesc() {
        return basketballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();
    }
}
