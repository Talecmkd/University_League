package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballPlayerWhoScoredException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballPlayerScored;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerScoredRepository;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerScoredService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballPlayerScoredServiceImpl implements FootballPlayerScoredService {

    private final FootballPlayerScoredRepository footballPlayerScoredRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballMatchRepository footballMatchRepository;

    @Override
    public List<FootballPlayerScored> listAllPlayersWhoScored() {
        return footballPlayerScoredRepository.findAll();
    }

    @Override
    public FootballPlayerScored findById(Long id) {
        return footballPlayerScoredRepository.findById(id).orElseThrow(InvalidFootballPlayerWhoScoredException::new);
    }

    @Override
    public FootballPlayerScored create(FootballPlayer player, LocalDateTime timeScored, FootballMatch match) {
        FootballPlayerScored playerScored = new FootballPlayerScored(player, timeScored, match);
        return footballPlayerScoredRepository.save(playerScored);
    }

    @Override
    public FootballPlayerScored update(Long id, FootballPlayer player, LocalDateTime timeScored, FootballMatch match) {
        FootballPlayerScored playerScored = footballPlayerScoredRepository.findById(id).orElseThrow(InvalidFootballPlayerWhoScoredException::new);
        playerScored.setPlayer(player);
        playerScored.setTimeScored(timeScored);
        playerScored.setFootballMatch(match);
        return footballPlayerScoredRepository.save(playerScored);
    }

    @Override
    public FootballPlayerScored delete(Long id) {
        FootballPlayerScored playerScored = footballPlayerScoredRepository.findById(id).orElseThrow(InvalidFootballPlayerWhoScoredException::new);
        footballPlayerScoredRepository.delete(playerScored);
        return playerScored;
    }


}
