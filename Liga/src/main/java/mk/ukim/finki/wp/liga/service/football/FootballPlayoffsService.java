package mk.ukim.finki.wp.liga.service.football;


import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.repository.football.PlayoffMatchRepository;

import java.util.List;

public interface FootballPlayoffsService {
    Playoff createPlayoff();
    void completeMatch(Long matchId, int homeTeamPoints, int awayTeamPoints);
    Playoff getPlayoff();
    PlayoffMatch getMatchById(Long matchId);
}
