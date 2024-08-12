package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballMatchException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballMatchServiceImpl implements FootballMatchService {
    private final FootballMatchRepository matchRepository;
    private final FootballTeamRepository teamRepository;
    private final FootballTeamService teamService;


    @Override
    @Transactional(readOnly = true)
    public List<FootballMatch> listAllFootballMatches() {
        return matchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FootballMatch findById(Long id) {
        return matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
    }

    @Override
    @Transactional
    public FootballMatch create(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballTeam home = teamRepository.findById(homeTeam.getId()).orElseThrow(InvalidFootballTeamException::new);
        FootballTeam away = teamRepository.findById(awayTeam.getId()).orElseThrow(InvalidFootballMatchException::new);
        FootballMatch fm = new FootballMatch(home, away, homeTeamPoints, awayTeamPoints, startTime, false);
        return matchRepository.save(fm);
    }

    @Override
    @Transactional
    public FootballMatch update(Long id, FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballTeam home = teamRepository.findById(homeTeam.getId()).orElseThrow(InvalidFootballTeamException::new);
        FootballTeam away = teamRepository.findById(awayTeam.getId()).orElseThrow(InvalidFootballMatchException::new);
        FootballMatch fm = matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
        fm.setHomeTeam(home);
        fm.setAwayTeam(away);
        fm.setHomeTeamPoints(homeTeamPoints);
        fm.setAwayTeamPoints(awayTeamPoints);
        fm.setStartTime(startTime);
        return matchRepository.save(fm);
    }

    @Override
    @Transactional
    public FootballMatch delete(Long id) {
        FootballMatch fm = matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
        matchRepository.delete(fm);
        return fm;
    }

    @Override
    @Transactional
    public void updateTeamStatistics(FootballMatch match) {
        teamService.updateStats(match.getHomeTeam().getId());
        teamService.updateStats(match.getAwayTeam().getId());
    }

    @Override
    @Transactional
    public FootballMatch createAndAddToFixtures(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballMatch match = this.create(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, startTime);

        if(startTime.isAfter(LocalDateTime.now())) {
            homeTeam.getFootballFixtures().add(match);
            awayTeam.getFootballFixtures().add(match);
        } else if(startTime.isBefore(LocalDateTime.now())){
            homeTeam.getFootballResults().add(match);
            awayTeam.getFootballResults().add(match);
        }


        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);

        return match;
    }
    @Override
    @Transactional
    public List<FootballMatch> createPlayoffMatches() {
        List<FootballTeam> teams = teamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs");
        }

        // Clear existing playoff matches if needed
        matchRepository.deleteAllByIsPlayoffMatchTrue();

        // Create the quarter-final matches
        List<FootballMatch> matches = new ArrayList<>();
        matches.add(createMatch(teams.get(0), teams.get(7), true)); // 1 vs 8
        matches.add(createMatch(teams.get(1), teams.get(6), true)); // 2 vs 7
        matches.add(createMatch(teams.get(2), teams.get(5), true)); // 3 vs 6
        matches.add(createMatch(teams.get(3), teams.get(4), true)); // 4 vs 5

        return matches;
    }

    private FootballMatch createMatch(FootballTeam homeTeam, FootballTeam awayTeam, boolean isPlayoffMatch) {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1); // Set the start time for the match
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, 0, 0, startTime, isPlayoffMatch);
        return matchRepository.save(match);
    }
    @Override
    //@Transactional(readOnly = true)
    public List<FootballMatch> listPlayoffMatches() {
        List<FootballTeam> teams = teamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs");
        }

        // Fetch all matches involving the top 8 teams
        return matchRepository.findAllByIsPlayoffMatchTrue();
    }
    @Override
    @Transactional
    public FootballMatch updatePlayoffMatchPoints(Long id, FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints) {
        FootballMatch match = matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setHomeTeamPoints(homeTeamPoints);
        match.setAwayTeamPoints(awayTeamPoints);
        return matchRepository.save(match);
    }
    private FootballTeam getWinner(FootballMatch match) {
        if (match.getHomeTeamPoints() > match.getAwayTeamPoints()) {
            return match.getHomeTeam();
        } else if (match.getAwayTeamPoints() > match.getHomeTeamPoints()) {
            return match.getAwayTeam();
        } else {
            throw new RuntimeException("Match cannot end in a draw in playoffs");
        }
    }
    private boolean isMatchCompleted(FootballMatch match) {
        return match.getHomeTeamPoints() > 0 && match.getAwayTeamPoints() > 0;
    }


    @Override
    @Transactional
    public List<FootballMatch> createSemiFinalMatches() {
        // Fetch all completed quarter-final matches
        List<FootballMatch> quarterFinals = matchRepository.findAllByIsPlayoffMatchTrue();

        if (quarterFinals.size() != 4 || quarterFinals.stream().anyMatch(match -> !match.isPlayoffMatch())) {
            throw new RuntimeException("Quarter-finals are not fully completed yet.");
        }

        // Proceed with creating semi-finals based on the winners
        FootballTeam semiFinalTeam1 = getWinner(quarterFinals.get(0));
        FootballTeam semiFinalTeam2 = getWinner(quarterFinals.get(1));
        FootballTeam semiFinalTeam3 = getWinner(quarterFinals.get(2));
        FootballTeam semiFinalTeam4 = getWinner(quarterFinals.get(3));

        List<FootballMatch> semiFinals = new ArrayList<>();
        semiFinals.add(createMatch(semiFinalTeam1, semiFinalTeam4, true)); // Semi-final 1
        semiFinals.add(createMatch(semiFinalTeam2, semiFinalTeam3, true)); // Semi-final 2

        return semiFinals;
    }

}
