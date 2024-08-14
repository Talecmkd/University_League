package mk.ukim.finki.wp.liga.service.volleyball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballPlayerWhoScoredException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballPlayerException;
import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballPlayerMatchStats;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballPlayerScoredRepository;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerScoredService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VolleyballPlayerScoredServiceImpl implements VolleyballPlayerScoredService {

    private final VolleyballPlayerScoredRepository volleyballPlayerScoredRepository;

    @Override
    public List<VolleyballPlayerMatchStats> listAllPlayerStatsForMatch(VolleyballMatch volleyballMatch) {
        return volleyballPlayerScoredRepository.findByVolleyballMatch(volleyballMatch);
    }

    @Override
    public VolleyballPlayerMatchStats findPlayerStatsByIdAndMatch(Long id, VolleyballMatch volleyballMatch) {
        return volleyballPlayerScoredRepository.findByIdAndVolleyballMatch(id,volleyballMatch);
    }

    @Override
    public VolleyballPlayerMatchStats create(VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks) {
        VolleyballPlayerMatchStats matchStats = new VolleyballPlayerMatchStats(player, match, pointsScored, assists, servings, blocks);
        return volleyballPlayerScoredRepository.save(matchStats);
    }

    @Override
    public VolleyballPlayerMatchStats update(Long id, VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks) {
        VolleyballPlayerMatchStats matchStats = volleyballPlayerScoredRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
        matchStats.setPlayer(player);
        matchStats.setVolleyballMatch(match);
        matchStats.setScoredPoints(pointsScored);
        matchStats.setAssists(assists);
        matchStats.setBlocks(blocks);
        matchStats.setServings(servings);
        return volleyballPlayerScoredRepository.save(matchStats);
    }

    @Override
    public VolleyballPlayerMatchStats delete(Long id) {
        VolleyballPlayerMatchStats matchStats = volleyballPlayerScoredRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
        volleyballPlayerScoredRepository.delete(matchStats);
        return matchStats;
    }

    @Override
    public VolleyballPlayerMatchStats findPlayerStatsByPlayerId(Long id) {
        return volleyballPlayerScoredRepository.findById(id).orElseThrow(InvalidFootballPlayerWhoScoredException::new);
    }
}
