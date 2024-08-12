package mk.ukim.finki.wp.liga.service.football;

import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayerScored;
import mk.ukim.finki.wp.liga.model.FootballTeam;

import java.time.LocalDateTime;
import java.util.List;

public interface FootballMatchService {
    List<FootballMatch> listAllFootballMatches();


    FootballMatch findById(Long id);


    FootballMatch create(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);


    FootballMatch update(Long id, FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);


    FootballMatch delete(Long id);

    void updateTeamStatistics(FootballMatch match);

    FootballMatch createAndAddToFixtures(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime);
    List<FootballMatch> createPlayoffMatches();
    List<FootballMatch> listPlayoffMatches();
    FootballMatch updatePlayoffMatchPoints(Long id,FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints);
    List<FootballMatch> createSemiFinalMatches();


}
