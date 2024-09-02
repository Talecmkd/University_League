package mk.ukim.finki.wp.liga.service.volleyball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballPlayerWhoScoredException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballPlayerException;
import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballPlayerMatchStats;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballPlayerMatchStatsRepository;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerMatchStatsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VolleyballPlayerMatchStatsServiceImpl implements VolleyballPlayerMatchStatsService {

    private final VolleyballPlayerMatchStatsRepository volleyballPlayerMatchStatsRepository;

    @Override
    public List<VolleyballPlayerMatchStats> listAllPlayerStatsForMatch(VolleyballMatch volleyballMatch) {
        return volleyballPlayerMatchStatsRepository.findByVolleyballMatch(volleyballMatch);
    }

    @Override
    public VolleyballPlayerMatchStats findPlayerStatsByIdAndMatch(Long id, VolleyballMatch volleyballMatch) {
        return volleyballPlayerMatchStatsRepository.findByIdAndVolleyballMatch(id,volleyballMatch);
    }

    @Override
    public VolleyballPlayerMatchStats create(VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks) {
        VolleyballPlayerMatchStats matchStats = new VolleyballPlayerMatchStats(player, match, pointsScored, assists, servings, blocks);
        return volleyballPlayerMatchStatsRepository.save(matchStats);
    }

    @Override
    public VolleyballPlayerMatchStats update(Long id, VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks) {
        VolleyballPlayerMatchStats matchStats = volleyballPlayerMatchStatsRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
        matchStats.setPlayer(player);
        matchStats.setVolleyballMatch(match);
        matchStats.setScoredPoints(pointsScored);
        matchStats.setAssists(assists);
        matchStats.setBlocks(blocks);
        matchStats.setServings(servings);
        return volleyballPlayerMatchStatsRepository.save(matchStats);
    }

    @Override
    public VolleyballPlayerMatchStats delete(Long id) {
        VolleyballPlayerMatchStats matchStats = volleyballPlayerMatchStatsRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
        volleyballPlayerMatchStatsRepository.delete(matchStats);
        return matchStats;
    }

    @Override
    public void save(VolleyballPlayerMatchStats existingPlayerScored) {
        volleyballPlayerMatchStatsRepository.save(existingPlayerScored);

    }

    @Override
    public VolleyballPlayerMatchStats findPlayerStatsByPlayerId(Long id) {
        return volleyballPlayerMatchStatsRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
    }
    public VolleyballPlayerMatchStats findByPlayerAndVolleyballMatch(VolleyballPlayer player, VolleyballMatch volleyballMatch) {
        return volleyballPlayerMatchStatsRepository.findByPlayerAndVolleyballMatch(player, volleyballMatch);
    }
}
