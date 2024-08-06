package mk.ukim.finki.wp.liga.service.football;


import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.Playoff;
import mk.ukim.finki.wp.liga.model.PlayoffMatch;
import mk.ukim.finki.wp.liga.repository.football.PlayoffMatchRepository;

import java.util.List;

public interface FootballPlayoffsService {
    Playoff createPlayoff();
    Playoff getCurrentPlayoff();
    PlayoffMatch createMatch (Long matchId, int homeTeamPoints, int awayTeamPoints);
    List<PlayoffMatch> getPlayoffMatches(Long playoffId);
}
