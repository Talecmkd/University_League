package mk.ukim.finki.wp.liga.service.basketball;

import mk.ukim.finki.wp.liga.model.BasketballMatch;
import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.BasketballPlayerMatchStats;

import java.util.List;

public interface BasketballPlayerMatchStatsService {

    List<BasketballPlayerMatchStats> listAllPlayerStatsForMatch(BasketballMatch basketballMatch);

    BasketballPlayerMatchStats findPlayerStatsByIdAndMatch(Long id, BasketballMatch basketballMatch);

    BasketballPlayerMatchStats create(BasketballPlayer player, BasketballMatch match, int pointsScored, int assists, int rebounds);

    BasketballPlayerMatchStats update(Long id, BasketballPlayer player, BasketballMatch match, int pointsScored, int assists, int rebounds);

    BasketballPlayerMatchStats delete(Long id);
}
