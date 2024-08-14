package mk.ukim.finki.wp.liga.service.volleyball;

import mk.ukim.finki.wp.liga.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface VolleyballPlayerScoredService {
    List<VolleyballPlayerMatchStats> listAllPlayerStatsForMatch(VolleyballMatch volleyballMatch);

    VolleyballPlayerMatchStats findPlayerStatsByIdAndMatch(Long id, VolleyballMatch volleyballMatch);

    VolleyballPlayerMatchStats create(VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks);

    VolleyballPlayerMatchStats update(Long id, VolleyballPlayer player, VolleyballMatch match, int pointsScored, int assists, int servings, int blocks);

    VolleyballPlayerMatchStats delete(Long id);

    VolleyballPlayerMatchStats findPlayerStatsByPlayerId(Long id);
}
