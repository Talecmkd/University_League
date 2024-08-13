package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballMatchException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerRepository;
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
    private final FootballPlayerRepository playerRepository;
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
        if(fm.getEndTime().isBefore(LocalDateTime.now())){
            fm.getHomeTeam().getFootballResults().remove(fm);
            fm.getAwayTeam().getFootballResults().remove(fm);
        }
        else {
            fm.getHomeTeam().getFootballFixtures().remove(fm);
            fm.getAwayTeam().getFootballFixtures().remove(fm);
        }
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

        if(startTime.plusHours(2).isAfter(LocalDateTime.now())) {
            homeTeam.getFootballFixtures().add(match);
            awayTeam.getFootballFixtures().add(match);
        } else if(startTime.plusHours(2).isBefore(LocalDateTime.now())){
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
        List<FootballMatch> existingPlayoffMatches = matchRepository.findAllByIsPlayoffMatchTrue();

        // If matches exist, don't reinitialize, just return the existing ones
        if (!existingPlayoffMatches.isEmpty()) {
            return existingPlayoffMatches;
        }
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
        return match.getHomeTeamPoints() != match.getAwayTeamPoints();
    }


    @Override
    @Transactional
    public List<FootballMatch> createSemiFinalMatches() {
        List<FootballMatch> playoffMatches = matchRepository.findAllByIsPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 6) {
            //System.out.println("Semi-final matches have already been created.");
            return playoffMatches.subList(4, 6); // Return existing final match
        }
        List<FootballMatch> quarterFinals = matchRepository.findAllByIsPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (quarterFinals.size() != 4 || quarterFinals.stream().anyMatch(match -> !isMatchCompleted(match))) {
            throw new RuntimeException("Quarter-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        FootballTeam semiFinalTeam1 = getWinner(quarterFinals.get(0));
        FootballTeam semiFinalTeam2 = getWinner(quarterFinals.get(1));
        FootballTeam semiFinalTeam3 = getWinner(quarterFinals.get(2));
        FootballTeam semiFinalTeam4 = getWinner(quarterFinals.get(3));

        // Create semi-final matches
        List<FootballMatch> semiFinals = new ArrayList<>();
        semiFinals.add(createMatch(semiFinalTeam1, semiFinalTeam4, true));
        semiFinals.add(createMatch(semiFinalTeam2, semiFinalTeam3, true));

        return semiFinals;
    }
    @Override
    @Transactional
    public List<FootballMatch> createFinalMatch()
    {
        List<FootballMatch> playoffMatches = matchRepository.findAllByIsPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 7) {
            //System.out.println("The final match has already been created.");
            return playoffMatches.subList(6, 7); // Return existing final match
        }
        List<FootballMatch> semiFinals = matchRepository.findAllByIsPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (semiFinals.size() < 6) {
            throw new RuntimeException("There are not enough matches to have semi-finals and finals.");
        }
        FootballMatch semiFinal1 = semiFinals.get(semiFinals.size() - 2);
        FootballMatch semiFinal2 = semiFinals.get(semiFinals.size() - 1);

        // Ensure both semi-final matches are completed
        if (!isMatchCompleted(semiFinal1) || !isMatchCompleted(semiFinal2)) {
            throw new RuntimeException("Semi-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        FootballTeam finalist1 = getWinner(semiFinal1);
        FootballTeam finalist2 = getWinner(semiFinal2);

        // Create the final match
        List<FootballMatch> finals = new ArrayList<>();
        finals.add(createMatch(finalist1, finalist2, true));

        return finals;
    }

    @Override
    public void updateLiveStats(Long footballMatchId, int goalsScored, Long playerId) {
        FootballPlayer player = playerRepository.getReferenceById(playerId);
        FootballMatch match = matchRepository.findById(footballMatchId).get();
        FootballTeam team = teamRepository.findAll().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();

        FootballTeam homeOrAway = match.getHomeTeam();

        if (team != homeOrAway) {
            match.setAwayTeamPoints(goalsScored);
        } else {
            match.setHomeTeamPoints(goalsScored);
        }

        matchRepository.save(match);
    }
}
