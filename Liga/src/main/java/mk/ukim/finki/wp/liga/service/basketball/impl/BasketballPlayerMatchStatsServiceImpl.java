package mk.ukim.finki.wp.liga.service.basketball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.BasketballPlayerMatchStats;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballPlayerException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballPlayerMatchStatsException;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballMatchRepository;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballPlayerMatchStatsRepository;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballPlayerRepository;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerMatchStatsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketballPlayerMatchStatsServiceImpl implements BasketballPlayerMatchStatsService {

    private final BasketballPlayerMatchStatsRepository basketballPlayerMatchStatsRepository;
    private final BasketballPlayerRepository basketballPlayerRepository;
    private final BasketballMatchRepository basketballMatchRepository;

    @Override
    public List<BasketballPlayerMatchStats> listAllPlayerStatsForMatch(BasketballMatch basketballMatch) {
        return basketballPlayerMatchStatsRepository.findByBasketballMatch(basketballMatch);
    }

    public BasketballPlayerMatchStats findByPlayerAndBasketballMatch(BasketballPlayer player, BasketballMatch basketballMatch) {
        return basketballPlayerMatchStatsRepository.findByPlayerAndBasketballMatch(player, basketballMatch);
    }

    @Override
    public BasketballPlayerMatchStats create(BasketballPlayer player, BasketballMatch match, int pointsScored, int assists, int rebounds) {
        BasketballPlayer p = basketballPlayerRepository.findById(player.getBasketball_player_id()).orElseThrow(InvalidBasketballPlayerException::new);
        BasketballPlayerMatchStats playerMatchStats = new BasketballPlayerMatchStats(p, match, pointsScored, assists, rebounds);
        return basketballPlayerMatchStatsRepository.save(playerMatchStats);
    }

    @Override
    public BasketballPlayerMatchStats update(Long id, BasketballPlayer player, BasketballMatch match, int pointsScored, int assists, int rebounds) {
        BasketballPlayerMatchStats playerMatchStats = basketballPlayerMatchStatsRepository.findById(id).orElseThrow(InvalidBasketballPlayerMatchStatsException::new);
        BasketballPlayer p = basketballPlayerRepository.findById(player.getBasketball_player_id()).orElseThrow(InvalidBasketballPlayerException::new);
        BasketballMatch bm = basketballMatchRepository.findById(match.getBasketball_match_id()).orElseThrow(InvalidBasketballPlayerException::new);
        playerMatchStats.setPlayer(p);
        playerMatchStats.setBasketballMatch(bm);
        playerMatchStats.setPointsScored(pointsScored);
        playerMatchStats.setAssists(assists);
        playerMatchStats.setRebounds(rebounds);
        return basketballPlayerMatchStatsRepository.save(playerMatchStats);
    }

    @Override
    public BasketballPlayerMatchStats delete(Long id) {
        BasketballPlayerMatchStats playerMatchStats = basketballPlayerMatchStatsRepository.findById(id).orElseThrow(InvalidBasketballPlayerMatchStatsException::new);
        basketballPlayerMatchStatsRepository.delete(playerMatchStats);
        return playerMatchStats;
    }

    @Override
    public void save(BasketballPlayerMatchStats existingPlayerScored) {
        basketballPlayerMatchStatsRepository.save(existingPlayerScored);
    }
}
